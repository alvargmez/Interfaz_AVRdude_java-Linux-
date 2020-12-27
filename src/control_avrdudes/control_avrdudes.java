package control_avrdudes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class control_avrdudes {

    public String cargar(String hex, String mc, String pg, String action){

        Runtime rt = Runtime.getRuntime();

        String s = "";
        String exit="";
        String t = "";

        if(action == "Write .hex") s = "avrdude -c " + pg +" -P usb -p " + mc + " -U flash:w:" + hex + ":i";
        else if(action == "Prueba conexión") s = "avrdude -p " + mc + " -c " + pg;
        else if(action == "Lista mc") s =  "avrdude -p ?";
        else if (action == "Lista programadores") s = "avrdude -c ?";

        try {

            Process p = rt.exec(s);

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line;

            while((line = reader.readLine()) != null){

                    exit = exit + "\n" + line;

            }

            p.destroy();

        } catch (IOException e) {

            e.printStackTrace();


        }finally{

            if(action == "Prueba conexión") t = "Prueba de conexionado:";
            else if(action == "Cargar .hex") t = "Cargar .hex:";

            return t + exit;

        }

    }

}
