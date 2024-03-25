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

    /**
     * Setta le label in chessboard con i nomi dei giocatori, in più verifica lo stato del checkBox (true o false)
     * a seconda della scelta di giocare contro il bot o no.
     * @param playerOne nome giocatore 1
     * @param playerTwo nome giocatore 2
     * @param checkBox  checkbox per verificare la presenza del bot o meno
     * @throws IOException
     */
    @FXML
    public void initGame(String playerOne, String playerTwo, CheckBox checkBox) throws IOException {


        labelNomePlayer1.setText("Player 1: " + playerOne);
        if (checkBox.isSelected()) {
            labelNomePlayer2.setText("Player 2: BOT");
        } else {
            labelNomePlayer2.setText("Player 2: " + playerTwo);
        }
    }

    /**
     * Ritorna alla schermata di start del gioco
     * @param event listener per la pressione del bottone PLAY
     * @throws IOException
     */
    @FXML
    public void getStart(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/start.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metodo che killa l'applicazione e termina l'esecuzione (nei pulsanti del menù)
     * @param actionEvent listener del pulsante "exit without save" del menù bar
     */
    public void exit(ActionEvent actionEvent) {

        System.exit(0);


    }

    /**
     * Metodo che permettere di scegliere la partita da giocare dall'explorer di sistema.
     * @param actionEvent listener per il click del pulsante nel menù a tendina "scegli partita"
     */
    public void fileChooser(ActionEvent actionEvent) {

        //@TODO: implements

    }
}
