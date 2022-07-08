package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO dto);
    boolean updateCustomer(CustomerDTO dto);
    boolean deleteCustomer(String id);
    String getCustomerIds();
    ArrayList<CustomerDTO> getAllCustomers();

}
