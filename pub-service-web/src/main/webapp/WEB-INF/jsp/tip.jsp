<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String json=request.getAttribute("tip").toString();
%>
<!DOCTYPE html>
<html lang="en">
    <head>
    </head>

    <body>
       ${tip}
    </body>

</html>