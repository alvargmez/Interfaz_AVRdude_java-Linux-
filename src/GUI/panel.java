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
    private String pg = "";
    private String action = "";

    private JLabel mc_info = new JLabel(mc);
    private JLabel pg_info = new JLabel(pg);
    private JLabel hex_info = new JLabel(hex);
    private JLabel separador_1 = new JLabel("||");
    private JLabel separador_2 = new JLabel("||");
    private JPanel lamina_info = new JPanel();

    private JMenuBar barra = new JMenuBar();
    private JMenu option = new JMenu("Opciónes");
    private JMenu m = new JMenu("Cargar .hex");

    private JComboBox desplegable_mc = new JComboBox();
    private JComboBox desplegable_pg = new JComboBox();

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
        boton(null, "pg");

//----------------------------------------Construir panel de Información-------------------------------------------------

        lamina_info.add(hex_info);
        lamina_info.add(separador_1);
        lamina_info.add(mc_info);
        lamina_info.add(separador_2);
        lamina_info.add(pg_info);

//---------------------------------------Añadir laminas a lamina principal-----------------------------------------------

        add(barra, BorderLayout.NORTH);
        add(lamina_texto, BorderLayout.CENTER);
        add(lamina_info, BorderLayout.SOUTH);

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

        if(action == "mc" | action == "pg"){

            String title = "";
            if(action == "mc") title = "Mc:";
            else title = "Programador:";

            JLabel select = new JLabel(title);

            barra.add(select);

            if(action == "mc"){

                desplegable_mc.setEditable(true);
                barra.add(desplegable_mc);

                desplegable_mc.addActionListener(new mc());

            }else {

                desplegable_pg.setEditable(true);
                barra.add(desplegable_pg);

                desplegable_pg.addActionListener(new pg());

            }



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

                hex_info.setText(archivo.getName());

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
            else if(action == "Lista programadores") s = "Lista de Programadores:";

            area_texto.setText(s + c.cargar(hex, mc, pg, action)) ;

        }
    }

    private class mc implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            mc = (String)desplegable_mc.getSelectedItem();

            mc_info.setText(mc);

            System.out.println(mc);

        }
    }

    private class pg implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            pg = (String)desplegable_pg.getSelectedItem();

            pg_info.setText(pg);

            System.out.println(pg);

        }
    }
}


