package control_avrdudes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class control_avrdudes {

    public String cargar(String hex, String g_hex, String mc, String pg, String action){

        Runtime rt = Runtime.getRuntime();

        String sf[] = {

                ("avrdude -c " + pg +" -P usb -p " + mc + " -U lfuse:r:" + "-" + ":h"),
                ("avrdude -c " + pg +" -P usb -p " + mc + " -U hfuse:r:" + "-" + ":h"),
                ("avrdude -c " + pg +" -P usb -p " + mc + " -U efuse:r:" + "-" + ":h"),

        };

        String s = "";
        String exit="";
        String t = "";

        if(action == "Write .hex") s = "avrdude -c " + pg +" -P usb -p " + mc + " -U flash:w:" + hex + ":i";
        else if (action == "Read .hex") s = "avrdude -c " + pg +" -P usb -p " + mc + " -U flash:r:" + g_hex + ":i";
        else if (action == "Verificar .hex") s = "avrdude -c " + pg +" -P usb -p " + mc + " -U flash:v:" + hex + ":i";
        else if(action == "Prueba conexión") s = "avrdude -p " + mc + " -c " + pg;
        else if(action == "Lista mc") s =  "avrdude -p ?";
        else if (action == "Lista programadores") s = "avrdude -c ?";

        try {

            if(action != "Read fuses"){

                Process p = rt.exec(s);

                BufferedReader reader_e = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                BufferedReader reader_i = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line;

                while((line = reader_e.readLine()) != null){

                    exit = exit + "\n" + line;

                }

                while((line = reader_i.readLine()) != null){

                    exit = exit + "\n" + line;

                }

                p.destroy();

            }else if (action == "Read fuses"){

                for(int i = 0; i<3; i++){

                    Process p = rt.exec(sf[i]);

                    BufferedReader reader_e = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    BufferedReader reader_i = new BufferedReader(new InputStreamReader(p.getInputStream()));

                    String line;

                    if( i == 0) exit = "Leyendo fuses:";

                    while((line = reader_e.readLine()) != null){

                        exit = exit + "\n" + line;

                    }

                    if(i == 0) exit = exit + "\n" + "Low fuse: ";
                    if(i == 1) exit = exit + "\n" + "High fuse: ";
                    if(i == 2) exit = exit + "\n" + "Extend fuse: ";

                    while((line = reader_i.readLine()) != null){

                        exit = exit + line;

                    }

                    p.destroy();

                }

            }

        } catch (IOException e) {

            e.printStackTrace();


        }finally{

            if(action == "Prueba conexión") t = "Prueba de conexionado:";
            else if(action == "Cargar .hex") t = "Cargar .hex:";

            return t + exit;

        }

    }

}
