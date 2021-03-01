/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dto.CategoryDTO;
import huynhntp.dto.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
/**
 *
 * @author ACER
 */
public class SearchAdminController extends HttpServlet {

    private final static String ERROR = "index.html";
    private final static String SUCCESS = "admin.jsp";
    private final static  float PAGE_SIZE = 5;
    private final Logger LOGGER = Logger.getLogger(SearchAdminController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String name = request.getParameter("nameSearch");
            String price = request.getParameter("priceSearch");
            String categoryID = request.getParameter("categoryIDSearch");
            ProductDAO dao = new ProductDAO();
            if(price.isEmpty()){
                price = Integer.toString(dao.getMaxPrice());
            }
            float totalSearchRecord = dao.getTotalRecordSearch(price, name, categoryID);
            int currenPage;
            try {
                currenPage = Integer.parseInt(request.getParameter("currentPage"));
            } catch (NumberFormatException e) {
                currenPage = 1;
            }
            int totalPage = (int)Math.ceil(totalSearchRecord/PAGE_SIZE);
            
            List<ProductDTO> list = dao.getForAdSearch(currenPage, name, price, categoryID);
            List<CategoryDTO> categoryList = dao.getCategory();
            List<Boolean> statusList = new ArrayList<>();
            statusList.add(true);
            statusList.add(false);
            
            
            if(list!=null){
                request.setAttribute("LIST", list);
                request.setAttribute("TOTAL_PAGE", totalPage);
                request.setAttribute("CATEGORY_LIST", categoryList);
                request.setAttribute("STATUS_LIST", statusList);
            }
            url =SUCCESS;
            
            
        } catch (SQLException | NamingException e) {
            LOGGER.error("Trouble at SearchAdminController: " + e.toString());
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
