<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/adder_style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.4.4.min.js" />"  type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.dataTables.min.js" />"  type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery-ui-1.10.3.custom.min.js" />"  type="text/javascript"></script>

    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>
    <title>CarPark App!</title>

    <link href="<c:url value="/resources/css/demo_page.css" />" rel="stylesheet"  />
    <link href="<c:url value="/resources/css/demo_table.css" />" rel="stylesheet"  />
    <link href="<c:url value="/resources/css/demo_table_jui.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/jquery-ui.css"  />"  media="all" rel="stylesheet"  />
    <link href="<c:url value="/resources/css/jquery-ui-1.7.2.custom.css" />" media="all" rel="stylesheet" />

    <script type="text/javascript">
        jQuery(document).ready(function ($) {
            $("#spacehist").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });
    </script>
    <script type="text/javascript">
        function timeConverter(UNIX_timestamp){
            var a = new Date(UNIX_timestamp);
            var year = a.getFullYear();
            var month = a.getMonth()+1;
            var date = a.getDate();
            var time = date+'-'+month+'-'+year+' '+a.toTimeString().substr(0,8);
            return time;
        }
        getSpaceHistory();
        myVar=setInterval(function(){getSpaceHistory()},10000);

        window.onbeforeunload = function(){
            clearTimeout(myVar);
        };


        var fl = window.location.href;
        var regex = new RegExp("/place/\\d{1,}");
        var sp=regex.exec(fl);

        if(sp!=null) {
        sp = sp[0].substr(7);
        }
        else {sp =-1;}
        if(id==-1){
            jq("#shortinfo").css('display', 'none');
        }
        function getSpaceHistory() {
            jq(function() {
                jq.post("/main/ajax/getSpaceHistory",
                        {space : sp},
                        function(data){
                            jq('#spacehist').dataTable().fnClearTable();

                            for(var i=0;i<data.length;i++){
                                var iconpr,iconnx;
                                if(data[i].prevsensor=="0"){
                                    iconpr ="<p style=\"font-size:120%;color: green\"><b>•</b></p>";
                                }
                                else {iconpr ="<p style=\"font-size:120%;color: #ff0000\"><b>•</b></p>";}
                                if(data[i].newsensor=="0"){
                                    iconnx ="<p style=\"font-size:120%;color: green\"><b>•</b></p>";
                                }
                                else {iconnx ="<p style=\"font-size:120%;color: #ff0000\"><b>•</b></p>";}




                                var userclick="<a href=\"/main/userdetails/"+data[i].user_id+"\">"+data[i].user_id+"</a>";
                                var ticketclick="<a href=\"/main/ticketdetails/"+data[i].ticket_id+"\">"+data[i].ticket_id+"</a>";
                                var spaceclick="<a href=\"/main/placestatus/place/"+data[i].space_id+"\">"+data[i].space_id+"</a>";
                                jq('#spacehist').dataTable().fnAddData([data[i].prevstate,data[i].newstate,iconpr,iconnx,timeConverter(data[i].date),userclick,ticketclick,spaceclick],i+1);

                            }
                        });
            });

            jq(function () {
                jq.post("/main/ajax/getShortSpaceInfo",
                        {id: sp},
                        function (data) {
                            var sens;
                            if(data.sensor=="0"){
                                sens ="<span style=\"font-size:120%;color: green\"><b>•</b></span>";
                            }
                            else {sens ="<span style=\"font-size:120%;color: #ff0000\"><b>•</b></span>";}
                            jq('#shortinfo').html("<h3>ID: " + data.id+" Space: "+data.place+"   State:"+data.state+" "+ sens+ "</h3>");

                        });
            });
        }
    </script>
</head>
<body id="dt_example">
<div id="container">
    <div id="demo_jui">
        <div>
            <ul id="menu">
                <li><button id="userlist" onclick="location.href='/main/userdetails/'">User list</button></li>
                <li><button id="floor" onclick="location.href='/main/floorstatus/'">Car park</button></li>
                <li><button id="tickets" onclick="location.href='/main/ticketsdetails'">Tickets</button> </li>
                <li><button id="paynemt" onclick="location.href='/main/paymentdetails'">Payments</button> </li>
                <li><button onclick="location.href='<c:url value="/j_spring_security_logout" />'">Logout</button></li>
            </ul>
        </div><br>
        <h3>Space details</h3>
        <div id = "shortinfo">
            Loading...
        </div>
        <br>
        <table id="spacehist" class="display">
            <thead>
            <tr>
                <th>State</th>
                <th>New state</th>
                <th>Sensor</th>
                <th>New sensor</th>
                <th>Date</th>
                <th>User</th>
                <th>Ticket</th>
                <th>Space</th>
            </tr>
            </thead>
            <tbody class="tbl">
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
