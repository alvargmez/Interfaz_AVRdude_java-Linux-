package GUI;

import javax.swing.*;
import java.awt.*;

public class frame extends JFrame {

    public frame(){

        frameInit();  //Para poder seleccionar el icono Pudoacerlo tambien en el frame instanciado en el main()

        setIconImage(new ImageIcon(getClass().getResource("Iconos/microcontrolador(64).png")).getImage()); //selecciono icono

        add(new panel());

    }

}
