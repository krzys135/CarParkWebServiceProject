package com.park.car.controller;

import com.park.car.model.SegmentModel;
import com.park.car.model.SpaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/main")
public class URLController {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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


    @RequestMapping(method = RequestMethod.GET, value = "/floorstatus/")
    public String printFloorStatus(ModelMap model) {
        return "floorstatus";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/spacesStatus/floor/{floor}")
    public String printSpacesStatusFromFloor(ModelMap model,@PathVariable int floor) {
        return "spacesStatus";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/placestatus/place/{pl}")
         public String printPlaceStatus(ModelMap model,@PathVariable int pl) {
        return "spacedetails";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/placestatus/place/")
    public String printPlacesHistory(ModelMap model) {
        return "spacedetails";
    }

    @RequestMapping(value = "/cashsensor", method = RequestMethod.GET)
    public String addCashAndChangeSensor(){
        return "cashandsensor";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/userdetails/{id}")
    public String printUserDetails(ModelMap model,@PathVariable int id) {
        return "userdetails";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userdetails/")
    public String printAllUsers(ModelMap model) {
        return "userdetails";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ticketdetails/{id}")
    public String printTicketDetails(@PathVariable int id){
        return "ticketinfo";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ticketsdetails")
    public String printTicketsDetails(){
        return "ticketsinfo";
    }
}