package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import io.muic.ooc.webapp.Routable;

public class LoginServlet extends AbstractRoutableHttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
        rd.include(request, response);

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (securityService.authenticate(username, password, request)) {
            response.sendRedirect("/");
        } else {
            String error = "Username or password incorrect. Please try again.";
            request.setAttribute("error", error);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
            rd.include(request, response);
        }
    }

        // check username and password against database
        // if valid then set username attribute to session via securityService
        // else put error message to render error on the login form



    @Override
    public String getMapping() { return "/login"; }

}
