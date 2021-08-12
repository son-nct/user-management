/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ncts.dao.PromotionDAO;
import ncts.dao.UserDAO;
import ncts.dto.PromotionDTO;
import ncts.dto.RoleDTO;
import ncts.dto.UserDTO;
import ncts.encodePassword.EncodePassword;

/**
 *
 * @author WIN 10
 */
public class LoginServlet extends HttpServlet {

    private final String MANAGE_PAGE = "manage.jsp";
    private final String INFO_PAGE = "info.jsp";

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
        String url = null;
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();

        String username = request.getParameter("txtUserID");
        String password = request.getParameter("txtPassword");

        try {
            byte[] encodeHash = EncodePassword.convertStringToByte(password);
            String passwordEncoded = EncodePassword.bytesToHex(encodeHash);

            UserDTO user = userDAO.checkLogin(username, passwordEncoded);

            if (user != null) {
                session.removeAttribute("ERROR_LOGIN");
                if (user.getRole().equals("Admin")) {
                    url = MANAGE_PAGE;
                    List<RoleDTO> listRole = userDAO.getListRole();

                    String tabRole = "";
                    session.setAttribute("TAB_ROLE", "All");
                    String searchValue = "";

                    List<UserDTO> users = userDAO.getListUser(tabRole, searchValue);
                    session.setAttribute("LIST_USER", users);
                    session.setAttribute("LIST_ROLE", listRole);

                    PromotionDAO promotionDAO = new PromotionDAO();
                    List<PromotionDTO> listUserHaveRank = promotionDAO.getListPromotions();

                    session.setAttribute("LIST_RANK", listUserHaveRank);

                } else if (user.getRole().equals("Employee")) {
                    url = INFO_PAGE;
                    String employeeName = user.getUsername();
                    int rank = userDAO.getRank(employeeName);
                    if(rank != -1) {
                         session.setAttribute("RANK", rank);
                    }
                }
                session.setAttribute("USER", user);
            } else {
                String error_msg = "User not found !";
                session.setAttribute("ERROR_LOGIN", error_msg);
                url = "";
            }

        } catch (NoSuchAlgorithmException e) {
            log("No such algorithm....", e.getCause());
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
