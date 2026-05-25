<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container" style="max-width: 460px;">
    <h1>Iniciar sesión</h1>

    <c:if test="${not empty errorLogin}">
        <p style="margin: 0 0 16px; padding: 12px 14px; border-radius: 10px; background: #fee2e2; color: #991b1b; border: 1px solid #fecaca;">
            <c:out value="${errorLogin}" />
        </p>
    </c:if>

    <p style="margin-top: -4px; margin-bottom: 18px; color: #475569; text-align: center;">
        Ingresa tus credenciales para acceder al inventario de productos.
    </p>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Usuario:
            <input type="text" name="username" required autofocus>
        </label>

        <label>Contraseña:
            <input type="password" name="password" required>
        </label>

        <button type="submit">Entrar</button>
    </form>

</div>
</body>
</html>

