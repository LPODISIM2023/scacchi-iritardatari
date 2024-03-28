package Engine.Servizi;

import Pezzi.Pedone;
import Pezzi.Pezzo;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;
import java.util.Map;

public class ScacchieraService {

    private final int numRighe=8;
    private final int numColonne=8;

    private Table<Integer, Integer, Pezzo> scacchieraTable;


    /**
     * Inizializza l oggetto scacchiera, dato due liste di pezzi dei rispettivi giocatori G1 e G2
     * @param pezziG1
     * @param pezziG2
     */
    public ScacchieraService(List<Pezzo> pezziG1, List<Pezzo> pezziG2){

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


    public void aggiornaPosizionePezzo(Pezzo pezzoDaMuovere, int riga, int colonna) {
        if (pezzoDaMuovere instanceof Pedone) ((Pedone) pezzoDaMuovere).setPrimaMossa(false);
        scacchieraTable.remove(pezzoDaMuovere.getRiga(), pezzoDaMuovere.getColonna());
        pezzoDaMuovere.setPosizione(riga, colonna);
        scacchieraTable.put(riga, colonna, pezzoDaMuovere);
    }


    //DEBUG

    /**
     * Metodo Usato per il Debug della scacchiera Service per capire le effettive posizioni dei pezzi
     * stampa la scacchiera su terminale
     */
    public void printScacchiera(){
        int v = 0;
        char[] let = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        System.out.print("   ");
        for (int i = 0; i < let.length; i++) {
            System.out.print(" " + let[i] + " ");
        }
        System.out.println();
        for (int i = 8; i >= 1; i--) {
            Map<Integer, Pezzo> riga = scacchieraTable.row(i); //recupero un rigo della tabella
            System.out.printf(" %-1s ", i);
            for (int j = 1; j <= 8; j++) { //scorro il rigo e recupero gli elementi
                Pezzo x = riga.get(j);
                if (x == null)
                    System.out.printf("|--"); //stampa pezzo
                else
                    System.out.printf("|%-2s", x.getCodice()); //stampa pezzo
            }
            System.out.println();
        }
    }

}//class Scacchiera
