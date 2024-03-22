package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RunnerController {



    @FXML
    private Label ciao = new Label();

    @FXML
    private Button play = new Button();

    @FXML
    private TextField plaOne;
    @FXML
    private TextField plaTwo;

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


    public void initGame(ActionEvent event) throws IOException {

        String player1 = getPlaOne().getText();
        String player2 = getPlaTwo().getText();




    }



}
