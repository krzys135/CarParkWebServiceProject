<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

    <link href="<c:url value="/css/main/main_page.css" />" rel="stylesheet">
    <script src="<c:url value="/js/jquery-1.4.4.min.js" />"  type="text/javascript"></script>

    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>

    <script type="application/javascript">
        jq(document).ready(function($){
            jq("p").click(function($){
                jq(this).hide();
            });
        });

        jq(document).ready(function() {
            jq('.tabs').each(function() {
                var $ul = jq(this);
                var $li = $ul.children('li');
                $li.each(function() {
                    var $trescTaba = jq(jq(this).children('a').attr('href'));
                    if (jq(this).hasClass('active')) {
                        $trescTaba.show();
                    } else {
                        $trescTaba.hide();
                    }
                    jq(this).append('<span class="right"></span>');
                });

                $li.click(function() {jq(this).children('a').click()});
                $li.children('a').click(function() {
                    $li.removeClass('active');
                    $li.each(function() {
                        jq(jq(this).children('a').attr('href')).hide();
                    });
                    jq(this).parent().addClass('active');
                    jq(jq(this).attr('href')).show();
                    return false;
                });
            });
        });

    </script>

</head>

<body>

<ul class="tabs">
    <li class="active"><a href="#tab-1" class="active">Użytkownicy</a></li>
    <li><a href="#tab-2">Zakładka 2</a></li>
    <li><a href="#tab-3">Zakładka 3</a></li>
</ul>

<div id="tab-1" class="tab">

</div>
<div id="tab-2" class="tab">22</div>
<div id="tab-3" class="tab">33</div>

<div id="msg">
</div>

<%--<div style="border: 1px solid #ccc; width: 250px;">
    Add Two Numbers:

    <input id="inputNumber1" name="inputNumber1" type="text" size="5"> +
    <input id="inputNumber2" name="inputNumber2" type="text" size="5">
    <input type="submit" value="Add" onclick="add()" />

    Sum: <span id="sum">(Result will be shown here)</span>
</div>


<script type="text/javascript">

    function add() {
        jq(function() {
            jq.post("/main/ajax/add",
                    {  inputNumber1:  jq("#inputNumber1").val(),
                        inputNumber2:  jq("#inputNumber2").val() },
                    function(data){
                        // data contains the result
                        // Assign result to the sum id
                        jq("#sum").replaceWith('<span id="sum">'+ data + '</span>');
                    });
        });
    }

</script>--%>

<div id="logged">
    <h3>Message : ${message}</h3>
    <h3>Username : ${username}</h3>
    <a href="<c:url value="/j_spring_security_logout" />" >Wyloguj</a>
</div>

</body>

</html>