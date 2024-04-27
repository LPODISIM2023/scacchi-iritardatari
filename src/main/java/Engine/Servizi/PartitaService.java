package Engine.Servizi;

import Engine.Data.Salvataggio;
import Engine.Giocatore.Bot;
import Engine.Giocatore.Giocatore;
import Engine.Giocatore.Umano;
import GUI.ScacchieraController;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.Serializable;

public class PartitaService implements Serializable {


    private static Giocatore g1;
    private static Giocatore g2;
    private static boolean isBot;

    public Giocatore g1NonStatico;
    public Giocatore g2NonStatico;

    public ScacchieraService scacchieraServiceNonStatica;
    private static ScacchieraController scacchieraController;
    private static ScacchieraService scacchieraService;
    private static boolean partitaInCorso = false;
    private static boolean turnoGiocatore = true;
    private static boolean giocatoreSottoScacco = false;
    private static int numeroMosseTotali = 1;
    static PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

    public static Giocatore getG1() {
        return g1;
    }

    public static void setG1(Giocatore g1) {
        PartitaService.g1 = g1;
    }

    public static Giocatore getG2() {
        return g2;
    }

    public static void setG2(Giocatore g2) {
        PartitaService.g2 = g2;
    }

    public static int getNumeroMosseTotali() {
        return numeroMosseTotali;
    }

    public static void setNumeroMosseTotali(int numeroMosseTotali) {
        PartitaService.numeroMosseTotali = numeroMosseTotali;
    }

    public PartitaService(Salvataggio salvataggio, ScacchieraController scacchieraController){

        PartitaService.isBot = salvataggio.isG2IsBot();

        //inializza i giocatori
        g1 = salvataggio.getG1();
        g2 = salvataggio.getG2();

        //crea scacchiera service

        scacchieraService = new ScacchieraService(g1.getPezziGiocatore(),g2.getPezziGiocatore());
        PartitaService.scacchieraController = scacchieraController;
        partitaInCorso = true;
    }


    /**
     * Metodo costruttore che viene utilizzato per creare un'istanza della partita, quindi per iniziare una partita dati i due nomi dei giocatori.
     * Inoltre si necessita il controllo sul tipo di giocatore ovvero che sia il bot o no.
     *
     * @param nome1
     * @param nome2
     * @param isBot
     * @param scacchieraController
     */
    public PartitaService(String nome1, String nome2, boolean isBot, ScacchieraController scacchieraController) {
        PartitaService.isBot = isBot;
        //inizializzazione Giocatori
        g1 = new Umano(nome1, true);
        if (isBot) {
            g2 = new Bot();
        } else {
            g2 = new Umano(nome2, false);
        }
        //creazione Scacchiera Service
        scacchieraService = new ScacchieraService(g1.getPezziGiocatore(), g2.getPezziGiocatore());
        this.scacchieraController = scacchieraController;
        partitaInCorso = true;
    }

    public PartitaService(Giocatore giocatore1, Giocatore giocatore2){
        this.g1NonStatico = giocatore1;
        this.g2NonStatico = giocatore2;
    }
    /**
     * Il metodo cambioTurno() è utile per cambiare il turno da un giocatore all'altro una volta effettuata una mossa.
     * Controlla inoltre se il re è sotto scacco per stabile se continuare o no la partita
     */
    public static void cambioTurno() {
        //Cambio turno del giocatore
        turnoGiocatore = !turnoGiocatore;

        giocatoreSottoScacco = Mossa.reSottoScacco();

        if ((g2 instanceof Bot) && g2.getColore() == getColoreTurnoGiocatore()) {
            pauseTransition.setOnFinished(event -> {
                ((Bot) g2).mossaRandom();
            });
            pauseTransition.play();
        }
        numeroMosseTotali++;

    }

    public static void controlloScaccoMatto() {
        if (Mossa.reSottoScacco() && Mossa.isScaccoMatto()) {
            try {
                scacchieraController.endGame();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Metodo utile per salvare la partita, quindi la scacchiera e i due giocatori che identificano quella partita
     *
     * @param sc
     * @param g1
     * @param g2
     * @return
     */
    public static PartitaService save(ScacchieraService sc, Giocatore g1, Giocatore g2) {
        return null;
    }

    public static Giocatore getGiocatore1() {
        return g1;
    }

    public static Giocatore getGiocatore2() {
        return g2;
    }

    public static ScacchieraService getScacchieraService() {
        return scacchieraService;
    }

    public static boolean getColoreTurnoGiocatore() {
        return turnoGiocatore;
    }

    public static boolean isPartitaInCorso() {
        return partitaInCorso;
    }

    public static boolean isGiocatoreSottoScacco() {
        return giocatoreSottoScacco;
    }

    public static boolean getIsBot() {
        return isBot;
    }

    public static ScacchieraController getScacchieraController() {
        return scacchieraController;
    }

    public static void setScacchieraController(ScacchieraController scacchieraController) {
        PartitaService.scacchieraController = scacchieraController;
    }

    public static boolean isIsBot() {
        return isBot;
    }

    public static void setIsBot(boolean isBot) {
        PartitaService.isBot = isBot;
    }

    /*
    public void endGame() throws InterruptedException {  //@TODO implementare eccezione per i file da sovrascrivere.

        Alert endGGame = new Alert(Alert.AlertType.NONE, "S");

        if (!PartitaService.getColoreTurnoGiocatore())  {
            endGGame.setContentText("Ha Vinto il Nero");
            endGGame.setTitle("Partita Conclusa");
        }
        else {
            endGGame.setContentText("Ha Vinto il Bianco");
            endGGame.setTitle("Partita Conclusa");
        }


        ButtonType nuovaPartita = new ButtonType("Nuova Partita");
        ButtonType esci = new ButtonType("Esci");

        endGGame.getButtonTypes().setAll(nuovaPartita, esci);

        endGGame.showAndWait().ifPresent(scelta -> {
            if (scelta == nuovaPartita) {
                try {
                     scacchieraController.initGame(g1.getNome(), g2.getNome(), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if (scelta == esci) {
                System.exit(0);
            }
        });

    }
     */

    @Override
    public String toString() {
        return "PartitaService{}";
    }
}
