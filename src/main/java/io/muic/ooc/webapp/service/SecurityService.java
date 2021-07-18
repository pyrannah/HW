/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.muic.ooc.webapp.model.User;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author gigadot
 */
public class SecurityService {

//    @Setter
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public boolean isAuthorized(HttpServletRequest request) throws UserServiceException {
        String username = (String) request.getSession()
                .getAttribute("username");
        // do checking
       return (username != null && userService.findByUsername(username)!= null);
    }

    public boolean authenticate(String username, String password, HttpServletRequest request) throws UserServiceException {
        User user = userService.findByUsername(username);
//        String passwordInDB = userService.get(username);
//        boolean isMatched = StringUtils.equals(password, passwordInDB);
        if (user != null && BCrypt.checkpw(password,user.getPassword())) {
            request.getSession().setAttribute("username", username);
            return true;
        } else {
            return false;
        }
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.invalidate();

//        request.getSession().invalidate();
    }
    public boolean login(HttpServletRequest request) throws UserServiceException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.findByUsername(username);
        if (user != null && Objects.equals(user.getPassword(), password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return true;
        } else {
            return false;
        }

    }



}
