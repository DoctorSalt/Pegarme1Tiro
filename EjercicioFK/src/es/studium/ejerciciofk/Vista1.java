package es.studium.ejerciciofk;

import java.awt.Button;
import java.awt.Frame;

public class Vista1 extends Frame{
	
	private static final long serialVersionUID = 1L;
	Button altaMenu = new Button("Alta Factura");
	Vista1(){
		setTitle("Menu Principal");
		setSize(400,200);
		setLocationRelativeTo(null);
		add(altaMenu);
	}
}
