<%@ page import="com.park.car.model.SpaceModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.JsonArray" %>
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
        myVar=setInterval(function(){add()},7000);

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
                            // data contains the result
                            // Assign result to the sum id
                            //var content = '<tbody class="tbl">';


                            <%--<% for(SpaceModel c: (ArrayList<SpaceModel>) %> data <%){ %>--%>

                            <%--<tr>--%>
                            <%--<td><%=c.getId()%></td>--%>
                            <%--<td><%=c.getPlace()%></td>--%>
                            <%--<td>asd</td>--%>
                            <%--<td>asdf</td>--%>
                            <%--</tr>--%>
                            <%--<% } %>--%>

                            <%--for(var i=0;i<data.length;i++){--%>
                            <%--content+= '<tr><td>'+data[i].segment_id;--%>
                            <%--content+= '</td><td>'+data[i].place;--%>
                            <%--content+= '</td><td>'+data[i].state;--%>
                            <%--content+= '</td><td>'+data[i].sensor;--%>
                            <%--content+= '</td></tr>';--%>
                            <%--}--%>
                            <%--content+='</tbody>';--%>

                            <%--jq(".tbl").replaceWith(content);--%>
                           alert("load");
                            /*var content = '<tbody class="tbl">';*/

                            <%--<%--%>

                        <%--for(SpaceModel c: (ArrayList<SpaceModel>) %> data <%){ %>--%>

                        <%--content +='<tr><td><%=c.getId()%>';--%>
                        <%--content +='</td><td><%=c.getPlace()%>';--%>
                        <%--content +='</td><td><%=c.getState()%>';--%>
                        <%--content +='</td><td><%=c.getSegment_id()%>';--%>
                        <%--content +='</td></tr>';--%>
                            <%--<% } %>--%>
                        <%--content +='</tbody>';--%>
                            var myJsonString = JSON.stringify(data);
                            jq(".tbl").replaceWith(myJsonString);
                            //var xxx = session.setAttribute("fromjs", data);
                            <%
                            Object object=null;
                            JSONArray arrayObj=null;
                            JSONParser jsonParser=new JSONParser();
                            object=jsonParser.parse(data);
                            arrayObj=(JSONArray) object;
                            System.out.println("Json object :: "+arrayObj);
                            %>
                        });
            });
        }

    </script>
</head>
<body id="dt_example">


<%--<table id="gradient-style" summary="Meeting Results" >--%>
    <%--<thead>--%>
    <%--<tr>--%>
        <%--<th scope="col">Segment</th>--%>
        <%--<th scope="col">Miejsce</th>--%>
        <%--<th scope="col">Stan</th>--%>
        <%--<th scope="col">Czujnik</th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tfoot>--%>
    <%--<tr>--%>
        <%--<td colspan="4">stopka test!!!</td>--%>
    <%--</tr>--%>
    <%--</tfoot>--%>
    <%--<tbody class="tbl">--%>

    <%--<c:forEach items="${spaces}" var="s">--%>
        <%--<tr>--%>
            <%--<td>${s.segment_id}</td>--%>
            <%--<td>${s.place}</td>--%>
            <%--<td>${s.state}</td>--%>
            <%--<td>${s.sensor}</td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>

    <%--</tbody>--%>
<%--</table>--%>

<div id="container">
    <div id="demo_jui">
        <table id="companies" class="display">
            <thead>
            <tr>
                <th>id</th>
                <th>pl</th>
                <th>stat</th>
                <th>segid</th>
            </tr>
            </thead>
            <tbody class="tbl">
            <%--<c:forEach items="${spaces}" var="s">--%>
            <%--<script>alert("load")</script>--%>
                    <%--<%--%>
                        <%--if( request.getAttribute("fromjs") != null) {--%>
                        <%--for(SpaceModel c: (ArrayList<SpaceModel>) request.getAttribute("fromjs")){ %>--%>

                    <%--<tr>--%>
                        <%--<td><%=c.getId()%></td>--%>
                        <%--<td><%=c.getPlace()%></td>--%>
                        <%--<td><%=c.getState()%></td>--%>
                        <%--<td><%=c.getSegment_id()%></td>--%>
                    <%--</tr>--%>
                    <%--<% }} %>--%>




                <%--<% for(SpaceModel c: (ArrayList<SpaceModel>) request.getAttribute("spaces")){ %>--%>

                <%--<tr>--%>
                    <%--<td><%=c.getId()%></td>--%>
                    <%--<td><%=c.getPlace()%></td>--%>
                    <%--<td>asd</td>--%>
                    <%--<td>asdf</td>--%>
                <%--</tr>--%>
                <%--<% } %>--%>

            <%--</c:forEach>--%>
            </tbody>
        </table>
    </div>
</div>




</body>
</html>
