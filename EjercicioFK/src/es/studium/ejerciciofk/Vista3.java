package es.studium.ejerciciofk;

import java.awt.Button;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Frame;

public class Vista3 extends Frame{

	private static final long serialVersionUID = 1L;
	
	Label tituloFactura = new Label ("Factura");
	Label numeroFactura = new Label ();
	
	Label articuloDetalleL = new Label("Articulos:");
	TextField articuloDetalleT = new TextField(9);
	Label cantidadDetalleL = new Label("Cantidad:");
	TextField cantidadDetalleT = new TextField (3);
	Button agregarDetalle = new Button("Agregar");
	
	TextArea tablaDatos = new TextArea();
	Label totalDatos = new Label("TOTAL:");
	TextField totalDatoT = new TextField(9);
	Button aceptarDatos = new Button("Aceptar");
	Button cancelarDatos = new Button("Cancelar");
	
	Vista3()
	{
		
	}
}
