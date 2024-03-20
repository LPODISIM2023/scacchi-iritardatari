package Pezzi;

import java.util.List;

public class Re extends Pezzo{

    //metodo costruttore classe Re: crea una nuova istanza di Re
    public Re(String nome, String codice, int valore, boolean colore, int x, int y){
        super(nome, codice, valore, colore, x, y);
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
