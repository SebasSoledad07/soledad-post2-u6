package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {
    private final ProductoService service = new ProductoService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!verificarSesion(req, resp)) return;
        

        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";
        switch (accion) {
            case "listar":
                listar(req, resp);
                break;
            case "formulario":
                mostrarFormulario(req, resp);
                break;
            case "editar":
                mostrarEdicion(req, resp);
                break;
            case "eliminar":
                eliminar(req, resp);
                break;
            default:
                resp.sendError(404);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!verificarSesion(req, resp)) return;
        String accion = req.getParameter("accion");
        if ("guardar".equals(accion)) guardar(req, resp);
        else if ("actualizar".equals(accion)) actualizar(req, resp);
        else resp.sendError(400);
    }
    // --- Métodos privados ---
    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("productos", service.obtenerTodos());
        String msg = req.getParameter("mensaje");
        if (msg != null) req.setAttribute("mensaje", msg);
        forward(req, resp, "/WEB-INF/views/lista.jsp");
    }
    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse
            resp)
            throws ServletException, IOException {
        prepararFormularioVacio(req);
        forward(req, resp, "/WEB-INF/views/formulario.jsp");
    }
    private void mostrarEdicion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Producto producto = service.obtenerPorId(id);
        if (producto == null) {
            resp.sendError(404);
            return;
        }
        prepararFormularioEdicion(req, producto);
        forward(req, resp, "/WEB-INF/views/formulario.jsp");
    }
    private void guardar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!validarFormulario(req, resp, false)) return;
        Producto p = extraerProducto(req, 0);
        service.guardar(p);
        resp.sendRedirect(req.getContextPath() +
                "/productos?mensaje=Producto+guardado+exitosamente");
    }
    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (!validarFormulario(req, resp, true)) return;
        Producto p = extraerProducto(req, id);
        service.actualizar(p);
        resp.sendRedirect(req.getContextPath() +
                "/productos?mensaje=Producto+actualizado");
    }
    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.eliminar(id);
        resp.sendRedirect(req.getContextPath() +
                "/productos?mensaje=Producto+eliminado");
    }
    private Producto extraerProducto(HttpServletRequest req, int id) {
        return new Producto(id,
                req.getParameter("nombre"),
                req.getParameter("categoria"),
                Double.parseDouble(req.getParameter("precio")),
                Integer.parseInt(req.getParameter("stock")));
    }
    private boolean validarFormulario(HttpServletRequest req, HttpServletResponse resp,
                                      boolean edicion)
            throws ServletException, IOException {
        Map<String, String> errores = new LinkedHashMap<>();

        String nombre = req.getParameter("nombre");
        String categoria = req.getParameter("categoria");
        String precioStr = req.getParameter("precio");
        String stockStr = req.getParameter("stock");

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio.");
        }
        if (precioStr == null || precioStr.trim().isEmpty()) {
            errores.put("precio", "El precio es obligatorio.");
        } else {
            try {
                double precio = Double.parseDouble(precioStr);
                if (precio < 0) errores.put("precio", "El precio no puede ser negativo.");
            } catch (NumberFormatException ex) {
                errores.put("precio", "El precio debe ser numérico.");
            }
        }

        if (stockStr == null || stockStr.trim().isEmpty()) {
            errores.put("stock", "El stock es obligatorio.");
        } else {
            try {
                int stock = Integer.parseInt(stockStr);
                if (stock < 0) errores.put("stock", "El stock no puede ser negativo.");
            } catch (NumberFormatException ex) {
                errores.put("stock", "El stock debe ser numérico.");
            }
        }

        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            req.setAttribute("nombre", nombre == null ? "" : nombre);
            req.setAttribute("categoria", categoria == null ? "" : categoria);
            req.setAttribute("precio", precioStr == null ? "" : precioStr);
            req.setAttribute("stock", stockStr == null ? "" : stockStr);
            if (edicion) {
                int id = Integer.parseInt(req.getParameter("id"));
                Producto producto = service.obtenerPorId(id);
                if (producto != null) {
                    req.setAttribute("producto", producto);
                }
            }
            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return false;
        }

        req.removeAttribute("errores");
        return true;
    }
    private void prepararFormularioVacio(HttpServletRequest req) {
        req.removeAttribute("producto");
        req.setAttribute("nombre", "");
        req.setAttribute("categoria", "");
        req.setAttribute("precio", "");
        req.setAttribute("stock", "");
    }
    private void prepararFormularioEdicion(HttpServletRequest req, Producto producto) {
        req.setAttribute("producto", producto);
        req.setAttribute("nombre", producto.getNombre());
        req.setAttribute("categoria", producto.getCategoria());
        req.setAttribute("precio", producto.getPrecio());
        req.setAttribute("stock", producto.getStock());
    }
    private void forward(HttpServletRequest req, HttpServletResponse resp, String
            path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private boolean verificarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("usuarioActual") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
