package bo.custom.impl;

import bo.custom.ManageOrderBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dto.OrderDTO;
import entity.Item;
import entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {
    private final CustomerDAO customerDAO=(CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    private final ItemDAO itemDAO=(ItemDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
    private final OrderDAO orderDAO=(OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    private final OrderDetailDAO detailDAO=(OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAIL);

    @Override
    public boolean addOrder(OrderDTO dto) {
        try {
            return orderDAO.add(new Order(dto.getOrderId(),dto.getOrderDate(),dto.getCustomerId(),dto.getOrderTime(),dto.getOrderCost()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateOrder(OrderDTO dto) {
        try {
            return orderDAO.update(new Order(dto.getOrderId(),dto.getOrderDate(),dto.getCustomerId(),dto.getOrderTime(),dto.getOrderCost()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String id) {
        try {
            return orderDAO.delete(id);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrder() {
        ArrayList<OrderDTO> orders=new ArrayList<>();
        try {
            ArrayList<Order> orderList=orderDAO.getAll();
            for(Order order : orderList) {
                orders.add(
                        new OrderDTO(order.getOrderId(),order.getOrderDate(),order.getCustomerId(),order.getOrderTime(),order.getOrderCost())
                );
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    @Override
    public String getOrderId() {
        try {
            return orderDAO.getId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
