package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.model.User;
import io.muic.ooc.webapp.service.UserService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserServlet extends AbstractRoutableHttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            String username = (String) request.getSession().getAttribute("username");
            UserService userService = UserService.getInstance();

            try{
                User currentUser = userService.findByUsername(username);

                User deletingUser = userService.findByUsername(request.getParameter("username"));

                if (currentUser.getUsername().equals(deletingUser.getUsername())) {
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", "You cannot delete your own account.");
                }
                else {
                    if (userService.deleteUserByUsername(deletingUser.getUsername())) {

                        request.getSession().setAttribute("hasError", false);
                        request.getSession().setAttribute("message", String.format("User %s is deleted", deletingUser.getUsername()));
                    }
                    else {

                        request.getSession().setAttribute("hasError", true);
                        request.getSession().setAttribute("message", String.format("Unable to delete user %s", deletingUser.getUsername()));
                    }
                }

            } catch (Exception e) {
                request.getSession().setAttribute("hasError", true);
                request.getSession().setAttribute("message", String.format("unable to delete user %s", request.getParameter("username")));
            }

            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    public String getMapping() {
        return "/user/delete";
    }
}