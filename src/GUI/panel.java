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
    private String mc = "";
    private String action = "";

    private JMenuBar barra = new JMenuBar();
    private JMenu option = new JMenu("Opciónes");
    private JMenu m = new JMenu("Cargar .hex");

    private JComboBox desplegable = new JComboBox();

    public panel(){

        setLayout(new BorderLayout());

        barra.setLayout(new FlowLayout());

        area_texto.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
        area_texto.setBackground(Color.BLACK);
        area_texto.setForeground(Color.GREEN);

        boton("Archivo .hex", "selection_archivo");
        boton("Cargar .hex", "option");
        boton("Prueba conexión", "option");
        boton("Lista mc", "option");
        boton("Lista programadores", "option");
        boton(null, "mc");

        add(barra, BorderLayout.NORTH);
        add(lamina_texto, BorderLayout.CENTER);

    }

//---------------------------Crea botones---------------------------------------------------------------------------------

    private void boton (String nombre, String action){

        if(action == "selection_archivo"){

            JMenuItem item = new JMenuItem(nombre);

            m.add(item);
            barra.add(m);

            item.addActionListener(new archivo());

        }

        if(action == "option"){

            JMenuItem item = new JMenuItem(nombre);

            option.add(item);

            barra.add(option);

            item.addActionListener(new cargar());

        }

        if(action == "mc"){

            JLabel mc = new JLabel("Microcontrolador:");

            barra.add(mc);

            desplegable.setEditable(true);
            desplegable.addItem("m16");
            barra.add(desplegable);

            desplegable.addActionListener(new mc());

        }

    }

//-------------------------------Selección de archivo .hex------------------------------------------------------------------

    private class archivo implements ActionListener {

        private final JFileChooser fc = new JFileChooser();

        public void actionPerformed(ActionEvent e) {

            fc.setDialogTitle("Selecciona .hex");

            int	option_fc = fc.showOpenDialog(barra);

            if(option_fc == JFileChooser.APPROVE_OPTION) {

                File archivo = fc.getSelectedFile();

                hex = archivo.getAbsolutePath();

                //System.out.println(hex);

            }else if(option_fc == JFileChooser.CANCEL_OPTION) hex ="";//System.out.println("Se canceló la selección del .hex");

        }
    }

    private class cargar implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            control_avrdudes c = new control_avrdudes();

            String s = "";

            action = e.getActionCommand();

            if(action == "Lista mc") s = "Lista de Microcontroladores:";
            if(action == "Lista programadores") s = "Lista de Programadores:";

            area_texto.setText(s + c.cargar(hex, mc, action)) ;

        }
    }

    private class mc implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            mc = (String)desplegable.getSelectedItem();

            System.out.println(mc);

        }
    }
}


