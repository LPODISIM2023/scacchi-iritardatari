package GUI;

import Pezzi.Pezzo;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class CasellaScacchiera extends StackPane {
    private int riga, colonna;

    public CasellaScacchiera(int riga, int colonna) {
        this.riga = riga;
        this.colonna = colonna;
        testEventi();
    }

    private void testEventi() {
        setOnMouseClicked(mouseEvent -> {

            //Se nella casella c e un pezzo selezionabile da poter muovere
            ObservableList<Node> listaNodi = this.getChildren();
            if (!listaNodi.isEmpty() && listaNodi.get(0) instanceof Pezzo) {
                Pezzo pezzo = (Pezzo) listaNodi.get(0);
                pezzo.selezionaCaselleDisponibili();
            }

            if (!listaNodi.isEmpty() && listaNodi.get(0) instanceof ImageView && (Objects.equals(listaNodi.get(0).getId(), "selettoreCasella"))) {
                System.out.println("riga " + riga + " colonna" + colonna);
            }
            if (listaNodi.isEmpty()) {
                ScacchieraController.toglieSelezione();
            }

        });
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
