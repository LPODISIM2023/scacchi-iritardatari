package GUI;

import Engine.Giocatore.Bot;
import Engine.Giocatore.Giocatore;
import Engine.Giocatore.Umano;
import Engine.Servizi.ScacchieraService;
import Pezzi.Pezzo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ScacchieraController {

    static Giocatore g1;
    static Giocatore g2;
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
    private GridPane gridPaneX;


    @FXML
    private Button testMossaBtn;

    private static ScacchieraService scacchieraLog;

    public static ArrayList<CasellaScacchiera> caselle = new ArrayList<>();

    /**
     * Setta le label in chessboard con i nomi dei giocatori, in più verifica lo stato del checkBox (true o false)
     * a seconda della scelta di giocare contro il bot o no.
     *
     * @param nome1 nome giocatore 1
     * @param nome2 nome giocatore 2
     * @param isBot boolean per verificare la presenza del bot o meno
     * @throws IOException
     */
    @FXML
    public void initGame(String nome1, String nome2, boolean isBot) throws IOException {

        //Inizializzazione Giocatori
        g1 = new Umano(nome1, true);
        if (isBot) {
            g2 = new Bot();
        } else {
            g2 = new Umano(nome2, false);
        }

        //Creazione Scacchiera Service
        ScacchieraService scacchiera = new ScacchieraService(g1.getPezziGiocatore(), g2.getPezziGiocatore());

        //Render Scacchiera
        renderScacchiera(gridPaneX, scacchiera);

        //Render Nomi
        labelNomePlayer1.setText("Player 1: " + g1.getNome());
        labelNomePlayer2.setText("Player 2: " + g2.getNome());

    }

    /**
     * Ritorna alla schermata di start del gioco
     *
     * @param event listener per la pressione del bottone PLAY
     * @throws IOException
     */
    @FXML
    public void getStart(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/start.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private static void renderScacchiera(GridPane chessBoard, ScacchieraService scacchiera) {
        scacchieraLog = scacchiera;
        // scacchieraLog.printScacchiera();
        for (int riga = 8; riga >= 1; riga--) {
            for (int colonna = 1; colonna <= 8; colonna++) {
                CasellaScacchiera casella = new CasellaScacchiera(riga, colonna);

                //Dimensioni casella
                casella.setPrefHeight(60);
                casella.setPrefWidth(60);

                //Aggiunta alla griglia principale
                chessBoard.add(casella, colonna, 8 - riga, 1, 1);

                System.out.println("Riga:" + riga + " Colonna:" + colonna);

                //Cambio Colore Casella
                if ((riga + colonna) % 2 == 0) {
                    casella.setBackground(new Background(new BackgroundFill(Color.web("#b1e4b9"), CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    casella.setBackground(new Background(new BackgroundFill(Color.web("#70a2a3"), CornerRadii.EMPTY, Insets.EMPTY)));
                }

                //Aggiunto al array di tutte le caselle
                caselle.add(casella);
            }
        }
        //Aggiungo i pezzi
        aggiungiPezzi();
    }

    private static void aggiungiPezzi() {
        for (CasellaScacchiera casella : caselle) {
            //Recupero il pezzo dalla scacchiare logica
            Pezzo pezzo = scacchieraLog.getPezzo(casella.getRiga(), casella.getColonna());
            //Se la casella é piena renderizzo il pezzo nella rispettiva casella di render
            if (pezzo != null) {
                casella.getChildren().add(pezzo);
            }
        }
    }


    /**
     * Metodo che killa l'applicazione e termina l'esecuzione (nei pulsanti del menù)
     *
     * @param actionEvent listener del pulsante "exit without save" del menù bar
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Metodo che permettere di scegliere la partita da giocare dall'explorer di sistema.
     *
     * @param actionEvent listener per il click del pulsante nel menù a tendina "scegli partita"
     */
    public void fileChooser(ActionEvent actionEvent) {

        //@TODO: implements

    }

    //TEST
    public void testMossa(ActionEvent actionEvent) {
        scacchieraLog.aggiornaPosizionePezzo(scacchieraLog.getPezzo(2,4),2,4 );
        System.out.println("Riga:asd");
        scacchieraLog.printScacchiera();
        aggiungiPezzi();
    }
}
