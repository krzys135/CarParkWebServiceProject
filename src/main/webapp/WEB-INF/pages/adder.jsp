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

            jq('#ids').keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    jq('#idserr').html("Wprowadź tylko liczby").show().fadeOut(6000);
                    return false;
                }
            });

        });

        addFloor();
        addSegment();
        addSpace();
        addCash();
        chnageSensor();

        function addCash() {
            jq(function () {
                jq('#button').click(function () {
                    if (jq('#id').val() != '' && jq('#ammount').val() != '') {
                        var id = jq('#id').val();
                        var am = jq('#ammount').val();
                        jq.get("/jdbc/addcash/id/" + id + "/amount/" + am + "/p",
                                function (data) {
                                    jq('#msg').html(data.message).show().fadeOut(6000);
                                });
                        jq('#id').val('');
                        jq('#ammount').val('');
                    } else {
                        jq('#msg').html("Uzupełnij wszytkie pola").show().fadeOut(6000);
                    }

                });

            });
        }

        function chnageSensor() {
            jq(function () {
                jq('#change').click(function () {
                    if (jq('#ids').val() != '' && jq('#state').val() != '') {
                        var id = jq('#ids').val();
                        var s = jq('#state').find(":selected").text();
                        jq.get("/jdbc/setsensor/id/" + id + "/state/" + s + "",
                                function (data) {
                                    jq('#ids').val('');
                                    jq('#msg').html(data.message).show().fadeOut(6000);
                                });
                    } else {
                        jq('#msg').html("Uzupełnij wszytkie pola").show().fadeOut(6000);
                    }
                });

            });
        }

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
                                    segmentid: segmentid
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
<div id="logout">
    <button onclick="location.href='<c:url value="/j_spring_security_logout" />'">Wyloguj</button>
</div>
<div id="msg"></div>


<div id="left">
    <div id="addfloor">
        <h4>Dodawanie piętra</h4>
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
        <h4>Dodawanie sektora</h4>
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
        <h4>Dodawanie miejsca</h4>
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
</div>

<div id="right">
    <div id="addcash">
        <h4>Doładowanie konta użytkownika</h4>
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
        <h4>Zmiana sensora miejsca parkingowego</h4>
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
                <td>
                    <select id="state">
                        <option value="zero">0</option>
                        <option value="one">1</option>
                    </select>
                </td>
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
</div>


</body>
</html>
