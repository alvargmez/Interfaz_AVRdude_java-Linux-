package control_avrdudes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class control_avrdudes {

    public String cargar(String hex, String mc, String action){

        Runtime rt = Runtime.getRuntime();

        String s = "";

        if(action == "Cargar .hex") s = "avrdude -c usbasp -P usb -p " + mc + " -U flash:w:" + hex + ":i";
        else if(action == "Prueba conexión") s = "avrdude -p " + mc + " -c usbasp";
        else if(action == "Lista mc") s =  "avrdude -c usbasp -p Microcontrolador";

        try {

            Process p = rt.exec(s);

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line;

            String exit="";

            while((line = reader.readLine()) != null){

                    exit = exit + "\n" + line;

            }

            p.destroy();

            return exit;

        } catch (IOException e) {

            e.printStackTrace();

            return "";

        }

    }

}
