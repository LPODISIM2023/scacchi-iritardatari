package Pezzi;

import java.util.List;

public class Pedone extends Pezzo{
    private boolean primaMossa = true;

    //metodo costruttore classe Pedone: crea una nuova istanza di Pedone
    public Pedone(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        super(nome, codice, valore, colore, riga, colonna);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }

    //metodo setPrimaMossa(): utile per settare la variabile primaMossa a true quando inzia una nuova partita
    public void setPrimaMossa(boolean primaMossa){this.primaMossa = primaMossa;}
    //metodo getPrimaMossa(): in base al risultato il pedone gioca in modo diverso. E' quindi utile per stabilire cosa deve fare il pedone nel caso in cui ha o non ha fatto la prima mossa
    public boolean getPrimaMossa(){return primaMossa;}
}


