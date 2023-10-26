<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Búsqueda de Empleados</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    
</head>
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
	


    <h1>Búsqueda de Empleados</h1>
    <form action="empresa" method="get">
        <input type="hidden" name="opcion" value="buscarEmpleados">
        <!-- Agregar campos de búsqueda aquí -->
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" id="nombre">

        <label for="dni">DNI:</label>
        <input type="text" name="dni" id="dni">

        <label for="sexo">Sexo:</label>
        <select name="sexo" id="sexo">
            <option value="M">Masculino</option>
            <option value="F">Femenino</option>
        </select>

        <label for="categoria">Categoría:</label>
        <input type="text" name="categoria" id="categoria">

        <label for="anyos">Años Trabajados:</label>
        <input type="text" name="anyos" id="anyos">

        <input type="submit" value="Buscar Empleados">
    </form>

    <!-- Mostrar resultados de la búsqueda aquí si es necesario -->
</body>
</html>
