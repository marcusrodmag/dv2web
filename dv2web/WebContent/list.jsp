<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Vídeos</title>
</head>
<body>
<%
TreeMap<String,String> sources = (TreeMap<String,String>)request.getAttribute("convertedvideos");
for (Map.Entry<String, String> entry : sources.entrySet())
{
    System.out.println(entry.getKey() + "/" + entry.getValue());
}
%>
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
			<h3>Lista de vídeos convertidos</h3>
		</td>
	</tr>
	<tr>
		<td>
		<table>
			<tr>
				<td>
					<ul>
						<% 
							for (Map.Entry<String, String> entry : sources.entrySet()) { %>
								<li>
									<a href="Controller?view=<% out.print(entry.getKey()); %>">
										<% out.print(entry.getKey()); %>
									</a>
								</li>
						<% } %>
					</ul>
				</td>
			</tr>
			<tr>
				<td align="left">
					<a href="index.jsp" ><< Converter um Vídeo</a>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>