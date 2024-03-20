package Pezzi;

import java.util.List;

public class Regina extends Pezzo{

    //metodo costruttore classe Regina: crea una nuova istanza di Regina
    public Regina(String nome, String codice, int valore, boolean colore, int x, int y){
        super(nome, codice, valore, colore, x, y);
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
