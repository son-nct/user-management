/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ncts.dao.UserDAO;
import ncts.dto.UserDTO;

/**
 *
 * @author WIN 10
 */
public class DeleteUserServlet extends HttpServlet {

    private final String MANAGE_PAGE = "manage.jsp";
    private final String LOGOUT_CONTROLLER = "logout";
    private final String INTERNAL_SERVER_ERROR_PAGE = "errorServer.jsp";

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
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String searchValue = request.getParameter("searchValue");
        String tabRole = request.getParameter("btnRole");

        String status = "inactive";
        String url = null;

        try {
            UserDAO dao = new UserDAO();
            HttpSession session = request.getSession();

            UserDTO urself = (UserDTO) session.getAttribute("USER");
            if (urself.getRole().equals("Admin")) {
                if (username.equals(urself.getUsername())) {
                    boolean check = dao.deleteUser(username, status);
                    if (check) {
                        url = LOGOUT_CONTROLLER;
                    } else {
                        url = INTERNAL_SERVER_ERROR_PAGE;
                    }
                } else {
                    boolean check = dao.deleteUser(username, status);
                    if (check) {
                        url = MANAGE_PAGE;

                        if (tabRole.equals("All")) {
                            tabRole = "";
                            session.setAttribute("TAB_ROLE", "All");
                        }
                        session.setAttribute("TAB_ROLE", tabRole);
                        session.setAttribute("SEARCH_VALUE", searchValue);

                        List<UserDTO> users = dao.getListUser(tabRole, searchValue);
                        session.setAttribute("LIST_USER", users);
                    } else {
                        url = INTERNAL_SERVER_ERROR_PAGE;
                    }
                }

            } else {
                boolean check = dao.deleteUser(username, status);
                if (check) {
                    url = LOGOUT_CONTROLLER;
                } else {
                    url = INTERNAL_SERVER_ERROR_PAGE;
                }
            }

        } catch (SQLException e) {
            log("Have error with database...", e.getCause());
        } catch (NamingException e) {
            log("Couldn't find the datasource", e.getCause());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
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
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
