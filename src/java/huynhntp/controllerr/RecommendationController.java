/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dto.ProductDTO;
import huynhntp.dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class RecommendationController extends HttpServlet {

    private static final String ERROR = "index.html";
    private static final String SUCCESS = "SearchController";
    
    private final Logger LOGGER = Logger.getLogger(RecommendationController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO)session.getAttribute("user");
            String productID = request.getParameter("productID");
            String userID = user.getUserID();
            ProductDAO dao = new ProductDAO();
            List<String > orderList = dao.getOrderList(userID);
            Map<String,ProductDTO> recomendationList = new HashMap<>();
            if(orderList!=null){
                for (String orderID : orderList) {
                    List<ProductDTO> productList = dao.getOrderDetail(orderID);
                    if(existProduct(productList, productID)){
                        addRecommendation(recomendationList, productList, productID);                       
                    }            
                }         
            }
            if(!recomendationList.isEmpty()){
                request.setAttribute("REC_LIST", recomendationList);
            }
            url = SUCCESS;     
        } catch (SQLException e) {
            LOGGER.error("Trouble at RecommendationController: " + e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);
        }
        
        
    }
    protected boolean existProduct(List<ProductDTO> productList, String productID ){
        boolean result = false;
        for (ProductDTO dto : productList) {
            if(dto.getProductID().equals(productID)){
                result = true;
                break;
            }
        }
        return result;  
    }
    
    protected void addRecommendation(Map<String,ProductDTO> recommendationList, List<ProductDTO> productList, String productID ){
        for (ProductDTO dto : productList) {
            if(recommendationList.containsKey(dto.getProductID())) continue;
            recommendationList.put(dto.getProductID(), dto);
        }
        recommendationList.remove(productID);
        
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
