package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CashierViewFormController {
    public AnchorPane mainContext;
    public AnchorPane detailContext;

    public void initialize() {
        URL resource = getClass().getResource("../view/CashierDashboardForm.fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);

    }

    public void customerOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);
    }

    public void manageOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainContext.getChildren().clear();
        mainContext.getChildren().add(load);
    }

    public void manageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);
    }
}
