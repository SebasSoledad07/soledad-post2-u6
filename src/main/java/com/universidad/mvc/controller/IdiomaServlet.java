package com.universidad.mvc.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/idioma")
public class IdiomaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String lang = req.getParameter("lang");
        if (lang != null && (lang.equals("es") || lang.equals("en"))) {
            req.getSession(true).setAttribute("locale", new
                    java.util.Locale(lang));
        }
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer != null ? referer : req.getContextPath() + "/productos");
    }
}

