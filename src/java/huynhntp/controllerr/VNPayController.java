/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import java.io.IOException;
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
public class VNPayController extends HttpServlet {

    public static final String ERROR = "index.html";
    public static final String SUCCESS = "index.jsp";
    public static final String FAILED = "viewPage.jsp";
    
    public static final Logger LOGGER = Logger.getLogger(VNPayController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            if (request.getParameter("address").trim().isEmpty()) {
                request.setAttribute("EMPTY_ADD", "Please give shop real address to shipping!");
                url = FAILED;
            } else if (request.getParameter("phone").trim().isEmpty()) {
                request.setAttribute("EMPTY_PHONE", "Please give shop real phone number to shipping!");
                url = FAILED;
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("ADDRESS", request.getParameter("address"));
                session.setAttribute("PHONE", request.getParameter("phone"));
                url = SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.error("Trouble at VNPayController: " + e.toString());
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
