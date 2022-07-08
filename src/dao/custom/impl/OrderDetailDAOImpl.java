package dao.custom.impl;

import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import entity.Order;
import entity.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean add(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO OrderDetail VALUES(?,?,?,?,?,?)",orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getUnitPrice(),orderDetail.getOrderQty(),orderDetail.getDiscount(),orderDetail.getPrice());
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE OrderDetail SET itemCode=?,unitPrice=?,orderQTY=?,discount=?,price=? WHERE orderId=?",orderDetail.getItemCode(),orderDetail.getUnitPrice(),orderDetail.getOrderQty(),orderDetail.getDiscount(),orderDetail.getPrice(),orderDetail.getOrderId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail get(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String orderId, String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM OrderDetail WHERE orderId=? AND itemCode=?",orderId,itemCode);
    }

    @Override
    public OrderDetail get(String orderId, String itemCode) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail=null;
        ResultSet rst=CrudUtil.executeQuery("SELECT * FROM Orders WHERE orderId=? AND itemCode=?",orderId,itemCode);
        if(rst.next()){
            orderDetail=new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            );
        }
        return orderDetail;
    }

    @Override
    public ArrayList<OrderDetail> getAll(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        ResultSet rst=CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE orderId=?",orderId);
        while(rst.next()){
            orderDetails.add(
                    new OrderDetail(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3),
                            rst.getInt(4),
                            rst.getDouble(5),
                            rst.getDouble(6)
                    )
            );
        }
        return orderDetails;
    }
}
