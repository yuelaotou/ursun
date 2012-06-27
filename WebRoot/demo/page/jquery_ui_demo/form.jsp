<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="n" uri="/wee-tags"%>
<!doctype html>
<html lang="en">
<head>
	<title>jQuery UI Form</title>
	<n:base />
	<n:jquery components="form,calendar"/>
	<title>Jquery Inline Form Validation Engine</title>
	
	<!-- AJAX SUCCESS TEST FONCTION	
		<script>function callSuccessFunction(){alert("success executed")}
				function callFailFunction(){alert("fail executed")}
		</script>
	-->
	<style type="text/css">
		
		table tr td{
			text-align : left
		}
	</style>
	<script type="text/javascript">
		function save(){
			if($("#formID").validation()){
				alert("成功！");
			}
		}
		$(function() {
			$("#date").datepicker({
			showOn: 'button', 
			buttonImage: 'images/calendar.gif', 
			buttonImageOnly: true,
			dateFormat: 'yy-mm-dd'
			});
			//$("#formID").validationEngine();
		});
	</script>
</head>
<body>
<br>
<br>
<br>
<br>
<div align="center">
	<table  id="formID"  width="700" height="400" >
			<tr>
			<td width="20%" >
				<span>Desired username : </span>
			</td><td width="30%">
				<input class="validate[optional,custom[noSpecialCaracters],length[0,20]]" type="text" name="user" />
			</td>
			<td width="20%">
				<span>First name : </span>
			</td><td width="30%">
				<input class="validate[length[0,3]]" type="text" name="firstname" />
			</td>
			</tr>
			<tr>
			<td width="20%">
				<span>Last name : </span>
			</td><td width="80%" colspan="3">
				<input class="validate[required,custom[onlyLetter],length[0,100]] text-input" type="text" name="lasttname"  />
			</td>
			</tr>
			<tr>
			<td width="20%"><span>Radio Groupe : </span></td>
			<td colspan="3">
				<span>radio 1: </span>
				<input class="validate[required] radio" type="radio"  name="radiogoupe" value="5">
				<span>radio 2: </span>
				<input class="validate[required] radio" type="radio" name="radiogoupe" value="3"/>
				<span>radio 3: </span>
				<input class="validate[required] radio" type="radio" name="radiogoupe" value="9"/>
			</td>
			</tr>
			<tr>
			<td width="20%"><span>Max 2 checkbox : </span></td>
			<td colspan="3">
				<input class="validate[minCheckbox[2]] checkbox" type="checkbox"  name="checkboxgroupe" value="5">
				
				<input class="validate[minCheckbox[2]] checkbox" type="checkbox" name="checkboxgroupe" value="3"/>
			
				<input class="validate[minCheckbox[2]] checkbox" type="checkbox" name="checkboxgroupe" value="9"/>
			</td>
			</tr>
			<tr>
			<td width="20%">
				<span>Date : (format YYYY-MM-DD)</span>
			</td><td width="30%">	
				<input class="validate[required,custom[date]] text-input" type="text" name="date" id="date" />
			</td><td width="20%">
				<span>Favorite sport 1:</span>
			</td><td width="30%">
				<select name="sport" id="sport"  class="validate[required]" >
					<option value="">Choose a sport</option>
					<option value="option1">Tennis</option>
					<option value="option2">Football</option>
					<option value="option3">Golf</option>
				</select>
			</td>
			</tr>
			<tr>
			<td width="20%">
				<span>Favorite sport 2:</span>
			</td><td width="30%">	
			<select name="sport2" id="sport2" multiple class="validate[required]" >
				<option value="">Choose a sport</option>
				<option value="option1">Tennis</option>
				<option value="option2">Football</option>
				<option value="option3">Golf</option>
			</select>
			</td><td width="20%">
				<span>Age : </span>
			</td><td width="30%">	
				<input class="validate[required,custom[onlyNumber],length[0,3]] text-input" type="text" name="age" />
			</td>
		</tr>
		<tr>		
			<td width="20%">
				<span>Telephone : </span>
			</td><td width="30%">		
				<input class="validate[required,custom[telephone]] text-input" type="text" name="telephone" />
			</td><td width="20%">	
				<span>Password : </span>
			</td><td width="30%">		
				<input class="validate[required,length[6,11]] text-input" type="password" name="password" id="password" />
			</td>
		</tr>
		<tr>	
			<td width="20%">
				<span>Confirm password : </span>
			</td><td width="30%">			
				<input class="validate[required,confirm[password]] text-input" type="password" name="password2" />
			</td>
			<td width="20%">	
				<span>Email address : </span>
			</td><td width="30%">			
				<input class="validate[required,custom[email]] text-input" type="text" name="email" id="email" />
			</td>
		</tr>
		<tr>	
			<td width="20%">
				<span>Confirm email address : </span>
			</td><td width="30%">			
				<input class="validate[required,confirm[email]]] text-input" type="text" name="email2" />
			</td>
			<td width="20%">	
				<span>Comments : </span>
			</td><td width="30%">			
				<textarea class="validate[required,length[6,300]] text-input" name="comments" id="comments" /> </textarea>
			</td>
		</tr>
		<tr>
		<td colspan="4">
		<div align="center">
			<input type="button" onclick="save()" value="提交" >
		</div></td></tr>
		</table>	
</div>
</body>
</html>
