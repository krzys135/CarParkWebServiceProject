<!DOCTYPE HTML SYSTEM>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>CarPark App!</title>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.4.4.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery-ui-1.10.3.custom.min.js" />" type="text/javascript"></script>
    <link href="<c:url value="/resources/css/demo_page.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/demo_table.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/demo_table_jui.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/jquery-ui.css"  />" media="all" rel="stylesheet">
    <link href="<c:url value="/resources/css/jquery-ui-1.7.2.custom.css" />" media="all" rel="stylesheet">

    <script type="text/javascript">
        var jq = jQuery.noConflict();
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

        var fl = window.location.href;
        var regex = new RegExp("userdetails/\\d{1,}");
        var id=regex.exec(fl);
        if(id!=null) {
            id = id[0].substr(12);
        }
        else {
            id =-1;
        }


        jQuery(document).ready(function ($) {
                $("#paymenttab").dataTable({
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true
                });
            });

        jQuery(document).ready(function ($) {
            $("#ticketstab").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });

        jQuery(document).ready(function ($) {
            $("#userstab").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });
        getUserDetails();
        myVar = setInterval(function () {
            getUserDetails()
        }, 10000);

        window.onbeforeunload = function () {
            clearTimeout(myVar);
        };



        function getUserDetails() {
            jq(function () {
                jq.post("/main/ajax/getUserInfo",
                        {id: id},
                        function (data) {
                            if (id ==-1) {
                                jq("#tohide").css('display', 'none');
                            jq('#userstab').dataTable().fnClearTable();
                            for (var i = 0; i < data.length; i++) {
                                var emailclick = "<a href=\"/main/userdetails/" + data[i].id +"\">" + data[i].email + "</a>";
                                jq('#userstab').dataTable().fnAddData([data[i].id, emailclick , data[i].accountModel.amount], i+1);
                            }
                            }
                            else {
                                jq(".allus").css('display', 'none');

                                jq('#ticketstab').dataTable().fnClearTable();
                                for (var i = 0; i < data[0].ticketModelList.length; i++) {

                                    var sek = data[0].ticketModelList[i].durationSeconds;
                                    var sec_num = parseInt(sek, 10); // don't forget the second param
                                    var hours   = Math.floor(sec_num / 3600);
                                    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
                                    var seconds = sec_num - (hours * 3600) - (minutes * 60);

                                    if (hours   < 10) {hours   = "0"+hours;}
                                    if (minutes < 10) {minutes = "0"+minutes;}
                                    if (seconds < 10) {seconds = "0"+seconds;}
                                    var time    = hours+':'+minutes+':'+seconds;
                                    var idclick="<a href=\"/main/placestatus/place/"+data[0].ticketModelList[i].space_id+"\">"+data[0].ticketModelList[i].space_id+"</a>";
                                    jq('#ticketstab').dataTable().fnAddData([data[0].ticketModelList[i].id, data[0].ticketModelList[i].fee , time, data[0].ticketModelList[i].state, idclick], i+1);

                                }

                                jq('#paymenttab').dataTable().fnClearTable();
                                for (var i = 0; i < data[0].accountModel.paymentModelList.length; i++) {
                                    var ticketclick=data[0].accountModel.paymentModelList[i].ticket_id;
                                    if (ticketclick!=null) {
                                        ticketclick="<a href=\"/main/ticketdetails/id/"+data[0].accountModel.paymentModelList[i].ticket_id+"\">"+data[0].accountModel.paymentModelList[i].ticket_id+"</a>";
                                    } else {ticketclick="";}
                                    jq('#paymenttab').dataTable().fnAddData([data[0].accountModel.paymentModelList[i].id, data[0].accountModel.paymentModelList[i].amount , data[0].accountModel.paymentModelList[i].paid, timeConverter(data[0].accountModel.paymentModelList[i].date), ticketclick], i+1);
                                }}
                                if (hours   < 10) {hours   = "0"+hours;}
                                if (minutes < 10) {minutes = "0"+minutes;}
                                if (seconds < 10) {seconds = "0"+seconds;}
                                var time    = hours+':'+minutes+':'+seconds;

                                jq('#ticketstab').dataTable().fnAddData([data.ticketModelList[i].id, data.ticketModelList[i].fee , time, data.ticketModelList[i].state, data.ticketModelList[i].space_id], i+1);
                            
                        });
            });

            jq(function () {
                jq.post("/main/ajax/getShortUserInfo",
                        {id: id},
                        function (data) {
                            jq('#shortinfo').html("ID: " + data.id+"     Email: "+data.email+"   Amount:<b>"+data.accountModel.amount+"</b> zł");

                        });
            });



        }



    </script>
</head>
<body id="dt_example">
<div id="container">
    <div id="demo_jui">
        <div id = "tohide">
        <div id = "shortinfo">
        Loading...
        </div>
        <br>
        Tickets<br>
            <div id="tickets">
                <table id="ticketstab" class="display">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fee</th>
                        <th>Duration</th>
                        <th>State</th>
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
                    </tr>
                    </tbody>
                </table>
            </div>
        <br>
        Payment activities<br>
        <div id="payment">
            <table id="paymenttab" class="display">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Amount</th>
                    <th>Paid</th>
                    <th>Date</th>
                    <th>Ticket Id</th>
                </tr>
                </thead>
                <tbody class="tbl">
                <tr>
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
        <div class="allus">
        <div id="users">
            <table id="userstab" class="display">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Balance</th>
                </tr>
                </thead>
                <tbody class="tbl">
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
        </div>
</div>
</div>
</body>
</html>
