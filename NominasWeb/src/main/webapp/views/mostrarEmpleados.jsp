<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Empleados</title>
</head>
<body>
	<h1>Lista de Empleados</h1>
	<table border="1">
		<tr>
			<th>DNI</th>
			<th>Nombre</th>
			<th>Sexo</th>
			<th>Categoría</th>
			<th>Años</th>
		</tr>
		<c:forEach items="${empleado}" var="empleado">
			<tr>
				<td>${empleado.dni}</td>
				<td>${empleado.nombre}</td>
				<td>${empleado.sexo}</td>
				<td>${empleado.categoria}</td>
				<td>${empleado.anyos}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
