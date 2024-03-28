package GUI;

import javafx.scene.layout.StackPane;

public class CasellaScacchiera extends StackPane {
    private int riga, colonna;

    public CasellaScacchiera(int riga, int colonna) {
        this.riga = riga;
        this.colonna = colonna;
    }


    public int getColonna() {
        return colonna;
    }

    public int getRiga() {
        return riga;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }
}
