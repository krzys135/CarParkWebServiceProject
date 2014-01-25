<%@ page import="com.google.gson.JsonArray" %>
<%@ page import="sun.org.mozilla.javascript.internal.json.JsonParser" %>
<%@ page import="com.google.gson.JsonElement" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="com.park.car.model.SpaceModel" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 14.12.13
  Time: 08:54
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Tytuł!</title>

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
        myVar=setInterval(function(){add()},3000);

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

                            for(var i=0;i<data.length;i++){
                                //alert(data[i].id+ " " +data[i].segment_id+ " " +data[i].place+ " " +data[i].state+ " " +data[i].sensor);
                                var xxxxxx="<a href=\"onet.pl/?asd="+data[i].id+"\">kjhgj</a>";
                                jq('#companies').dataTable().fnUpdate([data[i].id,data[i].segment_id,data[i].place,data[i].state,xxxxxx,data[i].sensor],i);
                            }
                        });
            });
        }

    </script>
</head>
<body id="dt_example">
<div id="container">
    <div id="demo_jui">
        <table id="companies" class="display">
            <thead>
            <tr>
                <th>id</th>
                <th>pl</th>
                <th>stat</th>
                <th>segid</th>
                <th>asd</th>
                <th>klik</th>
            </tr>
            </thead>
            <tbody class="tbl">
                <%  SpaceModel item =null;
                    ArrayList<SpaceModel> l = (ArrayList)request.getAttribute("spaces");
                    for(Iterator<SpaceModel> c = l.iterator(); c.hasNext(); ) {
                    item = c.next();
                    %>
                <tr>
                    <td>asdf</a></td>
                    <td><%=item.getPlace()%></td>
                    <td>asd</td>
                    <td>asdf</td>
                    <td>asdfge3</td>
                    <td><a href="http://onet.com"> hello world </a></td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
