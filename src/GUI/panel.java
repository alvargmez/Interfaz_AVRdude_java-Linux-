package GUI;

import control_avrdudes.control_avrdudes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class panel extends JPanel {

    private JTextPane area_texto = new JTextPane();
    private JScrollPane lamina_texto = new JScrollPane(area_texto);

    private String hex = "";

    private JPanel panel_b = new JPanel();

    public panel(){

        setLayout(new BorderLayout());

        boton("Archivo .hex", "selection_archivo");
        boton("Cargar .hex", "cargar");

        add(panel_b, BorderLayout.NORTH);
        add(lamina_texto, BorderLayout.CENTER);

    }

//---------------------------Crea botones---------------------------------------------------------------------------------

    private void boton (String nombre, String action){

        JButton b = new JButton(nombre);

        panel_b.add(b);

        if(action == "selection_archivo") b.addActionListener(new archivo());

        if(action == "cargar") b.addActionListener(new cargar());

    }

//-------------------------------Selección de archivo .hex------------------------------------------------------------------

    private class archivo implements ActionListener {

        private final JFileChooser fc = new JFileChooser();

        public void actionPerformed(ActionEvent e) {

            fc.setDialogTitle("Selecciona .hex");

            int	option_fc = fc.showOpenDialog(panel_b);

            if(option_fc == JFileChooser.APPROVE_OPTION) {

                File archivo = fc.getSelectedFile();

                hex = archivo.getAbsolutePath();

                System.out.println(hex);

            }else if(option_fc == JFileChooser.CANCEL_OPTION) System.out.println("Se canceló la selección del .hex");

        }
    }

    private class cargar implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            control_avrdudes c = new control_avrdudes();

            area_texto.setText(c.cargar(hex)) ;

        }
    }
}


