<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>CarPark App!</title>

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

        jq(document).ready(function () {

            jq('#id').keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    jq('#iderr').html("Wprowadź tylko liczby").show().fadeOut(6000);
                    return false;
                }
            });

            jq('#ammount').keypress(function (e) {
                if ((e.which != 46 || jq(this).val().indexOf('.') != -1) && (e.which < 48 || e.which > 57) || (e.which == 46 && jq(this).caret().start == 0)) {
                    jq('#ammounterr').html("Wprowadź kwote w formacie .00 ").show().fadeOut(6000);
                    return false;
                }
            });

            jq('#ammount').keyup(function (e) {
                if (jq(this).val().indexOf('.') == 0) {
                    jq(this).val(jq(this).val().substring(1));
                }
            });

            jq('#state').keypress(function (e) {
                var s = jq('#state').val;
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 49)) {
                    jq('#stateerr').html("Wprowadź tylko jedną liczbę z przedziału 0 - 1").show().fadeOut(6000);
                    return false;
                }
                if (s.length == 2) {
                    jq('#stateerr').html("Wprowadź tylko jedną liczbę z przedziału 0 - 1").show().fadeOut(6000);
                    return false;
                }
            });

            jq('#ids').keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    jq('#idserr').html("Wprowadź tylko liczby").show().fadeOut(6000);
                    return false;
                }
            });

        });

        addCash();
        chnageSensor();

        function addCash() {
            jq(function () {
                jq('#button').click(function () {
                    var id = jq('#id').val();
                    var am = jq('#ammount').val();
                    jq.get("/jdbc/addcash/id/" + id + "/amount/" + am + "/p",
                            function (data) {
                                jq('#msg').html(data.message).show().fadeOut(6000);
                            });
                    jq('#id').val('');
                    jq('#ammount').val('');
                });

            });
        }

        function chnageSensor() {
            jq(function () {
                jq('#change').click(function () {
                    var id = jq('#ids').val();
                    var s = jq('#state').val();
                    jq.get("/jdbc/setsensor/id/" + id + "/state/" + s + "",
                            function (data) {
                                jq('#ids').val('');
                                jq('#state').val('');
                                jq('#msg').html(data.message).show().fadeOut(6000);
                            });
                });

            });
        }

    </script>

</head>
<body>
<div id="msg"></div>

<div id="addcash">
    Doładowanie konta użytkownika
    <table>
        <tr>
            <td>Id klienta:</td>
            <td><input type="text" name="id" id="id"></td>
            <td>
                <div id="iderr"></div>
            </td>
        </tr>
        <tr>
            <td>Kwota:</td>
            <td><input type="text" id="ammount" name="ammount"/></td>
            <td>
                <div id="ammounterr"></div>
            </td>
        </tr>
        <tr>
            <td>
                <button id="button">Doładuj</button>
            </td>
        </tr>
    </table>
</div>

<div id="changesensor">
    Zmiana sensora miejsca parkingowego
    <table>
        <tr>
            <td>Id miejsca:</td>
            <td><input type="text" name="ids" id="ids"></td>
            <td>
                <div id="idserr"></div>
            </td>
        </tr>
        <tr>
            <td>Stan:</td>
            <td><input type="text" id="state" name="state" maxlength="1"/></td>
            <td>
                <div id="stateerr"></div>
            </td>
        </tr>
        <tr>
            <td>
                <button id="change">Zmień</button>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
