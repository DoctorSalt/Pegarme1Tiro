package es.studium.ejerciciofk;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class Vista2 extends Frame{

	private static final long serialVersionUID = 1L;
	
	Label fechaFacturaL = new Label("Fecha Factura:");
	TextField fechaFacturaT = new TextField ("DD/MM/AAA");
	Label clienteL = new Label("Cliente:");
	Choice clienteC = new Choice();
	Button siguienteAlta = new Button ("Siguiente");
	Button cancelarAlta = new Button ("Cancelar");
	Panel panelA1 = new Panel();
	Panel panelA2 = new Panel();
	Panel panelA3 = new Panel();
	
	Dialog correcto = new Dialog(this, true);
	Label bienCorrecto = new Label("La metiste bien wey");
	Button okCorrecto = new Button("OK");
	
	Dialog incorrecto = new Dialog(this, true);
	Label malIncorrecto = new Label("Salio mal wey");
	Button dxIncorrecto = new Button("DX patras");
	
	Vista2(){
		setTitle("Alta Factura");
		setSize(300,300);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3,1));
		panelA1.add(fechaFacturaL);
		panelA1.add(fechaFacturaT);
		panelA2.add(clienteL);
		panelA2.add(clienteC);
		panelA3.add(siguienteAlta);
		panelA3.add(cancelarAlta);
		add(panelA1);
		add(panelA2);
		add(panelA3);
	}
	
	public void correcto() {
		correcto.setTitle("Correcto");
		correcto.setSize(300,200);
		correcto.setLocationRelativeTo(null);
		correcto.add(bienCorrecto);
		correcto.add(okCorrecto);
		correcto.setVisible(true);
	}
	public void incorrecto() {
		incorrecto.setTitle("Correcto");
		incorrecto.setSize(300,200);
		incorrecto.setLocationRelativeTo(null);
		incorrecto.add(malIncorrecto);
		incorrecto.add(dxIncorrecto);
		incorrecto.setVisible(true);
	}

}
