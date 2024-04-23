package Pezzi;

import Engine.Servizi.Mossa;
import Engine.Servizi.ScacchieraService;
import GUI.CasellaScacchiera;

import java.util.ArrayList;
import java.util.List;

public class Alfiere extends Pezzo {

    //metodo costrutore classe Alfiere: crea una nuova istanza di Alfiere
    public Alfiere(String nome, String codice, int valore, boolean colore, int riga, int colonna, int codicePezzo){
        super(nome, codice, valore, colore, riga, colonna, codicePezzo);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }

    /**
     * Metodo che calcola tutte le possibili mosse legali che il Pezzo Alfiere può fare
     * @return
     */
    @Override
    public ArrayList<CasellaScacchiera> getArrayMosse() {

        ArrayList<CasellaScacchiera> mosseDisponibili = new ArrayList<>();

        int i;
        int j;

        // Mosse assi Diagonali
        j=getColonna();
        for (i = getRiga() + 1; i <= 8; i++) {
            --j;
            if (j >= 1) {
                if (!casellavuota(i, j, mosseDisponibili)) break;
            }
        }

        j=getColonna();
        for (i = getRiga() - 1; i >= 1; i--) {
            ++j;
            if (j <= 8) {
                if (!casellavuota(i, j, mosseDisponibili)) break;
            }
        }

        i=getRiga();
        for (j = getColonna() + 1; j <= 8; j++) {
            ++i;
            if (i <= 8) {
                if (!casellavuota(i, j, mosseDisponibili)) break;
            }
        }

        i=getRiga();
        for (j = getColonna() - 1; j >= 1; j--) {
            --i;
            if (i >= 1) {
                if (!casellavuota(i, j, mosseDisponibili)) break;
            }
        }


        return mosseDisponibili;
    }

    /**
     * Metodo che controlla se una casella è occupata
     * Se lo è: Permette al Pezzo di mangiare se è del colore opposto
     * Se non lo è: Permette al Pezzo di muoversi liberamente
     * @return
     */
    public boolean casellavuota(int riga, int colonna, ArrayList<CasellaScacchiera> mosseDisponibili) {
        if (ScacchieraService.getPezzo(riga, colonna) != null) {
            if (ScacchieraService.getPezzo(riga, colonna).getColore() != getColore()) {
                mosseDisponibili.add(new CasellaScacchiera(riga, colonna, true));
                return false;
            }
            return false;
        } else {
            mosseDisponibili.add(new CasellaScacchiera(riga, colonna, false));
            return true;
        }
    }
}
