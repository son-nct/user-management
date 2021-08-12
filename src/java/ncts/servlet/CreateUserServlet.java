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
import ncts.dao.UserDAO;
import ncts.dto.UserDTO;
import ncts.encodePassword.EncodePassword;

/**
 *
 * @author WIN 10
 */
public class CreateUserServlet extends HttpServlet {

    private final String CREATE_USER_PAGE = "createAccount.jsp";
    private final String LOAD_MANAGE_PAGE = "manage.jsp";

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

        String username = request.getParameter("txtUserID");
        String password = request.getParameter("txtPassword");
        String repassword = request.getParameter("txtRepassword");
        String email = request.getParameter("txtEmail");
        String phone = request.getParameter("txtPhone");
        String fullname = request.getParameter("txtFullname");
        String photo = request.getParameter("urlImg");
        String role = request.getParameter("cboRole");
        String status = "active";

        UserDAO userDAO = new UserDAO();
        String url = null;
        HttpSession session = request.getSession();
        try {
            int error = 0;

            if (username.length() == 0) {
                String empty_username = "Please input username !";
                session.setAttribute("empty_username", empty_username);
                error++;
            }

            String username_exit = userDAO.checkUsername(username);
            if (username.length() > 0 && username_exit != null) {
                String exist_username = "Username already exists. Try again !";
                session.setAttribute("exist_username", exist_username);
                error++;
            }

            if (email.length() == 0) {
                String empty_email = "Please input Email !";
                session.setAttribute("empty_email", empty_email);
                error++;
            }
            if (email.length() > 0 && !email.matches("^[A-Za-z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$")) {
                String invalid_email = "Not valid email. Try again !";
                session.setAttribute("invalid_email", invalid_email);
                error++;
            }

            if (password.length() == 0) {
                session.setAttribute("empty_password", "Please input password !");
                error++;
            }

            if (!repassword.equals(password)) {
                session.setAttribute("not_match", "Password and Repassword not match. Try again!");
                error++;
            }

            if (phone.length() == 0) {
                String empty_phone = "Please input phone number !";
                session.setAttribute("empty_phone", empty_phone);
                error++;
            }

            if (phone.length() > 0 && !phone.matches("^0\\d{9,11}$")) {
                String error_phone = "Not valid phone number. Try again !";
                session.setAttribute("error_phone", error_phone);
                error++;
            }

            if (fullname.length() == 0) {
                session.setAttribute("empty_fullname", "Please input fullname !");
                error++;
            }

            if (photo.length() == 0) {
                String empty_img = "Please choose image !";
                session.setAttribute("empty_img", empty_img);
                error++;
            }

            if (error > 0) {
                url = CREATE_USER_PAGE;
                
                UserDTO user = new UserDTO(username, password, email, phone, fullname, photo, role);
                session.setAttribute("CREATE_USER", user);
                session.setAttribute("REPASSWORD_VALUE", repassword);
            } else {
                UserDTO user = new UserDTO(username, password, email, phone, fullname, photo, role, status);

                byte[] encodeHash = EncodePassword.convertStringToByte(password);
                String passwordEncoded = EncodePassword.bytesToHex(encodeHash);

                user.setPassword(passwordEncoded);
                userDAO.createUser(user);

                String tabRole = "";
                String searchValue = "";
                List<UserDTO> users = userDAO.getListUser(tabRole,searchValue);
                session.setAttribute("LIST_USER", users);
                session.removeAttribute("CREATE_USER");
                url = LOAD_MANAGE_PAGE;
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
