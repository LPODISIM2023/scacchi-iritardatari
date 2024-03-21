package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Runner extends Application {

    @FXML
    public Label ciao = new Label();

    @FXML
    public Button play = new Button();

    @Override
    public void start(Stage stage) {
        try {
            //System.out.println(Objects.requireNonNull(getClass().getResource("/start.fxml")));
            //  System.out.println(getClass().getResource("logoApp.png"));
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/start.fxml"))));
            Scene scene = new Scene(root);
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("/logoApp.png")).toExternalForm());
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
