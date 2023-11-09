<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <a id="volver" href="empresa?opcion=index">
    	<img src="img/x-solid.svg"></img>
    </a>
  
	<div class="container" id="listar">
		<h1>Lista de Empleado</h1>

		<div class="tabla" id="lista">
			<table border="1">
				<tr>
					<td>DNI</td>
					<td>Nombre</td>
					<td>Sexo (M/F)</td>
					<td>Categoria</td>
					<td>Años</td>
					
				</tr>
				<c:forEach var="empleado" items="${lista}">
					<tr>
						<td><c:out value="${ empleado.dni}"></c:out></td>
						<td><c:out value="${ empleado.nombre}"></c:out></td>
						<td><c:out value="${ empleado.sexo}"></c:out></td>
						<td><c:out value="${ empleado.categoria}"></c:out></td>
						<td><c:out value="${ empleado.anyos}"></c:out></td>
						
						<td><a
							href="empresa?opcion=eliminar&dni=<c:out value="${ empleado.dni}"></c:out>">
								Eliminar </a></td>
						<td><a
							href="empresa?opcion=editar&dni=<c:out value="${ empleado.dni}"></c:out>">
								Editar </a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
