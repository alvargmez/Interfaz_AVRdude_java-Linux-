package control_avrdudes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class control_avrdudes {

    public String cargar(String hex){

        Runtime rt = Runtime.getRuntime();

        try {

            Process p = rt.exec("avrdude" + " -p m16 -c usbasp ");

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line;

            String exit="";

            while((line = reader.readLine()) != null){

                System.out.println(line);

                exit = (exit + "\n" + line);

            }

            p.destroy();

            return exit;

        } catch (IOException e) {

            e.printStackTrace();

            return "";

        }

    }

}
