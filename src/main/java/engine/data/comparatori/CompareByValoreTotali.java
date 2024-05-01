package engine.data.comparatori;

import engine.data.Salvataggio;

import java.util.Comparator;

public class CompareByValoreTotali implements Comparator<Salvataggio> {
    @Override
    public int compare(Salvataggio o1, Salvataggio o2) {
        int valoreO1G1 = o1.getG1().getValoreTotaleGiocatore();
        int valoreO1G2 = o1.getG2().getValoreTotaleGiocatore();
        int o1ValorePartita  = valoreO1G1 - valoreO1G2;
        Math.abs(o1ValorePartita);

        int valoreO2G1 = o2.getG1().getValoreTotaleGiocatore();
        int valoreO2G2 = o2.getG2().getValoreTotaleGiocatore();
        int o2ValorePartita  = valoreO2G1 - valoreO2G2;
        Math.abs(o2ValorePartita);

        return o1ValorePartita - o2ValorePartita;

    }
}
