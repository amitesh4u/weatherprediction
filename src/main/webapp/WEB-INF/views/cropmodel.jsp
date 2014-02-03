<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*"%>
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
<title>Crop Model Simulation</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/style/style.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/style/jquery-ui.css"/>" />

<script language="javascript"
	src="<c:url value="/static/script/jquery-1.11.0.min.js"/>"></script>
<script language="javascript"
	src="<c:url value="/static/script/jquery-ui.min.js"/>"></script>
<script language="javascript"
	src="<c:url value="/script/cropmodel.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/script/myjquery.js"/>"></script>
<script>
	$(function() {
		$("#simTabs").tabs();
	});
</script>

<script type='text/javascript'>
	//<![CDATA[

	var formData;
	//var divData;
	var finalData;
	//$(function() {
	// $('form[name="DODSForm"]').submit(function() {
	//	$('#cmhBtn,#cmnhmmBtn,#rmnhmmBtn').click(function(simCode){
	function runSimulation(simCode) {
		//alert(simCode);//simCode = "cmh";
		//return false;

		$('#simulationType').val(simCode);

		formData = JSON.stringify($('form[name="DODSForm"]').serializeObject());
		// alert(formData);
		// formData = formData.substring(0,formData.lastIndexOf('}'));
		//alert(formData);

		//divData = $('#pdParamVal').text();//showDivText('pdParamVal');
		//alert(divData);
		//divData = divData.substring(divData.indexOf('{')+1);//replaceFirst('{','');
		//alert(divData);
		//JSONObject jObject = new JSONObject(formData);

		//$('#result').text(JSON.stringify(jObject));
		finalData = formData + $('#nhmmInputSeedsParamVal').text()
				+ $('#gpParamVal').text() + $('#pdParamVal').text()
				+ $('#idParamVal').text() + $('#fdParamVal').text();
		// alert(data);
		finalData = finalData.replace(/}{/g, ",");//.replaceAll("}{", ',');
		//   alert(data);
		//$('#result').text(finalData);
		// $('#result').text(formData+ ',' +divData);

		$('#' + simCode + 'SimId').val(0);
		$('#' + simCode + 'SimNum').val(0);
		$('#' + simCode + 'SimStatus').val(0);
		$('#' + simCode + 'Btn').attr("disabled", "disabled");
		// 		document.getElementById(simCode + 'SimId').value = 0;
		// 		document.getElementById(simCode + 'SimStatus').value = 0;
		// 		document.getElementById(simCode + 'Btn').disabled = true;
		// 		simParams = 'Country=' + country + ', State=' + state + ', District=' 
		// 					+ district + ', Crop Name=' + cropName + ', Cropping Season=' 
		// 					+ cropSeason + ',  <br/> General Circulation Model=' + gcm 
		// 					+ ', Forecast initialization month=' + gcmSeason;
		simParams = finalData;
		//simParamObj = document.getElementById(simCode + 'SimParams');
		//simParamObj.innerHTML = simParams;
		//$('#' + simCode + 'SimParams').text(finalData).show();
		$('#' + simCode + 'SimParams').text(finalData);
		$('#' + simCode + 'SimHeader').show();

		//document.getElementById(simCode + 'SimHeader').style.display = 'block';
		//simParamObj.style.display = 'block';

		// 		simulationLoader = document.getElementById(simCode + 'SimLoader');
		// 		simulationLoader.style.display = 'block';
		// 		simulationLoader.innerHTML = '<img src="../images/loading.gif" width="150">';
		$('#' + simCode + 'SimLoader')
				.text(
						'<img src="<c:url value="/static/image/loading.gif"/>" width="150">')
				.show();

		jQuery.ajax({
			url : "/weatherprediction/Cropmodel",
			type : 'POST',
			dataType : "json",
			data : finalData,//JSON.stringify(jObject),
			contentType : 'application/json',
			mimeType : 'application/json'
		//               success : function(data, textStatus, jqXHR) {
		//                              alert(textStatus);
		//                          },
		//               error : function(jqXHR, textStatus, errorThrown) {
		//             	  			alert("jqXHR: "+jqXHR+" status: "+textStatus+" error:"+errorThrown);
		//                          }
		}).done(function(xhr) {
			if (xhr.status == 'success') {
				//alert( "Data submitted successfully. Simulation will start soon!: " + xhr.id);

				$('#' + simCode + 'SimId').val(xhr.id);
				$('#' + simCode + 'SimNum').val(xhr.num);
				$('#' + simCode + 'SimStatus').val(1);
				//$('#' + simCode + 'Btn').attr("disabled", "disabled");
				$('#' + simCode + 'SimLoader').hide().text('');

				//                 		document.getElementById(simCode + 'SimId').value = xhr.id;
				//                 		document.getElementById(simCode + 'SimStatus').value = 1;

				//                 		simulationLoader = document.getElementById(simCode + 'SimLoader');
				//                 		simulationLoader.style.display = 'none';
				//                 		simulationLoader.innerHTML = "";

				checkSimulationStatus(simCode);
			} else {
				//alert( "There is some issue in Input Data. Please verify and submit again. If issue persists, please try again later");
				handleSimErrorResponse(simCode);
			}
		}).fail(function(xhr, textStatus, errorThrown) {
			//alert("Internal Error. Please try again later");
			handleSimErrorResponse(simCode);
		})
		//                 .always(function() {
		//                   alert( "complete" );
		//                 })
		;

		return false;
		//      });
		//  });
	}
	$( document ).ready(function() {
	    //console.log( "ready!" );
	    checkSimulationReport();
	});
	
	
	

	//]]>
</script>

</head>
<body>
	<div style="height: 10px"></div>
	<div class="roundedDiv">
		<font size="2em" style="font-weight: bold" color="#3E3EFF">Welcome
			${fName} ${lName}!</font> | <a href="Logout">Logout</a> <img
			src="<c:url value="/static/images/spacer.gif"/>" width="350px"
			height="1px" /> <font size="3em" style="font-weight: bold"
			color="green">Crop Model Simulation</font>
	</div>
	<div style="height: 10px"></div>
	<div id="container" style="min-width: 1280px; height: 450px;width:100%">
		<div id="left" class="roundedDiv"
			style="float: left; min-width: 660px; width=100%; padding: 0px; margin-left: 5px">
			<form method="post" action="CropModel" name="DODSForm">
				<ul style='list-style-type: none;margin-left:-10px'>
					<li><label><font size="3em" style="font-weight: bold"
							color="green">Simulation Input</font> <font color="red">(all
								fields are mandatory)</font></label></li>
					<li><br /></li>
					<li><b>Country:</b><img
						src="<c:url value="/static/images/spacer.gif"/>" width="148px"
						height="1px"> <select onchange="updateState()"
						name="selCountry" id="selCountry"
						STYLE="font-family: verdana; font-size: 8pt">
							<option value="0" selected>..Select Country..</option>
							<OPTION VALUE="1">India</OPTION>
							<OPTION VALUE="2">Brazil</OPTION>
							<OPTION VALUE="3">Uruguay</OPTION>
					</select><br /> <br /></li>
					<li><b>State:</b><img
						src="<c:url value="/static/images/spacer.gif"/>" width="170px"
						height="1px"><select onchange="updateDistrict()"
						id="selState" name="selState"
						STYLE="font-family: verdana; font-size: 8pt">
							<option value="0" selected></option>
					</select><br /> <br /></li>
					<li><b>District:</b><img
						src="<c:url value="/static/images/spacer.gif"/>" width="155px"
						height="1px"><select name="selDistrict" id="selDistrict"
						STYLE="font-family: verdana; font-size: 8pt">
							<option value="0" selected></option>
					</select><br /> <br /></li>
					<li><b>Crop name:</b><img
						src="<c:url value="/static/images/spacer.gif"/>" width="132px"
						height="1px"><select name="selCropName" id="selCropName"
						style="font-family: verdana; font-size: 8pt">
							<option value="0" selected>..Select..</option>
							<option value="Wheat">Wheat</option>
							<option value="Rice">Rice</option>
							<option value="Maize">Maize</option>
							<option value="Millet">Millet</option>
							<option value="Barley">Barley</option>
							<option value="Bajra">Bajra</option>
							<option value="Sorghum">Sorghum</option>
							<option value="Peanut">Peanut</option>
							<option value="Sugarcane">Sugarcane</option>
							<option value="Soyabean">Soyabean</option>
							<option value="Cabbage">Cabbage</option>
							<option value="Chickpea">Chickpea</option>
							<option value="Pineapple">Pineapple</option>
							<option value="Potato">Potato</option>
							<option value="GreenBean">GreenBean</option>
							<option value="Cotton">Cotton</option>
							<option value="CowPea">CowPea</option>
							<option value="DryBean">DryBean</option>

					</select><br /> <br /></li>

					<li><b>Cropping Season:</b><img
						src="<c:url value="/static/images/spacer.gif"/>" width="92px"
						height="1px"><select name="selCropSeason" id="selCropSeason"
						style="font-family: verdana; font-size: 8pt">
							<option value="0" selected>..Select..</option>
							<option value="JFMA">JFMA</option>
							<option value="FMAM">FMAM</option>
							<option value="MAMJ">MAMJ</option>
							<option value="AMJJ">AMJJ</option>
							<option value="MJJA">MJJA</option>
							<option value="JJAS">JJAS</option>
							<option value="JASO">JASO</option>
							<option value="ASON">ASON</option>
							<option value="SOND">SOND</option>
							<option value="ONDJ">ONDJ</option>
							<option value="NDJF">NDJF</option>
							<option value="DJFM">DJFM</option>
					</select><br /> <br /></li>

					<li><b>General Circulation Model:</b><img
						src="<c:url value="/static/images/spacer.gif"/>" width="32px"
						height="1px"><select name="selGeneralCirculationModel"
						id="selGeneralCirculationModel"
						style="font-family: verdana; font-size: 8pt">
							<option value="0" selected>..Select..</option>
							<option value="COLA-RSMAS-CCSM35">COLA-RSMAS-CCSM35</option>
							<option value="CPC-CMAP">CPC-CMAP</option>
							<option value="CPC-PRECIP">CPC-PRECIP</option>
							<option value="GFDL-CM2p1">GFDL-CM2p1</option>
							<option value="GHCN_CAMS">GHCN_CAMS</option>
							<option value="IRI-ECHAM4p5-AnomalyCoupled">IRI-ECHAM4p5-AnomalyCoupled</option>
							<option value="IRI-ECHAM4p5-DirectCoupled">IRI-ECHAM4p5-DirectCoupled</option>
							<option value="LSMASK">LSMASK</option>
							<option value="GHCN_CAMS">GHCN_CAMS</option>
							<option value="IRI-ECHAM4p5-AnomalyCoupled">IRI-ECHAM4p5-AnomalyCoupled</option>
							<option value="IRI-ECHAM4p5-DirectCoupled">IRI-ECHAM4p5-DirectCoupled</option>
							<option value="LSMASK">LSMASK</option>
					</select><br /> <br /></li>

					<li><b>Forecast initialization month:</b><img
						src="<c:url value="/static/images/spacer.gif"/>" width="15px"
						height="1px"><select name="selGCMSeason" id="selGCMSeason"
						style="font-family: verdana; font-size: 8pt">
							<option value="0" selected>..Select..</option>
							<option value="Jan">Jan</option>
							<option value="Feb">Feb</option>
							<option value="Mar">Mar</option>
							<option value="Apr">Apr</option>
							<option value="May">May</option>
							<option value="Jun">Jun</option>
							<option value="Jul">Jul</option>
							<option value="Aug">Aug</option>
							<option value="Sep">Sep</option>
							<option value="Oct">Oct</option>
							<option value="Nov">Nov</option>
							<option value="Dec">Dec</option>
					</select><br /> <br /></li>
					<li><input type="radio" name="addinp" value="addin" checked><b>Additional Inputs:</b>
						<a
						href="javascript:openWin('static/jsp/NHMMInputSeeds.jsp', 'NHMM Input Seeds');">
							NHMM Input seeds</a>
						<div style="display: none" id="nhmmInputSeedsParamVal"></div> <img
						src="<c:url value="/static/images/spacer.gif"/>" width="5px"
						height="1px" /> <a
						href="javascript:openWin('static/jsp/gpparam.jsp', 'Genetic Parameters');">
							Genetic Parameters</a>
						<div style="display: none" id="gpParamVal"></div>
						<img src="<c:url value="/static/images/spacer.gif"/>" width="5px"
						height="1px" /><a
						href="javascript:openWin('static/jsp/planting.jsp', 'Planting Details');">
						Planting</a>
						<div style="display: none" id="pdParamVal"></div> <!-- input name= "pdiParamVal" id="pdiParamVal" type="hidden" value="" /-->
						<img src="<c:url value="/static/images/spacer.gif"/>" width="5px"
						height="1px" /> <a
						href="javascript:openWin('static/jsp/irrigation.jsp', 'Irrigation Details');">
							Irrigation</a>
						<div style="display: none" id="idParamVal"></div> <img
						src="<c:url value="/static/images/spacer.gif"/>" width="5px"
						height="1px" /> <a
						href="javascript:openWin('static/jsp/fertilizer.jsp', 'Fertilizer Details');">
							Fertilizer</a>
						<div style="display: none" id="fdParamVal"></div></li>
					<li><input type="hidden" id="simulationType"
						name="simulationType" value="cmh" /></li>
					<li><div style="height: 15px"></div></li>
					<li><input type="radio" name="addinp" value="formhub"><font size="2em" style="font-weight:bold;color:#F65327;font-family:Georgia">Formhub url: </font>
					<input type="text" id="formHub" value="" size="40" /></li>
					<li><div style="height: 20px"></div></li>
					<li><font size="2em" color="green" style="font-weight: bold">Run
							Simulation:</font><br />
							<input style="font-size: 1em" type="button" id="cmhBtn"
								value="Crop Model (Historical Yield)"
								onClick="runSimulation('cmh')" />
							<input style="font-size: 1em" type="button"
								id="cmnhmmBtn" value="Crop Model (NHMM Predictions)"
								onClick="runSimulation('cmnhmm')" />
							<input style="font-size: 1em" type="button"
								id="rmnhmmBtn" value="RainFall Model (NHMM predictions)"
								onClick="runSimulation('rmnhmm')" />
					</li>
				</ul>
			</form>
		</div>
		<div id="right" class="roundedDiv"
			style="float: right; padding: 0px; margin-right: 5px;min-width:620px;">
			<form method="post" action="SimulationReport" name="SimReportForm">
				<ul style='list-style-type: none;'>
					<li><label><font size="3em" style="font-weight: bold"
							color="green">Recent Completed Simulations</font></label></li>
					<li><br /></li>
					<li>
						<div id="simReport"></div>
					</li>
				</ul>
			</form>
		</div>
		<div style="float: clear;"></div>
	</div>
	<div id="simTabs">
		<ul id="simTabsHeader">
			<li><a href="#cmh"><b>Crop Model(Historical Yield)</b></a></li>
			<li><a href="#cmnhmm"><b>Crop Model (NHMM predictions)</b></a></li>
			<li><a href="#rmnhmm"><b>RainFall Model (NHMM
						predictions)</b></a></li>
		</ul>
		<div id="cmh">
			<font size="3em" style="font-weight: bold" color="green">Simulation Status</font>
			<input id="cmhSimId" type="hidden" value="0" />
			<input id="cmhSimNum" type="hidden" value="0" />

			<!--  1 denotes In Progress, 0 denotes Not Executed or Completed with either Success or Failure. Status will be checked only if Status is 1 -->
			<input id="cmhSimStatus" type="hidden" value="0" />

			<div id="cmhSimHeader" style="display: none">
				<h3>
					<font color="red">Simulation Parameters: </font>
					<a href="javascript:showHideInputParam('cmh');" id="cmhInParamLink">(Show Input Parameters)</a>
				</h3>
				<div class="roundedDiv" id="cmhSimParams" style="display:none;background-color:#EBF3E8"></div>
<!-- 				<label id='cmhSimParams'></label> -->
			</div>
			<div>
				<br />
			</div>
			<div id="cmhSimLoader" style="display: none"></div>
			<div id="cmhSimOutput" style="display: block"></div>
		</div>
		<div id="cmnhmm">
			<font size="3em" style="font-weight: bold" color="green">Simulation Status</font>
			<input id="cmnhmmSimId" type="hidden" value="0" />
			<input id="cmnhmmSimNum" type="hidden" value="0" />

			<!--  1 denotes In Progress, 0 denotes Not Executed or Completed with either Success or Failure. Status will be checked only if Status is 1 -->
			<input id="cmnhmmSimStatus" type="hidden" value="0" />

			<div id="cmnhmmSimHeader" style="display: none">
				<h3>
					<font color="red">Simulation Parameters: </font>
					<a href="javascript:showHideInputParam('cmnhmm');" id="cmnhmmInParamLink">(Show Input Parameters)</a>
				</h3>
				<div class="roundedDiv" id="cmnhmmSimParams" style="display:none;background-color:#EBF3E8"></div>
			</div>
			<div>
				<br />
			</div>
			<div id="cmnhmmSimLoader" style="display: none"></div>
			<div id="cmnhmmSimOutput" style="display: block"></div>
		</div>
		<div id="rmnhmm">
			<font size="3em" style="font-weight: bold" color="green">Simulation Status</font>
			<input id="rmnhmmSimId" type="hidden" value="0" />
			<input id="rmnhmmSimNum" type="hidden" value="0" />

			<!--  1 denotes In Progress, 0 denotes Not Executed or Completed with either Success or Failure. Status will be checked only if Status is 1 -->
			<input id="rmnhmmSimStatus" type="hidden" value="0" />

			<div id="rmnhmmSimHeader" style="display: none">
				<h3>
					<font color="red">Simulation Parameters: </font>
				<a href="javascript:showHideInputParam('rmnhmm');" id="rmnhmmInParamLink">(Show Input Parameters)</a>
				</h3>
				<div class="roundedDiv" id="rmnhmmSimParams" style="display:none;background-color:#EBF3E8"></div>
			</div>
			<div>
				<br />
			</div>
			<div id="rmnhmmSimLoader" style="display: none"></div>
			<div id="rmnhmmSimOutput" style="display: block"></div>
		</div>
	</div>

	<!-- <h2>JSON</h2> -->
	<!-- <div style="width:500px" id="result"></div> -->

</body>
</html>