package entity;

import dto.OrderDetailDTO;
//import model.OrderDetail;

import java.util.ArrayList;

public class SystemReport {
    private String orderId;
    private String orderDate;
    private String customerId;
    private String orderTime;
    private double orderCost;
    private ArrayList<OrderDetailDTO> detailList;


    public SystemReport() {
    }

    public SystemReport(String orderId, String orderDate, String customerId, String orderTime, double orderCost, ArrayList<OrderDetailDTO> detailList) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustomerId(customerId);
        this.setOrderTime(orderTime);
        this.setOrderCost(orderCost);
        this.setDetailList(detailList);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }

    public ArrayList<OrderDetailDTO> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<OrderDetailDTO> detailList) {
        this.detailList = detailList;
    }
}
