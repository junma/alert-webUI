<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Please login!</title>
<style>
.button {

	bgcolor: #990000;
	height: 40px;
	width: 206px;
	vertical-align: text-bottom;
	font-size: 20px;
}

body {
	margin: 10px auto;
	min-width: 1024px;
	min-height: 640px;
	width: expression(document.body.clientWidth <     1024 ?     "1024px" :   
		 document.body.clientWidth >     1024 ?     "100%" :     "auto");
	font-size: 20px;
	font-family: Arial, Helvetica, sans-serif;
	bgcolor: #C0C0C0;
	align: center;
	border-width: 5px;
	border-style: ridge;
	border-color: #990000;
}

div.top {
	margin: 1px auto;
	width: 100%;
	height: 100px;
	border-width: 5px;
	border-style: double;
	border-color: #990000;
	float: left;
	}
div.lg {
	position: absolute;
	left: 5px;
	top: 300px;
	min-width: 1000px;
	font-size: 20px;
	height: 100%;
	text-align: center;
	vertical-align: text-bottom;;
}
</style>
</head>
<body>
 
  <div class=top>
		<span style="position: absolute; left: 10px; vertical-align: middle;">
			<img src="img/logo.png" alt="logo.png">

		</span> <span
			style="position: absolute; right: 10px; vertical-align: middle;">
			<img src="img/7th_logo.png" alt="7th_logo.png">
		</span> <span
			style="position: absolute;  margin-left: 30%; margin-right: 20%; vertical-align: middle;">
			<img src="img/slogan.png" alt="slogan.png">
		</span>
	</div>

	<div class=lg>
		<center>
			<form method=get action=WebUIServlet onsubmit="document.body.style.cursor='wait'">
				<table
					style="text-align: center; vertical-align: middle; height: 100%;">
					<tr height=100px>
						<td><b>User Name:</b></td>
						<td><input type="text" name=username
							style="font-size: 20px; width: 206px; height: 40px; border-width: 2px; border-style: ridge; border-color: #990000; vertical-align: text-bottom;" /></td>
					</tr>
					<tr height=100px>
						<td><b>Email:</b></td>
						<td><input type="text" name=email
							style="font-size: 20px; width: 206px; height: 40px; border-width: 2px; border-style: ridge; border-color: #990000; vertical-align: text-bottom;" /></td>
					</tr>
					<tr height=100px>
						<td></td>
						<td><input class="button" type="submit" value=Login /></td>
					</tr>
				</table>
			</form>
		</center>

	</div>



</body>
</html>