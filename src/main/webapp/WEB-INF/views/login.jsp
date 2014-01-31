<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%  
    response.setHeader("Cache-Control","no-cache");  
    response.setHeader("Cache-Control","no-store");  
    response.setDateHeader("Expires", -1);  
%>
<%
// 		session = request.getSession(false); //false: new session will not be created  
// 		if (session != null && session.getAttribute("uname") != null && session.getAttribute("is_logged_in") != null) {
// 			String redirectURL = "/homePage";
// 			response.sendRedirect(redirectURL);
// 		}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<c:url value="/static/style/style.css"/>"/> 
<title>Sign in</title>
</head>
 <body>
    <div style="height:100px"></div>
    <div><center><h3>Weather Prediction</h3></center></div>
    <div>
    <form:form action="Login" method="post" commandName="loginForm">
        <table align="center">
        	<tr align="center">
        		<td colspan="3"><form:errors path="general" cssClass="error" /></td>
        	</tr>
            <tr align="center">
                <td> <form:label path="emailId">Email Id : </form:label></td>
                <td> <form:input path="emailId" size="15" maxlength="100" type="text" value="" /></td>
                <td><form:errors path="emailId" cssClass="error" /></td> 
            </tr>
            <tr align="center">
                <td> <form:label path="password">Password : </form:label> </td>
                <td> <form:input path="password" size="15" maxlength="50" type="password"  value="" /> </td>
                <td><form:errors path="password" cssClass="error" /></td> 
            </tr>
            <tr align="center"><td><input type="submit" value="Login" /></td><td><input type="reset" value="Reset" /></td></tr>
            <tr align="center"><td colspan="2"><br/><b>Not Yet Registered!! <a href="RegistrationPage">Register Here</a></b></td></tr>
        </table>
        
    </form:form>
    </div>
</body>
</html>