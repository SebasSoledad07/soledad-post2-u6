package com.universidad.mvc.controller;

import com.universidad.mvc.model.Usuario;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    // Usuarios en memoria (en producción se consulta la BD)
    private static final Map<String, String> CREDS;

    static {
        Map<String, String> creds = new HashMap<>();
        creds.put("admin", "Admin123!");
        creds.put("viewer", "View456!");
        CREDS = Collections.unmodifiableMap(creds);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        if (user != null && CREDS.containsKey(user) &&
                CREDS.get(user).equals(pass)) {
            HttpSession session = req.getSession(true);
            String rol = "admin".equals(user) ? "ADMIN" : "VIEWER";
            session.setAttribute("usuarioActual",
                    new Usuario(user, user + "@universidad.edu", rol));
            session.setMaxInactiveInterval(1800);
            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            req.setAttribute("errorLogin", "Credenciales incorrectas. Intente de nuevo.");
                    req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,
                            resp);
        }
    }
}
