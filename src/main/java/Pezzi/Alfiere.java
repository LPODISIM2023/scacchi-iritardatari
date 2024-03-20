package Pezzi;

import java.util.List;

public class Alfiere extends Pezzo {

    //metodo costrutore classe Alfiere: crea una nuova istanza di Alfiere
    public Alfiere(String nome, String codice, int valore, boolean colore, int x, int y){
        super(nome, codice, valore, colore, x, y);
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
