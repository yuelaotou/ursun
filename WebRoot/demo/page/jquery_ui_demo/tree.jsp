<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Dynatree - Example</title>
		<n:base />
		<n:jquery components="tree" />
		<!-- Start_Exclude: This block is not part of the sample code -->
		<link href='./css/tree.css' rel='stylesheet' type='text/css'>
		<!-- End_Exclude -->
		<script type='text/javascript' src="tree.js"></script>
	</head>

	<body class="example">
		<!-- Definition of context menu -->
		<ul id="myMenu" class="contextMenu">
			<li class="edit">
				<a href="#edit">Edit</a>
			</li>
			<li class="cut separator">
				<a href="#cut">Cut</a>
			</li>
			<li class="copy">
				<a href="#copy">Copy</a>
			</li>
			<li class="paste">
				<a href="#paste">Paste</a>
			</li>
			<li class="delete">
				<a href="#delete">Delete</a>
			</li>
			<li class="quit separator">
				<a href="#quit">Quit</a>
			</li>
		</ul>

		<!-- Definition tree structure -->
		<div id="html-tree">
			<ul>
				<li id="id1" title="Look, a tool tip!">
					item1 with key and tooltip
				<li id="id2" class="activate">
					item2: activated on init
				<li id="id3" class="folder">
					Folder with some children
					<ul>
						<li id="id3.1">
							Sub-item 3.1
						<li id="id3.2">
							Sub-item 3.2
					</ul>
				<li id="id4" class="expanded">
					Document with some children (expanded on init)
					<ul>
						<li id="id4.1">
							Sub-item 4.1
						<li id="id4.2">
							Sub-item 4.2
					</ul>
				<li id="id5" class="lazy folder">
					Lazy folder
			</ul>
		</div>
       
		<div>
			Selected node:
			<span id="echoActivated">-</span>
		</div>
		
 		<p>AJAX 请求 样例</p>
		<div id="ajax-tree"></div>
	</body>
</html>
