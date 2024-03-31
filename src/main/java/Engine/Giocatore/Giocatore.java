package Engine.Giocatore;

import Pezzi.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Giocatore {

    private String nome;

    private boolean colore;

    private List<Pezzo> pezziGiocatore = new ArrayList<>();

    private List<Pezzo> pezziMangiati = new ArrayList<>();

    private int valoreTotaleGiocatore = 0;

    public Giocatore (String nome, boolean colore){

        this.nome = nome;
        this.colore = colore;

        if ( colore == true ) {
            // Pezzo Pedone Bianco
            pezziGiocatore.add(new Pedone("Pedone", "b_p1", 1, true, 2, 1 ));
            pezziGiocatore.add(new Pedone("Pedone", "b_p2", 1, true, 2, 2 ));
            pezziGiocatore.add(new Pedone("Pedone", "b_p3", 1, true, 2, 3 ));
            pezziGiocatore.add(new Pedone("Pedone", "b_p4", 1, true, 2, 4 ));
            pezziGiocatore.add(new Pedone("Pedone", "b_p5", 1, true, 2, 5 ));
            pezziGiocatore.add(new Pedone("Pedone", "b_p6", 1, true, 2, 6 ));
            pezziGiocatore.add(new Pedone("Pedone", "b_p7", 1, true, 2, 7 ));
            pezziGiocatore.add(new Pedone("Pedone", "b_p8", 1, true, 2, 8 ));
            //Pezzo Torre Bianco
            pezziGiocatore.add(new Torre("Torre", "b_t1", 5, true, 1, 1 ));
            pezziGiocatore.add(new Torre("Torre", "b_t2", 5, true, 1, 8 ));
            //Pezzo Cavallo Bianco
            pezziGiocatore.add(new Cavallo("Cavallo", "b_c1", 3, true, 1, 2 ));
            pezziGiocatore.add(new Cavallo("Cavallo", "b_c2", 3, true, 1, 7 ));
            //Pezzo Alfiere Bianco
            pezziGiocatore.add(new Alfiere("Alfiere", "b_a1", 3, true, 1, 3 ));
            pezziGiocatore.add(new Alfiere("Alfiere", "b_a2", 3, true, 1, 6 ));
            //Pezzo Regina Bianco
            pezziGiocatore.add(new Regina("Regina", "b_q1", 3, true, 1, 4 ));
            //Pezzo Re Bianco
            pezziGiocatore.add(new Re("Re", "b_k1", 3, true, 1, 5 ));

        }

        else {
            // Pezzo Pedone Nero
            pezziGiocatore.add(new Pedone("Pedone", "n_p1", 1, false, 7, 1 ));
            pezziGiocatore.add(new Pedone("Pedone", "n_p2", 1, false, 7, 2 ));
            pezziGiocatore.add(new Pedone("Pedone", "n_p3", 1, false, 7, 3 ));
            pezziGiocatore.add(new Pedone("Pedone", "n_p4", 1, false, 7, 4 ));
            pezziGiocatore.add(new Pedone("Pedone", "n_p5", 1, false, 7, 5 ));
            pezziGiocatore.add(new Pedone("Pedone", "n_p6", 1, false, 7, 6 ));
            pezziGiocatore.add(new Pedone("Pedone", "n_p7", 1, false, 7, 7 ));
            pezziGiocatore.add(new Pedone("Pedone", "n_p8", 1, false, 7, 8 ));
            //Pezzo Torre Nero
            pezziGiocatore.add(new Torre("Torre", "n_t1", 5, false, 8, 1 ));
            pezziGiocatore.add(new Torre("Torre", "n_t2", 5, false, 8, 8 ));
            //Pezzo Cavallo Nero
            pezziGiocatore.add(new Cavallo("Cavallo", "n_c1", 3, false, 8, 2 ));
            pezziGiocatore.add(new Cavallo("Cavallo", "n_c2", 3, false, 8, 7 ));
            pezziGiocatore.add(new Cavallo("Cavallo", "n_c2", 3, false, 4, 4 )); //cavallo da rimuove
            //Pezzo Alfiere Nero
            pezziGiocatore.add(new Alfiere("Alfiere", "n_a1", 3, false, 8, 3 ));
            pezziGiocatore.add(new Alfiere("Alfiere", "n_a2", 3, false, 8, 6 ));
            //Pezzo Regina Nero
            pezziGiocatore.add(new Regina("Regina", "n_q1", 3, false, 8, 4 ));
            //Pezzo Re Nero
            pezziGiocatore.add(new Re("Re", "n_k1", 3, false, 8, 5 ));
        }

    }

    public String getNome(){return this.nome; }

    public void setNome(String nome){ this.nome = nome; }

    public boolean getColore(){ return this.colore; }

    public void setColore(boolean colore){ this.colore = colore; }

    public List<Pezzo> getPezziGiocatore(){ return this.pezziGiocatore; }

    public void setPezziGiocatore(List<Pezzo> listanuova){ this.pezziGiocatore=listanuova; }

    public List<Pezzo> getPezziMangiati(){ return this.pezziMangiati; }

    public void setPezziMangiati(List<Pezzo> pezzimangiati){ this.pezziMangiati= pezzimangiati; }

    /**
     * Medoto che aggiunge alla lista dei pezzi mangiati all'avversario un pezzo mangiato
     */
    public void addPezzoMangiato (Pezzo pezzomangiato) {
        pezziMangiati.add(pezzomangiato);
        valoreTotaleGiocatore = valoreTotaleGiocatore + pezzomangiato.getValore();
    }



}
