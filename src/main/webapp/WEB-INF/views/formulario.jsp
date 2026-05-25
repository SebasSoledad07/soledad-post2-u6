<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es"><head>
<meta charset="UTF-8">
<title>${empty producto ? "Nuevo Producto" : "Editar Producto"}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head><body>
<div class="container">
<h1>${empty producto ? "Registrar Producto" : "Editar Producto"}</h1>

<c:if test="${not empty errores}">
    <div class="alert-error">
        <ul>
            <c:forEach var="e" items="${errores}">
                <li><c:out value="${e.value}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<form method="post" action="<c:url value="/productos"/>">
    <c:if test="${not empty producto}">
        <input type="hidden" name="id" value="${producto.id}">
        <input type="hidden" name="accion" value="actualizar">
    </c:if>
    <c:if test="${empty producto}">
        <input type="hidden" name="accion" value="guardar">
    </c:if>

    <label>Nombre:
        <input type="text" name="nombre" required
               value="${empty nombre ? (empty producto ? '' : producto.nombre) : nombre}"
               class="${not empty errores.nombre ? 'input-error' : ''}">
        <c:if test="${not empty errores.nombre}">
            <span class="campo-error"><c:out value="${errores.nombre}"/></span>
        </c:if>
    </label>

    <label>Categoría:
        <input type="text" name="categoria"
               value="${empty categoria ? (empty producto ? '' : producto.categoria) : categoria}"
               class="${not empty errores.categoria ? 'input-error' : ''}">
        <c:if test="${not empty errores.categoria}">
            <span class="campo-error"><c:out value="${errores.categoria}"/></span>
        </c:if>
    </label>

    <label>Precio:
        <input type="number" name="precio" step="0.01" min="0" required
               value="${empty precio ? (empty producto ? '' : producto.precio) : precio}"
               class="${not empty errores.precio ? 'input-error' : ''}">
        <c:if test="${not empty errores.precio}">
            <span class="campo-error"><c:out value="${errores.precio}"/></span>
        </c:if>
    </label>

    <label>Stock:
        <input type="number" name="stock" min="0" required
               value="${empty stock ? (empty producto ? '' : producto.stock) : stock}"
               class="${not empty errores.stock ? 'input-error' : ''}">
        <c:if test="${not empty errores.stock}">
            <span class="campo-error"><c:out value="${errores.stock}"/></span>
        </c:if>
    </label>
    <button type="submit">${empty producto ? "Guardar" :
            "Actualizar"}</button>
    <a class="btn" href="<c:url value="/productos"/>">Cancelar</a>
</form>
</div>
</body></html>