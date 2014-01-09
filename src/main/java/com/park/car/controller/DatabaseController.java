package com.park.car.controller;

import com.park.car.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
@RequestMapping("/jdbc")
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userexist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel emial(@RequestBody final EmailModel emailModel) {
        String message = new String();
        String sqlResponse = "SELECT * FROM user WHERE email = '" + emailModel.getEmail() + "' AND sysdate() between validfrom and validto";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement psR = connection.prepareStatement(sqlResponse);
            ResultSet resultSet = psR.executeQuery();
            if (!resultSet.next()) {
                message = "false";
            } else {
                message = "true";
            }
            resultSet.close();
            psR.close();
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
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(message);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel reg(@RequestBody final RegisterModel registerModel) {
        String message = new String();
        String sql = "call addUser (null,'" + registerModel.getEmail() + "', MD5('" + registerModel.getPassword() + "'), '', '')";
        String sqlResponse = "SELECT * FROM user WHERE email = '" + registerModel.getEmail() + "' AND sysdate() between validfrom and validto";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement psR = connection.prepareStatement(sqlResponse);
            ResultSet resultSet = psR.executeQuery();
            if (!resultSet.next()) {
                ps.executeUpdate();
                ps.close();
                message = "Utworzono użytkownika";
            } else {
                message = "Taki użytkonik istnieje";
            }
            ps.close();
            resultSet.close();
            psR.close();
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
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(message);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getfloor")
    @ResponseBody
    public List<FloorModel> getFloor() {
        String sql = "SELECT * FROM floor where freespaces >0 ";
        Connection connection = null;
        List<FloorModel> list = new ArrayList<FloorModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new FloorModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
            resultSet.close();
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

    @RequestMapping(method = RequestMethod.GET, value = "/getsegmentfromfloor")
    @ResponseBody
    public List<SegmentModel> getSegmentFromFloor(@RequestParam(required = true) String id /*final FloorModel floorModel*/) {
        String sql = "SELECT * FROM segment where freespaces >0 AND floor_id = " + id/*floorModel.getId()*/;
        Connection connection = null;
        List<SegmentModel> list = new ArrayList<SegmentModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new SegmentModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
            resultSet.close();
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

    @RequestMapping(method = RequestMethod.GET, value = "/getspacesfromsegment")
    @ResponseBody
    public ArrayList<SpaceModel> getSpacesFromSegment(@RequestParam(required = true) String id /*final SegmentModel segmentModel*/) {
        String sql = "SELECT * FROM space where state='FREE' AND sensor = '0' AND segment_id = " + id/*segmentModel.getId()*/;
        Connection connection = null;
        ArrayList<SpaceModel> list = new ArrayList<SpaceModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new SpaceModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5)));
            }
            resultSet.close();
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

    @RequestMapping(method = RequestMethod.GET, value = "/makereservation")
    @ResponseBody
    public ResponseModel makeReservation(@RequestParam(required = true) String id, @RequestParam(required = true)
    String email /*final SegmentModel segmentModel*/) {
        Integer spaceId = null;
        Integer rand = null;
        TicketModel ticketModel = null;
        Integer segmentId, floorId;
        Calendar enter = new GregorianCalendar();
        BillsModel billsModel = new BillsModel();
        Timestamp timestamp;
        String sql = "SELECT * FROM space where state='FREE' AND sensor = '0' AND segment_id = " + id/*segmentModel.getId()*/;

        Connection connection = null;
        List<SpaceModel> list = new ArrayList<SpaceModel>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new SpaceModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5)));
            }
            resultSet.close();
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
        if (list != null) {
            rand = 0 + (int) (Math.random() * list.size());
            if (list.size() <= 1) {
                spaceId = list.get(0).getId();
            } else if (list.size() > 1) {
                spaceId = list.get(rand).getId();
            }
        }
        try {
            String sqlR = "call enter('" + spaceId + "' , '" + email + "')";
            String sqlT = "select * from ticket where user_id=(select id from user where email='"+email+"') and state='A' and space_id="+spaceId;
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlR);
            ps.executeQuery();
            ps.close();
            PreparedStatement psT = connection.prepareStatement(sqlT);
            ResultSet resultSet = psT.executeQuery();
            while (resultSet.next()){
                ticketModel = new TicketModel(resultSet.getInt(1),resultSet.getDouble(2),resultSet.getTime(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6));

                String sqlEnter = "select date from spacelog where prevstate='FREE' and newstate='RESERVED' and space_id="+ticketModel.getSpace_id()+" and user_id="+ticketModel.getUser_id();
                PreparedStatement psEnter = connection.prepareStatement(sqlEnter);
                ResultSet resultSetEnter = psEnter.executeQuery();
                if (resultSetEnter.next()){
                    timestamp = resultSetEnter.getTimestamp(1);
                    enter.setTimeInMillis(timestamp.getTime());
                    billsModel.setEnterDateTime(enter);
                }
                resultSetEnter.close();
                psEnter.close();

                String sqlSpace = "SELECT place, segment_id FROM space where id="+ticketModel.getSpace_id();

                ps = connection.prepareStatement(sqlSpace);
                resultSet = ps.executeQuery();
                if(resultSet.next()){
                    segmentId = resultSet.getInt(2);
                    billsModel.setPlace(resultSet.getInt(1));
                    String sqlSegment = "select seg, floor_id from segment where id="+segmentId;
                    ps = connection.prepareStatement(sqlSegment);
                    resultSet = ps.executeQuery();
                    if(resultSet.next()){
                        floorId = resultSet.getInt(2);
                        billsModel.setSeg(resultSet.getString(1));
                        String sqlFloor = "select floornumer from floor where id="+floorId;
                        ps = connection.prepareStatement(sqlFloor);
                        resultSet = ps.executeQuery();
                        if(resultSet.next()){
                            billsModel.setFloornumer(resultSet.getInt(1));
                        }
                    }
                }
            }
            resultSet.close();
            psT.close();
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
        ResponseModel responseModel = new ResponseModel();
        responseModel.setSpaceModel(list.get(rand));
        responseModel.setTicketModel(ticketModel);
        responseModel.setBillsModel(billsModel);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exit")
    @ResponseBody
    public ResponseModel exit(@RequestParam(required = true) String id){
        String sql="call outgoing("+id+");";
        String result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
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
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(result);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/setsensor/{spaceId}/{sensor}")
    @ResponseBody
    public String sensor(@PathVariable String spaceId, @PathVariable String sensor){
        String sql="";
        String result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(1);
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
        return "Wynik: " +spaceId+ "  ::  " +sensor;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activeticket/{email}/active")
    @ResponseBody
    public ResponseModel activeTicket(@PathVariable String email){
        String sql = "select * from ticket where user_id=(select id from user where email='"+email+"') and state='A'";
        String result = null;
        Integer segmentId, floorId;
        Calendar enter = new GregorianCalendar();
        Timestamp timestamp;
        TicketModel ticketModel = null;
        BillsModel billsModel = new BillsModel();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {

                ticketModel = new TicketModel(resultSet.getInt(1),resultSet.getDouble(2),resultSet.getTime(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6));
                result = "true";

                String sqlEnter = "select date from spacelog where prevstate='FREE' and newstate='RESERVED' and space_id="+ticketModel.getSpace_id()+" and user_id="+ticketModel.getUser_id();
                PreparedStatement psEnter = connection.prepareStatement(sqlEnter);
                ResultSet resultSetEnter = psEnter.executeQuery();
                if (resultSetEnter.next()){
                    timestamp = resultSetEnter.getTimestamp(1);
                    enter.setTimeInMillis(timestamp.getTime());
                    billsModel.setEnterDateTime(enter);
                }
                resultSetEnter.close();
                psEnter.close();

                String sqlSpace = "SELECT place, segment_id FROM space where id="+ticketModel.getSpace_id();
                ps = connection.prepareStatement(sqlSpace);
                resultSet = ps.executeQuery();
                if(resultSet.next()){
                    segmentId = resultSet.getInt(2);
                    billsModel.setPlace(resultSet.getInt(1));
                    String sqlSegment = "select seg, floor_id from segment where id="+segmentId;
                    ps = connection.prepareStatement(sqlSegment);
                    resultSet = ps.executeQuery();
                    if(resultSet.next()){
                        floorId = resultSet.getInt(2);
                        billsModel.setSeg(resultSet.getString(1));
                        String sqlFloor = "select floornumer from floor where id="+floorId;
                        ps = connection.prepareStatement(sqlFloor);
                        resultSet = ps.executeQuery();
                        if(resultSet.next()){
                            billsModel.setFloornumer(resultSet.getInt(1));
                        }
                    }
                }
            } else {
                result = "false";
            }
            resultSet.close();
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
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(result);
        responseModel.setTicketModel(ticketModel);
        responseModel.setBillsModel(billsModel);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/archiveticket/{email}/archive")
    @ResponseBody
    public ResponseModel archiveTickets(@PathVariable String email){
        String sql = "select * from ticket where user_id=(select id from user where email='"+email+"') and state='E'";
        Integer segmentId, floorId;
        Calendar enter = new GregorianCalendar();
        Calendar exit = new GregorianCalendar();
        Timestamp timestampEnter, timestampExit;
        TicketModel ticketModel = null;
        ArchiveBillsModel archiveBillsModel = new ArchiveBillsModel();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ticketModel = new TicketModel(resultSet.getInt(1),resultSet.getDouble(2),resultSet.getTime(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6));

                String sqlEnter = "select date from spacelog where prevstate='FREE' and newstate='RESERVED' and space_id="+ticketModel.getSpace_id()+" and user_id="+ticketModel.getUser_id();
                PreparedStatement psEnter = connection.prepareStatement(sqlEnter);
                ResultSet resultSetEnter = psEnter.executeQuery();
                if (resultSetEnter.next()){
                    timestampEnter = resultSetEnter.getTimestamp(1);
                    enter.setTimeInMillis(timestampEnter.getTime());
                    archiveBillsModel.setEnterDateTime(enter);
                }
                resultSetEnter.close();
                psEnter.close();

                String sqlExit = "select date from spacelog where prevstate='RESERVED' and newstate='FREE' and space_id="+ticketModel.getSpace_id()+" and user_id="+ticketModel.getUser_id();
                PreparedStatement psExit = connection.prepareStatement(sqlExit);
                ResultSet resultSetExit = psExit.executeQuery();
                if (resultSetExit.next()){
                    timestampExit = resultSetExit.getTimestamp(1);
                    exit.setTimeInMillis(timestampExit.getTime());
                    archiveBillsModel.setExitDateTime(exit);
                }
                resultSetExit.close();
                psExit.close();

                String sqlSpace = "SELECT place, segment_id FROM space where id="+ticketModel.getSpace_id();
                ps = connection.prepareStatement(sqlSpace);
                resultSet = ps.executeQuery();
                if(resultSet.next()){
                    segmentId = resultSet.getInt(2);
                    archiveBillsModel.setPlace(resultSet.getInt(1));
                    String sqlSegment = "select seg, floor_id from segment where id="+segmentId;
                    ps = connection.prepareStatement(sqlSegment);
                    resultSet = ps.executeQuery();
                    if(resultSet.next()){
                        floorId = resultSet.getInt(2);
                        archiveBillsModel.setSeg(resultSet.getString(1));
                        String sqlFloor = "select floornumer from floor where id="+floorId;
                        ps = connection.prepareStatement(sqlFloor);
                        resultSet = ps.executeQuery();
                        if(resultSet.next()){
                            archiveBillsModel.setFloornumer(resultSet.getInt(1));
                        }
                    }
                }
            }
            resultSet.close();
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
        ResponseModel responseModel = new ResponseModel();
        responseModel.setTicketModel(ticketModel);
        responseModel.setArchiveBillsModel(archiveBillsModel);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/s")
    public String printSamplePage(ModelMap model) {
        return "spacesStatus";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/spacesStatus")
    public String printSpacesStatus(ModelMap model) {
        String sql = "SELECT * FROM space";
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

        model.addAttribute("spaces", list);
        return "spacesStatus";
    }
}
