package com.bootiful.web.controller;

import com.bootiful.framework.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.Enumeration;

import static com.bootiful.web.util.SessionKey.CURRENT_USER;

public abstract class BaseController {
    
    private String viewBase = "";

    protected BaseController(String viewBase) {
        this.viewBase = "views/" + viewBase + "/";
    }

    protected String render(String viewName) {
        return viewBase + viewName;
    }

    protected String forwardTo(String location) {
        return "forward:/" + (location != null ? location : "");
    }

    protected String redirectTo(String location) {
        return "redirect:/" + (location != null ? location : "");
    }

    @SuppressWarnings("unchecked")
    protected String getMethodParameters(HttpServletRequest request) {

        StringBuilder result = new StringBuilder();

        Enumeration<String> params = request.getParameterNames();

        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String value = paramName + ":" + request.getParameter(paramName) + " ";
            result.append(value);
        }
        return result.toString();
    }

    protected String getClientIP(HttpServletRequest request) {
        String remoteIp = request.getRemoteAddr();
        String proxyIp = request.getHeader("X-Forwarded-For");
        if (proxyIp.isEmpty())
            return remoteIp;
        else {
            String forwardedIp = proxyIp;
            String[] ipTokens = forwardedIp.split(",");
            if (ipTokens != null && ipTokens.length > 1) forwardedIp = ipTokens[ipTokens.length - 2].trim();
            return forwardedIp.isEmpty() ? remoteIp : forwardedIp;
        }
    }

    protected String getServerIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            return null;
        }
    }

    protected User getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return getCurrentUser(request.getSession(true));
    }

    public static User getCurrentUser(HttpSession httpSession) {
        if (httpSession == null) {
            return null;
        }
        return (User) httpSession.getAttribute(CURRENT_USER);
    }
}
