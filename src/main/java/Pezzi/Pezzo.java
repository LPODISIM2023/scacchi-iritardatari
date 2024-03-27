package Pezzi;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.util.List;
import java.util.Map;
public abstract class Pezzo extends ImageView {

    private String nome, codice;
    private final int valore;
    private boolean colore;
    private int riga, colonna;
    //metodo costruttore: crea una nuova istanza Pezzo quando viene invocato
    public Pezzo(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        this.nome = nome;
        this.codice = codice;
        this.valore= valore;
        this.colore = colore;
        this.riga = riga;
        this.colonna = colonna;
    }

    //metodo set e get dell'attributo nome
    public void setNome(String nome){this.nome = nome;}
    public String getNome(){return nome;}
    public void setCodice(String codice){this.codice = codice;}
    public String getCodice(){return codice;}
    public int getValore(){return valore;}
    public void setColore(boolean colore){this.colore = colore;}
    public boolean getColore(){return colore;}

    /*motodo setPosizione(): utile per settere la posizione dei pezzi.
     Importante per modificare la posizione del pezzo dopo una mossa.
     */
    public void setPosizione(int newRiga, int newColonna){
        this.riga = newRiga;
        this.colonna = newColonna;
    }
    public int getRiga(){return riga;}
    public int getColonna(){return colonna;}
    //metodo getColore():

    //metodo printColore()
    public String printColore(){return (colore?codice+'b' : codice+'n');}

    //metodo toString(): stampa le informazioni principali del pezzo (nome, colore, posizione). Ad esempio: Pedone bianco posRiga: A posColonna: 4
    public String toString(){return nome + " " + (colore?"bianco":"nero") + " " + "posRiga: " + riga + " posColonna: " + colonna;}

    public abstract List<Mossa> getMosse();

    public void setImage(){
        this.setImage(new Image("/Pezzi/" + (this.colore ? "Bianco" : "Nero") + "/" + this.nome + ".png"));
    }

}
