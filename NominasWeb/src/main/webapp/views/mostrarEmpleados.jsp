<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Mostrar Información de Empleados</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>
<body>

	<div class="header">
		<div class="navbar">
			<a href="empresa?opcion=index">Inicio</a> <a
				href="empresa?opcion=mostarEmpleado">Mostrar Empleados</a> <a
				href="empresa?opcion=mostrarSalario">Mostrar Salario</a> <a
				href="empresa?opcion=editar">Modificar Empleado</a> <a
				href="empresa?opcion=buscarEmpleadosForm">Buscar Empleados</a>
		</div>
	</div>
	<h1>Información de Empleados</h1>
	<table>
		<tr>
			<th>Nombre</th>
			<th>DNI</th>
			<th>Sexo</th>
			<th>Categoría</th>
			<th>Años Trabajados</th>
		</tr>
		<c:forEach var="empleado" items="${empleados}">
			<tr>
				<td>${empleado.nombre}</td>
				<td>${empleado.dni}</td>
				<td>${empleado.sexo}</td>
				<td>${empleado.categoria}</td>
				<td>${empleado.anyos}</td>
			</tr>
		</c:forEach>
	</table>
</body>


</html>
