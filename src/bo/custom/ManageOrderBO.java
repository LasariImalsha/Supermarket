package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;

import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {
    boolean addOrder(OrderDTO dto);
    boolean updateOrder(OrderDTO dto);
    boolean deleteOrder(String id);
    ArrayList<OrderDTO> getAllOrder();
    String getOrderId();
}
