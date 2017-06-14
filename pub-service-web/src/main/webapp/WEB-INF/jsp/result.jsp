<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String json=request.getAttribute("json").toString();
%>
<!DOCTYPE html>
<html lang="en">
    <head>
    </head>

    <body>
       ${json}
    </body>

</html>
