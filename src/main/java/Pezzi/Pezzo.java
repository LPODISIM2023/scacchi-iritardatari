package Pezzi;

import GUI.CasellaScacchiera;
import GUI.ScacchieraController;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public abstract class Pezzo extends ImageView {
    @FXML
    Scene scene;
    private String nome, codice;
    private final int valore;
    private boolean colore;
    private int riga, colonna;


    private int codicePezzoUTF8;

    /** metodo costruttore: crea una nuova istanza Pezzo quando viene invocato
     */

    public Pezzo(String nome, String codice, int valore, boolean colore, int riga, int colonna, int codicePezzo) {
        this.nome = nome;
        this.codice = codice;
        this.valore = valore;
        this.colore = colore;
        this.riga = riga;
        this.colonna = colonna;
        this.codicePezzoUTF8 = codicePezzo;
        handleEventi();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    public int getValore() {
        return valore;
    }

    public void setColore(boolean colore) {
        this.colore = colore;
    }

    public boolean getColore() {
        return colore;
    }

    public int getCodicePezzoUTF8() {
        return codicePezzoUTF8;
    }

    public void setCodicePezzoUTF8(int codicePezzoUTF8) {
        this.codicePezzoUTF8 = codicePezzoUTF8;
    }


    /**
     * motodo setPosizione(): utile per settere la posizione dei pezzi.
     * Importante per modificare la posizione del pezzo dopo una mossa.
     */
    public void setPosizione(int newRiga, int newColonna) {
        this.riga = newRiga;
        this.colonna = newColonna;
    }

    public int getRiga() {
        return riga;
    }

    public int getColonna() {
        return colonna;
    }


    //metodo printColore()
    public String printColore() {
        return (colore ? codice + 'b' : codice + 'n');
    }

    /**
     * metodo toString(): stampa le informazioni principali del pezzo (nome, colore, posizione).
     * Ad esempio: Pedone bianco posRiga: A posColonna: 4
     * @return
     */
    public String toString() {
        return nome + " " + (colore ? "bianco" : "nero") + " " + "posRiga: " + riga + " posColonna: " + colonna;
    }

    public abstract List<Mossa> getMosse();

    public void setImage() {
        this.setImage(new Image("/Pezzi/" + (this.colore ? "Bianco" : "Nero") + "/" + this.nome + ".png"));
    }

    /**
     * Metodo che dopo aver calcolato l'array delle possibili mosse, chiama controller scacchiera per render
     * delle caselle disponibili per il movimento del pezzo
     */
    public void selezionaCaselleDisponibili() {
        ScacchieraController.selezionaPosizioniDisponibili(getArrayMosse(), this);
    }

    /**
     * Metodo che invoca i vari listener sul oggetto pezzo
     * Per ora Inutilizzato
     */
    public void handleEventi() {
        //  setOnMousePressed(this::pressPezzo);

        // setOnMouseReleased(this::releasePezzo);

        // setOnMouseDragged(this::dragPezzo);

    }

    /**
     * Metodo che viene invocato quando si fa click col mouse
     * Richiama il metodo che seleziona il pezzo nella casella e mette in primo piano il pezzo
     *
     * @param e
     */
    public void pressPezzo(MouseEvent e) {
        ((CasellaScacchiera) this.getParent()).clickSuPezzoNellaCasella(this);
        this.getParent().toFront();
    }

    /**
     * Metodo che viene invocato quando si trascina il mouse dopo il click
     * viene calcolata la posizione del pezzo(rispetto a tutta la finestra) e viene aggiornata centrando
     * il pezzo al cursore del mouse
     *
     * @param e
     */
    public void dragPezzo(MouseEvent e) {
        double posCasellaX=0;
        double posCasellaY=0;
        //Recupero la posizione della casella e del relativo pezzo in modo assoluto rispetto alla finestra
        if (this.getParent().getParent() != null) {
            posCasellaX = this.getParent().getParent().getLayoutX() + this.getParent().getLayoutX();
            posCasellaY = this.getParent().getParent().getLayoutY() + this.getParent().getLayoutY();
        }
        //Sposto il pezzo centrandolo al mouse (-30 perche la casella è 60)
        this.setTranslateX(e.getSceneX() - posCasellaX - 30);
        this.setTranslateY(e.getSceneY() - posCasellaY - 30);

        //evidenzia la casella sotto il cursore
        ((CasellaScacchiera) this.getParent()).coloraCasellaAlPassaggioDelMouse(e);
    }

    /**
     * Metodo che viene invocato quando viene rilasciato il click del mouse
     * viene reimpostata la posizione iniziale del pezzo e viene controllato se la posizone del cursore
     * e una casella valida per una mossa
     *
     * @param e
     */
    public void releasePezzo(MouseEvent e) {
        this.setTranslateX(0);
        this.setTranslateY(0);
        ((CasellaScacchiera) this.getParent()).controlloRilascioPezzo(e);
    }

    //Metodo che va rivisto sovrascritto in base al pezzo
    public ArrayList<CasellaScacchiera> getArrayMosse() {
        ArrayList<CasellaScacchiera> mosseDisponibili = new ArrayList<>();
        mosseDisponibili.add(new CasellaScacchiera(riga + 1, colonna, false));
        mosseDisponibili.add(new CasellaScacchiera(riga + 2, colonna, false));
        return mosseDisponibili;
    }
}
