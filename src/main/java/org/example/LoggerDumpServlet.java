package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggerDumpServlet extends HttpServlet
{
    private static final java.util.logging.Logger JUL_LOGGER = java.util.logging.Logger.getLogger(LoggerDumpServlet.class.getName());
//    private static final Logger SLF4J_LOGGER = LoggerFactory.getLogger(LoggerDumpServlet.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/plain");
        resp.setStatus(200);
        PrintWriter writer = resp.getWriter();

        System.err.println(getClass().getSimpleName() + " logging at System.err.");
        System.out.println(getClass().getSimpleName() + " logging at System.out.");

//        SLF4J_LOGGER.trace(getClass().getSimpleName() + " logging SLF4J at trace.");
//        SLF4J_LOGGER.debug(getClass().getSimpleName() + " logging SLF4J at debug.");
//        SLF4J_LOGGER.info(getClass().getSimpleName() + " logging SLF4J at info.");
//        SLF4J_LOGGER.warn(getClass().getSimpleName() + " logging SLF4J at warn.");
//        SLF4J_LOGGER.error(getClass().getSimpleName() + " logging SLF4J at error.");

        System.err.println("loggerLevel: " + JUL_LOGGER.getLevel());
        JUL_LOGGER.finest(getClass().getSimpleName() + " logging JUL at finest.");
        JUL_LOGGER.finer(getClass().getSimpleName() + " logging JUL at finer.");
        JUL_LOGGER.fine(getClass().getSimpleName() + " logging JUL at fine.");
        JUL_LOGGER.info(getClass().getSimpleName() + " logging JUL at info.");
        JUL_LOGGER.config(getClass().getSimpleName() + " logging JUL at config.");
        JUL_LOGGER.warning(getClass().getSimpleName() + " logging JUL at warning.");
        JUL_LOGGER.severe(getClass().getSimpleName() + " logging JUL at severe.");

        try
        {
            int i = 0;
            writer.println("configFile: " + System.getProperty("java.util.logging.config.file") + "\n");
            for (java.util.logging.Logger l = JUL_LOGGER; l != null; l = l.getParent())
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
