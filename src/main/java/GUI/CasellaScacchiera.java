package GUI;

import Pezzi.Pezzo;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class CasellaScacchiera extends StackPane {
    private final String colore_rosso = "#ff8080";
    private int riga, colonna;
    private static ScacchieraController sc;
    private boolean siPuoMangiare = false;
    private Pezzo pezzoDellaCasella;

    /**
     * Costruttore utilizzato per renderizzare le caselle con i relativi pezzi
     *
     * @param riga
     * @param colonna
     * @param sc
     */
    public CasellaScacchiera(int riga, int colonna, ScacchieraController sc) {
        this.riga = riga;
        this.colonna = colonna;
        this.sc = sc;
        handleEventi();
    }

    /**
     * Costruttore usato nella classe Pezzo per poter creare l'array di mosse disponibili
     *
     * @param riga
     * @param colonna
     */
    public CasellaScacchiera(int riga, int colonna) {
        this.riga = riga;
        this.colonna = colonna;
    }

    public int getColonna() {
        return colonna;
    }

    public int getRiga() {
        return riga;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    /**
     * Metodo usato per dichiarare i vari listener per l'oggetto casella
     */
    private void handleEventi() {
        setOnMouseClicked(mouseEvent -> {
            clickNellaCasella();
        });
    }


    /**
     * Meotodo di supporto al click sulla casella,
     * usato per capire lo stato delle selezioni dei pezzi e
     * gestire la relativa selezione o deselesezione
     *
     * @param pezzo
     */
    public void clickSuPezzoNellaCasella(Pezzo pezzo) {
        //Controllo se é gia presente un pezzo selezionato
        if (!ScacchieraController.getIsSelectCasella()) { //vero se non é selezionato neanche un pezzo
            //Pezzo dello stesso colore del giocatore che deve giocare
            if (pezzo.getColore() == ScacchieraController.getColorePezzoSelezionato()) {
                selezionaCasella(pezzo);
            } else {
                //Pezzo del colore opposto
                sc.toglieSelezione();
            }
        } else { // é gia selezionato un pezzo
            //Si clicca su un pezzo dello stesso colore
            if (pezzo.getColore() == ScacchieraController.getColorePezzoSelezionato()) {
                sc.toglieSelezione(); //tolgo la vecchia selezione
                selezionaCasella(pezzo);
            } else {
                //Si clicca un pezzo del colore opposto
                sc.toglieSelezione();
            }
        }
    }

    /**
     * Metodo che viene invocato quando si esegue un click su una casella (o sul pezzo da essa contenuta)
     * Si occupa di controllare lo stato della casella dove si é fatto click
     * ed esegue le varie operazioni necessarie
     */
    public void clickNellaCasella() {
        //Recupero Nodo(Pezzo) nella casella
        ObservableList<Node> listaNodi = this.getChildren();

        //La casella é vuota
        if (listaNodi.isEmpty()) {
            sc.toglieSelezione();
            return;
        }

        //La casella contiene un pezzo
        if (listaNodi.get(0) instanceof Pezzo) {
            Pezzo pezzo = (Pezzo) listaNodi.get(0);
            pezzoDellaCasella = pezzo;
            clickSuPezzoNellaCasella(pezzo);
            return;
        }

        //La casella contiene una posizione disponibile
        if (listaNodi.get(0) instanceof ImageView) {
            ImageView selettoreCasella = (ImageView) listaNodi.get(0);
            if (Objects.equals(selettoreCasella.getId(), "selettoreCasella")) {
                clickSuCasellaDisponibile();
            }
        }
    }

    /**
     * Metodo invocato se si esegue click su una posizione disponibile
     * ANCODA DA DEFINIRE LA LOGICA
     */
    public void clickSuCasellaDisponibile() {
        //Si clicca sulla casella dove si vuole spostare il pezzo
        sc.testMossa2(ScacchieraController.getPezzoSelezionato(), this);
    }

    public void clickSuCasellaDisponibile(CasellaScacchiera casellaDisp) {
        //Si clicca sulla casella dove si vuole spostare il pezzo
        sc.testMossa2(ScacchieraController.getPezzoSelezionato(), casellaDisp);
    }

    /**
     * Metodo di supporto per selezionare le caselle disponibili,
     * notificarlo al controller
     *
     * @param pezzo
     */
    public void selezionaCasella(Pezzo pezzo) {
        pezzo.selezionaCaselleDisponibili();
        ScacchieraController.setIsSelectCasella(true);
        ScacchieraController.setPezzoSelezionato(pezzo);
    }

    /**
     * Metodo di supporto per colorare la casella selezionata
     * invocato dalla casella stessa o da scacchiera controller
     */
    public void selezionaCasellaColore() {
        this.setBackground(new Background(new BackgroundFill(Color.web(colore_rosso), CornerRadii.EMPTY, Insets.EMPTY)));
    }


    /**
     * Metodo che viene invocato quando viene rilasciato il click del mouse quando sposta il pezzo
     * si occupa di calcolare la posizione del puntatore sulla scacchiera e in caso effettua la selezione
     *
     * @param e
     */
    public void controlloRilascioPezzo(MouseEvent e) {
        //Calcolo posizione della scacchiera
        double mouseX = e.getSceneX() - 440; // valore della scacchiera nella finestra, usato per creare le cordinare 0,0 nella scacchiera
        double mouseY = e.getSceneY() - 29; //
        int rigaNum = 1;
        int colonnaNum = 1;

        //Calcolo la casella in base alla posizione del puntatore
        for (int colonna = 0; (mouseX) > (colonna * 60); colonna++) {
            colonnaNum = colonna + 1;
        }
        for (int riga = 0; (mouseY) > (riga * 60); riga++) {
            rigaNum = riga + 1;
        }
        rigaNum = 9 - rigaNum;

        //Vedo se la casella è selezionabile
        for (CasellaScacchiera casella : ScacchieraController.getCaselleScacchiera()) {
            if (casella.getRiga() == rigaNum && casella.getColonna() == colonnaNum) {
                ObservableList<Node> listaNodi = casella.getChildren();
                if (!listaNodi.isEmpty()) {
                    ImageView selettoreCasella = (ImageView) listaNodi.get(0);
                    if (Objects.equals(selettoreCasella.getId(), "selettoreCasella")) {
                        clickSuCasellaDisponibile(casella);
                        return;
                    }
                }
            }
        }
    }


}
