package Pezzi;

import Engine.Giocatore.Giocatore;
import Engine.Servizi.Mossa;
import Engine.Servizi.PartitaService;
import Engine.Servizi.ScacchieraService;
import GUI.CasellaScacchiera;

import java.util.ArrayList;
import java.util.List;

public class Re extends Pezzo{


    static ArrayList<CasellaScacchiera> mosseReLimitate = new ArrayList<>();
    static ArrayList<CasellaScacchiera> mosseReLimitateAusiliario = new ArrayList<>();

    //metodo costruttore classe Re: crea una nuova istanza di Re
    public Re(String nome, String codice, int valore, boolean colore, int riga, int colonna, int codicePezzo){
        super(nome, codice, valore, colore, riga, colonna, codicePezzo);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }


    /**
     * Metodo che calcola tutte le possibili mosse legali che il Pezzo Re può fare
     * @return
     */
    public ArrayList<CasellaScacchiera> getArrayMosse() {

        ArrayList<CasellaScacchiera> mosseGiocatoreNemico = new ArrayList<>();
        List<Pezzo> pezziGiocatoreNemico = null;
        ArrayList<CasellaScacchiera> mosseDisponibili = new ArrayList<>();
        ArrayList<CasellaScacchiera> mosseDisponibiliLimitate = new ArrayList<>();

        // Mosse su assi Verticali e Orizzontali
        casellavuota(getRiga()+1, getColonna(), mosseDisponibili);
        casellavuota(getRiga()-1, getColonna(), mosseDisponibili);
        casellavuota(getRiga(), getColonna()+1, mosseDisponibili);
        casellavuota(getRiga(), getColonna()-1, mosseDisponibili);

        // Mosse su assi diagonali
        casellavuota(getRiga()+1, getColonna()+1, mosseDisponibili);
        casellavuota(getRiga()+1, getColonna()-1, mosseDisponibili);
        casellavuota(getRiga()-1, getColonna()+1, mosseDisponibili);
        casellavuota(getRiga()-1, getColonna()-1, mosseDisponibili);

        if(PartitaService.getColoreTurnoGiocatore()) {
            pezziGiocatoreNemico = PartitaService.getGiocatore2().getPezziGiocatore();
        }
        else pezziGiocatoreNemico = PartitaService.getGiocatore1().getPezziGiocatore();


        mosseReLimitateAusiliario.clear();
        mosseReLimitate = mosseDisponibili;

        for (Pezzo singoloPezzo : pezziGiocatoreNemico) {
            if(singoloPezzo.getValore()!=10){
                mosseGiocatoreNemico.addAll(singoloPezzo.getArrayMosse());
            }
        }

        for (CasellaScacchiera mossaRe : mosseReLimitate){
            for (CasellaScacchiera mossaNemico : mosseGiocatoreNemico){
                if(mossaRe.getRiga() == mossaNemico.getRiga() && mossaRe.getColonna() == mossaNemico.getColonna()) {
                    mosseReLimitateAusiliario.add(mossaRe); break;
                }
            }
        }

        for (CasellaScacchiera mossaRe : mosseReLimitateAusiliario){
            mosseReLimitate.remove(mossaRe);
        }

        return mosseReLimitate;

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
