package Engine.Data.comparatori;

import Engine.Data.Salvataggio;
import Pezzi.Pezzo;

import java.util.Comparator;
import java.util.List;

public class CompaireByNumeroPezzi implements Comparator<Salvataggio> {
    @Override
    public int compare(Salvataggio o1, Salvataggio o2) {
        int pezziO1G1 = o1.getG1().getPezziGiocatore().size();
        int pezziO1G2 = o1.getG2().getPezziGiocatore().size();
        int o1pezzi = pezziO1G1 + pezziO1G2;

        int pezziO2G1 = o2.getG1().getPezziGiocatore().size();
        int pezziO2G2 = o2.getG2().getPezziGiocatore().size();
        int o2pezzi = pezziO2G1 + pezziO2G2;

        return o1pezzi - o2pezzi;

    }
}
