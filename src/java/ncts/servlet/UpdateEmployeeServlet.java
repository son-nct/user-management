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
public class UpdateEmployeeServlet extends HttpServlet {

    private final String UPDATE_EMPLOYEE_PAGE = "updateEmployee.jsp";
    private final String INFO_EMPLOYEE_PAGE = "info.jsp";
    private final String LOGOUT_CONTROLLER = "logout";

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

        String curPassword = request.getParameter("curPassword");
        String password = request.getParameter("txtPassword");
        String repassword = request.getParameter("txtRepassword");

        String phone = request.getParameter("txtPhone");
        String fullname = request.getParameter("txtFullname");
        String photo = request.getParameter("urlImg");
        String role = request.getParameter("txtRole");

        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();

        boolean checkUpdateWithPassword = false;
        boolean checkUpdateNoPassword = false;
        String url = null;
        try {
            int error = 0;

            if (email.length() == 0) {
                String empty_email = "Please input Email !";
                session.setAttribute("empty_email", empty_email);
                error++;
            }
            if (!email.matches("^[A-Za-z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$")) {
                String invalid_email = "Not valid email. Try again !";
                session.setAttribute("invalid_email", invalid_email);
                error++;
            }

            if (phone.length() == 0) {
                String empty_phone = "Please input phone number !";
                session.setAttribute("empty_phone", empty_phone);
                error++;
            }

            if (phone.length() >= 1 && !phone.matches("^0\\d{9,11}$")) {
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


            if (curPassword.length() > 0) {
                session.removeAttribute("EMPTY_CUR_PASWORD");

                byte[] encodeHash = EncodePassword.convertStringToByte(curPassword);
                String passwordEncoded = EncodePassword.bytesToHex(encodeHash);

                UserDTO userDTO = userDAO.checkLogin(username, passwordEncoded);
                if (userDTO != null) {
                    session.removeAttribute("ERROR_PASSWORD");

                    if (password.length() == 0 && repassword.length() == 0) {
                        UserDTO userUpdated = new UserDTO(username, email, phone, fullname, photo, role);
                        checkUpdateNoPassword = userDAO.updateEmployeeNoPassword(userUpdated);
                        if (checkUpdateNoPassword) {
                            session.setAttribute("USER", userUpdated);
                        }
                    }

                    if (password.length() == 0 && repassword.length() > 0) {
                        session.setAttribute("empty_password", "Please input new password !");
                        error++;
                    } else if (repassword.length() > 0 && password.length() > 0) {
                        if (password.equals(repassword)) {
                            byte[] encodeHash2 = EncodePassword.convertStringToByte(password);
                            String newPasswordEncoded = EncodePassword.bytesToHex(encodeHash2);

                            UserDTO userUpdated = new UserDTO(username, newPasswordEncoded, email, phone, fullname, photo, role);
                            checkUpdateWithPassword = userDAO.updateEmployeeWithPassword(userUpdated);

                        } else {
                            session.setAttribute("not_match", "Password and Repassword not match. Try again!");
                            error++;
                        }
                    }
                } else {
                    session.setAttribute("ERROR_PASSWORD", "Current password is not correct. Try again!");
                    error++;
                }

            } else {
                error++;
                session.setAttribute("EMPTY_CUR_PASWORD", "Please input current password !");
                session.removeAttribute("ERROR_PASSWORD");
            }

            if (error > 0) {
                url = UPDATE_EMPLOYEE_PAGE;
                UserDTO user = new UserDTO(username, email, phone, fullname, photo, role);
                session.setAttribute("USER_UPDATE", user);

            } else {

                if (checkUpdateNoPassword) {
                    url = INFO_EMPLOYEE_PAGE;
                }

                if (checkUpdateWithPassword) {
                    url = LOGOUT_CONTROLLER;
                }
            }

        } catch (NoSuchAlgorithmException e) {
            log("No such algorithm....", e.getCause());
        } catch (SQLException e) {
            log("Have error with database...", e.getCause());
        } catch (NamingException e) {
            log("Couldn't find the datasource", e.getCause());
        } finally {
            response.sendRedirect(url);
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
