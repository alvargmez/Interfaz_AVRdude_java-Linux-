package interfaz;

import GUI.conf_pantalla;
import GUI.frame;

import javax.swing.*;

public class interfaz {

    static conf_pantalla pantalla = new conf_pantalla();

    public static void main(String[] args) {

        frame f = new frame();

        f.setVisible(true);
        f.setTitle("AVRdude");
        f.setBounds(10,10, pantalla.ancho()/2, pantalla.alto()/2);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
