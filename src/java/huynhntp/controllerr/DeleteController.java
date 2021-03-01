/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dto.UpdateDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ACER
 */
public class DeleteController extends HttpServlet {

    private final String ERROR = "index.html";
    private final String SUCCESS = "SearchAdminController";
    private final static Logger LOGGER = Logger.getLogger(DeleteController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String productID = request.getParameter("productID");
            String userID = request.getParameter("userID");
            ProductDAO dao = new ProductDAO();
            dao.delete(productID);
            Date date = new Date();
            SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String updateDate = "";
            try {
                updateDate = fm.format(date);
            } catch (Exception e) {
            }
            while (true) {
                String updateID = RandomStringUtils.randomAlphanumeric(10);
                if (!dao.duplicateUpdateID(updateID)) {
                    UpdateDTO dto = new UpdateDTO(updateID, userID, updateDate, productID);
                    dao.createUpRecord(dto);
                    request.setAttribute("UPDATE_MESSAGE", "Delete successfully!");
                    url = SUCCESS;
                    break;
                }
            }
                       
        } catch (SQLException | NamingException e) {
            LOGGER.error("Trouble at DeleteContronller :" + e.toString());
        } finally {
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
