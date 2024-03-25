package GUI;

import Eccezioni.EmptyTextField;
import Engine.Scacchiera;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.plaf.ColorUIResource;
import java.io.IOException;
import java.util.Objects;

public class StartController {


    @FXML
    private Button play = new Button();

    @FXML
    public TextField nomePlayer1;

    public Button getPlay() {
        return play;
    }

    public TextField getNomePlayer1() {
        return nomePlayer1;
    }

    public TextField getNomePlayer2() {
        return nomePlayer2;
    }

    public CheckBox getDisabilita() {
        return disabilita;
    }

    public Parent getRoot() {
        return root;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Label getEccezione() {
        return eccezione;
    }

    @FXML
    public TextField nomePlayer2;

    @FXML
    private CheckBox disabilita;

    @FXML
    private Parent root;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;
    
    @FXML
    public Label eccezione;



    @FXML
    public void switchChessboard(ActionEvent event) throws IOException, EmptyTextField {

        if (nomePlayer1.getText().trim().isEmpty() && nomePlayer2.getText().trim().isEmpty()) {
            eccezione.setFont(new Font("Arial", 24));
            eccezione.setTextFill(Color.RED);
            eccezione.setText("Non hai inserito i nomi, inseriscili.");
            throw new EmptyTextField("Non hai inserito i nomi");


        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cheesboard.fxml"));
            root = loader.load();
            ScacchieraController sc = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            String nome1 = nomePlayer1.getText();
            String nome2 = nomePlayer2.getText();

            sc.initGame(nome1, nome2, disabilita);
        }
    }


    public void disablePlayer2(ActionEvent event){

       nomePlayer2.setDisable(((CheckBox) (event.getSource())).isSelected());



    }

}
