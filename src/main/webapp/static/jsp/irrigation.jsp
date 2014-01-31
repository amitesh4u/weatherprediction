<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Irrigation Details</title>
<link rel="stylesheet" href="<c:url value="/static/style/style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/static/style/jquery-ui.css"/>"/>
<script language="javascript"
	src="<c:url value="/static/script/jquery-1.11.0.min.js"/>"></script>
<script language="javascript" src="<c:url value="/static/script/jquery-ui.min.js"/>"></script>	
<script type="text/javascript"
	src="<c:url value="/script/myjquery.js"/>"></script>
<style type="text/css">
 .ui-datepicker-trigger { position: relative; left: -29px; top: 2px; }
</style>	
<script type='text/javascript'>
	//<![CDATA[

	var formData;
	$(function() {
		$('form[name="irrigationForm"]').submit(
				function() {
					formData = JSON.stringify($('form[name="irrigationForm"]')
							.serializeObject());
					window.opener.$('#idParamVal').text(formData);
					//$('#result').text(formData);
					window.close();
					return false;
				});
	});

	$(function() {
	    $( "#irrigationDate0,#irrigationDate1,#irrigationDate2,#irrigationDate3,#irrigationDate4,#irrigationDate5,#irrigationDate6,#irrigationDate7,#irrigationDate8,#irrigationDate9" ).datepicker({
		      dateFormat : "dd/mm/yy",
		      showOn: "button",
		      buttonImage: "../image/calendar_g.gif",
		      buttonImageOnly: true
		      }).datepicker("setDate", new Date());
	    	    
	  });
	
	//]]>
</script>
</head>
<body>
	<h2>Irrigation details Form</h2>
	<form action="" method="post" name="irrigationForm">
		<table border="0" cellpadding="0" cellspacing="0">
			<thead>
				<!-- universal table heading -->
				<tr>
					<th>DATE (dd/mm/yy)</th>
					<th>Amount of Water(mm)</th>
					<th>Operation</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
					<input type="text" id="irrigationDate0" name="irrigation[0][date]" readonly/>
					<!-- input type="text" id="irrigation[0][date]" name="irrigation[0][date]" value=152--></td>
					<td><input type="text" id="irrigation[0][waterAmount]" name="irrigation[0][waterAmount]" value=50></td>
					<td>
					<select name="irrigation[0][operation]" id="irrigation[0][operation]">
						<OPTION VALUE="Flood Depth,mm" selected>Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day">Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate1" name="irrigation[1][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[1][waterAmount]" name="irrigation[1][waterAmount]" value=100></td>
					<td>
					<select name="irrigation[1][operation]" id="irrigation[1][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm" selected>Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day">Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate2" name="irrigation[2][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[2][waterAmount]" name="irrigation[2][waterAmount]" value=2></td>
					<td>
					<select name="irrigation[2][operation]" id="irrigation[2][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate3" name="irrigation[3][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[3][waterAmount]" name="irrigation[3][waterAmount]" value=0></td>
					<td>
					<select name="irrigation[3][operation]" id="irrigation[3][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate4" name="irrigation[4][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[4][waterAmount]" name="irrigation[4][waterAmount]" value=0></td>
					<td>
					<select name="irrigation[4][operation]" id="irrigation[4][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate5" name="irrigation[5][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[5][waterAmount]" name="irrigation[5][waterAmount]" value=0></td>
					<td>
					<select name="irrigation[5][operation]" id="irrigation[5][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate6" name="irrigation[6][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[6][waterAmount]" name="irrigation[6][waterAmount]" value=0></td>
					<td>
					<select name="irrigation[6][operation]" id="irrigation[6][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate7" name="irrigation[7][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[7][waterAmount]" name="irrigation[7][waterAmount]" value=0></td>
					<td>
					<select name="irrigation[7][operation]" id="irrigation[7][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate8" name="irrigation[8][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[8][waterAmount]" name="irrigation[8][waterAmount]" value=0></td>
					<td>
					<select name="irrigation[8][operation]" id="irrigation[8][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="irrigationDate9" name="irrigation[9][date]" readonly/>
					</td>
					<td><input type="text" id="irrigation[9][waterAmount]" name="irrigation[9][waterAmount]" value=0></td>
					<td>
					<select name="irrigation[9][operation]" id="irrigation[9][operation]">
						<OPTION VALUE="Flood Depth,mm">Flood Depth,mm</OPTION>
						<OPTION VALUE="Bund Depth,mm">Bund Depth,mm</OPTION>
						<OPTION VALUE="Percolation Depth,mm/day" selected>Percolation Depth,mm/day</OPTION>
					</select>
					</td>
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