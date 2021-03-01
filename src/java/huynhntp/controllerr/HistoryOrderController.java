/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dto.PurchaseHistoryDTO;
import huynhntp.dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class HistoryOrderController extends HttpServlet {

    private final static String ERROR = "index.html";
    private final static String SUCCESS = "historyPage.jsp";
    private final Logger LOGGER = Logger.getLogger(HistoryOrderController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO)session.getAttribute("user");
            String userID = user.getUserID();
            ProductDAO dao = new ProductDAO();
            List<String> orderList = dao.getOrderList(userID);
            List<PurchaseHistoryDTO> hisList = new ArrayList<>();
            if(orderList!=null){
                for (String orderID : orderList) {
                    addList(hisList, orderID);                      
                }
            }
            if(!hisList.isEmpty()){
                request.setAttribute("HIS_LIST", hisList);
            }
            url = SUCCESS;             
        } catch (SQLException e) {
            LOGGER.error("Trouble at HistoryOrderController " + e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);
        }       
    }
    
    protected void addList(List<PurchaseHistoryDTO> histList, String orderID) throws SQLException{
        ProductDAO dao = new ProductDAO();
        List<PurchaseHistoryDTO> tempList = dao.getHistory(orderID);
        for (PurchaseHistoryDTO dto : tempList) {
            histList.add(dto);
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
