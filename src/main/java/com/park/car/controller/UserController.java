package com.park.car.controller;

import com.park.car.model.AccountModel;
import com.park.car.model.ResponseModel;
import com.park.car.model.TicketModel;
import com.park.car.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public UserModel info(@RequestParam(value = "email", required = true) String email) {
        UserModel userModel = new UserModel();
        AccountModel accountModel;
        List<TicketModel> ticketModelList = new ArrayList<TicketModel>();

        String sql = "select * from ticket where user_id=(select id from user where email='" + email + "')";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                TicketModel ticketModel = new TicketModel(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));

                String sqlDurationSeconds = "select time_to_sec(duration) from ticket where id =" + ticketModel.getId();
                PreparedStatement psDurationSeconds = connection.prepareStatement(sqlDurationSeconds);
                ResultSet resultSetDurationSeconds = psDurationSeconds.executeQuery();
                if (resultSetDurationSeconds.next()) {
                    ticketModel.setDurationSeconds(resultSetDurationSeconds.getLong(1));
                }

                resultSetDurationSeconds.close();
                psDurationSeconds.close();
                ticketModelList.add(ticketModel);
            }
            resultSet.close();
            ps.close();

            String sqlI = "select id from user where email='" + email + "' AND sysdate() between validfrom and validto";
            PreparedStatement psI = connection.prepareStatement(sqlI);
            ResultSet resultSetI = psI.executeQuery();
            if (resultSetI.next()) {
                userModel.setId(resultSetI.getInt(1));
            }
            resultSetI.close();
            psI.close();

            DatabaseController controller = new DatabaseController();
            controller.setDataSource(dataSource);
            accountModel = controller.accountInformation(email);

            userModel.setEmail(email);
            userModel.setTicketModelList(ticketModelList);
            userModel.setAccountModel(accountModel);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return userModel;
    }

    @RequestMapping(value = "/shinfo", method = RequestMethod.POST)
    @ResponseBody
    public UserModel shortInfo(@RequestParam(value = "email", required = true) String email) {
        UserModel userModel = new UserModel();
        AccountModel accountModel = new AccountModel();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            String sqlI = "select id from user where email='" + email + "' AND sysdate() between validfrom and validto";
            PreparedStatement psI = connection.prepareStatement(sqlI);
            ResultSet resultSetI = psI.executeQuery();
            if (resultSetI.next()) {
                userModel.setId(resultSetI.getInt(1));
            }
            resultSetI.close();
            psI.close();

            String sqlA = "select amount from budget where user_id=(SELECT id FROM user where email='" + email + "' AND sysdate() between validfrom and validto )";
            PreparedStatement psA = connection.prepareStatement(sqlA);
            ResultSet resultSetA = psA.executeQuery();
            if (resultSetA.next()){
                accountModel.setAmount(resultSetA.getDouble(1));
            }
            resultSetA.close();
            psA.close();

            userModel.setEmail(email);
            userModel.setAccountModel(accountModel);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return userModel;
    }

    @RequestMapping(value = "/addmoney", method = RequestMethod.POST)
    @ResponseBody
    public String addCash(@RequestParam(value = "email", required = true) String email,
                          @RequestParam(value = "amount", required = true) String amount) {

        String userid = "SELECT id FROM user where email='" + email + "' AND sysdate() between validfrom and validto ";
        String sql = "call addcash((" + userid + ")," + amount + ");";
        String result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(result);
        return result;
    }

    @RequestMapping(value = "/getallusers", method = RequestMethod.GET)
    @ResponseBody
    public List<UserModel> getAllUsers(){
        String sql = "SELECT * FROM user where sysdate() between validfrom and validto";
        Connection connection = null;
        AccountModel accountModel = new AccountModel();
        List<UserModel> userModelList = new ArrayList<UserModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                String sqlA = "select amount from budget where user_id=(SELECT id FROM user where email='" + resultSet.getString(2) + "' AND sysdate() between validfrom and validto )";
                PreparedStatement psA = connection.prepareStatement(sqlA);
                ResultSet resultSetA = psA.executeQuery();
                if (resultSetA.next()){
                    accountModel.setAmount(resultSetA.getDouble(1));
                    userModelList.add(new UserModel(resultSet.getInt(1), resultSet.getString(2), accountModel));
                }
                resultSetA.close();
                psA.close();

            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return userModelList;
    }
}
