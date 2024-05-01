package GUI;

import Engine.Data.Salvataggio;
import Engine.Data.comparatori.CompareByNumeroPezzi;
import Engine.Data.comparatori.CompareByValoreTotali;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaPartitaSalvateController {
    @FXML
    ListView<String> listViewPartiteSalvate;

    static ArrayList<Salvataggio> listSalvataggi = new ArrayList<>();

    public void init(){
        caricaPartiteFiltrate();
        renderListaPartitaSalvate();
    }

    public static void caricaPartiteFiltrate(){
        ArrayList<File> fileList = new ArrayList<>();
        File directory = new File(System.getProperty("user.dir") + File.separator + "saved_games");

        if(directory.exists() && directory.isDirectory()) {
            fileList.addAll(List.of(directory.listFiles()));
        }else{
            //eccezione
        }

        if(!fileList.isEmpty()){
            for(File file: fileList){
                try(FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis)){
                    Salvataggio salvataggioRecuperato = (Salvataggio) ois.readObject();
                    salvataggioRecuperato.setNomeFile(file.getName());
                    listSalvataggi.add(salvataggioRecuperato);
                }catch(ClassNotFoundException | IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void renderListaPartitaSalvate(){
        ObservableList<String> elementi = FXCollections.observableArrayList();
        for(Salvataggio save : listSalvataggi){
            elementi.add(save.getNomeFile());
        }
        listViewPartiteSalvate.setItems(elementi);
        listViewPartiteSalvate.setOnMouseClicked(event ->{
            String selectedItem = listViewPartiteSalvate.getSelectionModel().getSelectedItem();
            if(selectedItem == null) return;
            File file  = new File(System.getProperty("user.dir") + File.separator + "saved_games" + File.separator + selectedItem);
            Salvataggio.caricaPartita(file, event);
        } );
    }

    /**
     * Questo metodo restituisce la lista delle partite salvate in ordine rispetto il numero dei pezzi.
     * Tale metodo viene richiamato dal click sul bottone "Numero pezzi".
     * @param actionEvent
     */
    public void OrdinaNumeroPezzi(ActionEvent actionEvent) {
        listSalvataggi.sort(new CompareByNumeroPezzi());
        renderListaPartitaSalvate();
    }

    /**
     * Questo metodo restituisce la lista delle partite salvate in ordine rispetto al valore dei pezzi.
     * Tale metodo viene richiamato dal click sul botton "Valore complessivo dei pezzi".
     * @param actionEvent
     */
    public void OrdinaValorePezzi(ActionEvent actionEvent) {
        listSalvataggi.sort(new CompareByValoreTotali());
        renderListaPartitaSalvate();
    }

    /**
     * Questo metodo restituisce la lista delle partite salvate in ordine rispetto al numero di mosse eseguite dai giocatori.
     * Tale metodo viene richiamato dla click sul botton "Numero di mosse".
     * @param actionEvent
     */
    public void OrdinaNumeroMosse(ActionEvent actionEvent) {
        listSalvataggi.sort(new CompareByNumeroPezzi());
        renderListaPartitaSalvate();
    }

    public void returnStart(ActionEvent actionEvent) {
    }

}