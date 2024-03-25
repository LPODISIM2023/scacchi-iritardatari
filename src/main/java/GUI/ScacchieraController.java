package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ScacchieraController {


    @FXML
    public Label labelNomePlayer1;

    @FXML
    public Label labelNomePlayer2;

    @FXML
    public Button comeBack;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initGame(String playerOne, String playerTwo, CheckBox checkBox) throws IOException {


        labelNomePlayer1.setText("Player 1: " + playerOne);
        if (checkBox.isSelected()) {
            labelNomePlayer2.setText("Player 2: BOT");
        } else {
            labelNomePlayer2.setText("Player 2: " + playerTwo);
        }
    }

    @FXML
    public void getStart(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/start.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void exit(ActionEvent actionEvent) {



    }

    public void fileChooser(ActionEvent actionEvent) {
    }
}
