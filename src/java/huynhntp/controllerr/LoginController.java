/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dao.UserDAO;
import huynhntp.dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author ACER
 */
public class LoginController extends HttpServlet {

   private final String CUSTOMER = "ShoppingController";
   private final String ERROR = "login.jsp";
   private final String ADMIN = "MngController";
   private final Logger LOGGER = Logger.getLogger(LoginController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userID = request.getParameter("userID");
            String pw = request.getParameter("pw");
            UserDAO dao = new UserDAO();
            ProductDAO dao2 = new ProductDAO();
            UserDTO user = dao.checkLogin(userID, pw);
            HttpSession session = request.getSession();
            List<ProductDAO> list = null;
            if(user!=null && user.isStatus()){
                if(user.getRoleID().equals("AD")){
                    session.setAttribute("user", user);                   
                    url = ADMIN;
                }
                if(user.getRoleID().equals("US")){
                    session.setAttribute("user", user);
                    url = CUSTOMER;
                }
            }else{
                request.setAttribute("error", "Incorect userID or passwords or account not allow");
                
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Trouble at LoginController: " +e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);
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
