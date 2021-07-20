/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.model.User;
import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.UserService;
import io.muic.ooc.webapp.service.UserServiceException;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends AbstractRoutableHttpServlet {

    @Override
    public String getMapping() {
        return "/index.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (securityService.isAuthorized(request)) {
                String username = (String) request.getSession().getAttribute("username");
                UserService userService = UserService.getInstance();

                request.setAttribute("currentUser", userService.findByUsername(username));
                request.setAttribute("users", userService.findAll());

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/home.jsp");
                requestDispatcher.include(request, response);

                // removing attributes as soon as they are used is known as flash session
                request.getSession().removeAttribute("hasError");
                request.getSession().removeAttribute("message");


            } else {

                request.removeAttribute("hasError");
                request.removeAttribute("message");
                response.sendRedirect("/login");
            }
        } catch (UserServiceException | SQLException e) {
            e.printStackTrace();
        }
    }
}

