<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/adder_style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.4.4.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery-ui-1.10.3.custom.min.js" />" type="text/javascript"></script>
    <link href="<c:url value="/resources/css/demo_page.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/demo_table.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/demo_table_jui.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"  />" media="all" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/jquery-ui-1.7.2.custom.css" />" media="all" rel="stylesheet"/>

    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>

    <script>
        setTicketTable();

        function setTicketTable() {
            jQuery(document).ready(function ($) {
                $("#ticketstab").dataTable({
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true
                });
            });
            setDataTicket();
            myVar = setInterval(function () {
                setDataTicket()
            }, 10000);

            window.onbeforeunload = function () {
                clearTimeout(myVar);
            };
        }

        function setDataTicket() {
            jq(function () {
                jq.post("/main/ajax/getTickets",
                        {},
                        function (data) {
                            jq('#ticketstab').dataTable().fnClearTable();
                            for (var i = 0; i < data.length; i++) {

                                var sek = data[i].durationSeconds;
                                var sec_num = parseInt(sek, 10);
                                var hours   = Math.floor(sec_num / 3600);
                                var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
                                var seconds = sec_num - (hours * 3600) - (minutes * 60);

                                if (hours   < 10) {hours   = "0"+hours;}
                                if (minutes < 10) {minutes = "0"+minutes;}
                                if (seconds < 10) {seconds = "0"+seconds;}
                                var time    = hours+':'+minutes+':'+seconds;

                                var userclick="<a href=\"/main/userdetails/"+data[i].user_id+"\">"+data[i].user_id+"</a>";
                                var spaceclick="<a href=\"/main/placestatus/place/"+data[i].space_id+"\">"+data[i].space_id+"</a>";

                                jq('#ticketstab').dataTable().fnAddData([data[i].id, data[i].fee , time, data[i].state, userclick, spaceclick], i+1);
                            }
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
             <li><button onclick="location.href='/main/ticketsdetails'">Tickets</button> </li>
             <li><button id="paynemt" onclick="location.href='/main/paymentdetails'">Payments</button> </li>
             <li><button onclick="location.href='<c:url value="/j_spring_security_logout" />'">Logout</button></li>
         </ul>
     </div><br>
<h3>Tickets</h3>
<div id="tickets">
    <table id="ticketstab" class="display">
        <thead>
        <tr>
            <th>ID</th>
            <th>Fee</th>
            <th>Duration</th>
            <th>State</th>
            <th>User Id</th>
            <th>Space Id</th>
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
        </tr>
        </tbody>
    </table>
</div>
     </div>
    </div>
</body>
</html>
