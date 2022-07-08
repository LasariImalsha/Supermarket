package controller;

import bo.BOFactory;
import bo.custom.SystemReportBO;
import com.jfoenix.controls.JFXComboBox;
import db.DbConnection;
import dto.SystemReportDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
//import model.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportFormController {
    public AnchorPane detailContext;
    public Label lblIncome;
    public Label lblLeast;
    public Label lblMost;
    public JFXComboBox<String> cmbCustomer;
    public Label lblAnnual;
    public JFXComboBox<String> cmbAnnualy;
    public JFXComboBox<String> cmbDaily;
    public Label lblDaily;
    public JFXComboBox<String> cmbMonthly;
    public Label lblMonthly;
    public JFXComboBox<String> cmbMoveableItem;
    public Label lblMItem;
    public Label lblCustomerWise;

    private final SystemReportBO systemReportBO = (SystemReportBO) BOFactory.getInstance().getBO(BOFactory.BOType.SYSTEMREPORT);


    public void initialize() throws SQLException, ClassNotFoundException {

        loadCustomerIds();
        loadYears();
        loadMonths();
        loadDates();

        cmbCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> CustomerIncomeDetails = new ArrayList<>();
            try {

                CustomerIncomeDetails = systemReportBO.getCustomerIncome(newValue);

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            calculateCustomerWiseIncome(CustomerIncomeDetails);

        });


        cmbAnnualy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> yearlyDetails = new ArrayList<>();
            try {

                yearlyDetails = systemReportBO.getYearlyDetails(newValue);

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            calculateAnnualIncome(yearlyDetails);


        });

        cmbMonthly.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> MonthlyDetails = new ArrayList<>();
            try {
                MonthlyDetails =systemReportBO.getMonthlyDetails(newValue);

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            calculateMonthIncome(MonthlyDetails);

        });

        cmbDaily.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> DailyDetails = new ArrayList<>();
            try {

                DailyDetails = systemReportBO.getDailyDetails(newValue);

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            calculateDailyIncome(DailyDetails);

        });


        ArrayList<String> MovaleList = new ArrayList<>();
        MovaleList.add("MostMovableItem");
        MovaleList.add("LeastMovableItem");

        for (String Movable : MovaleList) {
            cmbMoveableItem.getItems().add(Movable);
        }

        cmbMoveableItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> CustomerIncomeDetails = new ArrayList<>();

            try {
                if(newValue.equals("MostMovableItem")){
                    String a=systemReportBO.getMost(newValue);
                    lblMItem.setText(a);
                }else{
                    String a=systemReportBO.getLeast(newValue);
                    lblMItem.setText(a);
                }

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        });

    }

  /*  private String getLeast(Object newValue) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT itemCode,COUNT(itemCode) FROM OrderDetail GROUP BY itemCode ORDER BY itemCode ASC LIMIT 1");
        ResultSet rst = stm.executeQuery();

        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    private String getMost(Object itemCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT itemCode,COUNT(itemCode) FROM OrderDetail GROUP BY itemCode ORDER BY itemCode DESC LIMIT 1");
        ResultSet rst = stm.executeQuery();
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }*/


    private void calculateCustomerWiseIncome(ArrayList<SystemReportDTO> temp) {
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getOrderCost();
        }
        lblCustomerWise.setText((tPrice)+" /=");

    }

    private void calculateDailyIncome(ArrayList<SystemReportDTO> temp){
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getOrderCost();
        }
        lblDaily.setText((tPrice)+" /=");
    }

    private void calculateMonthIncome(ArrayList<SystemReportDTO> temp) {
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getOrderCost();
        }
        lblMonthly.setText((tPrice)+" /=");
    }

    private void calculateAnnualIncome(ArrayList<SystemReportDTO> temp) {
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getOrderCost();
        }
        lblAnnual.setText((tPrice)+" /=");

    }


    private void loadDates() throws SQLException, ClassNotFoundException {
        List<String> day = systemReportBO.getDate();
        cmbDaily.getItems().addAll(day);
    }

    private void loadMonths() throws SQLException, ClassNotFoundException {
        List<String> months = systemReportBO.getMonth();
        cmbMonthly.getItems().addAll(months);
    }

    private void loadYears() throws SQLException, ClassNotFoundException {
        List<String> years = systemReportBO.getYears();
        cmbAnnualy.getItems().addAll(years);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = systemReportBO.getCustomerIds();
        cmbCustomer.getItems().addAll(customerIds);
    }

    public void customerReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/Customer.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void itemReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/Item.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void placeOrderReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/PlaceOrder.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void orderReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/Order.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
