<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<c:url value="/static/style/style.css"/>"/> 
<title>Registration Status</title>
</head>
<body>
<% 
	Object status = request.getAttribute("status");
	if ( status != null && status.toString().equalsIgnoreCase("t")){
       out.println("<div style='height:100px'></div><div><center><h3>Weather Prediction</h3></center></div><div align='center'><font color = 'green' size='4'>Registration Successful. Please </font><a href=\"Login\">Login Here</a></div>");
	}else{
	   out.println("<div style='height:100px'></div><div><center><h3>Weather Prediction</h3></center></div><div align='center'><font color = 'red' size='4'>There is some issue. Please try again to </font><a href=\"RegistrationPage\">Register Here</a></div>");	
	}
%>
</body>
</html>