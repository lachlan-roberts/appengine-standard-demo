package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DumpServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();

        out.println("<h1>DumpServlet</h1>");
        out.println("<pre>");
        out.println("requestURI=" + request.getRequestURI());
        out.println("requestURL=" + request.getRequestURL().toString());
        out.println("contextPath=" + request.getContextPath());
        out.println("servletPath=" + request.getServletPath());
        out.println("pathInfo=" + request.getPathInfo());
        out.println("session=" + request.getSession(true).getId());

        String runtime = System.getenv("GAE_RUNTIME");
        out.println("RUNTIME_VERSION=" + (runtime == null ? "null" : runtime));
        out.println("Request=" + response.getClass().getName());
        out.println("Req_Location=" + response.getClass().getProtectionDomain().getCodeSource().getLocation().toString());

        ServletContext servletContext = getServletContext();

        String r = request.getParameter("resource");
        if (r != null)
        {
            out.println("resource(" + r + ")=" + servletContext.getResource(r));
        }

        Collections.list(request.getAttributeNames())
            .stream()
            .filter((name) -> name.startsWith("X-"))
            .sorted()
            .forEach((name) ->
                out.println("request.attribute[" + name + "]=" + request.getAttribute(name)));

        Collections.list(servletContext.getAttributeNames())
            .stream()
            .filter((name) -> name.startsWith("X-"))
            .sorted()
            .forEach((name) ->
                out.println("servletContext.attribute[" + name + "]=" + servletContext.getAttribute(name)));

        out.println("</pre>");
    }
}
