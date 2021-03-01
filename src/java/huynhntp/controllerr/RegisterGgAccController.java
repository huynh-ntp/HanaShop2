/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.UserDAO;
import huynhntp.dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
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
public class RegisterGgAccController extends HttpServlet {

    private static final String SUCCESS = "ShoppingController";
    private static final String ERROR = "index.html";
    private final Logger LOGGER = Logger.getLogger(RegisterGgAccController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String id = (String) request.getAttribute("id");
            String email = (String) request.getAttribute("email");
            int index = email.indexOf("@");
            String name = email.substring(0, index);
            String roleID = "US";
            UserDAO dao = new UserDAO();           
            if(!dao.checkExistGg(id)){
                dao.createGg(id, name, roleID, true);
            }
            UserDTO user = dao.checkLoginGoogle(id);
            if(user!=null){
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                url = SUCCESS;
            }
        } catch (SQLException e) {
            LOGGER.error("Trouble at RegisterGgAccController: " + e.toString());
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
