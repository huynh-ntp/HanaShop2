/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.controllerr;

import huynhntp.dao.ProductDAO;
import huynhntp.dto.CartDTO;
import huynhntp.dto.OrderDTO;
import huynhntp.dto.ProductDTO;
import huynhntp.dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ACER
 */
public class PayController extends HttpServlet {

    private final static String ERROR = "index.html";
    private final static String SUCCESS = "successPay.jsp";
    private final static String PAY_FAIL = "viewPage.jsp";
    private final Logger LOGGER = Logger.getLogger(PayController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        boolean check = true;
        try {
            String payKind = request.getParameter("btnAction");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("user");
            String userID = user.getUserID();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            float total = 0;
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            ProductDAO dao = new ProductDAO();
            List<String> error = new ArrayList<>();
            
            
            if (address != null) {
                if (address.trim().isEmpty()) {
                    check = false;
                    error.add("Please give Hana Shop your real address for ship product! ");
                }
                // Case if using VNPAY can not get parameter
            } else {
                address = (String) session.getAttribute("ADDRESS");
            }
            
            if (phone != null) {
                if (phone.trim().isEmpty()) {
                    check = false;
                    error.add("Please give Hana Shop your real phone number for ship product! ");
                }
            // Case if using VNPAY can not get parameter
            } else {
                phone = (String) session.getAttribute("PHONE");
            }
            
            
            if (cart != null) {
                for (ProductDTO dto : cart.getCart().values()) {
                    if (!dao.checkout(dto.getProductID(), dto.getQuantity())) {
                        error.add("Product " + dto.getName() + " don't have enough amount!\n Hanashop is only left " + dao.getQuantity(dto.getProductID())); // Case don't have enough quantity 
                        check = false;
                    }
                    total += dto.getQuantity() * dto.getPrice();
                }
            }
            
            // Case everything is oke
            if (check) {
                // Create orderID 
                String orderID;
                while (true) {
                    orderID = RandomStringUtils.randomNumeric(10);
                    if (!dao.duplicateOrderID(orderID)) {
                        break;
                    }
                }
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String orderDate = format.format(date);
                OrderDTO order = new OrderDTO(orderID, userID, total, orderDate, address, phone);
                dao.insertOrder(order);
                for (ProductDTO dto : cart.getCart().values()) {
                    dao.insertOrderDetail(orderID, dto.getProductID(), dto.getPrice() * dto.getQuantity(), dto.getQuantity());
                    int quantityRemain = dao.getQuantity(dto.getProductID()) - dto.getQuantity();
                    dao.updateQuantity(dto.getProductID(), quantityRemain);
                }
                url = SUCCESS;
                session.removeAttribute("CART");
                session.removeAttribute("Address");
            } else {
                request.setAttribute("PayError", error);
                url = PAY_FAIL;
            }

        } catch (NumberFormatException | SQLException e) {
            LOGGER.error("Trouble at PayController: " + e.toString());
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
