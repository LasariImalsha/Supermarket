package controller;

import bo.BOFactory;
import bo.custom.ManageOrderBO;
import dto.OrderDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//import model.Order;
import util.ValidationUtil;
//import model.tm.OrderTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageOrderFormController {
    public TextField txtCost;
    public TextField txtDate;
    public TextField txtCustomerId;
    public TextField txtTime;
    public TextField txtOrderId;

    public TableColumn<OrderDTO, String> colOrderId;
    public TableColumn<OrderDTO, String> colOrderDate;
    public TableColumn<OrderDTO, String> colCustomerId;
    public TableColumn<OrderDTO, String> colTime;
    public TableColumn<OrderDTO, String> colCost;
    public TableView<OrderDTO> tblOrder;

    Pattern idPattern = Pattern.compile("^(O)(-)[0-9]{4}$");
    Pattern datePattern = Pattern.compile("^[0-9]{4}([-][0-9]{2}){2}$");
    Pattern customerIdPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern timePattern = Pattern.compile("^[0-9]{2}([:][0-9]{2}){2}[ ](AM|PM)$");
    Pattern costPattern = Pattern.compile("^[0-9]*[.][0-9]*$");


    ObservableList<OrderDTO> obList = FXCollections.observableArrayList();
    LinkedHashMap<TextField, Pattern> validationList = new LinkedHashMap<>();

    private final ManageOrderBO manageOrderBO = (ManageOrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.MANAGEORDER);


    public void initialize() {
        showValidation();
        viewAllOrders();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("orderCost"));

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    setOrderData(newValue);
                }
        );
        listenFieldChange(validationList);

    }

    private void viewAllOrders() {
        obList.clear();
        ArrayList<OrderDTO> orders=manageOrderBO.getAllOrder();
        obList.addAll(orders);
        tblOrder.setItems(obList);
    }

    private void showValidation() {
        validationList.put(txtOrderId,idPattern);
        validationList.put(txtCost,costPattern);
        validationList.put(txtCustomerId,customerIdPattern);
        validationList.put(txtDate,datePattern);
        validationList.put(txtTime,timePattern);
    }


    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField field: list.keySet()) {
            field.textProperty().addListener((observable, oldValue, newValue) ->{
                ValidationUtil.validate(field,list.get(field));
            });
        }
    }

    private void setOrderData(OrderDTO i) {
        if(i!=null) {
            txtOrderId.setText(String.valueOf(i.getOrderId()));
            txtDate.setText(String.valueOf(i.getOrderDate()));
            txtCost.setText(String.valueOf(i.getOrderCost()));
            txtCustomerId.setText(i.getCustomerId());
            txtTime.setText(String.valueOf(i.getOrderTime()));
        }
    }

    public void searchIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       /* String orderId = txtOrderId.getText();
        Order o= new OrderController().getOrder(orderId);
        if (o==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(o);
        }
        */
    }

    private void setData(OrderDTO o) {
        txtOrderId.setText(o.getOrderId());
        txtDate.setText(o.getOrderDate());
        txtCustomerId.setText(o.getCustomerId());
        txtTime.setText(o.getOrderTime());
        txtCost.setText(String.valueOf(o.getOrderCost()));
    }

    public void deleteOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (ValidationUtil.isAllValidated(validationList)){
            if (manageOrderBO.deleteOrder(txtOrderId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                viewAllOrders();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    private void clearFields() {
        txtOrderId.clear();
        txtDate.clear();
        txtCustomerId.clear();
        txtTime.clear();
        txtCost.clear();
    }

    public void updateOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (ValidationUtil.isAllValidated(validationList)){
            OrderDTO order1 = new OrderDTO(txtOrderId.getText(), txtDate.getText(), txtCustomerId.getText(), txtTime.getText(), Double.parseDouble(txtCost.getText()));
            if (manageOrderBO.updateOrder(order1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                viewAllOrders();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }

    }
}
