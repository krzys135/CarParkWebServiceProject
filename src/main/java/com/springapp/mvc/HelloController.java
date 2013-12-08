package com.springapp.mvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.park.car.model.Emial;
import com.park.car.model.ResponseModel;
import com.springapp.mvc.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    private ExampleService exampleService;

    @Autowired
    private DataSource dataSource;
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "hello";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public @ResponseBody
    String hello(@RequestParam(required = false) String miejsce, String dupa) throws JsonProcessingException {
        Map<String, String> mapa = new HashMap<String, String>();
        mapa.put("email", "xrysiek@wp.pl");
       /// mapa.put("kwota", "11");
       // mapa.put("wolne miejsca", String.valueOf(exampleService.getEmpty()));

        if (miejsce != null) {
            mapa.put("miejsce", miejsce);
            mapa.put("dupa", dupa);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(mapa);

    }
    @RequestMapping(method = RequestMethod.GET, value = "/sample")
    public String printSamplePage(ModelMap model) {
        Map<String, String> mapa = new HashMap<String, String>();
        mapa.put("cena", "18");
        mapa.put("kwota", "11");
        model.addAttribute("mapa", mapa);
        return "samplepage";
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public @ResponseBody Emial emial(){
        Emial emial = new Emial();
        emial.setEmial("xrysiek@wp.pl");
        return emial;
    }

    @RequestMapping(value = "/datetime", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel dateTime(@RequestBody final Emial emial){

        String sql = "call addUser (null,'"+emial.getEmial()+"', MD5('"+"55555"+"'), '', '')";
        Connection connection = null;
        System.out.print(sql);

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

        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("Witam "+ emial.getEmial() + "Data: " + emial.getDate() + "Time: " + emial.getTime());
        //responseModel.setMessage("Poszlo");
        return responseModel;
    }
}