package es.studium.ejerciciofk;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Frame;
import java.awt.GridLayout;

public class Vista3 extends Frame{
	private static final long serialVersionUID = 1L;

	Label tituloFactura = new Label ("Factura Nº");
	Label numeroFactura = new Label ();

	Label articuloDetalleL = new Label("Articulos:");
	Choice articuloDetalleC = new Choice();
	Label cantidadDetalleL = new Label("Cantidad:");
	TextField cantidadDetalleT = new TextField (3);
	Button agregarDetalle = new Button("Agregar");

	TextArea tablaDatos = new TextArea(5,50);
	Label totalDatos = new Label("TOTAL:");
	TextField totalDatoT = new TextField(9);
	Button aceptarDatos = new Button("Aceptar");
	Button cancelarDatos = new Button("Cancelar");

	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Panel panel3 = new Panel();
	Panel panel4 = new Panel();
	Panel panel5 = new Panel();

	Panel panel21 = new Panel();
	Panel panel22 = new Panel();

	Vista3()
	{
		setTitle("Alta Factura");
		setSize(400,500);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(5,1));
		panel1.add(tituloFactura);
		panel1.add(numeroFactura);
		add(panel1);
		panel2.setLayout(new GridLayout(2,1));
		panel21.add(articuloDetalleL);
		panel21.add(articuloDetalleC);
		panel2.add(panel21);
		panel22.add(cantidadDetalleL);
		panel22.add(cantidadDetalleT);
		panel22.add(agregarDetalle);
		panel2.add(panel22);
		add(panel2);
		tablaDatos.setEditable(false);
		tablaDatos.setText("idArticulo  "+"Descripcion		"+"Cantidad  "+"Precio    "+"Subtotal");
		panel3.add(tablaDatos);
		add(panel3);
		totalDatoT.setEditable(false);
		panel4.add(totalDatos);
		panel4.add(totalDatoT);
		add(panel4);
		panel5.add(aceptarDatos);
		panel5.add(cancelarDatos);
		add(panel5);
	}
}
