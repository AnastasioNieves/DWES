<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
    <title>Buscar Empleado</title>
</head>
<body>
    <a id="volver" th:href="@{/empresa?opcion=index}">
        <img src="img/x-solid.svg" alt="Volver"/>
    </a>
    <div class="container">
        <h1>Buscar Empleado</h1>

        <form method="post" th:action="@{/empresa}">
            <label for="dniBusqueda">Buscar por DNI:</label>
            <input type="text" id="dniBusqueda" name="valorBusqueda" />
            <input type="hidden" name="opcion" value="buscarEmpleado"/>
            <input type="hidden" name="tipoBusqueda" value="dni"/>
            <input type="submit" value="Buscar">
        </form>

        <!-- Las otras formas se actualizan de manera similar -->

        <div th:if="${not #lists.isEmpty(lista)}" class="tabla" id="lista">
            <table border="1">
                <tr>
                    <td>DNI</td>
                    <td>Nombre</td>
                    <td>Sexo</td>
                    <td>Categoría</td>
                    <td>Años Trabajados</td>
                    <td colspan="2">Accion</td>
                </tr>
                <tr th:each="empleado : ${lista}">
                    <td th:text="${empleado.dni}"></td>
                    <td th:text="${empleado.nombre}"></td>
                    <td th:text="${empleado.sexo}"></td>
                    <td th:text="${empleado.categoria}"></td>
                    <td th:text="${empleado.anyos}"></td>
                    <td>
                        <a th:href="@{/empresa(opcion='eliminar', dni=${empleado.dni})}">Eliminar</a>
                    </td>
                    <td>
                        <a th:href="@{/empresa(opcion='editar', dni=${empleado.dni})}">Editar</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
