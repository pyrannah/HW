package io.muic.ooc.webapp.servlet;


import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.service.SecurityService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractRoutableHttpServlet extends HttpServlet implements Routable {

    protected SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}