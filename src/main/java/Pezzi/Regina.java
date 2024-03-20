package Pezzi;

import java.util.List;

public class Regina extends Pezzo{

    //metodo costruttore classe Regina: crea una nuova istanza di Regina
    public Regina(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        super(nome, codice, valore, colore, riga, colonna);
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
