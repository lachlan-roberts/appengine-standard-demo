package org.example;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.IO;

public class QuickStartServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/plain");
        InputStream inputStream = req.getServletContext().getResourceAsStream("/WEB-INF/quickstart-web.xml");
        IO.copy(inputStream, resp.getOutputStream());
    }
}
