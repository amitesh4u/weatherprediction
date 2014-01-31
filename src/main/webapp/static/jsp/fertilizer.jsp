<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fertilizer Details</title>
<link rel="stylesheet" href="<c:url value="/static/style/style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/static/style/jquery-ui.css"/>"/>
<script language="javascript"
	src="<c:url value="/static/script/jquery-1.11.0.min.js"/>"></script>
<script language="javascript" src="<c:url value="/static/script/jquery-ui.min.js"/>"></script>	
<script type="text/javascript"
	src="<c:url value="/script/myjquery.js"/>"></script>
<style type="text/css">
 .ui-datepicker-trigger { position: relative; left: 130px; top: -20px; }
</style>	
<script type='text/javascript'>
	//<![CDATA[

	var formData;
	$(function() {
		$('form[name="fertilizerForm"]').submit(
				function() {
					formData = JSON.stringify($('form[name="fertilizerForm"]')
							.serializeObject());
					window.opener.$('#fdParamVal').text(formData);
					//$('#result').text(formData);
					window.close();
					return false;
				});
	});
	
	$(function() {
	    $( "#fertilizerDate0,#fertilizerDate1,#fertilizerDate2,#fertilizerDate3,#fertilizerDate4,#fertilizerDate5,#fertilizerDate6,#fertilizerDate7,#fertilizerDate8,#fertilizerDate9" ).datepicker({
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
	<h2>Fertilizer details Form</h2>
	<form action="" method="post" name="fertilizerForm">
		<table border="0" cellpadding="0" cellspacing="0">
			<thead>
				<!-- universal table heading -->
				<tr>
					<th>DATE (dd/mm/yy)</th>
					<th>Fertilizer Material</th>
					<th>Fertilizer Applications</th>
					<th>Depth,cm</th>
					<th>N, kg ha-1</th>
					<th>P, kg ha-1</th>
					<th>K, kg ha-1</th>
					<th>Ca, kg ha-1</th>
					<th>Other Elements kg ha -1</th>
					<th>Other Element Code</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" id="fertilizerDate0" name="fertilizer[0][date]" readonly/></td>
					<td><input type="text" id="fertilizer[0][material]" name="fertilizer[0][material]" 
						value="Ammonium Sulpahte"></td>
					<td><input type="text" id="fertilizer[0][application]" name="fertilizer[0][application]"
						value="Broadcast on Flooded"></td>
					<td><input type="text" id="fertilizer[0][depth]" name="fertilizer[0][depth]" value=10></td>
					<td><input type="text" id="fertilizer[0][nKgha1]" name="fertilizer[0][nKgha1]" value=19></td>
					<td><input type="text" id="fertilizer[0][pKgha1]" name="fertilizer[0][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[0][kKgha1]" name="fertilizer[0][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[0][caKgha1]" name="fertilizer[0][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[0][otherElementKgha1]" name="fertilizer[0][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[0][otherElementCode]" name="fertilizer[0][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate1" name="fertilizer[1][date]" readonly/></td>
					<td><input type="text" id="fertilizer[1][material]" name="fertilizer[1][material]" 
						value="Ammonium Sulpahte"></td>
					<td><input type="text" id="fertilizer[1][application]" name="fertilizer[1][application]"
						value="Broadcast on Flooded"></td>
					<td><input type="text" id="fertilizer[1][depth]" name="fertilizer[1][depth]" value=1></td>
					<td><input type="text" id="fertilizer[1][nKgha1]" name="fertilizer[1][nKgha1]" value=19></td>
					<td><input type="text" id="fertilizer[1][pKgha1]" name="fertilizer[1][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[1][kKgha1]" name="fertilizer[1][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[1][caKgha1]" name="fertilizer[1][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[1][otherElementKgha1]" name="fertilizer[1][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[1][otherElementCode]" name="fertilizer[1][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate2" name="fertilizer[2][date]" readonly/></td>
					<td><input type="text" id="fertilizer[2][material]" name="fertilizer[2][material]" 
						value="Ammonium Sulpahte"></td>
					<td><input type="text" id="fertilizer[2][application]" name="fertilizer[2][application]"
						value="Broadcast on Flooded"></td>
					<td><input type="text" id="fertilizer[2][depth]" name="fertilizer[2][depth]" value=1></td>
					<td><input type="text" id="fertilizer[2][nKgha1]" name="fertilizer[2][nKgha1]" value=19></td>
					<td><input type="text" id="fertilizer[2][pKgha1]" name="fertilizer[2][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[2][kKgha1]" name="fertilizer[2][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[2][caKgha1]" name="fertilizer[2][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[2][otherElementKgha1]" name="fertilizer[2][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[2][otherElementCode]" name="fertilizer[2][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate3" name="fertilizer[3][date]" readonly/></td>
					<td><input type="text" id="fertilizer[3][material]" name="fertilizer[3][material]" 
						value=""></td>
					<td><input type="text" id="fertilizer[3][application]" name="fertilizer[3][application]"
						value=""></td>
					<td><input type="text" id="fertilizer[3][depth]" name="fertilizer[3][depth]" value=0></td>
					<td><input type="text" id="fertilizer[3][nKgha1]" name="fertilizer[3][nKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[3][pKgha1]" name="fertilizer[3][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[3][kKgha1]" name="fertilizer[3][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[3][caKgha1]" name="fertilizer[3][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[3][otherElementKgha1]" name="fertilizer[3][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[3][otherElementCode]" name="fertilizer[3][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate4" name="fertilizer[4][date]" readonly/></td>
					<td><input type="text" id="fertilizer[4][material]" name="fertilizer[4][material]" 
						value=""></td>
					<td><input type="text" id="fertilizer[4][application]" name="fertilizer[4][application]"
						value=""></td>
					<td><input type="text" id="fertilizer[4][depth]" name="fertilizer[4][depth]" value=0></td>
					<td><input type="text" id="fertilizer[4][nKgha1]" name="fertilizer[4][nKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[4][pKgha1]" name="fertilizer[4][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[4][kKgha1]" name="fertilizer[4][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[4][caKgha1]" name="fertilizer[4][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[4][otherElementKgha1]" name="fertilizer[4][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[4][otherElementCode]" name="fertilizer[4][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate5" name="fertilizer[5][date]" readonly/></td>
					<td><input type="text" id="fertilizer[5][material]" name="fertilizer[5][material]" 
						value=""></td>
					<td><input type="text" id="fertilizer[5][application]" name="fertilizer[5][application]"
						value=""></td>
					<td><input type="text" id="fertilizer[5][depth]" name="fertilizer[5][depth]" value=0></td>
					<td><input type="text" id="fertilizer[5][nKgha1]" name="fertilizer[5][nKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[5][pKgha1]" name="fertilizer[5][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[5][kKgha1]" name="fertilizer[5][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[5][caKgha1]" name="fertilizer[5][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[5][otherElementKgha1]" name="fertilizer[5][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[5][otherElementCode]" name="fertilizer[5][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate6" name="fertilizer[6][date]" readonly/></td>
					<td><input type="text" id="fertilizer[6][material]" name="fertilizer[6][material]" 
						value=""></td>
					<td><input type="text" id="fertilizer[6][application]" name="fertilizer[6][application]"
						value=""></td>
					<td><input type="text" id="fertilizer[6][depth]" name="fertilizer[6][depth]" value=0></td>
					<td><input type="text" id="fertilizer[6][nKgha1]" name="fertilizer[6][nKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[6][pKgha1]" name="fertilizer[6][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[6][kKgha1]" name="fertilizer[6][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[6][caKgha1]" name="fertilizer[6][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[6][otherElementKgha1]" name="fertilizer[6][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[6][otherElementCode]" name="fertilizer[6][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate7" name="fertilizer[7][date]" readonly/></td>
					<td><input type="text" id="fertilizer[7][material]" name="fertilizer[7][material]" 
						value=""></td>
					<td><input type="text" id="fertilizer[7][application]" name="fertilizer[7][application]"
						value=""></td>
					<td><input type="text" id="fertilizer[7][depth]" name="fertilizer[7][depth]" value=0></td>
					<td><input type="text" id="fertilizer[7][nKgha1]" name="fertilizer[7][nKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[7][pKgha1]" name="fertilizer[7][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[7][kKgha1]" name="fertilizer[7][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[7][caKgha1]" name="fertilizer[7][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[7][otherElementKgha1]" name="fertilizer[7][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[7][otherElementCode]" name="fertilizer[7][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate8" name="fertilizer[8][date]" readonly/></td>
					<td><input type="text" id="fertilizer[8][material]" name="fertilizer[8][material]" 
						value=""></td>
					<td><input type="text" id="fertilizer[8][application]" name="fertilizer[8][application]"
						value=""></td>
					<td><input type="text" id="fertilizer[8][depth]" name="fertilizer[8][depth]" value=0></td>
					<td><input type="text" id="fertilizer[8][nKgha1]" name="fertilizer[8][nKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[8][pKgha1]" name="fertilizer[8][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[8][kKgha1]" name="fertilizer[8][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[8][caKgha1]" name="fertilizer[8][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[8][otherElementKgha1]" name="fertilizer[8][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[8][otherElementCode]" name="fertilizer[8][otherElementCode]" value=0></td>
				</tr>
				<tr>
					<td><input type="text" id="fertilizerDate9" name="fertilizer[9][date]" readonly/></td>
					<td><input type="text" id="fertilizer[9][material]" name="fertilizer[9][material]" 
						value=""></td>
					<td><input type="text" id="fertilizer[9][application]" name="fertilizer[9][application]"
						value=""></td>
					<td><input type="text" id="fertilizer[9][depth]" name="fertilizer[9][depth]" value=0></td>
					<td><input type="text" id="fertilizer[9][nKgha1]" name="fertilizer[9][nKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[9][pKgha1]" name="fertilizer[9][pKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[9][kKgha1]" name="fertilizer[9][kKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[9][caKgha1]" name="fertilizer[9][caKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[9][otherElementKgha1]" name="fertilizer[9][otherElementKgha1]" value=0></td>
					<td><input type="text" id="fertilizer[9][otherElementCode]" name="fertilizer[9][otherElementCode]" value=0></td>
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