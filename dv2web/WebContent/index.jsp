<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DV 2 WEB - Converta seu v�deo para a WEB</title>
</head>
<body>
	<!-- form method=POST action="Controller" enctype="multipart/form-data"-->
	<form action="Controller" method="post" enctype="multipart/form-data">
		<p>
			Informe o arquivo de v�deo a ser convertido:</br>
			<input type="file" name="videofile" size="20">
		</p>
		<p>
			Qual o nome deste v�deo?</br>
			<input type="text" name="videoname" size="30">
		</p>
		<p>
			<input type="submit" value="Converter">
		</p>
	</form>

</body>
</html>