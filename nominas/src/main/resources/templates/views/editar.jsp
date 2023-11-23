<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
    <title>Editar Empleado</title>
</head>
<body>
    <a id="volver" th:href="@{/empresa?opcion=listarEmpleado}">
        <img src="img/x-solid.svg" alt="Volver"/>
    </a>
    <div class="container">
        <h1>Editar Empleado</h1>
        <form action="empresa" method="post">
            <input type="hidden" name="opcion" value="editar"/>
            <input type="hidden" name="dni" th:value="${empleado.nombre}"/>

            <div id="busqueda">
                <label for="nombre">Nombre:</label>
                <input type="text" name="nombre" size="50" th:value="${empleado.dni}"/>
            </div>

            <div id="busqueda">
                <label for="sexo">Sexo:</label>
                <input type="text" name="sexo" size="50" th:value="${empleado.sexo}"/>
            </div>

            <div id="busqueda">
                <label for="categoria">Categoría:</label>
                <input type="text" name="categoria" size="50" th:value="${empleado.categoria}"/>
            </div>

            <div id="busqueda">
                <label for="anyos">Años:</label>
                <input type="text" name="anyos" size="50" th:value="${empleado.anyos}"/>
            </div>

            <input type="submit" value="Guardar">
        </form>
    </div>
</body>
</html>
