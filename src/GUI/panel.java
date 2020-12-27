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
    private String g_hex = "";
    private String mc = "m16";
    private String pg = "usbasp";
    private String action = "";

    private JLabel mc_info = new JLabel(mc);
    private JLabel pg_info = new JLabel(pg);
    private JLabel hex_info = new JLabel(hex);
    private JLabel separador_1 = new JLabel("||");
    private JLabel separador_2 = new JLabel("||");
    private JPanel lamina_info = new JPanel();

    private JMenuBar barra = new JMenuBar();
    private JMenu build = new JMenu("Build");
    private JMenu list = new JMenu("Lista");
    private JMenu fuses = new JMenu("Fuses");

    private JToolBar barra_herr = new JToolBar();

    private JComboBox desplegable_mc = new JComboBox();
    private JComboBox desplegable_pg = new JComboBox();

    public panel(){

        setLayout(new BorderLayout());

        barra.setLayout(new FlowLayout());
        barra.setBackground(Color.LIGHT_GRAY);

        area_texto.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
        area_texto.setBackground(Color.BLACK);
        area_texto.setForeground(Color.GREEN);

        boton("Archivo .hex", "selection_archivo", new ImageIcon(panel.class.getResource("Iconos/carpeta.png")));
        boton("Guardar .hex", "g_archivo", new ImageIcon(panel.class.getResource("Iconos/guardar.png")));
        boton("Prueba conexión", "conexion", new ImageIcon(panel.class.getResource("Iconos/red.png")));
        boton("Write .hex", "Build", null);
        boton("Read .hex", "Build", null);
        boton("Read fuses", "Fuses", null);
        boton("Lista mc", "Lista", null);
        boton("Lista programadores", "Lista", null);
        boton(null, "mc", null);
        boton(null, "pg", null);

//----------------------------------------Construir panel de Información-------------------------------------------------

        lamina_info.setBackground(Color.LIGHT_GRAY);

        lamina_info.add(hex_info);
        lamina_info.add(separador_1);
        lamina_info.add(mc_info);
        lamina_info.add(separador_2);
        lamina_info.add(pg_info);

//---------------------------------------Añadir laminas a lamina principal-----------------------------------------------

        add(barra, BorderLayout.NORTH);
        add(barra_herr, BorderLayout.WEST);
        add(lamina_texto, BorderLayout.CENTER);
        add(lamina_info, BorderLayout.SOUTH);

    }

//---------------------------Crea botones---------------------------------------------------------------------------------

    private void boton (String nombre, String action, ImageIcon i){

        if(action == "g_archivo"){

            JButton item = new JButton("", i);

            //m.add(item);
            barra_herr.add(item);

            item.addActionListener(new g_archivo());

        }

        if(action == "selection_archivo"){

            JButton item = new JButton("", i);

            barra_herr.setOrientation(1);
            barra_herr.setBackground(Color.LIGHT_GRAY);

            //m.add(item);
            barra_herr.add(item);

            item.addActionListener(new archivo());

        }

        if(action == "conexion"){

            JButton item = new JButton("", i);

            //m.add(item);
            barra_herr.add(item);

            item.addActionListener(new cargar());

        }

        if(action == "Lista"){

            JMenuItem item = new JMenuItem(nombre);

            list.add(item);

            barra.add(list);

            item.addActionListener(new cargar());

        }

        if(action == "Build"){

            JMenuItem item = new JMenuItem(nombre);

            build.add(item);

            barra.add(build);

            item.addActionListener(new cargar());

        }

        if(action == "Fuses"){

            JMenuItem item = new JMenuItem(nombre);

            fuses.add(item);

            barra.add(fuses);

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
                desplegable_mc.addItem("m16");
                barra.add(desplegable_mc);

                desplegable_mc.addActionListener(new mc());

            }else {

                desplegable_pg.setEditable(true);
                desplegable_pg.addItem("usbasp");
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

            int	option_fc = fc.showOpenDialog(lamina_texto);

            if(option_fc == JFileChooser.APPROVE_OPTION) {

                File archivo = fc.getSelectedFile();

                hex = archivo.getAbsolutePath();

                hex_info.setText(archivo.getName());

                //System.out.println(hex);

            }else if(option_fc == JFileChooser.CANCEL_OPTION) hex ="";//System.out.println("Se canceló la selección del .hex");

        }
    }

//--------------------------Guardar .hex------------------------------------------------------------------------------------

    private class g_archivo implements ActionListener {

        private final JFileChooser fc = new JFileChooser();

        public void actionPerformed(ActionEvent e) {

            fc.setDialogTitle("Gurardar .hex");

            int	option_fc = fc.showOpenDialog(lamina_texto);

            if(option_fc == JFileChooser.APPROVE_OPTION) {

                control_avrdudes c = new control_avrdudes();
                File archivo = fc.getSelectedFile();

                String s = "Guardando flash en " + archivo.getAbsolutePath();

                g_hex = archivo.getAbsolutePath();

            }else if(option_fc == JFileChooser.CANCEL_OPTION) hex ="";//System.out.println("Se canceló la selección del .hex");

        }
    }

    private class cargar implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            control_avrdudes c = new control_avrdudes();

            String s = "";

            action = e.getActionCommand();

            if(action == "") action = "Prueba conexión";

            if(action == "Lista mc") s = "Lista de Microcontroladores:";
            else if(action == "Lista programadores") s = "Lista de Programadores:";
            else if(action == "Read .hex") s = "Guardando flash en " + g_hex;

            area_texto.setText(s + c.cargar(hex, g_hex, mc, pg, action)) ;

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


