<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Buscar Nóminas</title>
</head>
<body>
    <a id="volver" th:href="@{/empresa?opcion=index}">
        <img src="img/x-solid.svg" alt="Volver"/>
    </a>

    <div class="container">
        <h1>Buscar Nóminas</h1>

        <form method="post" action="empresa" id="ultimoForm">
            <label for="dniBusqueda">Buscar por DNI:</label>
            <input type="text" id="dniBusqueda" name="dniBusqueda"/>
            <input type="hidden" name="opcion" value="buscarSalario"/>
            <input type="submit" value="Buscar"/>
        </form>

        <div class="tabla" id="lista" th:if="${empleado != null and sueldo != null}">
            <table border="1">
                <tr>
                    <td>DNI</td>
                    <td>Nómina</td>
                </tr>
                <tr>
                    <td th:text="${empleado != null ? empleado.dni : ''}"></td>
                    <td th:text="${sueldo != null ? sueldo : ''}"></td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
