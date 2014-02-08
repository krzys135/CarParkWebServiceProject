<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <script type="text/javascript">
        var usermodel;

        <% if (request.getAttribute("usermodel") == null){
        %>
        usermodel = false;
        <%}else if (request.getAttribute("usermodel") != null){
        %>
        usermodel = true;
        <%}%>

        if (usermodel === false) {
            setUsersTable();
        } else if (usermodel === true) {
            jq(function () {
                jq('#users').remove();
                jq('#usersinfo').text("dupa");
            });
        }

        function setUsersTable() {
            jQuery(document).ready(function ($) {
                $("#userstab").dataTable({
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true
                });
            });
            getusers();
            myVar = setInterval(function () {
                getusers()
            }, 10000);

            window.onbeforeunload = function () {
                clearTimeout(myVar);
            };
        }

        function getusers() {
            jq(function () {
                jq.post("/user/getallusers",
                        {},
                        function (data) {
                            jq('#userstab').dataTable().fnClearTable();
                            for (var i = 0; i < data.length; i++) {
                                var emailclick = "<a href=\"/user/allinfo?email=" + data[i].email +"\">" + data[i].email + "</a>";
                                jq('#userstab').dataTable().fnAddData([data[i].id, emailclick , data[i].accountModel.amount], i+1);
                            }
                        });
            });
        }

    </script>

</head>
<body>
<h3>Users</h3>
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
<div id="usersinfo"></div>
</body>
</html>
