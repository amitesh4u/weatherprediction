<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NHMM Input Seeds</title>
<link rel="stylesheet" href="<c:url value="/static/style/style.css"/>" />
<script language="javascript" src="<c:url value="/static/script/jquery-1.11.0.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/myjquery.js"/>"></script>
<script type='text/javascript'>//<![CDATA[

	var formData;
	$(function() {
		$('form[name="nhmmForm"]').submit(
				function() {
					formData = JSON.stringify($('form[name="nhmmForm"]')
							.serializeObject());
					window.opener.$('#nhmmInputSeedsParamVal').text(formData);
					//$('#result').text(formData);
					window.close();
					return false;
				});
	});

//]]>
</script>
</head>
<body>
	<h2>NHMM Input Seeds Form</h2>
	<form action="" method="post" name="nhmmForm">
		<table border="0" cellpadding="0" cellspacing="10">
			<thead>
				<!-- universal table heading -->
				<tr>
					<th>Variable Name</th>
					<th>Input Value</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>No. of Hidden States</td>
					<td><input type="text" id="nhmmInputSeeds[noHiddenStates]" name="nhmmInputSeeds[noHiddenStates]" value="4"></input></td>
				</tr>
				<tr>
					<td>No. of Predictors</td>
					<td><input type="text" id="nhmmInputSeeds[noPredictors]" name="nhmmInputSeeds[noPredictors]" value="1"></input></td>
				</tr>
				<tr>
					<td>No of Restarts</td>
					<td><input type="text" id="nhmmInputSeeds[noRestarts]" name="nhmmInputSeeds[noRestarts]" value="10"></input></td>
				</tr>
				<tr>
					<td>EM Precision</td>
					<td><input type="text" id="nhmmInputSeeds[emPrecision]" name="nhmmInputSeeds[emPrecision]" value="5e-05"></input></td>
				</tr>
				<tr>
					<td>Random No. seed</td>
					<td><input type="text" id="nhmmInputSeeds[randomNoSeed]" name="nhmmInputSeeds[randomNoSeed]" value="0"></input></td>
				</tr>
				<tr>
					<td>No. of Simulations</td>
					<td><input type="text" id="nhmmInputSeeds[noSimulations]" name="nhmmInputSeeds[noSimulations]" value="10"></input></td>
				</tr>
			</tbody>
		</table>
		<p>
			<input type="submit" value="Submit" /> 
			<input type="reset" value="Reset"/> 
			<input type="button" value="Cancel" onclick="javascript:window.close();" />
		</p>
	</form>
<div style="width: 300px" id="result"></div>
</body>
</html>