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

public class StartController {

    @FXML
    private Label ciao = new Label();

    @FXML
    private Button play = new Button();

    @FXML
    private TextField plaOne;
    @FXML
    private TextField plaTwo;

    @FXML
    private CheckBox disabilita;

    @FXML
    Parent root;

    @FXML
    Stage stage;

    @FXML
    Scene scene;

    public Label getCiao() {
        return ciao;
    }

    public void setCiao(Label ciao) {
        this.ciao = ciao;
    }

    public Button getPlay() {
        return play;
    }

    public void setPlay(Button play) {
        this.play = play;
    }

    public TextField getPlaOne() {
        return plaOne;
    }

    public void setPlaOne(TextField plaOne) {
        this.plaOne = plaOne;
    }

    public TextField getPlaTwo() {
        return plaTwo;
    }

    public void setPlaTwo(TextField plaTwo) {
        this.plaTwo = plaTwo;
    }


    public void vediScacchiera(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scacchiera.fxml")));
        root = loader.load();

        ScacchieraController scacchieraController = loader.getController();
        scacchieraController.initGame(plaOne, plaTwo, disabilita);

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/chessboard.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
