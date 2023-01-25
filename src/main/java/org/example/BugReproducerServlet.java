
package org.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.IO;

public class BugReproducerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException,
            IOException {
        resp.setContentType("text/plain");

        System.out.println("in bug 1");
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.getWriter().format("Runtime: %s%n", System.getenv("GAE_RUNTIME"));
        resp.getWriter().format("Method: %s%n", req.getMethod());
        resp.getWriter().format("Scheme: %s%n", req.getScheme());

        resp.getWriter().format("Path: %s%n", req.getContextPath());
        resp.getWriter().format("ContentLength: %d%n", req.getContentLength());
        resp.getWriter().format("CharacterEncoding: %s%n", req.getCharacterEncoding());
        resp.getWriter().format("ContentType: %s%n", req.getContentType());
        resp.getWriter().format("RemoteAddr: %s%n", req.getRemoteAddr());
        resp.getWriter().format("RemoteHost: %s%n%n", req.getRemoteHost());

        resp.getWriter().format("HEADERS: %n");
        Enumeration<String> headers = req.getHeaderNames();
        while (headers != null && headers.hasMoreElements()) {
            String name = headers.nextElement();
            resp.getWriter().format("Header: %s = %s%n", name, req.getHeader(name));
        }

        String encoding = req.getHeader("Content-Encoding");
        System.out.println("Content-Encoding======" + encoding);
        if (encoding != null && encoding.toLowerCase().contains("gzip")) {
            resp.getWriter().format("%n%nUncompressing content");
        }

        Reader r = (encoding != null && encoding.toLowerCase().contains("gzip"))
                ? new InputStreamReader(new GZIPInputStream(req.getInputStream()))
                : req.getReader();

        resp.getWriter().format("%n%nContent: %n");
        IO.copy(r, resp.getWriter());
    }

}
