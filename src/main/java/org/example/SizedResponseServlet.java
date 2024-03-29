/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SizedResponseServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SizedResponseServlet.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");
        // resp.setHeader("Cache-Control", "public,max-age=8600");

        ServletInputStream inputStream = req.getInputStream();
        int totalRead = 0;
        byte[] bytes = new byte[1024];
        while (true)
        {
            int read = inputStream.read(bytes);
            if (read < 0)
                break;
            totalRead += read;
        }
        ServletOutputStream outputStream = resp.getOutputStream();
        if (totalRead > 0)
            outputStream.println("RequestContentLength: " + totalRead);

        String sizeParam = req.getParameter("size");

        // todo chunk size
        long size = (sizeParam == null) ? 0 : Long.parseLong(sizeParam);

        // Write 32MB of data.
        for (int i = 0; i < size; i++)
        {
            outputStream.write((byte)'x');
        }
    }
}
