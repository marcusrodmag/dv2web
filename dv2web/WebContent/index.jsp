<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="icon" type="image/gif" href="style/img/favicon.ico">
<head>
<link rel="stylesheet" type="text/css" href="style/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DV 2 WEB - Converta seu vídeo para a WEB</title>
</head>
<body>
<table class="center">
	<tr>
		<td>
			<p>
				<img alt="DV2WEB" src="style/img/logo.png"/>
			</p>
		</td>
	</tr>
	<tr>
		<td>
			<%
			if (request.getAttribute("errormsg") != null) { 
			%>
			<h1>${errormsg}</h1>
				<%
				if(request.getAttribute("errordesc") != null){
				%>
				<h2>${errordesc}</h2>
				<%
				}
			}
			 %>
		</td>
	</tr>
	<tr>
		<td>
			<form action="Controller" method="post" enctype="multipart/form-data">
				<p>
					Informe o arquivo de vídeo a ser convertido:<br/>
					<input type="file" name="videofile" size="20"/>
				</p>
				<p>
					<input type="submit" value="Converter"/>
				</p>
			</form>
		</td>
	</tr>
	<tr>
		<td align="right">
			<img alt="Vídeos Convertidos" src="style/img/list.jpg">
			<a href="Controller?list=converted">Lista de vídeos já convertidos</a>
		</td>
	</tr>
</table>
	
</body>
</html>