package Pezzi;

import Engine.Servizi.Mossa;
import Engine.Servizi.ScacchieraService;
import GUI.CasellaScacchiera;

import java.util.ArrayList;
import java.util.List;

public class Regina extends Pezzo{

    //metodo costruttore classe Regina: crea una nuova istanza di Regina
    public Regina(String nome, String codice, int valore, boolean colore, int riga, int colonna){
        super(nome, codice, valore, colore, riga, colonna);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }

    public ArrayList<CasellaScacchiera> getArrayMosse() {

        return  getArrayMosseNormali();

    }
    /**
     * Metodo che calcola tutte le possibili mosse legali che il Pezzo Regina può fare
     * @return
     */
    public ArrayList<CasellaScacchiera> getArrayMosseNormali() {

        ArrayList<CasellaScacchiera> mosseDisponibili = new ArrayList<>();

        int i;
        int j;

        // Mosse asse Orizzontale
        j=getColonna();
        for (i = getRiga() + 1; i <= 8; i++) {
            if (!casellavuota(i, --j, mosseDisponibili)) break;
        }

        j=getColonna();
        for (i = getRiga() - 1; i >= 1; i--) {
            if (!casellavuota(i, ++j, mosseDisponibili)) break;
        }

        // Mosse asse Verticale
        i=getRiga();
        for (j = getColonna() + 1; j <= 8; j++) {
            if (!casellavuota(++i, j, mosseDisponibili)) break;
        }

        i=getRiga();
        for (j = getColonna() - 1; j >= 1; j--) {
            if (!casellavuota(--i, j, mosseDisponibili)) break;
        }


        // Mosse Assi Diagonali
        for (i = getRiga() + 1; i <= 8; i++) {
            if (!casellavuota(i, getColonna(), mosseDisponibili)) break;
        }

        for (i = getRiga() - 1; i >= 1; i--) {
            if (!casellavuota(i, getColonna(), mosseDisponibili)) break;
        }

        for (j = getColonna() + 1; j <= 8; j++) {
            if (!casellavuota(getRiga(), j, mosseDisponibili)) break;
        }

        for (j = getColonna() - 1; j >= 1; j--) {
            if (!casellavuota(getRiga(), j, mosseDisponibili)) break;
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
        // Controllo casella occupata
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



