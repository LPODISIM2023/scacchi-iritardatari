package Pezzi;

import java.util.List;

public class Re extends Pezzo{

    //metodo costruttore classe Re: crea una nuova istanza di Re
    public Re(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        super(nome, codice, valore, colore, riga, colonna);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }
}
