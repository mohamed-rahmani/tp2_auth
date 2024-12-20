/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fr.devavance.metier.controllers;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author kn_21
 */
@WebServlet(name = "ControllerLogin", urlPatterns = {"/controller_login"})
public class ControllerLogin extends HttpServlet implements IController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        if (is_connected(request)) {
            dispatch("/", request, response);
        } else {
            dispatch("/login", request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        process_login_user(request, response);
    }
    
    @Override
    public void dispatch(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(path).forward(request, response);
    }
    
    private boolean is_connected(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession(false); 
        return session != null; 
    }
    
    private void process_login_user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (verify_credential(login, password)) {
            HttpSession session = (HttpSession) request.getSession(true);
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            dispatch("/", request, response);
        } else {
            dispatch(VUE_LOGIN, request, response);
        }
    }
    
    private boolean verify_credential(String login, String password) {
        return login.equals("admin") && password.equals("admin");
    }
}
