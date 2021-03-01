/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dto.CartDTO;
import huynhntp.dto.ProductDTO;
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
public class AddController extends HttpServlet {

    private final String ERRRO = "index.html";
    private final String SUCCESS = "RecommendationController";
    private final String NOT_LOGIN = "login.jsp";
    private final Logger LOGGER = Logger.getLogger(AddController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERRRO;
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String productImage = request.getParameter("image");
            String price = request.getParameter("price");
            ProductDAO dao = new ProductDAO();
            int currenPage;
            try {
                currenPage = Integer.parseInt(request.getParameter("currentPage"));
            } catch (NumberFormatException e) {
                currenPage = 1;
            }
            ProductDTO dto = new ProductDTO(productID, productName, productImage, Float.parseFloat(price), 1);
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("user");           
            // case user login
            if (user != null) {
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartDTO(null);
                    cart.add(dto);
                    session.setAttribute("CART", cart);
                    request.setAttribute("MESSAGE", "You have bought: " + productName + " thanh cong!" + "Price: " + cart.getCart().get(dto.getProductID()).getPrice()
                            + "\n Quantity: " + dto.getQuantity());
                } else {
                    // Case contain product but  don't have enough amound
                    if (cart.getCart().containsKey(productID)) {
                        if (cart.getCart().get(productID).getQuantity() >= dao.getQuantity(productID)) {
                            request.setAttribute("SOLD_OUT", "Product sold out!");
                        } 
                        else {
                            cart.add(dto);
                            session.setAttribute("CART", cart);
                            request.setAttribute("MESSAGE", "You have bought: " + productName + " thanh cong!" + "Price: " + cart.getCart().get(dto.getProductID()).getPrice()
                                    + "\n Quantity: " + dto.getQuantity());
                        }
                    // Case don't have this product in cart 
                    } else {
                        cart.add(dto);
                        session.setAttribute("CART", cart);
                        request.setAttribute("MESSAGE", "You have bought: " + productName + " thanh cong!" + "Price: " + cart.getCart().get(dto.getProductID()).getPrice()
                                + "\n Quantity: " + dto.getQuantity());
                    }               
                }
//                request.setAttribute("CURRENT_PAGE", currenPage);
                url = SUCCESS;
            } else {
                url = NOT_LOGIN;
            }

        } catch (NumberFormatException | SQLException e) {
            LOGGER.error("Trouble at AddController: " + e.toString());
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
