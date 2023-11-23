<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
    <title>Lista de Empleados</title>
</head>
<body>
    <a id="volver" th:href="@{/empresa?opcion=index}">
        <img src="img/x-solid.svg" alt="Volver"/>
    </a>

    <div class="container" id="listar">
        <h1>Lista de Empleados</h1>

        <div class="tabla" id="lista">
            <table border="1">
                <tr>
                    <td>DNI</td>
                    <td>Nombre</td>
                    <td>Sexo (M/F)</td>
                    <td>Categoría</td>
                    <td>Años</td>
                </tr>
                <tr th:each="empleado : ${lista}">
                    <td th:text="${empleado.dni}"></td>
                    <td th:text="${empleado.nombre}"></td>
                    <td th:text="${empleado.sexo}"></td>
                    <td th:text="${empleado.categoria}"></td>
                    <td th:text="${empleado.anyos}"></td>
                    <td><a th:href="@{/empresa?opcion=eliminar(dni=${empleado.dni})}">Eliminar</a></td>
                    <td><a th:href="@{/empresa?opcion=editar(dni=${empleado.dni})}">Editar</a></td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
