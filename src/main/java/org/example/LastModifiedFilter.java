package org.example;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class LastModifiedFilter extends HttpFilter
{
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        res = new HttpServletResponseWrapper(res)
        {
            @Override
            public void setDateHeader(String name, long date)
            {
                if (!"Last-Modified".equals(name))
                    super.setDateHeader(name, date);
            }

            @Override
            public void setHeader(String name, String value)
            {
                if (!"Last-Modified".equals(name))
                    super.setHeader(name, value);
            }

            @Override
            public void setIntHeader(String name, int value)
            {
                if (!"Last-Modified".equals(name))
                    super.setIntHeader(name, value);
            }

            @Override
            public void addHeader(String name, String value)
            {
                if (!"Last-Modified".equals(name))
                    super.addHeader(name, value);
            }
        };

        chain.doFilter(req, res);
    }
}
