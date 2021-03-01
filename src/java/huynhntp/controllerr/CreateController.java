/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dto.ErrorProductDTO;
import huynhntp.dto.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CreateController extends HttpServlet {

    private final String ERROR = "index.html";
    private final String SUCCESS = "GotoCreateController";
    private final Logger LOGGER = Logger.getLogger(CreateController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        boolean check = true;
        ErrorProductDTO error = new ErrorProductDTO("", "", "");
        try {
            String id = request.getParameter("productID");
            String name = request.getParameter("name");
            String image = request.getParameter("image");
            String description = request.getParameter("description");
            String price = request.getParameter("price");
            String createDate = "";
            String categoryID = request.getParameter("categoryID");
            String quantity = request.getParameter("quantity");
            
            if(id.trim().isEmpty()){
                check = false;
                error.setProductIDError("ID of product not empty!");
            }
            if(name.trim().isEmpty()){
                check = false;
                error.setNameError("Name of product not empty!");
            }
            if(description.trim().isEmpty()){
                check = false;
                error.setDescriptionError("Description of product not empty!");
            }
            if(check){
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    createDate = format.format(date);
                } catch (Exception e) {
                }
                ProductDAO dao = new ProductDAO();
                ProductDTO dto = new ProductDTO(id, name, image, description, Float.parseFloat(price), createDate, categoryID, Integer.parseInt(quantity));
                dao.createProduct(dto);
                request.setAttribute("SUCCESS", "One product was created successfull!");
            }else{
                request.setAttribute("ERROR", error);
            }
            url = SUCCESS;            
        } catch (NumberFormatException | SQLException | NamingException e) {
            if(e.toString().contains("duplicate")){
                error.setProductIDError("ID of product duplicate!");
                request.setAttribute("ERROR", error);
                url = SUCCESS;  
            }
            LOGGER.error("Trouble at CreateController : " + e.toString());
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
