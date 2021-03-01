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


//@WebServlet("/ProductMngController")
public class ProductMngController extends HttpServlet {

    private final String SUCCESS = "admin.jsp";
    private final String ERROR = "index.html";
    private final float PAGE_SIZE = 5;
    private final Logger LOGGER = Logger.getLogger(ProductMngController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            ProductDAO dao = new ProductDAO();
            float toalRecord = dao.getTotalRecord();
            int totalPage = (int)Math.ceil(toalRecord/PAGE_SIZE);
            int currenPage;
            try {
                currenPage = Integer.parseInt(request.getParameter("currentPage"));
            } catch (NumberFormatException e) {
                currenPage = 1;
            }           
            List<ProductDTO> list = dao.getAllForAd(currenPage);  
            List<CategoryDTO> categoryList = dao.getCategory();
            List<Boolean> statusList = new ArrayList<>();
            statusList.add(true);
            statusList.add(false);
            if(list!=null && categoryList!=null){                
                request.setAttribute("LIST", list);
                request.setAttribute("CATEGORY_LIST", categoryList);
                request.setAttribute("STATUS_LIST", statusList);
                request.setAttribute("TOTAL_PAGE", totalPage);
            }
            url = SUCCESS;
        } catch (SQLException | NamingException e ) {
            LOGGER.error("Trouble at PorudctMngController: " + e.toString());
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
