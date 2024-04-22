package Engine.Data;

import Engine.Servizi.PartitaService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Salvataggio{


    public static void salvaPartita(PartitaService ps) throws IOException {
        try{
        FileOutputStream fis = new FileOutputStream("salvataggio.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fis);
            oos.writeObject(ps);
            fis.close();
            oos.close();
    }catch(Exception e){
            e.printStackTrace();
        }
    }


}
