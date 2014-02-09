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
    <title>CarPark App</title>

    <link href="<c:url value="/resources/css/demo_page.css" />" rel="stylesheet"  />
    <link href="<c:url value="/resources/css/demo_table.css" />" rel="stylesheet"  />
    <link href="<c:url value="/resources/css/demo_table_jui.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/jquery-ui.css"  />"  media="all" rel="stylesheet"  />
    <link href="<c:url value="/resources/css/jquery-ui-1.7.2.custom.css" />" media="all" rel="stylesheet" />

    <script type="text/javascript">
        jQuery(document).ready(function ($) {
            $("#companies").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });
    </script>
    <script type="text/javascript">
        add();
        myVar=setInterval(function(){add()},10000);

        window.onbeforeunload = function(){
            clearTimeout(myVar);
        };
        var fl = window.location.href;
        var regex = new RegExp("/floor/\\d");
        var flo=regex.exec(fl);
        flo = flo[0].substr(7);
        function add() {
            jq(function() {

                jq.post("/main/ajax/add",
                        {floor:  flo },
                        function(data){
                            jq.post("/main/ajax/getSegments",
                                    { },
                                    function(segments){

                                    jq('#companies').dataTable().fnClearTable();


                                    for(var i=0;i<data.length;i++){

                                        //var xxxxxx="<a href=\"onet.pl/?asd="+data[i].id+"\">kjhgj</a>";

                                        var found ="0";
                                        for(var iter=0;iter<segments.length;iter++){
                                        if(found=="0"){
                                            if(segments[iter].id==data[i].segment_id) {
                                                found=segments[iter].seg;
                                                var icon;
                                                if(data[i].sensor=="0"){
                                                    icon ="<p style=\"font-size:120%;color: green\"><b>•</b></p>";
                                                }
                                                else {icon ="<p style=\"font-size:120%;color: #ff0000\"><b>•</b></p>";}
                                                var idclick="<a href=\"/main/placestatus/place/"+data[i].id+"\">"+found+""+data[i].place+"</a>";
                                                jq('#companies').dataTable().fnAddData([data[i].id,idclick,data[i].state,icon],i+1);
                                            }
                                        }
                                        }
                                    }

                            });
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
        <h3>Spaces</h3><br>
        <table id="companies" class="display">
            <thead>
            <tr>
                <th>ID</th>
                <th>Place</th>
                <th>State</th>
                <th>Sensor</th>
            </tr>
            </thead>
            <tbody class="tbl">
                <tr>
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
