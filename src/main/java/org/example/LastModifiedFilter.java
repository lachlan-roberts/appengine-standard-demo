package org.example;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class LastModifiedFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void destroy()
    {
    }

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
{
    HttpServletResponse res = (HttpServletResponse)response;
    res = new HttpServletResponseWrapper(res)
    {
        @Override
        public void setDateHeader(String name, long date)
        {
            if (!"Last-Modified".equals(name))
                super.setDateHeader(name, date);
        }
    };

    chain.doFilter(request, res);
}
}
