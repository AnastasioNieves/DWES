<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Buscar Salario de Empleado</title>
<link rel="stylesheet" type="text/css" href="css/Styles.css">

</head>
<body>

	<body>

	<div class="header">
    <div class="navbar">
        <a href="empresa?opcion=index">Inicio</a>
        <a href="empresa?opcion=mostarEmpleado">Mostrar Empleados</a>
        <a href="empresa?opcion=mostrarSalario">Mostrar Salario</a>
        <a href="empresa?opcion=editar">Modificar Empleado</a>
        <a href="empresa?opcion=buscarEmpleados">Buscar Empleados</a>
    </div>
</div>
    <h1>Buscar Salario de Empleado</h1>
	<form action="empresa" method="post">
		<input type="hidden" name="opcion" value="buscarSalario"> DNI
		del Empleado: <input type="text" name="dniBusqueda"> <input
			type="submit" value="Buscar Salario">
	</form>


</body>


</html>
