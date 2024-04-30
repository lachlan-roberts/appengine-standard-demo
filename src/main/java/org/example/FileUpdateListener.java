package org.example;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class FileUpdateListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        Path path = Paths.get(sce.getServletContext().getRealPath("console"));
        try
        {
            Files.walkFileTree(path.toAbsolutePath(), new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                {
                    Files.setLastModifiedTime(file, FileTime.fromMillis(System.currentTimeMillis()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
                {
                    sce.getServletContext().log("Failed to access file: " + file, exc);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e)
        {
            sce.getServletContext().log("Error updating file modified times: ", e);
        }
    }
}
