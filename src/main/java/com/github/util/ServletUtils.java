package com.github.util;


import com.github.vo.AjaxResult;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtils {
    public static void renderJson(HttpServletResponse response, AjaxResult data) {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(data.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}