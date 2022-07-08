package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    ArrayList<String> getAllCustomerIds();
    ArrayList<String> getAllItemCodes();
    String getOrderId();
    ItemDTO getItem(String code);
    CustomerDTO getCustomer(String id);
    boolean placeOrder(OrderDTO dto);
}
