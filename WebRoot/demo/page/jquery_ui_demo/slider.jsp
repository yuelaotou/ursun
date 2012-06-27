<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<!doctype html>
<html>
	<head>
		<n:base />
		<n:jquery components="slider" />
		<link type="text/css" href="./css/jquery-ui-demo.css" rel="stylesheet" />
		<style type="text/css">
		.ui-progressbar-value { background-image: url(images/pbar-ani.gif); }
	</style>
		<script type="text/javascript">
		$(function() {
			$("#slider").slider({
				value:100,
				min: 0,
				max: 500,
				step: 50,
				slide: function(event, ui) {
					$("#amount").val('$' + ui.value);
				}
			});
			
			$("#amount").val('$' + $("#slider").slider("value"));
			
			$("#slider-min").slider({
				range: "min",
				value:100,
				min: 0,
				max: 500,
				step: 50,
				slide: function(event, ui) {
					$("#amount-min").val('$' + ui.value);
				}
			});
			
			$("#amount-min").val('$' + $("#slider-min").slider("value"));
			
			$("#slider-range").slider({
				range: true,
				min: 0,
				max: 500,
				values: [75, 300],
				slide: function(event, ui) {
					$("#amount-range").val('$' + ui.values[0] + ' - $' + ui.values[1]);
				}
			});
			
			$("#amount-range").val('$' + $("#slider-range").slider("values", 0) + ' - $' + $("#slider-range").slider("values", 1));
				
			$("#slider-vertical").slider({
				orientation: "vertical",
				range: "min",
				min: 0,
				max: 100,
				value: 60,
				slide: function(event, ui) {
					$("#amount-vertical").val(ui.value);
				}
			});
			
			$("#amount-vertical").val($("#slider-vertical").slider("value"));
		});
	</script>
	</head>
	<body>
		<div class="demo">
			<p>
				<label for="amount">
					Volume($50 increments):
				</label>
				<input type="text" id="amount"
					style="border:0; color:#f6931f; font-weight:bold;" />
			</p>
			<div id="slider"></div>
			<p>
				<label for="amount-min">
					Volume($50 increments):
				</label>
				<input type="text" id="amount-min"
					style="border:0; color:#f6931f; font-weight:bold;" />
			</p>
			<div id="slider-min"></div>
			<p>
				<label for="amount-range">
					Volume range:
				</label>
				<input type="text" id="amount-range"
					style="border:0; color:#f6931f; font-weight:bold;" />
			</p>
			<div id="slider-range"></div>
			<p>
				<label for="amount-vertical">
					Volume:
				</label>
				<input type="text" id="amount-vertical"
					style="border:0; color:#f6931f; font-weight:bold;" />
			</p>
			<div id="slider-vertical" style="height:200px;"></div>
		</div>
	</body>
</html>

