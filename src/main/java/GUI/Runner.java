package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Runner extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/start.fxml"))));
            Scene scene = new Scene(root);
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("/logoAppBg.png")).toExternalForm());
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
