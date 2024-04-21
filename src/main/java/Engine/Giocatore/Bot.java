package Engine.Giocatore;

import Engine.Servizi.PartitaService;
import Engine.Servizi.ScacchieraService;
import GUI.CasellaScacchiera;
import GUI.ScacchieraController;
import Pezzi.Pezzo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends Giocatore {

    List<Pezzo> listaPezzi = this.getPezziGiocatore();
    List<CasellaScacchiera> listaMosseAmmissibili = new ArrayList<>();
    Random randomP = new Random();
    Random randomM = new Random();

    public Bot() {
        super("Bot", false);
    }

    /**
     * Il metodo mossaRadom implementa le mosse del bot controllando prima i pezzi che possono effettuare mosse e in modo randomsi scegli un pezzo e una sua mossa da effettuare.
     */
    public void mossaRandom() {
        ArrayList<Pezzo> tuttePezziDisponibili = new ArrayList<>();
        ScacchieraController sc = PartitaService.getScacchieraController();

        //lista dei pezzi che possono effettuare delle mosse
        for (Pezzo pezzo : listaPezzi) {
            if (pezzo.getArrayMosse().isEmpty()) continue;
            tuttePezziDisponibili.add(pezzo);
        }
        int numeroRandomPezzo = randomP.nextInt(tuttePezziDisponibili.size());
        Pezzo pezzorandom = tuttePezziDisponibili.get(numeroRandomPezzo);//otteniamo il pezzo random

        //scelta in modo random della mossa del pezzorandom
        int numeroRandomMossa = randomM.nextInt(pezzorandom.getArrayMosse().size());
        sc.testMossa2(pezzorandom, pezzorandom.getArrayMosse().get(numeroRandomMossa));


    }

}