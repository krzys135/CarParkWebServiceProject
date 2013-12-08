package com.park.car.controller;

import com.park.car.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping("/jdbc")
public class DatabaseController {

    @Autowired
    private DataSource dataSource;
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/example")
    public @ResponseBody
    ArrayList<SpaceModel> example(ModelMap modelMap){

        String sql = "select * from space";
        Connection connection = null;
        ArrayList<SpaceModel> mapa = new ArrayList<SpaceModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SpaceModel spaceModel = new SpaceModel(rs.getInt("id"),rs.getString("place"),rs.getString("state"),rs.getInt("floor_id"));
                mapa.add(spaceModel);
            }
            System.out.print(mapa.size());
            modelMap.addAttribute("mapa", mapa);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapa;
        }

    @RequestMapping(method = RequestMethod.POST, value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel reg(@RequestBody final RegisterModel registerModel){
        String message = new String();
        String sql = "call addUser (null,'"+ registerModel.getEmail()+"', MD5('"+ registerModel.getPassword()+"'), '', '')";
        String sqlResponse = "SELECT * FROM user WHERE email = '"+ registerModel.getEmail()+"' AND sysdate() between validfrom and validto";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement psR = connection.prepareStatement(sqlResponse);
            ResultSet resultSet = psR.executeQuery();
            if (!resultSet.next()){
                ps.executeUpdate();
                ps.close();
                message = "Utworzono użytkownika";
            } else {
                message = "Taki użytkonik istnieje";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(message);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getfloor")
         @ResponseBody
         public ArrayList<FloorModel> getFloor(){
        String sql = "SELECT * FROM floor";
        Connection connection = null;
        ArrayList<FloorModel> list = new ArrayList<FloorModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(new FloorModel(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getInt(5)));

            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        return list;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getspacefromfloor")
    @ResponseBody
    public ArrayList<SpaceModel> getSpaceFromFloor(final FloorModel floorModel){
        String sql = "SELECT * FROM space where state = 'FREE' AND floor_id = "+ floorModel.getId();
        Connection connection = null;
        ArrayList<SpaceModel> list = new ArrayList<SpaceModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(null);

            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        return list;
    }

}
