package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.PlaceOrderBO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import util.ValidationUtil;
import model.tm.CartTM;
import model.tm.OrderTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class OrderFormController {
    public TextField txtQty;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String>  cmbItemId;

   // public TableColumn<CartTM,String>  colOrderId;
    public TableColumn<CartTM,String>  colItemCode;
    public TableColumn<CartTM,String>  colUnitPrice;
    public TableColumn<CartTM,String>  colQty;
    public TableColumn<CartTM,String>  colDiscount;
    public TableColumn<CartTM,String>  colPrice;

    public Label lblDate;
    public Label lblTime;
    public Label lblOrderId;

    public JFXTextField txtTitle;
    public JFXTextField txtCity;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public JFXTextField txtPostalCode;
    public JFXTextField txtProvince;
    public JFXTextField txtDiscount;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;

    public Label lblNetTotal;
    public TableView<CartTM> tblOrder;
    public TableColumn<CartTM,String> colCustId;

    private final PlaceOrderBO placeOrderBO=(PlaceOrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERDETAIL);
    private final ItemBO itemBO=(ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);
    private final CustomerBO customerBO=(CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);


    Pattern qtyPattern=Pattern.compile("^[0-9]*$");

    int cartSelectedRowForRemove = -1;
    ObservableList<CartTM> obList= FXCollections.observableArrayList();
    LinkedHashMap<TextField, Pattern> validationList = new LinkedHashMap<>();


    public void initialize() {

        loadDateAndTime();
        setOrderId();
        showValidation();

        try {
            loadCustomerId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            loadItemId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

        listenFieldChange(validationList);
    }

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> validationList) {
        for(TextField field: validationList.keySet()) {
            field.textProperty().addListener((observable, oldValue, newValue) ->{
                ValidationUtil.validate(field,validationList.get(field));
            });
        }
    }

    private void showValidation() {
        validationList.put(txtQty,qtyPattern);
    }

    private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = placeOrderBO.getItem(newValue);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            //txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            setQtyOnHand(i1);
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtDiscount.setText(String.valueOf(i1.getDiscountPercentage()));
        }
    }

    private void setCustomerData(String newValue) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = placeOrderBO.getCustomer(newValue);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtTitle.setText(c1.getTitle());
            txtName.setText(c1.getName());
            txtAddress.setText(c1.getAddress());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtPostalCode.setText(c1.getPostalCode());
        }
    }

    private void loadItemId() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> all = itemBO.getAllItems();
        for (ItemDTO dto : all) {
            cmbItemId.getItems().add(dto.getItemCode());
        }
    }

    private void loadCustomerId() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> all = customerBO.getAllCustomers();
        for (CustomerDTO dto : all) {
            cmbCustomerId.getItems().add(dto.getCustomerId());
        }
    }


    private void setOrderId() {
        lblOrderId.setText(placeOrderBO.getOrderId());
    }


    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a"))
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        if (ValidationUtil.isAllValidated(validationList)){
            String orderId = lblOrderId.getText();
            String itemCode = cmbItemId.getSelectionModel().getSelectedItem();
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
            int orderQty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            double discount = Double.parseDouble(txtDiscount.getText());
            double total = (orderQty * unitPrice) - ((orderQty * unitPrice)*discount/100);

            if (qtyOnHand < orderQty) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }
            ItemDTO itemDTO=placeOrderBO.getItem(cmbItemId.getValue());

            CartTM tm = new CartTM(cmbCustomerId.getValue(),itemCode,unitPrice,orderQty, discount, total);

            int rowNumber=isExists(tm);

            if (rowNumber==-1){
                obList.add(tm);
            }else {
                CartTM temp = obList.get(rowNumber);
                CartTM newTm = new CartTM(temp.getCustId(), temp.getItemCode(), temp.getUnitPrice(), temp.getOrderQty() + orderQty, temp.getDiscount(), total + temp.getPrice());

                obList.remove(rowNumber);
                obList.add(newTm);

            }

            tblOrder.setItems(obList);
            calculateCost();
            setQtyOnHand(itemDTO);
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }

    }

    private void setQtyOnHand(ItemDTO item){
        if (item!=null) {
            int newQty=item.getQtyOnHand();
            for (CartTM cartTM : obList) {
                if (item.getItemCode().equals(item.getItemCode())) {
                    newQty-=cartTM.getOrderQty();
                }
            }
            txtQtyOnHand.setText(String.valueOf(newQty));
        }
    }

    private int isExists(CartTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemCode().equals(obList.get(i).getItemCode())){
                return i;
            }
        }
        return -1;
    }

    public void clearOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            ItemDTO item1 = placeOrderBO.getItem(obList.get(cartSelectedRowForRemove).getItemCode());
            obList.remove(cartSelectedRowForRemove);
            if (item1.getItemCode().equals(cmbItemId.getValue())) {
                setQtyOnHand(item1);
            }
            calculateCost();
            tblOrder.setItems(obList);
        }
    }

    private void calculateCost() {
        double ttl=0;
        for (CartTM tm:obList) {
            ttl+=tm.getPrice();
        }
        lblNetTotal.setText(ttl+" /=");
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        ArrayList<OrderDetailDTO> items= new ArrayList<>();
        double total=0;
        for (CartTM tempTm:obList) {
            total+=tempTm.getPrice();
            items.add(new OrderDetailDTO(lblOrderId.getText(),tempTm.getItemCode(),tempTm.getUnitPrice(), tempTm.getOrderQty(),tempTm.getDiscount(),tempTm.getPrice()));
        }
        OrderDTO orders = new OrderDTO(lblOrderId.getText(), lblDate.getText(), cmbCustomerId.getValue(), lblTime.getText(), total , items);
        if (placeOrderBO.placeOrder(orders)){
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setOrderId();
            refreshWindow();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private void refreshWindow() {

    }
}
