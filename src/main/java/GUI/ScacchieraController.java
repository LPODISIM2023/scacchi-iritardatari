package GUI;

import Engine.Data.Partita;
import Engine.Giocatore.Bot;
import Engine.Giocatore.Giocatore;
import Engine.Giocatore.Umano;
import Engine.Servizi.ScacchieraService;
import Pezzi.Cavallo;
import Pezzi.Pezzo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static Engine.Data.Partita.save;

public class ScacchieraController {

    static Giocatore g1;
    static Giocatore g2;

    ScacchieraService sc;

    public File percorsoSalvatggi = new File("src/main/java/salvataggi") ;

    @FXML
    FileChooser filePartita = new FileChooser();
    @FXML
    public Label labelNomePlayer1;

    @FXML
    public MenuItem caricaPartita;
    @FXML
    public Label labelNomePlayer2;


    @FXML
    private GridPane gridPaneX = new GridPane();

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    public static ArrayList<CasellaScacchiera> getCaselleScacchiera() {
        return caselle;
    }

    public static void setCaselleScacchiera(ArrayList<CasellaScacchiera> caselle) {
        ScacchieraController.caselle = caselle;
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

    //metodi per i pulsanti nel menù


    /**
     * Metodo che salva una partita dopo aver cliccato sul bottone del menu "Salva"
     * @param event listener per l'evento del bottone Salva
     */
    @FXML
    public void saveButton(ActionEvent event){

        System.out.println("Save!");
        save(sc,g2,g1);
    }

    /**
     * Metodo che permette all'utente di scegliere se uscire e salvare, uscire o annullare l'operazione,
     * gestione queste scelte tramite  un "incapsulamente" di Alert e Dialog pane, ovvero classi anonime che vengono
     * create all'interno del metodo visto che l'azione performata verrà eseguita poche volte.
     * Si preferiscono le classi anonime per scopi di questo tipo, dove l'azione viene eseguita un numero minore di volte.
     * @param event listener del pulsante Salva e esci
     * @throws InterruptedException
     */
    @FXML
    public void saveAndExit(ActionEvent event) throws InterruptedException {  //@TODO implementare eccezione per i file da sovrascrivere.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Non hai salvato, vuoi salvare?");
        alert.setTitle("Dialog event!");

        ButtonType ok = new ButtonType("Salva");
        ButtonType esciSenzaSalvare = new ButtonType("Esci senza salvare");
        ButtonType annulla = new ButtonType("Annulla");

        alert.getButtonTypes().setAll(ok,esciSenzaSalvare,annulla);

        alert.showAndWait().ifPresent(scelta -> {
            if (scelta == ok) {
                save(sc, g1, g2);
                System.exit(0);
            } else if (scelta == esciSenzaSalvare) {
                Dialog<ButtonType> uscita = new Dialog<>();
                ButtonType chiudi = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                uscita.getDialogPane().getButtonTypes().addAll(chiudi);
                uscita.setContentText("Sei uscito senza salvare");
                uscita.setOnCloseRequest(new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent dialogEvent) {
                        System.exit(0);
                    }
                });
                uscita.showAndWait().ifPresent(risposta -> {
                    if(risposta == chiudi){
                        System.exit(0);
                    }
                });
            }else if(scelta == annulla){
                return;
            }
        });

}


    /**
     * Metodo che killa l'applicazione e termina l'esecuzione (nei pulsanti del menù)
     *
     * @param actionEvent listener del pulsante "exit without save" del menù bar
     */
    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Metodo che permettere di scegliere la partita da giocare dall'explorer di sistema.
     *
     * @param event listener per il click del pulsante nel menù a tendina "scegli partita"
     */
    @FXML
    public void carica(ActionEvent event) throws IOException {

        filePartita.setTitle("Salvataggi");
        filePartita.setInitialDirectory(new File(System.getProperty("user.dir")));
        filePartita.getExtensionFilters().clear();
//        filePartita.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json files", "*.json"));
        File file = filePartita.showOpenDialog(new Stage());

        System.out.println(file);

//        if(percorsoSalvatggi.exists()){
//
//
//        }else{
//            Path path = Paths.get(percorsoSalvatggi.toURI());
//            Files.createDirectories(path);
//        }


//        String userSavePath = System.getProperty("user.dir/salvataggi");
//        if (userSavePath != null) {
//            System.out.println("Percorso dei salvataggi: " + userSavePath);
//        } else {
//            System.out.println("La chiave 'user.home' non è stata impostata.");
//        }



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
                casella.setColoreCasella((riga + colonna) % 2 == 0); //(riga + colonna) % 2 == 0 ritorna (vero o falso)

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
                casella.setPezzo(pezzo);
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
