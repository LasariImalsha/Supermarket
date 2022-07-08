package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import dto.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//import model.Item;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageItemFormController {
    public TextField txtQty;
    public TextField txtItemCode2;
    public TextField txtQty2;
    public TextField txtDescription;
    public TextField txtSize;
    public TextField txtUnitPrice;
    public TextField txtDiscount;
    public TextField txtItemId;
    public TextField txtDescription2;
    public TextField txtSize2;
    public TextField txtUnitPrice2;
    public TextField txtDiscount2;

    public TableColumn<ItemDTO,String> colItemCode;
    public TableColumn<ItemDTO,String> colDescription;
    public TableColumn<ItemDTO,String> colSize;
    public TableColumn<ItemDTO,String> colUnitPrice;
    public TableColumn<ItemDTO,String> colQtyOnHand;
    public TableColumn<ItemDTO,String> colDiscount;
    public TableView<ItemDTO> tblItem;

    Pattern idPattern = Pattern.compile("^(I)[0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-Z0-9][0-9A-z,-/ ]*$");
    Pattern sizePattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]*[.][0-9]*$");
    Pattern qtyOnHandPattern=Pattern.compile("^[0-9]+$");
    Pattern discountPattern = Pattern.compile("^[0-9]*[.][0-9]*$");

    ObservableList<ItemDTO> obList= FXCollections.observableArrayList();
    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();

    private final ItemBO itemBO=(ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);


    public void initialize(){
        showValidation();
        viewAllItems();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
                    setItemData(newValue);
                }
        );
        listenFieldChange(validationList);

    }

    private void listenFieldChange(LinkedHashMap<TextField, Pattern> list) {
        for(TextField field: list.keySet()) {
            field.textProperty().addListener((observable, oldValue, newValue) ->{
                ValidationUtil.validate(field,list.get(field));
            });
        }
    }

    private void setItemData(ItemDTO i) {
        if(i!=null) {
            txtQty2.setText(String.valueOf(i.getQtyOnHand()));
            txtQty.setText(String.valueOf(i.getQtyOnHand()));
            txtSize2.setText(i.getPackSize());
            txtSize.setText(i.getPackSize());
            txtDiscount.setText(String.valueOf(i.getDiscountPercentage()));
            txtItemId.setText(i.getItemCode());
            txtItemCode2.setText(i.getItemCode());
            txtDiscount2.setText(String.valueOf(i.getDiscountPercentage()));
            txtDescription.setText(i.getDescription());
            txtDescription2.setText(i.getDescription());
            txtUnitPrice2.setText(String.valueOf(i.getUnitPrice()));
            txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
        }
    }

    private void viewAllItems() {
        obList.clear();
        ArrayList<ItemDTO> items=itemBO.getAllItems();
        obList.addAll(items);
        tblItem.setItems(obList);
    }

    private void showValidation() {
        validationList.put(txtItemCode2,idPattern);
        validationList.put(txtDescription,descriptionPattern);
        validationList.put(txtDiscount2,discountPattern);
        validationList.put(txtDiscount,discountPattern);
        validationList.put(txtQty2,qtyOnHandPattern);
        validationList.put(txtQty,qtyOnHandPattern);
        validationList.put(txtSize2,sizePattern);
        validationList.put(txtSize,sizePattern);
        validationList.put(txtUnitPrice2,unitPricePattern);
        validationList.put(txtUnitPrice,unitPricePattern);
        validationList.put(txtDescription2,descriptionPattern);
        validationList.put(txtItemId,idPattern);
    }


    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        if (ValidationUtil.isAllValidated(validationList)){
            ItemDTO item1 = new ItemDTO(txtItemId.getText(),txtDescription.getText(),txtSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQty.getText()),Double.parseDouble(txtDiscount.getText()));
            if(itemBO.addItem(item1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                viewAllItems();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
//        }else{
//            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
//        }

    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
          if (ValidationUtil.isAllValidated(validationList)){
            ItemDTO item1 = new ItemDTO(txtItemCode2.getText(),txtDescription2.getText(),txtSize2.getText(),Double.parseDouble(txtUnitPrice2.getText()),Integer.parseInt(txtQty2.getText()),Double.parseDouble(txtDiscount2.getText()));
            if (itemBO.updateItem(item1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                viewAllItems();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (ValidationUtil.isAllValidated(validationList)){
            if (itemBO.deleteItem(txtItemCode2.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                viewAllItems();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
             new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    private void clearFields() {
        txtQty2.clear();
        txtQty.clear();
        txtSize2.clear();
        txtSize.clear();
        txtDiscount.clear();
        txtItemId.clear();
        txtItemCode2.clear();
        txtDiscount2.clear();
        txtDescription.clear();
        txtDescription2.clear();
        txtUnitPrice2.clear();
        txtUnitPrice.clear();
    }

    public void searchIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
     /*   String ItemId = txtItemCode2.getText();
        ItemDTO item1= itemBO().getItem(ItemId);
        if (item1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(item1);
        }
      */
    }

   /* private void setData(ItemDTO item1) {
        txtItemCode2.setText(item1.getItemCode());
        txtDescription2.setText(item1.getDescription());
        txtSize2.setText(item1.getPackSize());
        txtUnitPrice2.setText(String.valueOf(item1.getUnitPrice()));
        txtQty2.setText(String.valueOf(item1.getQtyOnHand()));
        txtDiscount2.setText(String.valueOf(item1.getDiscountPercentage()));
    }

    */
}
