<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", -1);
%>
<%
	session = request.getSession(false); //false: new session will not be created  
	String fName = (String) session.getAttribute("fName");
	String lName = (String) session.getAttribute("lName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<c:url value="/static/style/style.css"/>"/> 
<title>Home</title>
</head>
<body>
	<h1>Welcome ${fName} ${lName}!</h1>

	<P>Current time on the Server is ${serverTime}.</P>
	<P>
		<a href="Logout">Logout</a>
	</P>
</body>
</html>
