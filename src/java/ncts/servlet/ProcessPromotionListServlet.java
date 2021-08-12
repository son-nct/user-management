/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ncts.dto.CartObj;
import ncts.dto.UserDTO;

/**
 *
 * @author WIN 10
 */
public class ProcessPromotionListServlet extends HttpServlet {

    private final String VIEW_PROMOTION_CONTROLLER = "viewListPromotion";

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

        String username = request.getParameter("txtUsername");

        String btnAction = request.getParameter("btnAction");
        String url = null;
        try {
            HttpSession session = request.getSession();
            CartObj cart = (CartObj) session.getAttribute("CART");
            Map<UserDTO, Integer> items = cart.getItems();

            if (btnAction.equals("update")) {
                int newRank = Integer.parseInt(request.getParameter("txtRank"));
                for (UserDTO userDTO : items.keySet()) {
                    if (userDTO.getUsername().equals(username)) {
                        items.replace(userDTO, newRank);
                    }
                }
            }

            if (btnAction.equals("delete")) {
                cart.deleteUserFromPromotionList(username);

                List<String> listUserAdding = (List<String>) session.getAttribute("LIST_USER_ADDING");
                for (String userAdded : listUserAdding) {
                    if (userAdded.equals(username)) {
                        listUserAdding.remove(userAdded);
                        break;
                    }
                }
                session.setAttribute("LIST_USER_ADDING", listUserAdding);
            }

            session.setAttribute("CART", cart);
            url = VIEW_PROMOTION_CONTROLLER;

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
