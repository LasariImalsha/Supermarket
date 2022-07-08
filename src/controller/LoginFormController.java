package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {
    public JFXTextField txtUserName;
    public AnchorPane mainContext;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("admin")) {
            URL resource = getClass().getResource("../view/AdminViewForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) mainContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }else if (txtUserName.getText().equals("cashier")){
            URL resource = getClass().getResource("../view/CashierViewForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) mainContext.getScene().getWindow();
            window.setScene(new Scene(load));

        }else {
            URL resource = getClass().getResource("../view/LoginForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) mainContext.getScene().getWindow();
            window.setScene(new Scene(load));

        }
    }
}
