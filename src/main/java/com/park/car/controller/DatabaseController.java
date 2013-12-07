package com.park.car.controller;

import com.park.car.model.Reg;
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
    @ResponseBody
    public Resp reg(@RequestBody final Reg reg){
        String message = new String();
        String sql = "call addUser (null,'"+reg.getE()+"', MD5('"+reg.getP()+"'), '', '')";
        String sqlResponse = "SELECT * FROM user WHERE email = '"+reg.getE()+"' AND sysdate() between validfrom and validto";
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
        Resp resp = new Resp();
        resp.setMessage(message);
        return resp;
    }

}
