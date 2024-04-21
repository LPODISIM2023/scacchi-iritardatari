package Pezzi;

import Engine.Servizi.ScacchieraService;
import GUI.CasellaScacchiera;

import java.util.ArrayList;
import java.util.List;

public class Pedone extends Pezzo {
    private boolean primaMossa = true;

    //metodo costruttore classe Pedone: crea una nuova istanza di Pedone
    public Pedone(String nome, String codice, int valore, boolean colore, int riga, int colonna, int codicePezzo) {
        super(nome, codice, valore, colore, riga, colonna, codicePezzo);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }

    //metodo setPrimaMossa(): utile per settare la variabile primaMossa a true quando inzia una nuova partita
    public void setPrimaMossa(boolean primaMossa) {
        this.primaMossa = primaMossa;
    }

    //metodo getPrimaMossa(): in base al risultato il pedone gioca in modo diverso. E' quindi utile per stabilire cosa deve fare il pedone nel caso in cui ha o non ha fatto la prima mossa
    public boolean getPrimaMossa() {
        return primaMossa;
    }


    /**
     * Metodo che calcola tutte le possibili mosse legali che il Pezzo Pedone può fare
     * @return
     */
    public ArrayList<CasellaScacchiera> getArrayMosse() {

        ArrayList<CasellaScacchiera> mosseDisponibili = new ArrayList<>();

        // Controllo se può mangiare sulla sinistra(Bianco) / destra (Nero)
        if (ScacchieraService.getPezzo(getRiga() + 1, getColonna() - 1) != null) {
            if (ScacchieraService.getPezzo(getRiga() + 1, getColonna() - 1).getColore() != getColore()) {
                mosseDisponibili.add(new CasellaScacchiera(getRiga() + 1, getColonna() - 1, true));
            }
        }

        // Controllo se può mangiare sulla destra(Bianco) / sinistra (Nero)
        if (ScacchieraService.getPezzo(getRiga() + 1, getColonna() + 1) != null) {
            if (ScacchieraService.getPezzo(getRiga() + 1, getColonna() + 1).getColore() != getColore()) {
                mosseDisponibili.add(new CasellaScacchiera(getRiga() + 1, getColonna() + 1, true));
            }
        }

        // Controllo se può muoversi in avanti
        if (ScacchieraService.getPezzo(getRiga() + 1, getColonna()) == null){
            // Controllo se è la prima mossa
            if (primaMossa) {
                if (ScacchieraService.getPezzo(getRiga() + 2, getColonna()) == null) {
                    mosseDisponibili.add(new CasellaScacchiera(getRiga() + 1, getColonna(), false));
                    mosseDisponibili.add(new CasellaScacchiera(getRiga() + 2, getColonna(), false));
                }
                else {
                    mosseDisponibili.add(new CasellaScacchiera(getRiga() + 1, getColonna(), false));
                }
            }

            else {
                    mosseDisponibili.add(new CasellaScacchiera(getRiga() + 1, getColonna(), false));
            }
        }

        return mosseDisponibili;
    }
}
