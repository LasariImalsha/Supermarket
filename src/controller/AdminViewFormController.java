package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AdminViewFormController {
    public AnchorPane detailContext;
    public AnchorPane mainContext;

    public void initialize() {
        URL resource = getClass().getResource("../view/AdminDashboardForm.fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);

    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);
    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        detailContext.getChildren().clear();
        detailContext.getChildren().add(load);
    }

    public void manageItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemForm.fxml");
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
}
