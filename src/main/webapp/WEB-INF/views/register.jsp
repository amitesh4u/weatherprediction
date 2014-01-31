<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<title>Registration</title>
</head>
<body>
<div style="height:100px"></div>
<div><center><h3>Weather Prediction</h3></center></div>
        <form:form method="post" action="Registration" commandName = "registrationForm">
            <center>
            <!-- form:errors path="*" cssClass="errorblock" element="div"/> --><!-- Shows errors together --> 
            <table border="0" align="center">
                <thead>
                    <tr>
                        <th colspan="3" align="left">Enter Information Here (All Fields are mandatory)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr align="center">
        				<td colspan="3"><form:errors path="general" cssClass="error" /></td>
        			</tr>
                    <tr>
                        <td><form:label path="firstName">First Name : </form:label></td>
                        <td><form:input path="firstName" size="15" maxlength="50" type="text"  value="" /></td>
                        <td><form:errors path="firstName" cssClass="error" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="lastName">Last Name : </form:label></td>
                        <td><form:input path="lastName" size="15" maxlength="50" type="text"  value="" /></td>
                        <td><form:errors path="lastName" cssClass="error" /></td>
                    </tr>
                    <tr>
						<td><form:label path="emailId">Email : </form:label></td>
						<td><form:input path="emailId" size="15" maxlength="100" type="text" value="" /></td>
						<td><form:errors path="emailId" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path="password">Password (min 8 characters) : </form:label></td>
						<td><form:input path="password" size="15" type="password" value="" /></td>
						<td><form:errors path="password" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path="confirmPassword">Confirm Password : </form:label></td>
						<td><form:input path="confirmPassword" size="15" type="password" value="" /></td>
						<td><form:errors path="confirmPassword" cssClass="error" /></td>
					</tr>
					<tr>
                        <td><input type="submit" value="Submit" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                    <tr>
                        <td colspan="2"><br/><b>Already registered!! Please <a href="Login">Login Here</a></b></td>
                    </tr>
                </tbody>
            </table>
            </center>
        </form:form>
    </body>
</html>