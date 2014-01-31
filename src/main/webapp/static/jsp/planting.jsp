<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Planting Details</title>
<link rel="stylesheet" href="<c:url value="/static/style/style.css"/>" />
<script language="javascript"
	src="<c:url value="/static/script/jquery-1.11.0.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/script/myjquery.js"/>"></script>
<script type='text/javascript'>
	//<![CDATA[

	var formData;
	$(function() {
		$('form[name="plantingForm"]').submit(
				function() {
					formData = JSON.stringify($('form[name="plantingForm"]')
							.serializeObject());
					window.opener.$('#pdParamVal').text(formData);
					//$('#result').text(formData);
					window.close();
					return false;
				});
	});

	//]]>
</script>
</head>
<body>
	<h2>Planting details Form</h2>
	<form action="" method="post" name="plantingForm">
		<table border="0" cellpadding="0" cellspacing="0">
			<thead>
				<!-- universal table heading -->
				<tr>
					<th>Level</th>
					<th>Description</th>
					<th>Cultivar</th>
					<th>Field</th>
					<th>Soil. Anal.</th>
					<th>Init. Cond.</th>
					<th>Plant</th>
					<th>Irrigat</th>
					<th>Fertil</th>
					<th>Resid</th>
					<th>Chem. App</th>
					<th>Tillage</th>
					<th>Env.Mod</th>
					<th>Harv</th>
					<th>Sim Controller</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" name="planting[0][level]" value=1></td>
					<td><input type="text" name="planting[0][desc]"
						value="0-0-0 NPK"></td>
					<td><input type="text" name="planting[0][cultivar]" value=1></td>
					<td><input type="text" name="planting[0][field]" value=1></td>
					<td><input type="text" name="planting[0][soilanalysis]"
						value=1></td>
					<td><input type="text" name="planting[0][initcond]" value=1></td>
					<td><input type="text" name="planting[0][plant]" value=0></td>
					<td><input type="text" name="planting[0][irrigation]" value=1></td>
					<td><input type="text" name="planting[0][fertilizer]" value=1></td>
					<td><input type="text" name="planting[0][residual]" value=1></td>
					<td><input type="text" name="planting[0][chemapp]" value=0></td>
					<td><input type="text" name="planting[0][tillage]" value=0></td>
					<td><input type="text" name="planting[0][envmod]" value=0></td>
					<td><input type="text" name="planting[0][harvest]" value=0></td>
					<td><input type="text" name="planting[0][simcont]" value=1></td>
				</tr>
				<tr>
					<td><input type="text" name="planting[1][level]" value=2></td>
					<td><input type="text" name="planting[1][desc]"
						value="38 kg ha -1 of appl"></td>
					<td><input type="text" name="planting[1][cultivar]" value=1></td>
					<td><input type="text" name="planting[1][field]" value=1></td>
					<td><input type="text" name="planting[1][soilanalysis]"
						value=1></td>
					<td><input type="text" name="planting[1][initcond]" value=1></td>
					<td><input type="text" name="planting[1][plant]" value=1></td>
					<td><input type="text" name="planting[1][irrigation]" value=1></td>
					<td><input type="text" name="planting[1][fertilizer]" value=1></td>
					<td><input type="text" name="planting[1][residual]" value=1></td>
					<td><input type="text" name="planting[1][chemapp]" value=0></td>
					<td><input type="text" name="planting[1][tillage]" value=0></td>
					<td><input type="text" name="planting[1][envmod]" value=0></td>
					<td><input type="text" name="planting[1][harvest]" value=0></td>
					<td><input type="text" name="planting[1][simcont]" value=1></td>
				</tr>
				<tr>
					<td><input type="text" name="planting[2][level]" value=3></td>
					<td><input type="text" name="planting[2][desc]"
						value="75 kg ha -1 of appl"></td>
					<td><input type="text" name="planting[2][cultivar]" value=1></td>
					<td><input type="text" name="planting[2][field]" value=1></td>
					<td><input type="text" name="planting[2][soilanalysis]"
						value=1></td>
					<td><input type="text" name="planting[2][initcond]" value=1></td>
					<td><input type="text" name="planting[2][plant]" value=1></td>
					<td><input type="text" name="planting[2][irrigation]" value=1></td>
					<td><input type="text" name="planting[2][fertilizer]" value=1></td>
					<td><input type="text" name="planting[2][residual]" value=1></td>
					<td><input type="text" name="planting[2][chemapp]" value=0></td>
					<td><input type="text" name="planting[2][tillage]" value=0></td>
					<td><input type="text" name="planting[2][envmod]" value=0></td>
					<td><input type="text" name="planting[2][harvest]" value=0></td>
					<td><input type="text" name="planting[2][simcont]" value=1></td>
				</tr>
				<tr>
					<td><input type="text" name="planting[3][level]" value=4></td>
					<td><input type="text" name="planting[3][desc]"
						value="113 kg ha -1 of appl"></td>
					<td><input type="text" name="planting[3][cultivar]" value=1></td>
					<td><input type="text" name="planting[3][field]" value=1></td>
					<td><input type="text" name="planting[3][soilanalysis]"
						value=1></td>
					<td><input type="text" name="planting[3][initcond]" value=1></td>
					<td><input type="text" name="planting[3][plant]" value=1></td>
					<td><input type="text" name="planting[3][irrigation]" value=1></td>
					<td><input type="text" name="planting[3][fertilizer]" value=1></td>
					<td><input type="text" name="planting[3][residual]" value=1></td>
					<td><input type="text" name="planting[3][chemapp]" value=0></td>
					<td><input type="text" name="planting[3][tillage]" value=0></td>
					<td><input type="text" name="planting[3][envmod]" value=0></td>
					<td><input type="text" name="planting[3][harvest]" value=0></td>
					<td><input type="text" name="planting[3][simcont]" value=1></td>
				</tr>
				<tr>
					<td><input type="text" name="planting[4][level]" value=5></td>
					<td><input type="text" name="planting[4][desc]"
						value="150 kg ha -1 of appl"></td>
					<td><input type="text" name="planting[4][cultivar]" value=1></td>
					<td><input type="text" name="planting[4][field]" value=1></td>
					<td><input type="text" name="planting[4][soilanalysis]"
						value=1></td>
					<td><input type="text" name="planting[4][initcond]" value=1></td>
					<td><input type="text" name="planting[4][plant]" value=1></td>
					<td><input type="text" name="planting[4][irrigation]" value=1></td>
					<td><input type="text" name="planting[4][fertilizer]" value=1></td>
					<td><input type="text" name="planting[4][residual]" value=1></td>
					<td><input type="text" name="planting[4][chemapp]" value=0></td>
					<td><input type="text" name="planting[4][tillage]" value=0></td>
					<td><input type="text" name="planting[4][envmod]" value=0></td>
					<td><input type="text" name="planting[4][harvest]" value=0></td>
					<td><input type="text" name="planting[4][simcont]" value=1></td>
				</tr>
				<tr>
					<td><input type="text" name="planting[5][level]" value=6></td>
					<td><input type="text" name="planting[5][desc]"
						value="188 kg ha -1 of appl"></td>
					<td><input type="text" name="planting[5][cultivar]" value=1></td>
					<td><input type="text" name="planting[5][field]" value=1></td>
					<td><input type="text" name="planting[5][soilanalysis]"
						value=1></td>
					<td><input type="text" name="planting[5][initcond]" value=0></td>
					<td><input type="text" name="planting[5][plant]" value=1></td>
					<td><input type="text" name="planting[5][irrigation]" value=1></td>
					<td><input type="text" name="planting[5][fertilizer]" value=1></td>
					<td><input type="text" name="planting[5][residual]" value=1></td>
					<td><input type="text" name="planting[5][chemapp]" value=0></td>
					<td><input type="text" name="planting[5][tillage]" value=0></td>
					<td><input type="text" name="planting[5][envmod]" value=0></td>
					<td><input type="text" name="planting[5][harvest]" value=0></td>
					<td><input type="text" name="planting[5][simcont]" value=1></td>
				</tr>
		</table>
		<p>
			<input type="submit" value="Submit" /> <input type="reset"
				value="Reset" /> <input type="button" value="Cancel"
				onclick="javascript:window.close();" />
		</p>
	</form>
	<div style="width: 300px" id="result"></div>
</body>
</html>