<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Bienvenido</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/tu-archivo.css}" />
</head>
<body>

    <div class="container">
        <h1>Bienvenido a mi Aplicación</h1>
        <p>¡Gracias por visitar nuestro sitio!</p>
        
        <!-- Puedes agregar más contenido aquí -->

        <a th:href="@{/login}">Iniciar Sesión</a>
    </div>

</body>
</html>
