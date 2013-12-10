package com.park.car.controller;

import com.park.car.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/jdbc")
public class DatabaseController {

    @Autowired
    private DataSource dataSource;
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/example")
//    public @ResponseBody
//    ArrayList<SpaceModel> example(ModelMap modelMap){
//
//        String sql = "select * from space";
//        Connection connection = null;
//        ArrayList<SpaceModel> mapa = new ArrayList<SpaceModel>();
//        try {
//            connection = dataSource.getConnection();
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                SpaceModel spaceModel = new SpaceModel(rs.getInt("id"),rs.getString("place"),rs.getString("state"),rs.getInt("floor_id"));
//                mapa.add(spaceModel);
//            }
//            System.out.print(mapa.size());
//            modelMap.addAttribute("mapa", mapa);
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return mapa;
//        }

    @RequestMapping(method = RequestMethod.POST, value = "/userexist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel emial(@RequestBody final EmailModel emailModel){
        String message = new String();
        String sqlResponse = "SELECT * FROM user WHERE email = '"+ emailModel.getEmail()+"' AND sysdate() between validfrom and validto";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement psR = connection.prepareStatement(sqlResponse);
            ResultSet resultSet = psR.executeQuery();
            if (!resultSet.next()){
                message = "false";
            } else {
                message = "true";
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
         public List<FloorModel> getFloor(){
        String sql = "SELECT * FROM floor";
        Connection connection = null;
       List<FloorModel> list = new ArrayList<FloorModel>();
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

    @RequestMapping(method = RequestMethod.GET, value = "/getsegmentfromfloor")
    @ResponseBody
    public List<SegmentModel> getSegmentFromFloor(@RequestParam(required = true) String id /*final FloorModel floorModel*/){
        String sql = "SELECT * FROM segment where freespaces >0 AND floor_id = "+ id/*floorModel.getId()*/;
        Connection connection = null;
        List<SegmentModel> list = new ArrayList<SegmentModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(new SegmentModel(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5)));
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

    @RequestMapping(method = RequestMethod.GET, value = "/getspacesfromsegment")
    @ResponseBody
    public ArrayList<SpaceModel> getSpacesFromSegment(@RequestParam(required = true) String id /*final SegmentModel segmentModel*/){
        String sql = "SELECT * FROM space where state='FREE' AND sensor = '0' AND segment_id = "+ id/*segmentModel.getId()*/;
        Connection connection = null;
        ArrayList<SpaceModel> list = new ArrayList<SpaceModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(new SpaceModel(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5)));
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



    @RequestMapping(method = RequestMethod.GET, value = "/makereservation")
    @ResponseBody
    public ArrayList<SpaceModel> makeReservation(@RequestParam(required = true) String id /*final SegmentModel segmentModel*/){
        String sql = "SELECT * FROM space where state='FREE' AND sensor = '0' AND segment_id = "+ id/*segmentModel.getId()*/;
        Connection connection = null;
        ArrayList<SpaceModel> list = new ArrayList<SpaceModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(new SpaceModel(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5)));
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
