<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu empleados</title>

<link rel="stylesheet" href="css/styles.css">

</head>
<body>
  <div class="container">
    <h1>Menu de Opciones Empleados</h1>
    	<div class="tabla" id="inicio">
	    	<table>
	      		<tr>
	        		<td><a href="empresa?opcion=listarEmpleado">Mostrar empleados</a></td>
	      		</tr>
	      		<tr>
	        		<td><a href="empresa?opcion=listarSalario">Buscar salario (empleado)</a></td>
	      		</tr>
	      		<tr>
	        		<td><a href="empresa?opcion=buscarEmpleado">buscar empleado</a></td>
	      		</tr>
	    	</table>
    	</div>
  </div>
</body>
</html>