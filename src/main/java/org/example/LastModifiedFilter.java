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
            public void setHeader(String name, String value)
            {
                if (!"Last-Modified".equals(name))
                    super.setHeader(name, value);
            }
        };

        chain.doFilter(req, res);
    }
}
