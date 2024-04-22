package Engine.Servizi;

import Engine.Giocatore.Giocatore;
import GUI.CasellaScacchiera;
import Pezzi.Pedone;
import Pezzi.Pezzo;
import Pezzi.Re;

import java.util.ArrayList;
import java.util.List;


public class Mossa {


    static ArrayList<CasellaScacchiera> mosseGiocatoreNemico = new ArrayList<>();

    static ArrayList<CasellaScacchiera> mosseLimitateAusiliario = new ArrayList<>();

    static ArrayList<CasellaScacchiera> mosseGiocatoreAlleato = new ArrayList<>();



    public static boolean reSottoScacco (Giocatore gNemico, Giocatore gAlleato){ //Giocatore nemico


        //riempio mosse possibili del giocatore nemico
        mosseGiocatoreNemico.clear();
        for (Pezzo singoloPezzo : gNemico.getPezziGiocatore()) {
            mosseGiocatoreNemico.addAll(singoloPezzo.getArrayMosse());
        }

        Pezzo reAlleato = null;
        if(gNemico.getColore()){
         reAlleato = ScacchieraService.getPezzoByCodice("n_k1");
        }
        else { reAlleato = ScacchieraService.getPezzoByCodice("b_k1"); }

        //vedo se la posizione del re è tra le mosse possibili del nemico
        for (CasellaScacchiera mossa : mosseGiocatoreNemico){
            if(mossa.getColonna()==reAlleato.getColonna() && mossa.getRiga()==reAlleato.getRiga()){

                System.out.println(reAlleato.getColore() + " ha il Re sotto scacco");
                if (PartitaService.isGiocatoreSottoScacco()) {
                    // mosseLimitate(gAlleato, gNemico);
                }
                return true;
            }
        }

        // PartitaService.isGiocatoreSottoScacco() = false;
        System.out.println(reAlleato.getColore() + " non sta sott scacco");
        return false;

    }


    public static ArrayList<CasellaScacchiera> mosseNemico (){

        Giocatore gNemico = null;

        if(PartitaService.getColoreTurnoGiocatore()) {
            gNemico = PartitaService.getGiocatore2();
        }
        else { gNemico = PartitaService.getGiocatore1(); }
System.out.println("Pezzi Giocatore nemico "+gNemico.getColore());
System.out.println(gNemico.getPezziGiocatore());
        mosseGiocatoreNemico.clear();
        for (Pezzo singoloPezzo : gNemico.getPezziGiocatore()) {
            mosseGiocatoreNemico.addAll(singoloPezzo.getArrayMosseNormali());
        }
        return mosseGiocatoreNemico;


    }


}
