package util;

import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static void validate(TextField textField, Pattern pattern){
        if(pattern.matcher(textField.getText()).matches()){
            showValidation(1,textField);
        }else{
            showValidation((textField.getText().isEmpty()?0:-1),textField);
        }

    }

    private static void showValidation(int index, TextField textField) {
        switch (index){
            case 1 :
                textField.getParent().setStyle("-fx-border-color : #028f02");
                break;
            case 0 :
                textField.getParent().setStyle("-fx-border-color : rgba(76,73,73,0.83)");
                break;
            case -1 :
                textField.getParent().setStyle("-fx-border-color : #db0f0f");
                break;
        }
    }

    public static boolean isAllValidated(LinkedHashMap<TextField, Pattern> list) {
        for(TextField field: list.keySet()) {
            if(!field.getParent().getStyle().equals("-fx-border-color : #028f02")){
                return false;
            }
        }
        return true;
    }
}
