package com.springapp.mvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.park.car.service.Emial;
import com.park.car.service.Resp;
import com.springapp.mvc.service.ExampleService;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    private ExampleService exampleService;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "hello";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public @ResponseBody
    String hello(@RequestParam(required = false) String miejsce) throws JsonProcessingException {
        Map<String, String> mapa = new HashMap<String, String>();
        mapa.put("email", "xrysiek@wp.pl");
       /// mapa.put("kwota", "11");
       // mapa.put("wolne miejsca", String.valueOf(exampleService.getEmpty()));

        if (miejsce != null) {
            mapa.put("miejsce", miejsce);
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
    public Resp dateTime(@RequestBody final Emial emial){
        /*Emial emial1 = new Emial();
        Date date = Date.valueOf("2013-12-05");
        Time time = Time.valueOf("20:22:00");
        emial.setEmial("sss@sdfasdf.pl");
        emial.setDate(date);
        emial.setTime(time);*/
        Resp resp = new Resp();
        //String result = new String("Witam "+ emial.getEmial());
        resp.setMessage("Witam "+ emial.getEmial() + "Data: " + emial.getDate() + "Time: " + emial.getTime());
        return resp;
    }
}