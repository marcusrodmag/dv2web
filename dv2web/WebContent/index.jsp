<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DV 2 WEB - Converta seu vídeo para a WEB</title>
</head>
<body>
	<p>
	<img alt="DV2WEB" src="style/img/logo.png">
	</p>
	<p>
		<h1>
			${errormsg}
		</h1>
	</p>
	<!-- form method=POST action="Controller" enctype="multipart/form-data"-->
	<form action="Controller" method="post" enctype="multipart/form-data">
		<p>
			Informe o arquivo de vídeo a ser convertido:</br>
			<input type="file" name="videofile" size="20">
		</p>
		<p>
			<input type="submit" value="Converter">
		</p>
	</form>
</body>
</html>