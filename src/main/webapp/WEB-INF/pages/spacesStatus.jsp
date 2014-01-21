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
    <script src="<c:url value="/resources/js/jquery-1.4.4.min.js" />"></script>
    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>
    <title>Tytuł!</title>
</head>
<body>


<table id="gradient-style" summary="Meeting Results" >
    <thead>
    <tr>
        <th scope="col">Segment</th>
        <th scope="col">Miejsce</th>
        <th scope="col">Stan</th>
        <th scope="col">Czujnik</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <td colspan="4">stopka test!!!</td>
    </tr>
    </tfoot>
    <tbody class="tbl">

    <c:forEach items="${spaces}" var="s">
        <tr>
            <td>${s.segment_id}</td>
            <td>${s.place}</td>
            <td>${s.state}</td>
            <td>${s.sensor}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>

<script type="text/javascript">
    myVar=setInterval(function(){add()},3000);
    //add();

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
                        var content = '<tbody class="tbl">';
                        for(var i=0;i<data.length;i++){
                            content+= '<tr><td>'+data[i].segment_id;
                            content+= '</td><td>'+data[i].place;
                            content+= '</td><td>'+data[i].state;
                            content+= '</td><td>'+data[i].sensor;
                            content+= '</td></tr>';
                        }
                        content+='</tbody>';
                        jq(".tbl").replaceWith(content);

                    });
        });
     //   window.setTimeout(function(){ document.location.reload(true); }, 5000);
   // setTimeout(add(), 10000);
    }

</script>

</body>
</html>
