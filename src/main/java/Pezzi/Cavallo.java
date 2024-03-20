package Pezzi;

import java.util.List;

public class Cavallo extends Pezzo{

    //metodo costruttore classe Cavallo: crea una nuova istanza di Cavallo
    public Cavallo(String nome, String codice, int valore, boolean colore, int x, int y){
        super(nome, codice, valore, colore, x, y);
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
