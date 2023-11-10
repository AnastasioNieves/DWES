<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu empleados</title>

<link rel="stylesheet" href="css/styles.css">

</head>
<body>
	<div class="container">

		<%@ include file="bases/header.jsp"%>

		<div class="content">

		<% if(request.getAttribute("content") == null || ((String) request.getAttribute("content")).isEmpty()){ %>
            <jsp:include page="/views/bienvenida.jsp" />
        <%}else{%>
             <jsp:include page='<%= (String) request.getAttribute("content") %>' />
        <%}%>



		</div>

		<%@ include file="bases/footer.jsp"%>
	</div>
</body>
</html>