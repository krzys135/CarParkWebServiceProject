<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.4.4.min.js"/>"></script>
    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>
 </head>
<body>
<div id="msg"></div>

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