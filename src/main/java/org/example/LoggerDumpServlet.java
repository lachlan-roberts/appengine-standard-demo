package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerDumpServlet extends HttpServlet
{
    private static final Logger logger = Logger.getLogger(LoggerDumpServlet.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/plain");
        resp.setStatus(200);
        PrintWriter writer = resp.getWriter();

        try
        {
            int i = 0;
            writer.println("configFile: " + System.getProperty("java.util.logging.config.file") + "\n");
            for (Logger l = logger; l != null; l = l.getParent())
            {
                writer.println(l.getName() + " " + l.getClass() + " " + l.hashCode());
                writer.println("level: " + l.getLevel());
                writer.println("filter: " + l.getFilter());
                writer.println("handlers: " + Arrays.toString(l.getHandlers()));
                writer.println();

                if (l.getParent() == l)
                    break;
                if (i++ > 10)
                    break;
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace(writer);
        }

        writer.close();
    }
}
