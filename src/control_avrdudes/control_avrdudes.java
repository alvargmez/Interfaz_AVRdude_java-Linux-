package control_avrdudes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class control_avrdudes {

    public String cargar(String hex, String g_hex, String mc, String pg, String fuses[], String action){

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
        else if (action == "Write fuses"){

            if(fuses[0].charAt(0) == '0' && fuses[0].charAt(1) == 'b'){

                fuses[0] = "0x" + conversion(fuses[0]);

            }
            if(fuses[1].charAt(0) == '0' && fuses[1].charAt(1) == 'b'){

                fuses[1] = "0x" + conversion(fuses[1]);

            }
            if(fuses[2].charAt(0) == '0' && fuses[2].charAt(1) == 'b'){

                fuses[2] = "0x" + conversion(fuses[2]);

            }

            s = "avrdude -c " + pg +" -P usb -p " + mc + " -U lfuse:w:" + fuses[0] + ":m" + " -U hfuse:w:" + fuses[1] + ":m" + " -U efuse:w:" + fuses[2] + ":m";

        }
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

    private String conversion (String fuse){

        String fuse_intercambio="";

        for(int i=2; i< fuse.length(); i++) {

            fuse_intercambio = fuse_intercambio + fuse.charAt(i); //le quito el prefijo 0b para poder pasarlo a hexadecimal

        }

        int fuse_int = Integer.parseInt(fuse_intercambio);


        return decimalAHexadecimal(binarioADecimal(fuse_int));

    }

    private String decimalAHexadecimal(int decimal) {

        int residuo;
        String hexadecimal = "";

        char[] caracteresHexadecimales = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while (decimal > 0) {
            residuo = decimal % 16;
            char caracterHexadecimal = caracteresHexadecimales[residuo];
            hexadecimal = caracterHexadecimal + hexadecimal;
            decimal = decimal / 16;
        }
        return hexadecimal;
    }

    // Conversiones de otras bases a decimal
    private int binarioADecimal(int binario) {

        int decimal = 0;
        int potencia = 0;

        // Ciclo infinito hasta que binario sea 0
        while (true) {
            if (binario == 0) {
                break;
            } else {
                int temp = binario % 10;
                decimal += temp * Math.pow(2, potencia);
                binario = binario / 10;
                potencia++;
            }
        }
        return decimal;
    }

}
