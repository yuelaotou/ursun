<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<!doctype html>
<html >
<head>
	<title>jQuery UI Calendar</title>
	<n:base />
	<n:jquery components="calendar"/>
	<style type="text/css">
			.demo{border:1px solid #ddd;width:500px;height:300px;padding:10px;float:left;}
			ul{float:left;padding:0px;margin:0 0 0 10px;}
			ul li{padding:5px 0;cursor:pointer;margin-left:20px;list-style:disc outside;}
			ul li:hover{text-decoration:underline;color:#1b75bb;}
			ul.color{width:200px;}
			ul.color li{float:left;margin-right:15px;}
	</style>
	
</head>
<body>
		<!-- Datepicker -->
		<li class="default">Default functionality</li>
		<div>
			<p>Date: <input type="text" size="30" id="default-datepicker" class=""/></p>
			<script type="text/javascript">
			$(document).ready(function(){
				$(function() {
					$("#default-datepicker").datepicker();
				});
			});
			</script>
		</div>
		
		<li class="date-formats">Format date</li>
		<div>
			<p>Date: <input type="text" size="30" id="date-formats-datepicker" class=""/></p>
			<p>Format options:<br/>
				<select id="format">
					<option value="mm/dd/yy">Default - mm/dd/yy</option>
					<option value="yy-mm-dd">ISO 8601 - yy-mm-dd</option>
					<option value="d M, y">Short - d M, yy</option>
					<option value="d MM, y">Medium - d MM, yy</option>
					<option value="DD, d MM, yy">Full - DD, d MM, yy</option>
					<option value="'day' d 'of' MM 'in the year' yy">With text - 'day' d 'of' MM 'in the year' yy</option>
				</select>
			</p>
			<script type="text/javascript">
			$(document).ready(function(){
				$(function() {
					$("#date-formats-datepicker").datepicker();
					$("#format").change(function() { $('#date-formats-datepicker').datepicker('option', {dateFormat: $(this).val()}); });
				});
			});
			</script>
		</div>
		<li class="min-max">Restrict date range</li>
		<div>
		<p>Date: <input type="text" id="min-max-datepicker" class=""/></p>
		<script type="text/javascript">
		$(document).ready(function(){
			$(function() {
				$("#min-max-datepicker").datepicker({minDate: -20, maxDate: '+1M +10D'});
			});
		});
		</script>
		</div>
		
		<li class="alt-field">Populate alternate field</li>
		<div>
			<p>Date: <input type="text" id="alt-field-datepicker" class=""/> <input type="text" size="30" id="alternate"/></p>
			<script type="text/javascript">
			$(document).ready(function(){
				$(function() {
					$("#alt-field-datepicker").datepicker({altField: '#alternate', altFormat: 'DD, d MM, yy'});
				});
			});
			</script>
		</div>
		<li class="inline">Display inline</li>
		<div>
		<p>Date: <div id="inline-datepicker" class=""></div>
		<script type="text/javascript">
		$(document).ready(function(){
			$(function() {
				$("#inline-datepicker").datepicker();
			});
		});
		</script>
		</div>
		<li class="buttonbar">Display button bar</li>
		<div>
		<p>Date: <input type="text" size="30" id="buttonbar-datepicker" class=""/></p>
		<script type="text/javascript">
		$(document).ready(function(){
			$(function() {
				$('#buttonbar-datepicker').datepicker({
					showButtonPanel: true
				});
			});
		});
		</script>
		</div>
		<li class="dropdown-month-year">Display month and year menus</li>
		<div>
		<p>Date: <input type="text" size="30" id="dropdown-month-year-datepicker" class=""/></p>
		<script type="text/javascript">
		$(document).ready(function(){
			$(function() {
				$('#dropdown-month-year-datepicker').datepicker({
					changeMonth: true,
					changeYear: true
				});
			});
		});
		</script>
		</div>
		<li class="multiple-calendars">Display multiple months</li>
		<div>
		<p>Date: <input type="text" size="30" id="multiple-calendars-datepicker" class=""/></p>
		<script type="text/javascript">
		$(document).ready(function(){
			$(function() {
				$('#multiple-calendars-datepicker').datepicker({
					numberOfMonths: 2,
					showButtonPanel: true
				});
			});
		});
		</script>
		</div>
		<li class="icon-trigger">Icon trigger</li>
		<div>
		<p>Date: <input type="text" size="30" id="icon-trigger-datepicker" class=""/></p>
		<script type="text/javascript">
		$(document).ready(function(){
			$(function() {
				$("#icon-trigger-datepicker").datepicker({showOn: 'button', buttonImage: 'images/calendar.gif', buttonImageOnly: true});
			});
		});
		</script>
		</div>
		
</body>
</html>
