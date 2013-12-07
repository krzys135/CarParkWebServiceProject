package com.park.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
    Map<String, String> example(ModelMap modelMap){

        String sql = "select name from place where id=1";
       // String sql = "select age from data where No=1";
        Connection connection = null;
        Map<String, String> mapa = new HashMap<String, String>();
        try {
            System.out.print(dataSource.toString());
            connection = dataSource.getConnection();
            System.out.print(dataSource.toString());
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                mapa.put("String", rs.getString("name"));
                //mapa.put("String", rs.getInt("age"));
            }
            modelMap.addAttribute("mapa", mapa);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapa;
        }
}
