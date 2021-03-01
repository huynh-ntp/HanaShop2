/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.dao;

import huynhntp.dto.CategoryDTO;
import huynhntp.dto.OrderDTO;
import huynhntp.dto.ProductDTO;
import huynhntp.dto.PurchaseHistoryDTO;
import huynhntp.dto.UpdateDTO;
import huynhntp.util.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ACER
 */
public class ProductDAO {

    private final int PAGE_SIZE = 5;

    public List<ProductDTO> getAllForAd(int currenPage) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT productID,productName,image,description,price,createDate,categoryID,quantity,status FROM tblProducts"
                        + " ORDER BY createDate\n"
                        + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, (currenPage - 1) * PAGE_SIZE);
                stm.setInt(2, PAGE_SIZE);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new ProductDTO(rs.getString("productID"), rs.getString("productName"), rs.getString("image"), rs.getString("description"),
                                rs.getFloat("price"), rs.getString("createDate"), rs.getString("categoryID"),
                                rs.getInt("quantity"), rs.getBoolean("status")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<CategoryDTO> getCategory() throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<CategoryDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT categoryID,name FROM tblCategory";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new CategoryDTO(rs.getString("categoryID"), rs.getString("name")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public void createProduct(ProductDTO dto) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblProducts(productID,productName,image,description,price,createDate,categoryID,quantity)\n"
                        + "VALUES(?,?,?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getProductID());
                stm.setString(2, dto.getName());
                stm.setString(3, dto.getImage());
                stm.setString(4, dto.getDescription());
                stm.setFloat(5, dto.getPrice());
                stm.setString(6, dto.getCreateDate());
                stm.setString(7, dto.getCategoryID());
                stm.setInt(8, dto.getQuantity());
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public void delete(String productID) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "	UPDATE tblProducts\n"
                        + "	SET status = 0\n"
                        + "	WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, productID);
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    

    public String getImage(String productID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String result = "";
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT image FROM tblProducts\n"
                        + "WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, productID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getString("image");
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public void update(ProductDTO dto) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "	UPDATE tblProducts\n"
                        + "SET productName = ?, image = ?, description = ? , price = ? , categoryID = ?, quantity = ?, status = ?\n"
                        + "WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getName());
                stm.setString(2, dto.getImage());
                stm.setString(3, dto.getDescription());
                stm.setFloat(4, dto.getPrice());
                stm.setString(5, dto.getCategoryID());
                stm.setInt(6, dto.getQuantity());
                stm.setBoolean(7, dto.isStatus());
                stm.setString(8, dto.getProductID());
                stm.executeUpdate();
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public float getTotalRecord() throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float result = 0;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT count(productID) as totalRecord FROM tblProducts";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("totalRecord");
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public float getTotalRecordSearch(String price, String name, String categoryID) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float result = 0;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT count(productID) as totalRecord FROM tblProducts\n"
                        + "WHERE status = 1 and quantity > 0 and productName like " + "'%" + name + "%'" + " and price <= " + price + " and categoryID like " + " '%" + categoryID + "%'\n";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("totalRecord");
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
    
    public float getTotalRecordAdSearch(String price, String name, String categoryID) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float result = 0;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT count(productID) as totalRecord FROM tblProducts\n"
                        + "WHERE productName like " + "'%" + name + "%'" + " and price <= " + price + " and categoryID like " + " '%" + categoryID + "%'\n";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("totalRecord");
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public float getTotalRecordActive() throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float result = 0;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT count(productID) as totalRecord FROM tblProducts WHERE status = 1 and quantity>0";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("totalRecord");
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public void createUpRecord(UpdateDTO dto) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblUpdateProduct(updateID,userID,updateDate,productID)"
                        + " VALUES(?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getUpdateID());
                stm.setString(2, dto.getUserID());
                stm.setString(3, dto.getUpdateDate());
                stm.setString(4, dto.getProductID());
                stm.executeUpdate();
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public List<ProductDTO> getForShopping(int currenPage) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT productID,productName,image,description,price FROM tblProducts\n"
                        + "WHERE status = 1 and quantity>0\n"
                        + "ORDER BY productID\n"
                        + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, (currenPage - 1) * PAGE_SIZE);
                stm.setInt(2, PAGE_SIZE);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new ProductDTO(rs.getString("productID"), rs.getString("productName"), rs.getString("image"), rs.getString("description"),
                                rs.getFloat("price")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<ProductDTO> getForSearch(int currenPage, String name, String price, String categoryID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT productID,productName,image,description,price FROM tblProducts\n"
                        + "WHERE status = 1 and quantity > 0 and productName like " + "'%" + name + "%'" + " and price <= " + price + " and categoryID like " + " '%" + categoryID + "%'\n"
                        + "ORDER BY createDate\n"
                        + "OFFSET " + (currenPage - 1) * PAGE_SIZE + " ROWS FETCH NEXT " + PAGE_SIZE + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
//                stm.setString(1, price);
//                stm.setInt(2, (currenPage - 1) * PAGE_SIZE);
//                stm.setInt(3, PAGE_SIZE);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new ProductDTO(rs.getString("productID"), rs.getString("productName"), rs.getString("image"), rs.getString("description"),
                                rs.getFloat("price")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public List<ProductDTO> getForAdSearch(int currenPage, String name, String price, String categoryID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT productID,productName,image,description,price,createDate,categoryID,quantity,status FROM tblProducts\n"
                        + "WHERE productName like " + "'%" + name + "%'" + " and price <= " + price + " and categoryID like " + " '%" + categoryID + "%'\n"
                        + "ORDER BY createDate\n"
                        + "OFFSET " + (currenPage - 1) * PAGE_SIZE + " ROWS FETCH NEXT " + PAGE_SIZE + " ROWS ONLY";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new ProductDTO(rs.getString("productID"), rs.getString("productName"), rs.getString("image"), rs.getString("description"),
                                rs.getFloat("price"), rs.getString("createDate"), rs.getString("categoryID"),
                                rs.getInt("quantity"), rs.getBoolean("status")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public int getQuantity(String productID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT quantity FROM tblProducts WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, productID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("quantity");
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public int getMaxPrice() throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT MAX(price) as maxPrice FROM tblProducts";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("maxPrice");
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public boolean duplicateUpdateID(String updateID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT quantity FROM tblProducts WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, updateID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public boolean checkout(String productID, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT productID FROM tblProducts\n"
                        + "WHERE quantity >=? AND productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, productID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
 
    
    public boolean duplicateOrderID(String orderID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT orderID FROM tblOrders\n"
                        + "WHERE orderID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public void insertOrder(OrderDTO order) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblOrders(orderID,userID,totalPrice,orderDate,address,phone)"
                        + " VALUES(?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, order.getOrderID());
                stm.setString(2, order.getUserID());
                stm.setFloat(3, order.getTotalPrice());
                stm.setString(4, order.getOrderDate());
                stm.setString(5, order.getAddress());
                stm.setString(6, order.getPhone());
                stm.executeUpdate();
            }
        } catch (SQLException | NamingException e) {
        } finally {

            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void insertOrderDetail(String orderID, String productID, float price, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblOrderDetails(orderID,productID,price,quantity)"
                        + " VALUES(?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setString(2, productID);
                stm.setFloat(3, price);
                stm.setInt(4, quantity);
                stm.executeUpdate();
            }
        } catch (SQLException | NamingException e) {
        } finally {

            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void updateQuantity(String productID, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblProducts\n"
                        + "SET quantity = ?\n"
                        + "WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, productID);
                stm.executeUpdate();
            }
        } catch (SQLException | NamingException e) {
        } finally {

            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public List<String> getOrderList(String userID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<String> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT orderID FROM tblOrders\n"
                        + "WHERE userID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(rs.getString("orderID"));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public List<String> getOrderListWithDate(String userID,String date) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<String> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT orderID FROM tblOrders\n"
                        + "WHERE userID = ? and orderDate >= ? and orderDate < ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                
                stm.setString(2, date +" 00:00:00");
                stm.setString(3, date + " 23:59:59");
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(rs.getString("orderID"));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<ProductDTO> getOrderDetail(String orderID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT b.productID, b.productName, b.image, b.description, b.price\n"
                        + "FROM tblorderDetails a join tblProducts b on a.productID = b.productID\n"
                        + "WHERE b.status = 1 and a.orderID = ? and b.quantity >0";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderID);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new ProductDTO(rs.getString("productID"), rs.getString("productName"), rs.getString("image"), rs.getString("description"), rs.getFloat("price")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<PurchaseHistoryDTO> getHistory(String orderID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<PurchaseHistoryDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "	SELECT productName,image,a.quantity,a.price\n"
                        + "	FROM tblOrderDetails a JOIN tblProducts b ON a.productID = b.productID\n"
                        + " WHERE orderID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderID);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new PurchaseHistoryDTO(rs.getString("productName"), rs.getString("image"), rs.getInt("quantity"), rs.getFloat("price")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public List<PurchaseHistoryDTO> getHistoryWithName(String orderID, String productName) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<PurchaseHistoryDTO> list = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "	SELECT productName,image,a.quantity,a.price\n"
                        + "	FROM tblOrderDetails a JOIN tblProducts b ON a.productID = b.productID\n"
                        + " WHERE orderID = "+orderID+" and productName like "+"'%"+productName+"%'";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs != null) {
                    list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new PurchaseHistoryDTO(rs.getString("productName"), rs.getString("image"), rs.getInt("quantity"), rs.getFloat("price")));
                    }
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

}
