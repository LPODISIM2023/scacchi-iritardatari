package Pezzi;

import java.util.List;

public class Cavallo extends Pezzo{

    //metodo costruttore classe Cavallo: crea una nuova istanza di Cavallo
    public Cavallo(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        super(nome, codice, valore, colore, riga, colonna);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
