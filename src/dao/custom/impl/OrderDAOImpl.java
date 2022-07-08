package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.Customer;
import entity.Order;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Orders VALUES(?,?,?,?,?)",order.getOrderId(),order.getOrderDate(),order.getCustomerId(),order.getOrderTime(),order.getOrderCost());
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Orders SET orderDate=?,custId=?,time=?,cost=? WHERE orderId=?",order.getOrderDate(),order.getCustomerId(),order.getOrderTime(),order.getOrderCost(),order.getOrderId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Orders WHERE orderId=?",id);

    }

    @Override
    public Order get(String id) throws SQLException, ClassNotFoundException {
        Order order = null;
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Orders WHERE orderId=?", id);
        if (rst.next()) {
            order = new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
        }
        return order;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM Orders");
        while (resultSet.next()) {
            orders.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));
        }
        return orders;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.executeQuery("SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1");
        if(resultSet.next()){
            int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
            if(index<9){
                return "O-000"+ ++index;
            }else if(index<99){
                return "O-00"+ ++index;
            }else if(index<999){
                return "O-0"+ ++index;
            }else{
                return "O-"+ ++index;
            }
        }else{
            return "O-0001";
        }
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> orders = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM Orders");
        while (rst.next()) {
            orders.add(rst.getString(1));
        }
        return orders;
    }
}
