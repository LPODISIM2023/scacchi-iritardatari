package Engine.Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;


public class Logger {

    static int numMossa = 1;

    static JSONObject jsonMosse = new JSONObject();

    private static ArrayList<LogMossa> listaMosse = new ArrayList<>();

    public static ArrayList<LogMossa> getListaMosse() {
        return listaMosse;
    }
    public static void addMossaLog(int oldRiga, int oldColonna, int newRiga, int newColonna, String codPezzoMosso, String codPezzoMangiato){
        LogMossa lm = new LogMossa(oldRiga, oldColonna, newRiga, newColonna, codPezzoMangiato, codPezzoMosso, numMossa++);
        listaMosse.add(lm);
        System.out.println(listaMosse);
        writeLog();
    }

    public static void writeLog(){
        JSONArray jsonarray = new JSONArray();
        for(LogMossa lg: listaMosse){
            jsonarray.put(lg.toJson());
        }
        String stringJson = jsonarray.toString();
        try (FileWriter file = new FileWriter("log_mosse.json")) {
            file.write(stringJson);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonMosse);
    }
}
