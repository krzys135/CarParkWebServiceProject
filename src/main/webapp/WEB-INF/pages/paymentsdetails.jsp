<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
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
            $("#paymenttabs").dataTable({
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
        getPayments();
        myVar=setInterval(function(){getPayments()},10000);

        window.onbeforeunload = function(){
            clearTimeout(myVar);
        };


        var fl = window.location.href;
        var regex = new RegExp("/paymentdetails/\\d{1,}");
        var sp=regex.exec(fl);
        if(sp!=null) {
            sp = sp[0].substr(16);
        }
        else {sp =-1;
            }


        function getPayments() {
            jq(function() {
                jq.post("/main/ajax/getPayments",
                        {},
                        function(data){
                            jq('#paymenttabs').dataTable().fnClearTable();
                            for(var i=0;i<data.length;i++){

                                var userclick="<a href=\"/main/userdetails/"+data[i].user_id+"\">"+data[i].user_id+"</a>";
                                var ticketclick="<a href=\"/main/ticketdetails/"+data[i].ticket_id+"\">"+data[i].ticket_id+"</a>";
                                var idclick = "<a href=\"/main/paymentdetails/"+data[i].id+"\">"+data[i].id+"</a>";
                                var type = "";
                                if(data[i].ticket_id== null){
                                    type = "Recharge";
                                    ticketclick = "";
                                } else {
                                    type = "Charge"
                                }

                                jq('#paymenttabs').dataTable().fnAddData([idclick,type,timeConverter(data[i].tstmp),data[i].amount,data[i].paid,userclick,ticketclick],i+1);

                            }
                        });
            });

            jq(function () {
                if (sp != -1){
                jq.post("/main/ajax/getPaymentsSh",
                        {id: sp},
                        function (data) {
                            var userclick="<a href=\"/main/userdetails/"+data.user_id+"\">"+data.user_id+"</a>";
                            var ticketclick="<a href=\"/main/ticketdetails/"+data.ticket_id+"\">"+data.ticket_id+"</a>";
                            var type = "";
                            if(data.ticket_id== null){
                                type = "Recharge";
                            } else {
                                type = "Charge"
                            }
                            if(data.id!=null){
                            jq('#shortinfo').html("<h3>ID: "+data.id + " Date: "+timeConverter(data.tstmp) + " Type: "+type+" Amount: " + data.amount + " Paid: " + data.paid+" UserID: "+ userclick + " TicketID: "+ ticketclick +"</h3>");
                            }
                            else  jq('#shortinfo').html("");
                        });
                } else jq('#shortinfo').html("");
            });
        }
    </script>
</head>
<body id="dt_example">
<div id="container">
    <div id="demo_jui">
        <div id = "shortinfo">
            Loading...
        </div>
        <br>
        <table id="paymenttabs" class="display">
            <thead>
            <tr>
                <th>Id</th>
                <th>Type</th>
                <th>Date</th>
                <th>Amount</th>
                <th>Paid</th>
                <th>User</th>
                <th>Ticket</th>
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

            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
