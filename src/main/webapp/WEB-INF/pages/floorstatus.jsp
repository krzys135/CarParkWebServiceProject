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
            $("#floors").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });
    </script>
    <script type="text/javascript">
        getfloor();
        myVar=setInterval(function(){getfloor()},10000);

        window.onbeforeunload = function(){
            clearTimeout(myVar);
        };
        function getfloor() {
            jq(function() {
                jq.post("/main/ajax/getFloors",
                        {},
                        function(data){
                        jq('#floors').dataTable().fnClearTable();
                        for(var i=0;i<data.length;i++){

                                var idclick="<a href=\"/main/spacesStatus/floor/"+data[i].id+"\">"+data[i].floornumber+"</a>";
                                jq('#floors').dataTable().fnAddData([idclick,data[i].freespaces,data[i].allspaces-data[i].freespaces,data[i].allspaces],i+1);
                        }
                    });
            });
        }
    </script>
</head>
<body id="dt_example">
<div id="container">
    <div id="demo_jui">
        <h3>Floors</h3><br>
        <table id="floors" class="display">
            <thead>
            <tr>
                <th>Floor</th>
                <th>Free</th>
                <th>Occupied</th>
                <th>All</th>
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
