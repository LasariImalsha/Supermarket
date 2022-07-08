package bo.custom.impl;

import bo.custom.PlaceOrderBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DbConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    private final CustomerDAO customerDAO=(CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    private final ItemDAO itemDAO=(ItemDAO)DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
    private final OrderDAO orderDAO=(OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    private final OrderDetailDAO detailDAO=(OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAIL);

    @Override
    public ArrayList<String> getAllCustomerIds() {
        ArrayList<String> customerIds=new ArrayList<>();
        try {
            customerIds=customerDAO.getAllIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return customerIds;
    }

    @Override
    public ArrayList<String> getAllItemCodes() {
        ArrayList<String> itemCodes=new ArrayList<>();
        try {
            itemCodes=itemDAO.getAllIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return itemCodes;
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

    @Override
    public ItemDTO getItem(String code) {
        try {
            Item item=itemDAO.get(code);
            return new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand(),item.getDiscountPercentage());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public CustomerDTO getCustomer(String id) {
        try {
            Customer customer=customerDAO.get(id);
            return new CustomerDTO(customer.getCustomerId(),customer.getTitle(),customer.getName(),customer.getAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean placeOrder(OrderDTO dto) {
        Connection connection=null;
        try{
            connection= DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = orderDAO.add(new Order(dto.getOrderId(), dto.getOrderDate(), dto.getCustomerId(), dto.getOrderTime(), dto.getOrderCost()));
            if(isOrderSaved){
                ArrayList<OrderDetailDTO> detailList=dto.getDetailList();
                int affectedDetailRows =0;
                for(OrderDetailDTO detailDTO : detailList) {
                    boolean isDetailAdded = detailDAO.add(new OrderDetail(detailDTO.getOrderId(), detailDTO.getItemCode(), detailDTO.getUnitPrice(), detailDTO.getOrderQty(), detailDTO.getDiscount(), detailDTO.getPrice()));
                    if(isDetailAdded){
                        affectedDetailRows++;
                    }else{
                        return false;
                    }
                }
                boolean isOrderDetailSaved = (detailList.size()==affectedDetailRows);
                if(isOrderDetailSaved){
                    int affectedItems=0;
                    for(OrderDetailDTO detailDTO : detailList) {
                        Item item=itemDAO.get(detailDTO.getItemCode());
                        item.setQtyOnHand(item.getQtyOnHand()-detailDTO.getOrderQty());
                        boolean isUpdated = itemDAO.update(item);
                        if(isUpdated){
                            affectedItems++;
                        }else{
                            return false;
                        }
                    }
                    boolean isItemUpdated = (detailList.size()== affectedItems);
                    if(isItemUpdated){
                        connection.commit();
                        return true;
                    }else{
                        connection.rollback();
                        return false;
                    }
                }else{
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }
        }catch(SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                assert connection != null;
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;

    }
}
