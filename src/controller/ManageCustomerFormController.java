package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageCustomerFormController {
    public TextField txtCity;
    public TextField txtTitle;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtProvince;
    public TextField txtCustomerId;
    public TextField txtPostalCode;
    public TextField txtCity2;
    public TextField txtTitle2;
    public TextField txtName2;
    public TextField txtAddress2;
    public TextField txtProvince2;
    public TextField txtCustomerId2;
    public TextField txtPostalCode2;

    public TableColumn<CustomerDTO,String> colId;
    public TableColumn<CustomerDTO,String> colTitle;
    public TableColumn<CustomerDTO,String> colName;
    public TableColumn<CustomerDTO,String> colAddress;
    public TableColumn<CustomerDTO,String> colCity;
    public TableColumn<CustomerDTO,String> colProvince;
    public TableColumn<CustomerDTO,String> colPostalCode;
    public TableView<CustomerDTO> tblCustomer;


    Pattern idPattern = Pattern.compile("^(C)[0-9]{3}$");
    Pattern titlePattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern namePattern = Pattern.compile("^[A-Z][a-z]*([ ][A-Z][a-z]*)*$");
    Pattern addressPattern = Pattern.compile("^[A-Z0-9][0-9A-z,-/ ]*$");
    Pattern cityPattern = Pattern.compile("^[A-Z0-9][0-9A-z,-/ ]*$");
    Pattern provincePattern = Pattern.compile("^[A-Z0-9][0-9A-z,-/ ]*$");
    Pattern postalCodePattern = Pattern.compile("^[A-Z0-9][0-9A-z,-/ ]*$");

    private final CustomerBO customerBO=(CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    ObservableList<CustomerDTO> obList= FXCollections.observableArrayList();
    LinkedHashMap<TextField,Pattern> validationList = new LinkedHashMap<>();
    
    
    public void initialize(){
        showValidation();
        viewAllCustomers();

        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
                    setCustomerData(newValue);
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

    private void setCustomerData(CustomerDTO c) {
        if(c!=null) {
            txtCustomerId.setText(c.getCustomerId());
            txtCustomerId2.setText(c.getCustomerId());
            txtProvince2.setText(c.getProvince());
            txtProvince.setText(c.getProvince());
            txtPostalCode2.setText(c.getPostalCode());
            txtPostalCode.setText(c.getPostalCode());
            txtCity2.setText(c.getCity());
            txtCity.setText(c.getCity());
            txtTitle2.setText(c.getTitle());
            txtTitle.setText(c.getTitle());
            txtName2.setText(c.getName());
            txtName.setText(c.getName());
            txtAddress2.setText(c.getAddress());
            txtAddress.setText(c.getAddress());
        }
    }

    private void viewAllCustomers() {
        obList.clear();
        ArrayList<CustomerDTO> customers=customerBO.getAllCustomers();
        obList.addAll(customers);
        tblCustomer.setItems(obList);
    }

    private void showValidation() {
        validationList.put(txtCustomerId,idPattern);
        validationList.put(txtAddress,addressPattern);
        validationList.put(txtCustomerId2,idPattern);
        validationList.put(txtAddress2,addressPattern);
        validationList.put(txtCity2,cityPattern);
        validationList.put(txtCity,cityPattern);
        validationList.put(txtName2,namePattern);
        validationList.put(txtName,namePattern);
        validationList.put(txtPostalCode2,postalCodePattern);
        validationList.put(txtPostalCode,postalCodePattern);
        validationList.put(txtProvince2,provincePattern);
        validationList.put(txtProvince,provincePattern);
        validationList.put(txtTitle2,titlePattern);
        validationList.put(txtTitle,titlePattern);

    }


    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        if (ValidationUtil.isAllValidated(validationList)){
            CustomerDTO c1=new CustomerDTO(
                    txtCustomerId.getText(),txtTitle.getText(), txtName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()
            );
            if(customerBO.addCustomer(c1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                viewAllCustomers();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
//        }else{
//         new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
//       }
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtCustomerId2.clear();
        txtProvince2.clear();
        txtProvince.clear();
        txtPostalCode2.clear();
        txtPostalCode.clear();
        txtCity2.clear();
        txtCity.clear();
        txtTitle2.clear();
        txtTitle.clear();
        txtName2.clear();
        txtName.clear();
        txtAddress2.clear();
        txtAddress.clear();
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       if (ValidationUtil.isAllValidated(validationList)){
            CustomerDTO c1 = new CustomerDTO(
                    txtCustomerId2.getText(),txtTitle2.getText(), txtName2.getText(),txtAddress2.getText(),txtCity2.getText(),txtProvince2.getText(),txtPostalCode2.getText()
            );

            if(customerBO.updateCustomer(c1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                viewAllCustomers();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }else{
           new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (ValidationUtil.isAllValidated(validationList)){
            String customerId=txtCustomerId2.getText();
            if(customerBO.deleteCustomer(customerId)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                viewAllCustomers();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }else{
         new Alert(Alert.AlertType.WARNING, "Fields are not filled properly..").show();
        }
    }

   public void searchCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       /* String custId = txtCustomerId2.getText();
        CustomerDTO c1= customerBO().(custId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }*/
    }

    private void setData(CustomerDTO c1) {
        txtCustomerId2.setText(c1.getCustomerId());
        txtName2.setText(c1.getName());
        txtAddress2.setText(c1.getAddress());
        txtTitle2.setText(c1.getTitle());
        txtCity2.setText(c1.getCity());
        txtPostalCode2.setText(c1.getPostalCode());
        txtProvince2.setText(c1.getProvince());
    }
}
