package GUI;

import Engine.Data.LogMossa;
import Engine.Data.Logger;
import Engine.Data.Salvataggio;
import Engine.Giocatore.Giocatore;
import Engine.Servizi.PartitaService;
import Engine.Servizi.ScacchieraService;
import Pezzi.Pezzo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.util.*;

import static Engine.Data.Partita.save;

public class ScacchieraController {


    JSONObject jsonMosse = new JSONObject();
    @FXML
    TextArea textAreaMosse = new TextArea();

    @FXML
    ScrollPane scrollPaneMosse = new ScrollPane();

    public String[] array = {"x", "A", "B", "C", "D", "E", "F", "G", "H"};

    static Giocatore g1;
    static Giocatore g2;

    ScacchieraService sc;

    public File percorsoSalvataggi = new File("src/main/java/salvataggi");

    @FXML
    FileChooser filePartita = new FileChooser();

    @FXML
    public Button buttonUndo;
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
    private static PartitaService partita;

    private static ScacchieraService scacchieraService;

    public static ArrayList<CasellaScacchiera> caselle = new ArrayList<>();

    public static PartitaService getPartita() {
        return partita;
    }

    public static void setPartita(PartitaService partita) {
        ScacchieraController.partita = partita;
    }


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
        textAreaMosse.setEditable(false);
        scrollPaneMosse.setContent(textAreaMosse);

        partita = new PartitaService(nome1, nome2, isBot, this);

        g1 = PartitaService.getGiocatore1();
        g2 = PartitaService.getGiocatore2();

        //Creazione Scacchiera Service
        scacchieraService = PartitaService.getScacchieraService();

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
     * Metodo collegato al menu button "Nuova Partita", inizializza una nuova partita chiedendo prima se si voglia salvare o meno
     *
     * @param event listener degli eventi
     * @throws IOException
     */
    @FXML
    public void newGame(ActionEvent event) throws IOException {

        Alert nuovaPartita = new Alert(Alert.AlertType.NONE, "Sei sicuro di voler iniziare una nuova partita? I dati precedenti andranno persi");
        nuovaPartita.setTitle("Vuoi salvare?");

        ButtonType si = new ButtonType("Si");
        ButtonType no = new ButtonType("No");
        nuovaPartita.getButtonTypes().add(si);
        nuovaPartita.getButtonTypes().add(no);

        nuovaPartita.showAndWait().ifPresent(scelta -> {
            if (scelta == si) {
                try {
                    save(sc, g1, g2);
                    textAreaMosse.clear();
                    log = "";
                    if (!(PartitaService.getColoreTurnoGiocatore())) {
                        PartitaService.cambioTurno();
                    }
                    initGame(g1.getNome(), g2.getNome(), true);  //migliorare terzo parametro.
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else //renderScacchiera();
                if (scelta == no) {
                    try {
                        initGame(g1.getNome(), g2.getNome(), false);  //migliorare terzo parametro.
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        });
    }


    /**
     * Metodo che salva una partita dopo aver cliccato sul bottone del menu "Salva"
     *
     * @param event listener per l'evento del bottone Salva
     */
    @FXML
    public void saveButton(ActionEvent event) throws IOException {
        filePartita.setTitle("Salvataggi");
        filePartita.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("File chess", "*.chess");
        filePartita.getExtensionFilters().addAll(jsonFilter);
        File file = filePartita.showSaveDialog(new Stage());

        if (file != null) {
            Salvataggio.salvaPartita(partita);
            System.out.println("Salvato");
        } else {
            //eccezione
        }

    }

    /**
     * Metodo che permette all'utente di scegliere se uscire e salvare, uscire o annullare l'operazione,
<<<<<<< HEAD
     * gestione queste scelte tramite un "incapsulamente" di Alert e Dialog pane, ovvero classi anonime che vengono
     * create all'interno del metodo visto che l'azione performata verrà eseguita poche volte.
=======
     * gestione queste scelte tramite un "incapsulamento" di Alert e Dialog pane, ovvero classi anonime che vengono
     * create all'interno del metodo.
>>>>>>> 93f03dc6c13f921afd18706f0127333bca151c1b
     * Si preferiscono le classi anonime per scopi di questo tipo, dove l'azione viene eseguita un numero minore di volte.
     *
     * @param event listener del pulsante Salva e esci
     * @throws InterruptedException
     */
    @FXML
    public void saveAndExit(ActionEvent event) throws InterruptedException {  //@TODO implementare eccezione per i file da sovrascrivere.
        Alert salvaEEsci = new Alert(Alert.AlertType.NONE, "Non hai salvato, vuoi salvare?");
        salvaEEsci.setTitle("Salva e esci");

        ButtonType ok = new ButtonType("Salva");
        ButtonType esciSenzaSalvare = new ButtonType("Esci senza salvare");
        ButtonType annulla = new ButtonType("Annulla");

        salvaEEsci.getButtonTypes().setAll(ok, esciSenzaSalvare, annulla);

        salvaEEsci.showAndWait().ifPresent(scelta -> {
            if (scelta == ok) {
                try {
                    Salvataggio.salvaPartita(partita);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            } else if (scelta == esciSenzaSalvare) {
                Dialog<ButtonType> uscita = new Dialog<>();
                ButtonType chiudi = new ButtonType("Chiudi", ButtonBar.ButtonData.OK_DONE);
                uscita.getDialogPane().getButtonTypes().addAll(chiudi);

                uscita.setContentText("Grazie per aver giocato");
                uscita.setOnCloseRequest(dialogEvent -> System.exit(0));
                uscita.showAndWait().ifPresent(risposta -> {
                    if (risposta == chiudi) {
                        System.exit(0);
                    }
                });
            } else if (scelta == annulla) {
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
        File file = filePartita.showOpenDialog(new Stage());


//        if(percorsoSalvatggi.exists()){
//
//
//        }else{
//            Path path = Paths.get(percorsoSalvatggi.toURI());
//            Files.createDirectories(path);
//        }


//        String userSavePath = System.getProperty("user.dir/salvataggi");
//        if (userSavePath != null) {
//            System.out.println("Percorso dei salvataggi:" + userSavePath);
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
     *
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

    String mossa;
    String log = "";

    /**
     * Metodo principale per spostare i pezzi sulla scacchiera
     *
     * @param pezzo   , il pezzo che viene spostato
     * @param casella , la casella su cui finirà
     */
    public void testMossa2(Pezzo pezzo, CasellaScacchiera casella) {

        //Vedo a quale giocatore è stato mangiato il pezzo
        if (!PartitaService.getColoreTurnoGiocatore()) {
            g1.addPezzoMangiato(casella);
        } else {
            g2.addPezzoMangiato(casella);
        }

        //Aggiorno il file del log e lo mostro a schermo
        datiLogMosse(pezzo, casella);

        //Aggiorno la posizione nella scacchiera logica
        scacchieraService.aggiornaPosizionePezzo(pezzo, casella.getRiga(), casella.getColonna());

        //Reimposto l undo delle mosse al ultima mossa eseguita
        numUndoEseguitiDiSeguito = 1;

        //Cambio turno e rerenderdella Scacchiera
        PartitaService.cambioTurno();
        renderScacchiera();

    }


    /**
     * Metodo che recupera la mossa effettuata sulla scacchiera e l'aggiunge all'array LogMosse per portarla scrive sui file.
     *
     * @param pezzo pezzo mosso
     * @param cs    casella della scacchiera
     */
    public void datiLogMosse(Pezzo pezzo, CasellaScacchiera cs) {
        if (cs.getPezzoDellaCasella() == null) {
            Logger.addMossaLog(pezzo.getRiga(), pezzo.getColonna(), cs.getRiga(), cs.getColonna(), pezzo.getCodice(), "-");
        } else {
            Pezzo temp = cs.getPezzoDellaCasella();
            Logger.addMossaLog(pezzo.getRiga(), pezzo.getColonna(), cs.getRiga(), cs.getColonna(), pezzo.getCodice(), temp.getCodice());
        }
        log = getLogMosse(pezzo);
        textAreaMosse.appendText(log);
    }


    /**
     * Questo metodo ritorna l'ultima mossa effettua per porterla poi riportare nel textArea che abbiamo messo a video.
     *
     * @param pezzo pezzo mosso
     * @return Stringa che contiene la mossa precendente e quella effettuata in questa formattazione:
     * 1° mossa (3,A) -> (4,A)
     */
    public String getLogMosse(Pezzo pezzo) {
        ArrayList<LogMossa> arrayDiMosse = Logger.getListaMosse();
        LogMossa ultimaMossa = arrayDiMosse.get(arrayDiMosse.size() - 1);

        System.out.println("ASDDSADASDA");
        return ultimaMossa.getNumeroMossa() + "° (" + ultimaMossa.getOldRiga() + "," + array[ultimaMossa.getOldColonna()] + ")" + "->" + "(" + pezzo.getRiga() + "," + array[pezzo.getColonna()] + ")" + new String(Character.toChars(pezzo.getCodicePezzoUTF8())) + "\n";
    }


    /**
     * Metodo mappato con il pulsante "UNDO" sulla scacchiera.
     * Il metodo ritorna allo stato mossa precendente a quello appena giocato.
     *
     * @param event listener del pulsante.
     */
    public static int numeroMosseUndo = 0;
    public static int numUndoEseguitiDiSeguito = 1;

    @FXML
    public void undo(ActionEvent event) {

        if (numeroMosseUndo >= 5) return;

        ArrayList<LogMossa> arrayTutteLeMosse = Logger.getListaMosse();

        if (arrayTutteLeMosse.isEmpty()) return;
        if (arrayTutteLeMosse.size() - numUndoEseguitiDiSeguito < 0) return;
        numeroMosseUndo += 1;


        LogMossa ultimaMossa = arrayTutteLeMosse.get(arrayTutteLeMosse.size() - (numUndoEseguitiDiSeguito++));
        if (ultimaMossa.getCodPezzoMangiato().equals("-")) {
            Pezzo pezzoDaSpostare = scacchieraService.getPezzoByCodice(ultimaMossa.getCodPezzoMosso());
            scacchieraService.aggiornaPosizionePezzo(pezzoDaSpostare, ultimaMossa.getOldRiga(), ultimaMossa.getOldColonna());

        } else {
            Pezzo pezzoDaSpostare = scacchieraService.getPezzoByCodice(ultimaMossa.getCodPezzoMosso());
            scacchieraService.aggiornaPosizionePezzo(pezzoDaSpostare, ultimaMossa.getOldRiga(), ultimaMossa.getOldColonna());

            Pezzo pezzoMangiatoDaRipristinare = null;
            for (Pezzo pezzoMangiatoG1 : g1.getPezziMangiati()) {
                if (pezzoMangiatoG1.getCodice().equals(ultimaMossa.getCodPezzoMangiato())) {
                    pezzoMangiatoDaRipristinare = pezzoMangiatoG1;
                }
            }
            for (Pezzo pezzoMangiatoG2 : g2.getPezziMangiati()) {
                if (pezzoMangiatoG2.getCodice().equals(ultimaMossa.getCodPezzoMangiato())) {
                    pezzoMangiatoDaRipristinare = pezzoMangiatoG2;
                }
            }
            if (pezzoMangiatoDaRipristinare != null) {
                scacchieraService.aggiornaPosizionePezzo(pezzoMangiatoDaRipristinare, ultimaMossa.getNewRiga(), ultimaMossa.getNewColonna());
            }

        }
        buttonUndo.setText("Undo disponibili " + (5 - numeroMosseUndo));
        PartitaService.cambioTurno();
        renderScacchiera();
        scacchieraService.printScacchiera();

    }
}
