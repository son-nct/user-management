/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ncts.dto.UserDTO;

/**
 *
 * @author WIN 10
 */
public class LoadInfoUpdateServlet extends HttpServlet {

    private final String UPDATE_FOR_ADMIN_PAGE = "update.jsp";
    private final String UPDATE_FOR_USER_PAGE = "updateEmployee.jsp";

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
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("txtUsername");
        String email = request.getParameter("txtEmail");
        String phone = request.getParameter("txtPhone");
        String fullname = request.getParameter("txtFullname");
        String photo = request.getParameter("urlImg");
        String role = request.getParameter("txtRole");
        String url = null;
        try {
            HttpSession session = request.getSession();
            UserDTO userUpdate = new UserDTO(username, email, phone, fullname, photo, role);
            session.setAttribute("USER_UPDATE", userUpdate);

            UserDTO userDTO = (UserDTO) session.getAttribute("USER");
            String checkRole = userDTO.getRole();
            if (checkRole.equals("Admin")) {
                url = UPDATE_FOR_ADMIN_PAGE;
            } else {
                url = UPDATE_FOR_USER_PAGE;
            }

            session.removeAttribute("CUR_PASS");
            session.removeAttribute("RE_NEW_PASSWORD");
            session.removeAttribute("NEW_PASSWORD");
            session.removeAttribute("EMPTY_CUR_PASWORD");
            session.removeAttribute("ERROR_PASSWORD");
            session.removeAttribute("empty_password");
            session.removeAttribute("not_match");
            session.removeAttribute("empty_email");
            session.removeAttribute("invalid_email");
            session.removeAttribute("empty_phone");
            session.removeAttribute("error_phone");
            session.removeAttribute("empty_fullname");
            session.removeAttribute("empty_img");
            
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
