<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Genetic Parameters</title>
<link rel="stylesheet" href="<c:url value="/static/style/style.css"/>" />
<script language="javascript" src="<c:url value="/static/script/jquery-1.11.0.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/myjquery.js"/>"></script>
<script type='text/javascript'>//<![CDATA[

	var formData;
	$(function() {
		$('form[name="gpForm"]').submit(
				function() {
					formData = JSON.stringify($('form[name="gpForm"]')
							.serializeObject());
					window.opener.$('#gpParamVal').text(formData);
					//$('#result').text(formData);
					window.close();
					return false;
				});
	});

//]]>
</script>
</head>
<body>
	<h2>Genetic Parameters Form</h2>
	<form action="" method="post" name="gpForm">
		<table border="0" cellpadding="0" cellspacing="10">
			<thead>
				<!-- universal table heading -->
				<tr>
					<th>Variable Name</th>
					<th>Description</th>
					<th>Input Parameters</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>VAR #</td>
					<td>Identification code or number for a specific cultivar</td>
					<td><input type="text" id="genetic[varNum]]" name="genetic[varNum]"
						value="990001"></input></td>
				</tr>
				<tr>
					<td>VAR-NAME</td>
					<td>Name of Cultivar</td>
					<td><input type="text" id="genetic[varName]" name="genetic[varName]"
						value="N.AMERICAN"></input></td>
				</tr>
				<tr>
					<td>EXPNO</td>
					<td>Number of experiments used to estimate cultivar parameters</td>
					<td><input type="text" id="genetic[expNo]" name="genetic[expNo]" value="IB0001"></input></td>
				</tr>
				<tr>
					<td>ECO #</td>
					<td>Ecotype code for this cultivar points to the Ecotype in
						the EC file (currently not used)</td>
					<td><input type="text" id="genetic[ecoNum]" name="genetic[ecoNum]" value="220.0"></input></td>
				</tr>
				<tr>
					<td>P1</td>
					<td>Time period (expressed as growing degree days [GDD] in øC
						above a base temperature of 9degreeC)<br> from seedling
						emergence during which the rice plant is not responsive to changes
						in photoperiod.<br> This period is also referred to as the
						basic vegetative phase of the plant.
					</td>
					<td><input type="text" id="genetic[p1]" name="genetic[p1]" value="35.0"></input></td>
				</tr>
				<tr>
					<td>P2O</td>
					<td>Critical photoperiod or the longest day length (in hours)
						at which the development occurs at a <br> maximum rate. At
						values higher than P2O developmental rate is slowed, hence there
						is delay due to <br> longer day lengths.
					</td>
					<td><input type="text" id="genetic[p2O]" name="genetic[p2O]" value="510.0"></input></td>
				</tr>
				<tr>
					<td>P5</td>
					<td>Time period in GDD øC) from beginning of grain filling (3
						to 4 days after flowering) to <br> physiological maturity
						with a base temperature of 9øC.
					</td>
					<td><input type="text" id="genetic[p5]" name="genetic[p5]" value="12.0"></input></td>
				</tr>
				<tr>
					<td>G1</td>
					<td>Potential spikelet number coefficient as estimated from
						the number of spikelets per g of <br> main culm dry weight
						(less leaf blades and sheaths plus spikes) at anthesis.
					</td>
					<td><input type="text" id="genetic[g1]" name="genetic[g1]" value="55"></input></td>
				</tr>
				<tr>
					<td>G2</td>
					<td>Single grain weight (g) under ideal growing conditions,
						i.e. nonlimiting light, water, nutrients,<br> and absence of
						pests and diseases.
					</td>
					<td><input type="text" id="genetic[g2]" name="genetic[g2]" value=".0250"></input></td>
				</tr>
				<tr>
					<td>G3</td>
					<td>Tillering coefficient (scaler value) relative to IR64
						cultivar under ideal conditions. <br> A higher tillering
						cultivar would have coefficient greater than 1.0.
					</td>
					<td><input type="text" id="genetic[g3]" name="genetic[g3]" value="1.00"></input></td>
				</tr>
				<tr>
					<td>G4</td>
					<td>Temperature tolerance coefficient. Usually 1.0 for
						varieties grown in normal environments.<br> G4 for japonica
						type rice growing in a warmer environment would be 1.0 or greater.
						Likewise, the G4 <br> value for indica type rice in very cool
						environments on season would be less than 1.0.
					</td>
					<td><input type="text" id="genetic[g4]" name="genetic[g4]" value="1.00"></input></td>
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