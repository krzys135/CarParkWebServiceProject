package com.springapp.mvc;
import com.park.car.controller.DatabaseController;
import com.park.car.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

/**
 * Handles and retrieves the main requests
 */
@Controller
@RequestMapping("/main/ajax")
public class AjaxController {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @RequestMapping(value = "/add1", method = RequestMethod.GET)
    public String getAjaxAddPage() {


        // This will resolve to /WEB-INF/jsp/ajax-add-page.jsp
        return "ajax-add-page";
    }

    /**
     * Handles request for adding two numbers
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody  List<SpaceModel> add(@RequestParam(value="floor", required=true) String floor,
                                  Model model) {

        String sql = "select * from space where segment_id in (Select id from segment where floor_id = (SELECT id FROM floor where floornumer="+floor+")) order by segment_id, place";
        Connection connection = null;
        List<SpaceModel> list = new ArrayList<SpaceModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new SpaceModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5)));
            }
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
        //model.addAttribute("fromjs",list);
        return list;

    }


    @RequestMapping(value = "/getSegments", method = RequestMethod.POST)
    public @ResponseBody  List<SegmentModel> getSegments(Model model) {

        String sql = "select * from segment";
        Connection connection = null;
        List<SegmentModel> list = new ArrayList<SegmentModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new SegmentModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
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
        return list;

    }
    @RequestMapping(value = "/getFloors", method = RequestMethod.POST)
    public @ResponseBody  List<FloorModel> getFloors(Model model) {

        String sql = "select * from floor";
        Connection connection = null;
        List<FloorModel> list = new ArrayList<FloorModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new FloorModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
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
        return list;

    }


    @RequestMapping(value = "/getSpaceHistory", method = RequestMethod.POST)
    public @ResponseBody  List<SpacelogModel> getSpaceHistory(@RequestParam(value="space", required=false) int space,Model model) {

        String sql = "";
        if(space != -1){
            sql="select * from spacelog where space_id = "+space+" order by date desc";
        } else {
            sql="select * from spacelog";
        }

        Connection connection = null;
        List<SpacelogModel> list = new ArrayList<SpacelogModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new SpacelogModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5),resultSet.getInt(6),resultSet.getInt(7),resultSet.getInt(8)));
            }
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
        return list;

    }


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public @ResponseBody UserModel getUserInfo(@RequestParam(value="id", required=false) int id,Model model) {

        String sql = "";
        if(id != -1){
            sql="select * from ticket where user_id=" + id + "";
        } else {
            sql="select 'DUPA' from dual";
        }


        UserModel userModel = new UserModel();
        AccountModel accountModel;
        List<TicketModel> ticketModelList = new ArrayList<TicketModel>();


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

            String sqlE = "select email from user where id=" + id;
            PreparedStatement psE = connection.prepareStatement(sqlE);
            ResultSet resultSetE = psE.executeQuery();
            if (resultSetE.next()) {
                userModel.setEmail(resultSetE.getString(1));
            }
            resultSetE.close();
            psE.close();

            DatabaseController controller = new DatabaseController();
            controller.setDataSource(dataSource);
            accountModel = controller.accountInformation(userModel.getEmail());

            userModel.setId(id);
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



    @RequestMapping(value = "/getShortUserInfo", method = RequestMethod.POST)
    public @ResponseBody UserModel getShortUserInfo(@RequestParam(value="id", required=true) int id,Model model) {
        UserModel userModel = new UserModel();
        AccountModel accountModel = new AccountModel();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            String sqlE = "select email from user where id=" + id;
            PreparedStatement psE = connection.prepareStatement(sqlE);
            ResultSet resultSetE = psE.executeQuery();
            if (resultSetE.next()) {
                userModel.setEmail(resultSetE.getString(1));
            }
            resultSetE.close();
            psE.close();

            String sqlA = "select amount from budget where user_id=" + id;
            PreparedStatement psA = connection.prepareStatement(sqlA);
            ResultSet resultSetA = psA.executeQuery();
            if (resultSetA.next()) {
                accountModel.setAmount(resultSetA.getDouble(1));
            }
            resultSetA.close();
            psA.close();

            userModel.setId(id);
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
//
//
//    @RequestMapping(method = RequestMethod.GET, value = "/floorstatus/")
//    public String printFloorStatus(ModelMap model) {
//        return "floorstatus";
//    }
}