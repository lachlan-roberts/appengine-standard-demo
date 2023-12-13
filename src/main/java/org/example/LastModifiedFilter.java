package org.example;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

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
