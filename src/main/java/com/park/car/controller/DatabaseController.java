package com.park.car.controller;

import com.park.car.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

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
            String sqlT = "select * from ticket where user_id=(select id from user where email='" + email + "') and state='A' and space_id=" + spaceId;
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlR);
            ps.executeQuery();
            ps.close();
            PreparedStatement psT = connection.prepareStatement(sqlT);
            ResultSet resultSet = psT.executeQuery();
            while (resultSet.next()) {
                ticketModel = new TicketModel(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getTime(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));

                String sqlEnter = "select date from spacelog where prevstate='FREE' and newstate='RESERVED' and space_id=" + ticketModel.getSpace_id() + " and user_id=" + ticketModel.getUser_id() + " and ticket_id=" + ticketModel.getId();
                PreparedStatement psEnter = connection.prepareStatement(sqlEnter);
                ResultSet resultSetEnter = psEnter.executeQuery();
                if (resultSetEnter.next()) {
                    timestamp = resultSetEnter.getTimestamp(1);
                    enter.setTimeInMillis(timestamp.getTime());
                    billsModel.setEnterDateTime(enter);
                }
                resultSetEnter.close();
                psEnter.close();

                String sqlSpace = "SELECT place, segment_id FROM space where id=" + ticketModel.getSpace_id();

                ps = connection.prepareStatement(sqlSpace);
                resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    segmentId = resultSet.getInt(2);
                    billsModel.setPlace(resultSet.getInt(1));
                    String sqlSegment = "select seg, floor_id from segment where id=" + segmentId;
                    ps = connection.prepareStatement(sqlSegment);
                    resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        floorId = resultSet.getInt(2);
                        billsModel.setSeg(resultSet.getString(1));
                        String sqlFloor = "select floornumer from floor where id=" + floorId;
                        ps = connection.prepareStatement(sqlFloor);
                        resultSet = ps.executeQuery();
                        if (resultSet.next()) {
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

    @RequestMapping(method = RequestMethod.GET, value = "/makereservationsteadily")
    @ResponseBody
    public ResponseModel makeReservationSteadily(@RequestParam(required = true) String email) {
        Integer spaceId = null;
        Integer rand;
        TicketModel ticketModel = null;
        Integer segmentId, floorId;
        Calendar enter = new GregorianCalendar();
        BillsModel billsModel = new BillsModel();
        Timestamp timestamp;
        Connection connection = null;

        List<FloorModel> floorModelList = getFloor();
        if (floorModelList != null) {
            Integer max = 0;
            Integer maxId = 0;
            for (int i = 0; i < floorModelList.size(); i++) {
                if (floorModelList.get(i).getFreespaces() > max) {
                    max = floorModelList.get(i).getFreespaces();
                    maxId = floorModelList.get(i).getId();
                }
            }
            List<SegmentModel> segmentModelList = getSegmentFromFloor(maxId.toString());
            if (segmentModelList != null) {
                Integer maxSeg = 0;
                Integer maxSegId = 0;
                for (int i = 0; i < segmentModelList.size(); i++) {
                    if (segmentModelList.get(i).getFreespaces() > maxSeg) {
                        maxSeg = segmentModelList.get(i).getFreespaces();
                        maxSegId = segmentModelList.get(i).getId();
                    }
                }
                List<SpaceModel> spaceModelList = getSpacesFromSegment(maxSegId.toString());
                if (spaceModelList != null) {
                    rand = 0 + (int) (Math.random() * spaceModelList.size());
                    if (spaceModelList.size() <= 1) {
                        spaceId = spaceModelList.get(0).getId();
                    } else if (spaceModelList.size() > 1) {
                        spaceId = spaceModelList.get(rand).getId();
                    }
                }
            }
        }
        try {
            String sqlR = "call enter('" + spaceId + "' , '" + email + "')";
            String sqlT = "select * from ticket where user_id=(select id from user where email='" + email + "') and state='A' and space_id=" + spaceId;
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlR);
            ps.executeQuery();
            ps.close();
            PreparedStatement psT = connection.prepareStatement(sqlT);
            ResultSet resultSet = psT.executeQuery();
            while (resultSet.next()) {
                ticketModel = new TicketModel(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getTime(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));

                String sqlEnter = "select date from spacelog where prevstate='FREE' and newstate='RESERVED' and space_id=" + ticketModel.getSpace_id() + " and user_id=" + ticketModel.getUser_id() + " and ticket_id=" + ticketModel.getId();
                PreparedStatement psEnter = connection.prepareStatement(sqlEnter);
                ResultSet resultSetEnter = psEnter.executeQuery();
                if (resultSetEnter.next()) {
                    timestamp = resultSetEnter.getTimestamp(1);
                    enter.setTimeInMillis(timestamp.getTime());
                    billsModel.setEnterDateTime(enter);
                }
                resultSetEnter.close();
                psEnter.close();

                String sqlSpace = "SELECT place, segment_id FROM space where id=" + ticketModel.getSpace_id();

                ps = connection.prepareStatement(sqlSpace);
                resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    segmentId = resultSet.getInt(2);
                    billsModel.setPlace(resultSet.getInt(1));
                    String sqlSegment = "select seg, floor_id from segment where id=" + segmentId;
                    ps = connection.prepareStatement(sqlSegment);
                    resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        floorId = resultSet.getInt(2);
                        billsModel.setSeg(resultSet.getString(1));
                        String sqlFloor = "select floornumer from floor where id=" + floorId;
                        ps = connection.prepareStatement(sqlFloor);
                        resultSet = ps.executeQuery();
                        if (resultSet.next()) {
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
        responseModel.setTicketModel(ticketModel);
        responseModel.setBillsModel(billsModel);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exit")
    @ResponseBody
    public ResponseModel exit(@RequestParam(required = true) String id) {
        String sql = "call outgoing(" + id + ");";
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
        calculateFee(id);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(result);
        return responseModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/setsensor/id/{spaceId}/state/{sensor}")
    @ResponseBody
    public String sensor(@PathVariable String spaceId, @PathVariable String sensor) {
        String sql = "call setsensor(" + spaceId + "," + sensor + ")";
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
        return "Wynik: " + spaceId + "  ::  " + sensor;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activeticket/{email}/active")
    @ResponseBody
    public ResponseModel activeTicket(@PathVariable String email) {
        String sql = "select * from ticket where user_id=(select id from user where email='" + email + "') and state='A'";
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

                ticketModel = new TicketModel(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getTime(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));
                result = "true";

                String sqlEnter = "select date from spacelog where prevstate='FREE' and newstate='RESERVED' and space_id=" + ticketModel.getSpace_id() + " and user_id=" + ticketModel.getUser_id() + " and ticket_id=" + ticketModel.getId();
                PreparedStatement psEnter = connection.prepareStatement(sqlEnter);
                ResultSet resultSetEnter = psEnter.executeQuery();
                if (resultSetEnter.next()) {
                    timestamp = resultSetEnter.getTimestamp(1);
                    enter.setTimeInMillis(timestamp.getTime());
                    billsModel.setEnterDateTime(enter);
                }
                resultSetEnter.close();
                psEnter.close();

                String sqlSpace = "SELECT place, segment_id FROM space where id=" + ticketModel.getSpace_id();
                ps = connection.prepareStatement(sqlSpace);
                resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    segmentId = resultSet.getInt(2);
                    billsModel.setPlace(resultSet.getInt(1));
                    String sqlSegment = "select seg, floor_id from segment where id=" + segmentId;
                    ps = connection.prepareStatement(sqlSegment);
                    resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        floorId = resultSet.getInt(2);
                        billsModel.setSeg(resultSet.getString(1));
                        String sqlFloor = "select floornumer from floor where id=" + floorId;
                        ps = connection.prepareStatement(sqlFloor);
                        resultSet = ps.executeQuery();
                        if (resultSet.next()) {
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

    @RequestMapping(method = RequestMethod.GET, value = "/calculatefee/{ticketId}/")
    @ResponseBody
    public double calculateFee(@PathVariable String ticketId) {
        Map<Integer, FeeModel> fm = new HashMap<>();
        String sql = "select time_to_sec(duration) from ticket where id =" + ticketId;
        double fee = 0;
        int result = 0;
        long seconds = 0;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                seconds = resultSet.getLong(1);
                result = (int) seconds / 60;
                result++;
//                if ((seconds - result * 60) > 30) {
//                    result++;
//                }
            }

            String sqlF = "SELECT * FROM fee where ((select sysdate() from dual) between validform  and ifnull(validto,sysdate()+1)) order by `order`";
            ps = connection.prepareStatement(sqlF);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (result > resultSet.getInt(5)) {
                    fm.put(resultSet.getInt(2), new FeeModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getTimestamp(8), resultSet.getTimestamp(9), resultSet.getInt(10)));
                } else break;
            }
            if (result <= resultSet.getInt(5)) {
                fm.put(resultSet.getInt(2), new FeeModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getTimestamp(8), resultSet.getTimestamp(9), resultSet.getInt(10)));
            }
            resultSet.close();

            int k0 = 0;

            if (fm.containsKey(999)) {
                fee = fm.get(999).getCharge();
            } else {
                for (Map.Entry<Integer, FeeModel> f : fm.entrySet()) {
                    FeeModel fml = f.getValue();
                    if (f.getKey() > k0) {
                        k0 = f.getKey();
                        if (!fm.get(fm.size()).equals(f.getValue())) {
                            int count = fml.getMaxdur() / fml.getDuration();
                            result -= fml.getDuration() * count;
                            fee += fml.getCharge() * count;
                        } else {
                            double count = Math.ceil((double) result / (double) fml.getSegment());
                            int segInDur = fml.getDuration() / fml.getSegment();
                            System.out.println(Math.ceil(count));
                            result -= fml.getSegment() * count;
                            fee += count * (fml.getCharge() / segInDur);
                        }
                    }
                }
            }

            String sqlT = "update ticket set fee = " + fee + " where id=" + ticketId;
            ps = connection.prepareStatement(sqlT);
            ps.execute();
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
        return fee;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/archiveticket/{email}/archive")
    @ResponseBody
    public ResponseModel archiveTickets(@PathVariable String email) {
        String sql = "select * from ticket where user_id=(select id from user where email='" + email + "') and state='E'";
        List<ArchiveBillsModel> archiveBillsModelsList = new ArrayList<>();
        Connection connection = null;
        String message = "false";
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                message = "true";
                Integer segmentId, floorId;
                Calendar enter = new GregorianCalendar();
                Calendar exit = new GregorianCalendar();
                Timestamp timestampEnter, timestampExit;

                ArchiveBillsModel archiveBillsModel = new ArchiveBillsModel();
                TicketModel ticketModel = new TicketModel(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));

                String sqlEnter = "select date from spacelog where prevstate='FREE' and newstate='RESERVED' and space_id=" + ticketModel.getSpace_id() + " and user_id=" + ticketModel.getUser_id() + " and ticket_id=" + ticketModel.getId();
                PreparedStatement psEnter = connection.prepareStatement(sqlEnter);
                ResultSet resultSetEnter = psEnter.executeQuery();
                if (resultSetEnter.next()) {
                    timestampEnter = resultSetEnter.getTimestamp(1);
                    enter.setTimeInMillis(timestampEnter.getTime());
                    archiveBillsModel.setEnterDateTime(enter);
                }
                resultSetEnter.close();
                psEnter.close();

                String sqlExit = "select date from spacelog where prevstate='RESERVED' and newstate='FREE' and space_id=" + ticketModel.getSpace_id() + " and user_id=" + ticketModel.getUser_id() + " and ticket_id=" + ticketModel.getId();
                PreparedStatement psExit = connection.prepareStatement(sqlExit);
                ResultSet resultSetExit = psExit.executeQuery();
                if (resultSetExit.next()) {
                    timestampExit = resultSetExit.getTimestamp(1);
                    exit.setTimeInMillis(timestampExit.getTime());
                    archiveBillsModel.setExitDateTime(exit);
                }
                resultSetExit.close();
                psExit.close();

                String sqlSpace = "SELECT place, segment_id FROM space where id=" + ticketModel.getSpace_id();
                PreparedStatement psTicket = connection.prepareStatement(sqlSpace);
                ResultSet resultSetTicket = psTicket.executeQuery();
                if (resultSetTicket.next()) {
                    segmentId = resultSetTicket.getInt(2);
                    archiveBillsModel.setPlace(resultSetTicket.getInt(1));
                    String sqlSegment = "select seg, floor_id from segment where id=" + segmentId;
                    psTicket = connection.prepareStatement(sqlSegment);
                    resultSetTicket = psTicket.executeQuery();
                    if (resultSetTicket.next()) {
                        floorId = resultSetTicket.getInt(2);
                        archiveBillsModel.setSeg(resultSetTicket.getString(1));
                        String sqlFloor = "select floornumer from floor where id=" + floorId;
                        psTicket = connection.prepareStatement(sqlFloor);
                        resultSetTicket = psTicket.executeQuery();
                        if (resultSetTicket.next()) {
                            archiveBillsModel.setFloornumer(resultSetTicket.getInt(1));
                        }
                    }
                }
                resultSetTicket.close();
                psTicket.close();

                String sqlDurationSeconds = "select time_to_sec(duration) from ticket where id =" + ticketModel.getId();
                PreparedStatement psDurationSeconds = connection.prepareStatement(sqlDurationSeconds);
                ResultSet resultSetDurationSeconds = psDurationSeconds.executeQuery();
                if (resultSetDurationSeconds.next()) {
                    ticketModel.setDurationSeconds(resultSetDurationSeconds.getLong(1));
                }
                resultSetDurationSeconds.close();
                psDurationSeconds.close();
                archiveBillsModel.setTicketModel(ticketModel);
                archiveBillsModelsList.add(archiveBillsModel);
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
        responseModel.setArchiveBillsModelList(archiveBillsModelsList);
        responseModel.setMessage(message);
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
