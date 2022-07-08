package model.tm;

public class CartTM {
    private String custId;
    private String itemCode;
    private double unitPrice;
    private int orderQty;
    private double discount;
    private double price;

    public CartTM() {
    }

    public CartTM(String custId, String itemCode, double unitPrice, int orderQty, double discount, double price) {
        this.setCustId(custId);
        this.setItemCode(itemCode);
        this.setUnitPrice(unitPrice);
        this.setOrderQty(orderQty);
        this.setDiscount(discount);
        this.setPrice(price);
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String orderId) {
        this.custId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
