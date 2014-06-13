<%@page import="java.util.*, com.mycompany.app.common.*"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html"/>
<title>Space Contents</title>
</head>
<body>
<h3>Space Contents</h3>
<%	
	String hostName = request.getServerName(); 
	String sessionId = session.getId();
%>
<br/>
<table>	
	<tr>
		<td>Servlet container host name:</td>
		<td><%= hostName %></td>
	</tr>
	<tr>
		<td>Session id:</td>
		<td><%= sessionId %></td>
	</tr>
</table>
<h3>Space Contents:</h3>
<%
	Data[] spaceEntries = (Data[])request.getAttribute("spaceEntries");
	if (spaceEntries != null ) {
		for(Data data : spaceEntries) { %>
			<%= data.getRawData() %> processed: <%= data.isProcessed() %><br/>
<%		} %>
<%	} %>
<p/>

<form action="AccessSpaceServlet" method="post">
Name:
<input type="text" size="20" name="dataname"/>
<br/>
Value:
<input type="text" size="20" name="datavalue"/>
<br/>
<input type="submit"/>
</form>
</body>
</html>