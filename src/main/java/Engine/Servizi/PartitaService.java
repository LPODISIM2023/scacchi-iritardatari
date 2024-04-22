package Engine.Servizi;

import Engine.Giocatore.Bot;
import Engine.Giocatore.Giocatore;
import Engine.Giocatore.Umano;

import javax.swing.text.StyledEditorKit;

public class PartitaService {

    private static Giocatore g1;
    private static Giocatore g2;
    private static boolean isBot;
    private static ScacchieraService scacchieraService;
    private static boolean partitaInCorso = false;
    private static boolean turnoGiocatore = true;
    private static boolean giocatoreSottoScacco = false;


    /**
     * Metodo che viene utilizzato per iniziare una partita dati i due nomi dei giocatori. Inoltre si necessita il controllo
     * sul tipo di giocatore ovvero che sia il bot o no.
     * @param nome1
     * @param nome2
     * @param isBot
     */
    public static void iniziaPartita(String nome1, String nome2, boolean isBot){
        //inizializzazione Giocatori
        g1 = new Umano(nome1, true);
        if(isBot){
            g2 = new Bot();
        }else{
            g2 = new Umano(nome2, false);
        }
        //creazione Scacchiera Service
        scacchieraService = new ScacchieraService(g1.getPezziGiocatore(), g2.getPezziGiocatore());

        partitaInCorso = true;
    }

    /**
     * Il metodo cambioTurno() è utile per cambiare il turno da un giocatore all'altro una volta effettuata una mossa.
     * Controlla inoltre se il re è sotto scacco per stabile se continuare o no la partita
     */
    public static void cambioTurno(){
        //Cambio turno del giocatore
        turnoGiocatore = !turnoGiocatore;

        // a nero turnG è false
        if(turnoGiocatore){
            giocatoreSottoScacco = Mossa.reSottoScacco(g2,g1);
        }
        //chiamo que
        else giocatoreSottoScacco = Mossa.reSottoScacco(g1,g2); //sul bianco per il nero



        //controllo se il giocatore è sotto scacco
            //Se si controllo se è scacco
                //se non è scacco matto limito le mosse
                //altrimenti finisce la partita
        //se non può fare tutte le mosse
    }

    public static Giocatore getGiocatore1(){return g1;}
    public static Giocatore getGiocatore2(){return g2;}
    public static ScacchieraService getScacchieraService(){return scacchieraService;}
    public static boolean getColoreTurnoGiocatore(){return turnoGiocatore;}
    public static boolean isPartitaInCorso(){return partitaInCorso;}

    public static boolean isGiocatoreSottoScacco(){return giocatoreSottoScacco;}
    public static boolean getIsBot(){return isBot;}

}
