/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp;

import io.muic.ooc.webapp.service.UserService;
import io.muic.ooc.webapp.servlet.*;
import io.muic.ooc.webapp.service.SecurityService;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gigadot
 */
public class ServletRouter {

    private static final List<Class<? extends AbstractRoutableHttpServlet>> routables = new ArrayList<>();

    static {
        routables.add(HomeServlet.class);
        routables.add(DeleteUserServlet.class);
        routables.add(LoginServlet.class);
        routables.add(LogoutServlet.class);
        routables.add(CreateUserServlet.class);
        routables.add(EditUserServlet.class);
        routables.add(ChangePasswordServlet.class);
    }


    public void init(Context ctx) {

        UserService userService = new UserService();
        SecurityService securityService = new SecurityService();
        securityService.setUserService(userService);

        for (Class<? extends AbstractRoutableHttpServlet> routableClass : routables) {
            try {
                AbstractRoutableHttpServlet routable = routableClass.newInstance();
                routable.setSecurityService(securityService);
                Tomcat.addServlet(ctx, routableClass.getSimpleName(), (HttpServlet) routable);
                ctx.addServletMapping(routable.getMapping(), routableClass.getSimpleName());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
