package dao.custom;

import dao.CrudDAO;
import entity.Order;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean delete(String orderId,String itemCode) throws SQLException, ClassNotFoundException;
    OrderDetail get(String orderId,String itemCode) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDetail> getAll(String orderId) throws SQLException, ClassNotFoundException;
}
