<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<html>
	<head>
		<title>jQuery UI Accordion - Default functionality</title>
		<n:base />
		<n:jquery components="accordion" />
		<script type="text/javascript">
	$(function() {
	$("#accordion").accordion({ 
animated:false,
collapsible:false, 
event:'click', 
fillSpace:true 
  }); 
	});
	</script>
	</head>
	<body>

		<div class="demo">
			<div id="accordion" style="width:170px;">
				<h3>
					<a href="#">系统管理</a>
				</h3>
				<div>
					<ul>
						<li>
							<a class="ui-corner-all" href="${base}/system/system/member">学生管理</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/system/user">权限管理</a>
						</li>
					</ul>
				</div>
				<h3>
					<a href="#">新闻</a>
				</h3>
				<div>
					<ul>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/bulletin">公告管理</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/part">版块(专题)划分</a>
						</li>
					</ul>
				</div>
				<h3>
					<a href="#">新闻</a>
				</h3>
				<div>
					<ul>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/bulletin">公告管理</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/part">版块(专题)划分</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/bulletin">公告管理</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/part">版块(专题)划分</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/bulletin">公告管理</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/part">版块(专题)划分</a>
						</li>
					</ul>
				</div>
				<h3>
					<a href="#">新闻</a>
				</h3>
				<div>
					<ul>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/bulletin">公告管理</a>
						</li>
						<li>
							<a class="ui-corner-all" href="${base}/system/campus/part">版块(专题)划分</a>
						</li>
					</ul>
				</div>
			</div>

		</div>
		<!-- End demo -->

		<div class="demo-description">
			<p>
				Click headers to expand/collapse content that is broken into logical sections, much like tabs. Optionally, toggle
				sections open/closed on mouseover.
			</p>
			<p>
				The underlying HTML markup is a series of headers (H3 tags) and content divs so the content is usable without
				JavaScript.
			</p>
		</div>
		<!-- End demo-description -->

	</body>
</html>
