package GUI;

import Engine.Data.LogMossa;
import Engine.Data.Logger;
import Engine.Data.Salvataggio;
import Engine.Giocatore.Giocatore;
import Engine.Servizi.PartitaService;
import Engine.Servizi.ScacchieraService;
import Pezzi.Pedone;
import Pezzi.Pezzo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.Serializable;
import java.util.*;



public class ScacchieraController implements Serializable {


    String directoryPath = System.getProperty("user.dir") + File.separator + "saved_games";
    File directory = new File(directoryPath);


    public TextArea getTextAreaMosse() {
        return textAreaMosse;
    }

    @FXML
    TextArea textAreaMosse = new TextArea();

    @FXML
    ScrollPane scrollPaneMosse = new ScrollPane();

    @FXML
    Label pezziMangiatiGiocatoreBianco = new Label();

    @FXML
    Label pezziMangiatiGiocatoreNero = new Label();
    public String[] array = {"x", "A", "B", "C", "D", "E", "F", "G", "H"};

    static Giocatore g1;
    static Giocatore g2;

    private Salvataggio salvataggio = new Salvataggio();

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
    private PartitaService partita;

    private ScacchieraService scacchieraService;

    public static ArrayList<CasellaScacchiera> caselle = new ArrayList<>();

    public void setScacchieraService(ScacchieraService sS){
        this.scacchieraService = sS;
    }

    public  PartitaService getPartita() {
        return partita;
    }

    public void setPartita(PartitaService partita) {
        this.partita = partita;
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
        textAreaMosse.clear();
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

    public void initGameRecuperato(Salvataggio salvataggio){
        textAreaMosse.clear();
        textAreaMosse.setEditable(false);
        scrollPaneMosse.setContent(textAreaMosse);

        partita = new PartitaService(salvataggio, this);

        g1 = PartitaService.getGiocatore1();
        g2 = PartitaService.getGiocatore2();

        //Creo scacchieraService

        scacchieraService = PartitaService.getScacchieraService();

        //RenderScacchiera

        renderScacchiera();
        ScacchieraService.printScacchiera();

        //RenderNomi
        labelNomePlayer1.setText(g1.getNome());
        labelNomePlayer2.setText(g2.getNome());
        textAreaMosse.toString();

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
                    File file = filePartita.showSaveDialog(new Stage());
                    if(file != null) {
                        salvataggio.salvaPartita(g1,g2,partita,file);
                    }else{
                        throw new FileNotFoundException();
                    }
                    textAreaMosse.clear();
                    log = "";
                    if (!(PartitaService.getColoreTurnoGiocatore())) {
                        PartitaService.cambioTurno();
                    }
                    getStart(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else //renderScacchiera();
                if (scelta == no) {
                    try {
                        initGame(g1.getNome(), g2.getNome(), PartitaService.getIsBot());  //migliorare terzo parametro.
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

        if(!directory.exists()) directory.mkdirs();

        filePartita.setTitle("Salvataggi");
        filePartita.setInitialDirectory(new File(directoryPath));
        FileChooser.ExtensionFilter cheesFilter = new FileChooser.ExtensionFilter("File chess","*.chess");
        filePartita.getExtensionFilters().add(cheesFilter);
        File file = filePartita.showSaveDialog(new Stage());

        if(file != null){
            salvataggio.salvaPartita(g1,g2,partita,file);
            System.out.println("Salvato!");
        }else{
            throw new FileNotFoundException();
        }

    }

    /**
     * Metodo che permette all'utente di scegliere se uscire e salvare, uscire o annullare l'operazione,
     * gestione queste scelte tramite un "incapsulamento" di Alert e Dialog pane, ovvero classi anonime che vengono
     * create all'interno del metodo.
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
                   saveButton(event);
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
            Pezzo pezzo = ScacchieraService.getPezzo(casella.getRiga(), casella.getColonna());
            //Se la casella é piena renderizzo il pezzo nella rispettiva casella di render
            if (pezzo != null) {
                pezzo.setImage();
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

                        Pezzo pezzoVecchio = casella.getPezzoDellaCasella();
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
    public void eseguiMossa(Pezzo pezzo, CasellaScacchiera casella) {

        //Vedo a quale giocatore è stato mangiato il pezzo
        if (!PartitaService.getColoreTurnoGiocatore()) {
            g1.addPezzoMangiato(casella);
            if(casella.getPezzoDellaCasella() != null) {
                visualizzaPezziMangiatiNeri(casella.getPezzoDellaCasella().getCodicePezzoUTF8());
            }
        } else {
            g2.addPezzoMangiato(casella);
            if(casella.getPezzoDellaCasella() != null) {
                visualizzaPezziMangiatiBianchi(casella.getPezzoDellaCasella().getCodicePezzoUTF8());
            }
        }


        //Aggiorno il file del log e lo mostro a schermo
        datiLogMosse(pezzo, casella);

        //Controllo se si muove un pedone per la prima volta
        if (pezzo instanceof Pedone) ((Pedone) pezzo).setPrimaMossa(false);

        //Aggiorno la posizione nella scacchiera logica
        scacchieraService.aggiornaPosizionePezzo(pezzo, casella.getRiga(), casella.getColonna());

        //Reimposto l undo delle mosse al ultima mossa eseguita
        numUndoEseguitiDiSeguito = 1;

        //Cambio turno e rerender della Scacchiera
        PartitaService.cambioTurno();
        renderScacchiera();
        PartitaService.controlloScaccoMatto();
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

        return ultimaMossa.getNumeroMossa() + "° (" + ultimaMossa.getOldRiga() + "," + array[ultimaMossa.getOldColonna()] + ")" + "->" + "(" + ultimaMossa.getNewRiga() + "," + array[ultimaMossa.getNewColonna()] + ")" + new String(Character.toChars(pezzo.getCodicePezzoUTF8())) + "\n";
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

        if (PartitaService.getIsBot()) {
            eseguiUnaMossaUndo(arrayTutteLeMosse);
            eseguiUnaMossaUndo(arrayTutteLeMosse);
        } else {
            eseguiUnaMossaUndo(arrayTutteLeMosse);
            PartitaService.cambioTurno();
        }

        buttonUndo.setText("Undo disponibili " + (5 - numeroMosseUndo));

        renderScacchiera();
        scacchieraService.printScacchiera();

    }

    public void eseguiUnaMossaUndo(ArrayList<LogMossa> arrayTutteLeMosse) {
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
    }

    public void endGame() throws InterruptedException {  //@TODO implementare eccezione per i file da sovrascrivere.

        Alert endGGame = new Alert(Alert.AlertType.NONE, "S");

        if (!PartitaService.getColoreTurnoGiocatore())  {
            endGGame.setContentText("Ha Vinto il Bianco");
            endGGame.setTitle("Partita Conclusa: SCACCO MATTO");
        }
        else {
            endGGame.setContentText("Ha Vinto il Nero");
            endGGame.setTitle("Partita Conclusa: SCACCO MATTO");
        }


        ButtonType nuovaPartita = new ButtonType("Nuova Partita");
        ButtonType esci = new ButtonType("Esci");

        endGGame.getButtonTypes().setAll(nuovaPartita, esci);

        endGGame.showAndWait().ifPresent(scelta -> {
            if (scelta == nuovaPartita) {
                try {
                    initGame(g1.getNome(), g2.getNome(), PartitaService.getIsBot());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (scelta == esci) {
                        System.exit(0);
            }
        });

    }

    /**
     * Il metodo assegna ad una label una stringa con i caratteri dei pezzi mangiati dal giocatore Bianco.
     * @param codPezzoNero codice UTF8 dei vari pezzi neri
     */
    String pezziMangiatiDaBianco=" ";
    public void visualizzaPezziMangiatiBianchi(int codPezzoNero){
        pezziMangiatiDaBianco += Character.toString(codPezzoNero);
        pezziMangiatiGiocatoreBianco.setText(pezziMangiatiDaBianco);
    }



    /**
     * Il metodo assegna ad una label una stringa con i caratteri dei pezzi mangiati dal giocatore Nero.
     * @param codPezzoBianco codice UTF8 dei vari pezzi bianchi
     */
    String pezziMangiatiDaNero = " ";
    public void visualizzaPezziMangiatiNeri(int codPezzoBianco){
        pezziMangiatiDaNero += Character.toString(codPezzoBianco);
        pezziMangiatiGiocatoreNero.setText(pezziMangiatiDaNero);
    }

    @FXML
    /**
     * Il metodo permette al giocatore di abbandonare la partita nel momento in cui desidera.
     * Tale metodo mostrerà una finestra dalla quale il giocatore sceglierà se abbadonare la partita.
     * @param event
     */
    public void abbandona(ActionEvent event){
        Alert abbandona = new Alert(Alert.AlertType.NONE, "Sicuro di voler abbandonare la partita?");
        abbandona.setTitle("Abbandono della partita");

        ButtonType si = new ButtonType("SI");
        ButtonType no = new ButtonType("NO");

        abbandona.getButtonTypes().setAll(si, no);

        abbandona.showAndWait().ifPresent(scelta->{
            if(scelta == si){
                System.exit(0);
            }
            if(scelta == si && PartitaService.getIsBot()){
                System.exit(0);
            }

        });
    }

    /**
     * Il metodo gestisce la patta. Un giocatore umano può richiedere la patta al contrario del Giocatore Bot.
     * @param event
     */
    public void patta(ActionEvent event) {
        Alert patta;
        if (PartitaService.getColoreTurnoGiocatore() && !PartitaService.getIsBot()) {
            patta = new Alert(Alert.AlertType.NONE, "Vuoi richiedere la patta a " + PartitaService.getGiocatore2().getNome());
            patta.setTitle("Richiesta patta");
        } else {
            patta = new Alert(Alert.AlertType.NONE, "Vuoi richiedere la patta a " + PartitaService.getGiocatore1().getNome());
            patta.setTitle("Richiesta patta");
        }
        if (PartitaService.getIsBot()) {
            patta = new Alert(Alert.AlertType.NONE, "Vuoi richiedere la patta e terminare la partita?");
            patta.setTitle("Richiesta patta");
        }

        ButtonType si = new ButtonType("SI");
        ButtonType no = new ButtonType("NO");

        patta.getButtonTypes().setAll(si, no);

        patta.showAndWait().ifPresent(scelta -> {
            if (scelta == si && !PartitaService.getIsBot()) {
                PartitaService.cambioTurno();
                System.out.println(PartitaService.getColoreTurnoGiocatore());
                accettoPatta();
            }
            if(scelta == si && PartitaService.getIsBot()){
                System.exit(0);
            }
        });
    }
        public void accettoPatta(){
            Alert messaggio;

            if(PartitaService.getColoreTurnoGiocatore()) {
                messaggio = new Alert(Alert.AlertType.NONE, PartitaService.getGiocatore2().getNome() + " ha richiesto la patta. Accetti la patta?");
            }
            else{
                messaggio = new Alert(Alert.AlertType.NONE, PartitaService.getGiocatore1().getNome() + " ha richiesto la patta. Accetti la patta?");
            }

            messaggio.setTitle("Conferma della patta");

            ButtonType siPatta = new ButtonType("Accetto");
            ButtonType noPatta = new ButtonType("Non Accetto");

            messaggio.getButtonTypes().setAll(siPatta, noPatta);

            messaggio.showAndWait().ifPresent(scelta->{
                if(scelta == siPatta){
                    System.exit(0);
                }
            });
            PartitaService.cambioTurno();
        }
}

