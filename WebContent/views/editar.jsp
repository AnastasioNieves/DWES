
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <a id="volver" href="empresa?opcion=listarEmpleado">
    	<img src="img/x-solid.svg"></img>
    </a>

	<div class="container">
	 <h1>Editar Empleado</h1>
	 <form action="empresa" method="post">
	  	<c:set var="empleado" value="${empleado}"></c:set>
	  	<input type="hidden" name="opcion" value="editar">
	  	<input type="hidden" name="dni" value="${empleado.nombre}">
	  	
	  	<div id="busqueda">
			<label for="nombre">Nombre:</label>
		    <input type="text" name="nombre" size="50" value="${empleado.dni}">
		</div>
		
	  	<div id="busqueda">
		    <label for="sexo">Sexo:</label>
		    <input type="text" name="sexo" size="50" value="${empleado.sexo}">
		</div>
		
	  	<div id="busqueda">
		    <label for="categoria">Categoria:</label>
		    <input type="text" name="categoria" size="50" value="${empleado.categoria}">
		</div>
		
	  	<div id="busqueda">
		    <label for="anyos">Años:</label>
		    <input type="text" name="anyos" size="50" value="${empleado.anyos}">
	   	</div>
	   	
	  	<input type="submit" value="Guardar">
	 </form>
  	</div>
