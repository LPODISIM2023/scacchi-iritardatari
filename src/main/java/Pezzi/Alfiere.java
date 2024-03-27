package Pezzi;

import java.util.List;

public class Alfiere extends Pezzo {

    //metodo costrutore classe Alfiere: crea una nuova istanza di Alfiere
    public Alfiere(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        super(nome, codice, valore, colore, riga, colonna);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
