package com.payment.global;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyFreeMarkerView extends FreeMarkerView {

    private static final String CONTEXT_PATH = "basePath";

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        String contextPath = request.getContextPath();
        String reqUrl = request.getRequestURI();

        model.put(CONTEXT_PATH, contextPath);
        model.put("contextPath", reqUrl.substring(contextPath.length(), reqUrl.length()));
        super.exposeHelpers(model, request);
    }
}