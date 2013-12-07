package com.park.car.controller;

import com.park.car.model.Registration;
import com.park.car.model.Resp;
import com.park.car.model.Space;
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
    ArrayList<Space> example(ModelMap modelMap){

        String sql = "select * from space";
        Connection connection = null;
        ArrayList<Space> mapa = new ArrayList<Space>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Space space = new Space(rs.getInt("id"),rs.getString("place"),rs.getString("state"),rs.getInt("floor_id"));
                mapa.add(space);
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
    public @ResponseBody
    Resp register(@RequestBody final Registration registration){
        String sql = "call addUser (null,'"+registration.getEmail()+"', MD5('"+registration.getPassword()+"'), '', '')";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
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
        return null;
    }


}
