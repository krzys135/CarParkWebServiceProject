<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>CarPark App!</title>

    <link href="<c:url value="/resources/css/adder_style.css" />" media="all" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.4.4.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery-ui-1.10.3.custom.min.js" />" type="text/javascript"></script>

    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>

    <script>
        ticketInfo();

        function ticketInfo(){
            var fl = window.location.href;
            var regex = new RegExp("ticketdetails/\\d{1,}");
            var id=regex.exec(fl);
            id = id[0].substr(14);

            jq(function(){
                jq.post("/main/ajax/getTicketInfo",
                        {id: id},
                        function (data){
                            var sek = data.ticketModel.durationSeconds;
                            var sec_num = parseInt(sek, 10);
                            var hours   = Math.floor(sec_num / 3600);
                            var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
                            var seconds = sec_num - (hours * 3600) - (minutes * 60);

                            if (hours   < 10) {hours   = "0"+hours;}
                            if (minutes < 10) {minutes = "0"+minutes;}
                            if (seconds < 10) {seconds = "0"+seconds;}
                            var time    = hours+':'+minutes+':'+seconds;

                            jq('#id').text(data.ticketModel.id);
                            jq('#state').text(data.message);
                            jq('#duration').text(time);
                            jq('#userid').text(data.ticketModel.user_id);
                            jq('#spaceid').text(data.ticketModel.space_id);
                            jq('#paid').text(data.paymentModel.paid);
                            jq('#charge').text(data.message2);
                        }
                );
            });
        }
    </script>

</head>
<body>

<h3>Ticket Information</h3>
<table>
    <tr>
        <td>Id:</td>
        <td><div id="id"></div></td>
    </tr>
    <tr>
        <td>State:</td>
        <td><div id="state"></div></td>
    </tr>
    <tr>
        <td>Duration:</td>
        <td><div id="duration"></div></td>
    </tr>
    <tr>
        <td>User Id:</td>
        <td><div id="userid"></div></td>
    </tr>
    <tr>
        <td>Space Id:</td>
        <td><div id="spaceid"></div></td>
    </tr>
    <tr>
        <td>Paid:</td>
        <td><div id="paid"></div></td>
    </tr>
    <tr>
        <td>Charge:</td>
        <td><div id="charge"></div></td>
    </tr>
</table>

</body>
</html>
