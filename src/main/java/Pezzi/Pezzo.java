package Pezzi;

import java.util.List;
import java.util.Map;
public abstract class Pezzo {

    private String nome, codice;
    private final int valore;
    private boolean colore;
    private int x, y;
    //metodo costruttore: crea una nuova istanza Pezzo quando viene invocato
    public Pezzo(String nome, String codice, int valore, boolean colore, int x, int y){
        this.nome = nome;
        this.codice = codice;
        this.valore= valore;
        this.colore = colore;
        this.x = x;
        this.y = y;
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
    public void setPosizione(int newX, int newY){
        this.x = newX;
        this.y = newY;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    //metodo getColore():

    //metodo printColore()
    public String printColore(){return (colore?codice+'b' : codice+'n');}

    //metodo toString(): stampa le informazioni principali del pezzo (nome, colore, posizione). Ad esempio: Pedone bianco posX: A posY: 4
    public String toString(){return nome + " " + (colore?"bianco":"nero") + " " + "posX: " + x + " posY: " + y;}

    public abstract List<Mossa> getMosse();

}
