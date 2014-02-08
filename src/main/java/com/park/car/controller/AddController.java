package com.park.car.controller;

import com.park.car.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
@RequestMapping("/add")
public class AddController {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/floor/{floor}/")
    @ResponseBody
    public ResponseModel floor(@PathVariable Integer floor) {
        String sql = "call addFloor(0,1," + floor + ",0,0);";
        String result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(1).equals("ADDED")) {
                    result = "Piętro dodane";
                } else {
                    result = "Piętro nie zostało dodane";
                }
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            result = "Piętro nie zostało dodane";
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
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/segment")
    @ResponseBody
    public ResponseModel segment(@RequestParam(value = "floorid", required = true) Integer floorId,
                                 @RequestParam(value = "segment", required = true) String seg) {
        String sql = "CALL addSegment(" + floorId + ", '" + seg + "');";
        String result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(1).equals("ADDED")) {
                    result = "Sektor dodany";
                } else {
                    result = "Sektor nie został dodany";
                }
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Sektor nie został dodany";
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
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/space")
    @ResponseBody
    public ResponseModel space(@RequestParam(value = "space", required = true) String space,
                               @RequestParam(value = "segmentid", required = true) Integer segmentId) {
        String sql = "CALL addSpace('" + space + "','FREE'," + segmentId + ",'0');";
        String result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(1).equals("ADDED")) {
                    result = "Miejsce dodane";
                } else {
                    result = "Miejsce nie zostało dodane";
                }
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Miejsce nie zostało dodane";
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
        return responseModel;
    }

}
