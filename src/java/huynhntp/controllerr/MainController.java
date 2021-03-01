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
public class MainController extends HttpServlet {
    private static final String LOGIN = "LoginController";
    private static final String ERROR = "index.html";
    private static final String LOGOUT = "LogoutController";
    private static final String GOTO_CREATE = "GotoCreateController";
    private static final String CREATE_NEW = "CreateController";
    private static final String EDIT = "EditController";
    private static final String DELETE = "DeleteController";
    private static final String UPDATE = "UpdateController";
    private static final String START = "ShoppingController";
    private static final String ADD = "AddController";
    private static final String UPDATE_CART = "UpdateCartController";
    private static final String DELETE_CART = "DeleteCartController";
    private static final String SEARCH = "SearchController";
    private static final String PAY = "PayController";
    private static final String MNG = "MngController";
    private static final String HISTORY = "HistoryOrderController";
    private static final String SEARCH_HISTORY = "SearchHisController";
    private static final String SEARCH_ADMIN = "SearchAdminController";
    private static final String VNPAY = "VNPayController";
    private static final String VIEW_PAGE = "viewPage.jsp";
    private final Logger LOGGER = Logger.getLogger(MainController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("btnAction");
            if("Login".equals(action)){
                url = LOGIN;
            }else if("Logout".equals(action)){
                url = LOGOUT;
            }else if("GotoCreateController".equals(action)){
                url = GOTO_CREATE;
            }else if("Create".equals(action)){
                url = CREATE_NEW;
            }else if("Edit".equals(action)){
                url = EDIT;
            }else if("Delete".equals(action)){
                url = DELETE;
            }else if("Update".equals(action)){
                url = UPDATE;
            }else if("Start".equals(action)){
                url = START;
            }else if("Add".equals(action)){
                url = ADD;
            }else if("DeleteCart".equals(action)){
                url = DELETE_CART;
            }else if("UpdateCart".equals(action)){
                url = UPDATE_CART;
            }else if("Search".equals(action)){
                url = SEARCH;
            }else if("Payment".equals(action)){
                url = PAY;
            }else if("Manage".equals(action)){
                url = MNG;
            }else if("History".equals(action)){
                url = HISTORY;
            }else if("Search History".equals(action)){
                url = SEARCH_HISTORY;
            }else if("Search Admin".equals(action)){
                url = SEARCH_ADMIN;
            }else if("VnPay".equals(action)){
                url = VNPAY;
                
            }
        } catch (Exception e) {
            LOGGER.error("Trouble at MainContrller:" + e.toString());
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
