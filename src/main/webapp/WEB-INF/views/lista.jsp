<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale != null ? sessionScope.locale.language : 'es'}">
<head>
<meta charset="UTF-8">
<title><fmt:message key="app.titulo"/></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
<h1><fmt:message key="app.titulo"/></h1>
<p><fmt:message key="app.bienvenida"/>, ${sessionScope.usuarioActual.username}</p>

<div class="page-actions" style="justify-content: space-between; flex-wrap: wrap; gap: 10px;">
    <div>
        <a href="<c:url value="/idioma?lang=es"/>">Español</a> |
        <a href="<c:url value="/idioma?lang=en"/>">English</a>
    </div>
    <div style="display:flex; gap: 12px; align-items:center; flex-wrap: wrap;">
        <a class="btn btn-primary" href="<c:url value="/productos?accion=formulario"/>">+ <fmt:message key="menu.nuevo"/></a>
        <a class="btn" href="<c:url value="/logout"/>"><fmt:message key="menu.cerrarSesion"/></a>
    </div>
</div>

<c:if test="${not empty mensaje}">
    <p class="alert-success">${mensaje}</p>
</c:if>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th><fmt:message key="tabla.nombre"/></th>
        <th><fmt:message key="tabla.categoria"/></th>
        <th><fmt:message key="tabla.precio"/></th>
        <th><fmt:message key="tabla.stock"/></th>
        <th><fmt:message key="tabla.acciones"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${productos}" varStatus="s">
        <tr class="${s.index % 2 == 0 ? 'par' : 'impar'}">
            <td>${p.id}</td>
            <td><c:out value="${p.nombre}"/></td>
            <td><c:out value="${p.categoria}"/></td>
            <td><fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="$"/></td>
            <td>${p.stock}</td>
            <td class="actions">
                <a href="<c:url value="/productos?accion=editar&id=${p.id}"/>"><fmt:message key="btn.editar"/></a> |
                <a class="btn-danger" href="<c:url value="/productos?accion=eliminar&id=${p.id}"/>" onclick="return confirm('¿Eliminar ${p.nombre}?')"><fmt:message key="btn.eliminar"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</body>
</html>
