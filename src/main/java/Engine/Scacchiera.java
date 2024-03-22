package Engine;

import Pezzi.Pezzo;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;

public class Scacchiera {

    private final int numRighe=8;
    private final int numColonne=8;

    private Table<Integer, Integer, Pezzo> scacchieraTable;


    /**
     * Inizializza l oggetto scacchiera, dato due liste di pezzi dei rispettivi giocatori G1 e G2
     * @param pezziG1
     * @param pezziG2
     */
    public Scacchiera(List<Pezzo> pezziG1, List<Pezzo> pezziG2){

        //Creazione della tabella
        scacchieraTable = HashBasedTable.create(numRighe, numColonne);

        //Aggiunta Pezzi alla scacchiera
        for (Pezzo singoloPezzo : pezziG1) {
            scacchieraTable.put(singoloPezzo.getRiga(), singoloPezzo.getColonna(), singoloPezzo);
        }
        for (Pezzo singoloPezzo2 : pezziG2) {
            scacchieraTable.put(singoloPezzo2.getRiga(), singoloPezzo2.getColonna(), singoloPezzo2);
        }
    }

    /**
     * Restituisce un Singolo Pezzo nella scacchiera data la posizione Riga, Colonna
     * @param riga
     * @param colonna
     * @return un Pezzo nella scacchiera
     */
    public Pezzo getPezzo(int riga, int colonna) {
        return scacchieraTable.get(riga, colonna);
    }

    /**
     * Restituisce un Singolo Pezzo nella scacchiera dato il suo Codice
     * @param codice
     * @return un Pezzo nella scacchiera
     */
    public Pezzo getPezzoByCodice(String codice) {
        Pezzo pezzo = null;
        for (Table.Cell<Integer, Integer, Pezzo> cell : scacchieraTable.cellSet()) {
            if (cell.getValue().toString().equals(codice)) {
                pezzo = cell.getValue();
            }
        }
        return pezzo;
    }

}//class Scacchiera
