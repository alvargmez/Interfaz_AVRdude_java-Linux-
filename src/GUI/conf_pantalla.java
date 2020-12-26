package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;

public class conf_pantalla {

	private int ancho;
	private int alto;
	
	public conf_pantalla() {
		
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension size_pantalla = pantalla.getScreenSize();
		alto = size_pantalla.height;
		ancho = size_pantalla.width;
		
	}
	
	public int ancho() {
		
		return ancho;
	
	}
	
	public int alto() {
		
		return alto;
		
	}
}
