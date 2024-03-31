package GUI;

import Engine.Giocatore.Bot;
import Engine.Giocatore.Giocatore;
import Engine.Giocatore.Umano;
import Engine.Servizi.ScacchieraService;
import Pezzi.Cavallo;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
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
    private GridPane gridPaneX = new GridPane();


    @FXML
    private Button testMossaBtn;

    private static boolean isSelectCasella = false;
    private static boolean colorePezzoSelezionato = true; //false=nero true=bianco
    private static Pezzo pezzoSelezionato;

    private static ScacchieraService scacchieraService;

    public static ArrayList<CasellaScacchiera> caselle = new ArrayList<>();

    public static boolean getIsSelectCasella() {
        return isSelectCasella;
    }

    public static void setIsSelectCasella(boolean x) {
        isSelectCasella = x;
    }

    public static Pezzo getPezzoSelezionato() {
        return pezzoSelezionato;
    }

    public static void setPezzoSelezionato(Pezzo pezzoSelezionato) {
        ScacchieraController.pezzoSelezionato = pezzoSelezionato;
    }

    public static boolean getColorePezzoSelezionato() {
        return colorePezzoSelezionato;
    }

    public static void setColorePezzoSelezionato(boolean colorePezzoSelezionato) {
        ScacchieraController.colorePezzoSelezionato = colorePezzoSelezionato;
    }

    /**
     * Metodo mappato con il pulsante "UNDO" sulla scacchiera.
     * Il metodo ritorna allo stato mossa precendente a quello appena giocato.
     *
     * @param event listener del pulsante.
     */
    @FXML
    public void undo(ActionEvent event) {
        //@TODO: implements
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

    /**
     * Setta le label in chessboard con i nomi dei giocatori, in più verifica lo stato del checkBox (true o false)
     * a seconda della scelta di giocare contro il bot o no.
     * Inizializza la scacchiera service passandogli i 2 giocatori
     * Esegue il render iniziale della scacchiera a schermo
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
        scacchieraService = new ScacchieraService(g1.getPezziGiocatore(), g2.getPezziGiocatore());

        //Render Scacchiera
        renderScacchiera();

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

    /**
     * Metodo che renderizza la scacchiera a schermo, prende scacchiera service e sulla base
     * della scacchiera logica, crea la scacchiera con i vari StackPane (CasellaScacchiera)
     */
    public void renderScacchiera() {

        //Pulisco la scacchiera precedente in caso di aggiornamento
        gridPaneX.getChildren().clear();
        caselle.clear();

        //Itero per ogni casella
        for (int riga = 8; riga >= 1; riga--) {
            for (int colonna = 1; colonna <= 8; colonna++) {
                CasellaScacchiera casella = new CasellaScacchiera(riga, colonna, this);

                //Dimensioni casella
                casella.setPrefHeight(60);
                casella.setPrefWidth(60);

                //Aggiunta alla griglia principale
                gridPaneX.add(casella, colonna, 8 - riga, 1, 1);

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

    /**
     * Metodo che Itera su tutte le caselle e sulla base della scacchiera service aggiunge i pezzi nella casella
     */
    private static void aggiungiPezzi() {
        for (CasellaScacchiera casella : caselle) {
            //Recupero il pezzo dalla scacchiare logica
            Pezzo pezzo = scacchieraService.getPezzo(casella.getRiga(), casella.getColonna());
            //Se la casella é piena renderizzo il pezzo nella rispettiva casella di render
            if (pezzo != null) {
                casella.getChildren().add(pezzo);
            }
        }
    }

    /**
     * Metodo usato per renderizzare nella scacchiera le possibili posizioni disponibili per muovere il pezzo selezionato
     * @param caselleDisponibili
     * @param pezzoCliccato
     */
    public static void selezionaPosizioniDisponibili(ArrayList<CasellaScacchiera> caselleDisponibili, Pezzo pezzoCliccato) {
        for (CasellaScacchiera casella : caselle) {
            //Coloro la casella selezionata
            if (casella.getColonna() == pezzoCliccato.getColonna() && casella.getRiga() == pezzoCliccato.getRiga()) {
                ((CasellaScacchiera) pezzoCliccato.getParent()).selezionaCasellaColore();
            }
            //Aggiungo img di selezione alle caselle delle posizioni disponibili
            for (CasellaScacchiera casellaDisponibile : caselleDisponibili) {
                if (casella.getRiga() == casellaDisponibile.getRiga() && casella.getColonna() == casellaDisponibile.getColonna()) {

                    ImageView selectImage = new ImageView("/evidenzaCasella.png");
                    selectImage.setId("selettoreCasella");
                    selectImage.setFitWidth(60);
                    selectImage.setFitHeight(60);

                    //Controllo se la casella é vuota
                    if (!casella.getChildren().isEmpty()) {
                        //La casella é piena
                        //img selettore di supporto per poter visualizzarlo sopra il pezzo
                        ImageView selectImage2 = new ImageView("/evidenzaCasella.png");
                        selectImage2.setId("selettoreCasella");
                        selectImage2.setFitWidth(60);
                        selectImage2.setFitHeight(60);

                        Pezzo pezzoVecchio = (Pezzo) casella.getChildren().get(0);
                        casella.getChildren().clear();
                        casella.getChildren().addAll(selectImage, pezzoVecchio, selectImage2);
                    } else {
                        //La casella é vuota
                        casella.getChildren().add(selectImage);
                    }
                }
            }
        }
    }

    /**
     * Metodo invocato quando si ha bisogno di rimuovere la selezione di un pezzo
     */
    public void toglieSelezione() {
        renderScacchiera();
        setIsSelectCasella(false);
    }

    //TEST
    int posizioneRiga = 2;
    public void testMossa(ActionEvent actionEvent) {
        scacchieraService.aggiornaPosizionePezzo(scacchieraService.getPezzo(posizioneRiga, 5), posizioneRiga + 1, 5);
        posizioneRiga = posizioneRiga + 1;
        renderScacchiera();
        scacchieraService.printScacchiera();
    }

    public void testMossa2(Pezzo pezzo, CasellaScacchiera casella) {
        scacchieraService.aggiornaPosizionePezzo(pezzo, casella.getRiga(), casella.getColonna());
        renderScacchiera();
        scacchieraService.printScacchiera();
    }

}
