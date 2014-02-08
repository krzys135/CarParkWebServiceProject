<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
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
        var email;
        <% if (request.getAttribute("email") != null){
        %>
        <%String email = (String) request.getAttribute("email"); %>
        email = "<%=email%>";
        <%}%>

        setPaymentTable();
        setTicketTable();

        function setPaymentTable() {
            jQuery(document).ready(function ($) {
                $("#paymenttab").dataTable({
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true
                });
            });
            setDataPayment();
            myVar = setInterval(function () {
                setDataPayment()
            }, 10000);

            window.onbeforeunload = function () {
                clearTimeout(myVar);
            };
        }

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

        function timeConverter(UNIX_timestamp){
            var a = new Date(UNIX_timestamp);
            var year = a.getFullYear();
            var month = a.getMonth()+1;
            var date = a.getDate();

            if (month < 10) {month = "0"+month;}
            if (date < 10) {date = "0"+date;}

            var time = date+'-'+month+'-'+year+' '+a.toTimeString().substr(0,8);
            return time;
        }

        function setDataPayment() {
            jq(function () {
                jq.post("/user/info",
                        {email: email},
                        function (data) {
                            jq('#paymenttab').dataTable().fnClearTable();
                            for (var i = 0; i < data.accountModel.paymentModelList.length; i++) {
                                jq('#paymenttab').dataTable().fnAddData([data.accountModel.paymentModelList[i].id, data.accountModel.paymentModelList[i].amount , data.accountModel.paymentModelList[i].paid, timeConverter(data.accountModel.paymentModelList[i].date), data.accountModel.paymentModelList[i].ticket_id], i+1);
                            }
                        });
            });
        }

        function setDataTicket() {
            jq(function () {
                jq.post("/user/info",
                        {email: email},
                        function (data) {
                            jq('#ticketstab').dataTable().fnClearTable();
                            for (var i = 0; i < data.ticketModelList.length; i++) {

                                var sek = data.ticketModelList[i].durationSeconds;
                                var sec_num = parseInt(sek, 10); // don't forget the second param
                                var hours   = Math.floor(sec_num / 3600);
                                var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
                                var seconds = sec_num - (hours * 3600) - (minutes * 60);

                                if (hours   < 10) {hours   = "0"+hours;}
                                if (minutes < 10) {minutes = "0"+minutes;}
                                if (seconds < 10) {seconds = "0"+seconds;}
                                var time    = hours+':'+minutes+':'+seconds;

                                jq('#ticketstab').dataTable().fnAddData([data.ticketModelList[i].id, data.ticketModelList[i].fee , time, data.ticketModelList[i].state, data.ticketModelList[i].space_id], i+1);
                            }
                        });
            });
        }

    </script>


</head>
<body>
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
</body>
</html>
