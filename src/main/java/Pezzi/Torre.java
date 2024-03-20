package Pezzi;

import java.util.List;

public class Torre extends Pezzo{
    //metodo costruttore classe Torre: crea una nuova istanza di Torre
    public Torre(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        super(nome, codice, valore, colore, riga, colonna);
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
