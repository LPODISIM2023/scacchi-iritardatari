package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class FileNulloException extends Exception{

    public FileNulloException(String errMess){
        super(errMess);
        Alert filenullo = new Alert(Alert.AlertType.NONE, "Attenzione NON hai salvato. Il file è vuoto.");
        filenullo.setTitle("File nullo");

        ButtonType ok = new ButtonType("OK");
        filenullo.getButtonTypes().add(ok);

        filenullo.showAndWait().ifPresent(scelta ->{
            if(scelta == ok){
                return;
            }
        });
    }
}
