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
        f.setBounds(pantalla.ancho() + pantalla.ancho()/2, pantalla.alto()/4, pantalla.ancho()/2, pantalla.alto());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
