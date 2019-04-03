package es.studium.ejerciciofk;

import java.util.Calendar;

public class Modelo {
	public String Elegir(String elegido){
		String[] cosasElegidas = elegido.split(" - ");
		String numeroElegido = cosasElegidas[0];
		return numeroElegido;
	}
	public String actualFecha() {
		Calendar c1 = Calendar.getInstance(); 
		int dia = c1.get(Calendar.DATE);
		int mes = c1.get(Calendar.MONTH)+1;
		int anyo = c1.get(Calendar.YEAR);
		String fecha = dia+"/"+mes+"/"+anyo;
		return fecha;
	}
	public String americanizacionFecha(String fechaOrigen) {
		String[] fecha = fechaOrigen.split("/");
		String fechaAmericana =fecha[1]+"-"+fecha[0]+"-"+fecha[2];
		return fechaAmericana;
	}

}
