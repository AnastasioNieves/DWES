<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Modificar Datos de un Empleado</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>
	<body>

	<div class="header">
    <div class="navbar">
        <a href="empresa?opcion=index">Inicio</a>
        <a href="empresa?opcion=mostarEmpleado">Mostrar Empleados</a>
        <a href="empresa?opcion=mostrarSalario">Mostrar Salario</a>
        <a href="empresa?opcion=editar">Modificar Empleado</a>
        <a href="empresa?opcion=buscarEmpleadosForm">Buscar Empleados</a>
    </div>
</div>
    <h1>Modificar Datos de Empleado</h1>
	<form action="ModificarEmpleadoServlet" method="post">
		DNI del Empleado: <input type="text" name="dni"> Nueva
		Categoría: <input type="text" name="nuevaCategoria"> Nuevos
		Años Trabajados: <input type="text" name="nuevosAnyos"> <input
			type="submit" value="Modificar">
	</form>

</body>


</html>
