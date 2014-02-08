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

            jq('#number, #floorid, #space, #segmentid').keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    jq('#msg').html("Wprowadź tylko liczby").show().fadeOut(6000);
                    return false;
                }
            });

            jq('#segment').keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 65 || e.which > 90)) {
                    jq('#msg').html("Wprowadź tylko duże litery").show().fadeOut(6000);
                    return false;
                }
            });

        });

        addFloor();
        addSegment();
        addSpace();

        function addFloor() {
            jq(function () {
                jq('#btnaddfloor').click(function () {
                    if (jq('#number').val() != '') {
                        var n = jq('#number').val();
                        jq.post("/add/floor/" + n + "/",
                                {},
                                function (data) {
                                    jq('#msg').html(data.message).show().fadeOut(6000);
                                });
                        jq('#number').val('');
                    } else {
                        jq('#msg').html("Uzupełnij wszytkie pola").show().fadeOut(6000);
                    }

                });

            });
        }

        function addSegment() {
            jq(function () {
                jq('#btnaddsegmnet').click(function () {
                    if (jq('#floorid').val() != '' && jq('#segment').val() != '') {
                        var f = jq('#floorid').val();
                        var s = jq('#segment').val();
                        jq.post("/add/segment",
                                {
                                    floorid: f,
                                    segment: s
                                },
                                function (data) {
                                    jq('#floorid').val('');
                                    jq('#segment').val('');
                                    jq('#msg').html(data.message).show().fadeOut(6000);
                                });
                    } else {
                        jq('#msg').html("Uzupełnij wszytkie pola").show().fadeOut(6000);
                    }
                });

            });
        }

        function addSpace() {
            jq(function () {
                jq('#btnaddsspace').click(function () {
                    if (jq('#space').val() != '' && jq('#segmentid').val() != '') {
                        var space = jq('#space').val();
                        var segmentid = jq('#segmentid').val();

                        jq.post("/add/space",
                                {
                                    space: space,
                                    segmentid: segmentid,
                                },
                                function (data) {
                                    jq('#space').val('');
                                    jq('#segmentid').val('');
                                    jq('#msg').html(data.message).show().fadeOut(6000);
                                });
                    } else {
                        jq('#msg').html("Uzupełnij wszytkie pola").show().fadeOut(6000);
                    }
                });

            });
        }

    </script>

</head>
<body>
<div id="msg"></div>

<div id="addfloor">
    Dodawanie piętra
    <table>
        <tr>
            <td>Nr pietra:</td>
            <td><input type="number" name="number" id="number"></td>
            <td>
                <div id="numbererr"></div>
            </td>
        </tr>
        <tr>
            <td>
                <button id="btnaddfloor">Dodaj pietro</button>
            </td>
        </tr>
    </table>
</div>

<div id="addsegment">
    Dodawanie sektora
    <table>
        <tr>
            <td>Id piętra:</td>
            <td><input type="number" name="floorid" id="floorid"></td>
            <td>
                <div id="flooriderr"></div>
            </td>
        </tr>
        <tr>
            <td>Nazwa sektora (dużą literą):</td>
            <td><input type="text" id="segment" name="segment" maxlength="1"/></td>
            <td>
                <div id="segmenterr"></div>
            </td>
        </tr>
        <tr>
            <td>
                <button id="btnaddsegmnet">Dodaj sektor</button>
            </td>
        </tr>
    </table>
</div>

<div id="addspace">
    Dodawanie miejsca
    <table>
        <tr>
            <td>Numer miejsca:</td>
            <td><input type="number" name="space" id="space"></td>
            <td>
                <div id="spaceerr"></div>
            </td>
        </tr>
        <tr>
            <td>Id sektora:</td>
            <td><input type="number" id="segmentid" name="segmentid"/></td>
            <td>
                <div id="segmentiderr"></div>
            </td>
        </tr>
        <tr>
            <td>
                <button id="btnaddsspace">Dodaj miejsce</button>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
