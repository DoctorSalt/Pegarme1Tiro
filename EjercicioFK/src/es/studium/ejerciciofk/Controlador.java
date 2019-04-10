package es.studium.ejerciciofk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controlador implements WindowListener, ActionListener{

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejemplofk?autoReconnect=true&useSSL=false";
	String login = "root";
	String password = "Studium2018;";
	String sentencia = "";
	Connection connection = null;
	java.sql.Statement statement = null;
	ResultSet rs = null;

	//valores estaticos que utilizo porque utilizo try y catch y el java da la brasa con los return
	String facturaMax="";
	String elegido;
	String numeroElegido;
	String nombreArticuloElegido;
	String fechaTactica;
	String fechaNormal;
	int dinerosElegidos=0;
	int cantidadArticuloElegida=0;
	String datosDeArticulos="";
	//datos que almaceno para luego imprimirlo como una nueva linea pasandolo todo a un string
	String[] datosAlmacenados=new String[5];
	//un booleano que comprueba que al buscar el dato no te lo devuelva vacío
	boolean error1=false;
	//variables como total y subtotal utilizadas en Vista3
	int subtotal=0;
	int total=0;

	Vista1 v1 = new Vista1();
	Vista2 v2 = new Vista2();
	Vista3 v3 = new Vista3();
	Modelo m = new Modelo();

	Controlador(){
		v1.addWindowListener(this);
		v1.altaMenu.addActionListener(this);
		v1.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(v1.altaMenu.equals(arg0.getSource())) {
			v2.addWindowListener(this);
			v2.siguienteAlta.addActionListener(this);			
			v2.cancelarAlta.addActionListener(this);
			v2.dxIncorrecto.addActionListener(this);
			v1.setVisible(false);
			MeterDatos();
			v2.fechaFacturaT.setText(m.actualFecha());
			v2.setVisible(true);
		} else
		if(v2.siguienteAlta.equals(arg0.getSource())) {
			elegido = v2.clienteC.getSelectedItem();
			numeroElegido = m.Elegir(elegido);
			fechaNormal = fechaTactica;
			fechaTactica = v2.fechaFacturaT.getText();
			fechaTactica = m.americanizacionFecha(fechaTactica);
			InsertarDatos();
			v2.setVisible(false);
			Continuar();
		} else if(v2.cancelarAlta.equals(arg0.getSource())) {
			v2.setVisible(false);
		}  else if(v2.dxIncorrecto.equals(arg0.getSource())) {
			v2.incorrecto.setVisible(false);
			v2.setVisible(true);
		} else if(v3.agregarDetalle.equals(arg0.getSource())) {
			AgregameEsta();
		}else if(v3.aceptarDatos.equals(arg0.getSource())) {
			v3.setVisible(false);
		}else if(v3.cancelarDatos.equals(arg0.getSource())) {
			eliminaEsa();
			v3.setVisible(false);
			v2.setVisible(true);
		}

	}

	private void Continuar() {
		CogerNumeroFacturaReciente();
		if(error1==true) {
			System.out.println("error1");
		}else if(error1==false){
			v3.numeroFactura.setText(facturaMax);
			v3.addWindowListener(this);
			MeteEseArticulo();
			v3.agregarDetalle.addActionListener(this);
			v3.aceptarDatos.addActionListener(this);
			v3.cancelarDatos.addActionListener(this);
			v3.setVisible(true);
		}		
	}

	private void eliminaEsa() {
		sentencia="DELETE FROM ejemplofk.lineasfactura where idFacturaFK="+facturaMax+";";
		conectar();
		ejecutar2();
		sentencia="delete from ejemplofk.facturas where idFactura="+facturaMax+";";
		ejecutar2();
		desconectar();
	}

	private void InsertarLineaFactura(String articuloElegido, int cantidadElegida) {
		sentencia="INSERT INTO ejemplofk.lineasfactura VALUES(null,"+facturaMax+","+articuloElegido+","+cantidadElegida+");";
		conectar();
		ejecutar2();
		desconectar();
	}

	private void ejecutar2() {
		try {
		statement = connection.createStatement();
		statement.executeUpdate(sentencia);	
		}catch(SQLException error)
		{
			System.out.println("SQl te dio el siguiente error:");
			System.out.println(error);
		}		
	}

	private void AgregameEsta() {
		String articuloElegido = m.Elegir(v3.articuloDetalleC.getSelectedItem());
		RecogeElDinero(articuloElegido);
		int cantidadArticuloElegida = Integer.parseInt(v3.cantidadDetalleT.getText());
		datosAlmacenados[0]=articuloElegido;
		AlmacenaNombre(articuloElegido);
		datosAlmacenados[1]=nombreArticuloElegido;
		datosAlmacenados[3]=dinerosElegidos+"";
		datosAlmacenados[2]=""+cantidadArticuloElegida;
		for(int i=0;i<cantidadArticuloElegida;i++) {
			subtotal=subtotal+dinerosElegidos;
			total=total+dinerosElegidos;
		}
		datosAlmacenados[4]=subtotal+"";
		subtotal=0;
		InsertarLineaFactura(articuloElegido, cantidadArticuloElegida);
		MeteTodoArticulos();
		v3.tablaDatos.append("\n"+datosDeArticulos);
		v3.totalDatoT.setText(""+total);
	}

	private void AlmacenaNombre(String articuloElegido) {
		sentencia="Select descripcionArticulo as 'nombre' from ejemplofk.articulos where idArticulo=+'"+articuloElegido+"';";
		try {
			conectar();
			ejecutar();
			rs.next();
			nombreArticuloElegido=rs.getString("nombre");
		}catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
		}

		finally
		{
			desconectar();
		}			

	}

	private void ejecutar() {
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
		}
		catch(SQLException sqle) {
			System.out.println("Error al ejecutar:");
			System.out.println(sqle);
		}
	}

	private void MeteTodoArticulos() {
		datosDeArticulos=""+datosAlmacenados[0]+"  "+datosAlmacenados[1]+"		"+datosAlmacenados[2]+"  "+datosAlmacenados[3]+"    "+datosAlmacenados[4];
	}

	private void RecogeElDinero(String articuloElegido) {
		sentencia="Select precioArticulo as 'money' from ejemplofk.articulos where idArticulo"
				+ "='"+articuloElegido+"';";
		try {
			conectar();
			ejecutar();
			rs.next();
			dinerosElegidos=rs.getInt("money");
		}catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
		}

		finally
		{
			desconectar();
		}				
	}

	private void MeteEseArticulo() {
		sentencia="Select * from ejemplofk.articulos";
		int datosChoice;
		String nombreChoice;
		try
		{
			conectar();
			ejecutar();
			while (rs.next())
			{
				datosChoice =rs.getInt("idArticulo");
				nombreChoice = rs.getString("descripcionArticulo");
				v3.articuloDetalleC.addItem(datosChoice+" - "+nombreChoice);
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
			v2.incorrecto();	
		}
		finally
		{
			desconectar();
		}			
	}

	private void CogerNumeroFacturaReciente() {
		sentencia="SELECT MAX(idFactura) as 'maximo' from ejemplofk.facturas";
		try
		{
			conectar();
			ejecutar();
			rs.next();
			facturaMax=rs.getInt("maximo")+"";
			desconectar();
		}catch(SQLException error){
			v2.incorrecto();
			System.out.println("SQl te dio el siguiente error:");
			System.out.println(error);
			if(facturaMax=="") {
				error1 = true;
			}
		}
	}

	private void InsertarDatos() {
		sentencia="INSERT INTO ejemplofk.facturas VALUES(null,"+fechaTactica+","+numeroElegido+");";
		conectar();
		ejecutar2();
		desconectar();
	}

	private void MeterDatos() {
		sentencia="Select * from ejemplofk.clientes";
		int datosChoice;
		String nombreChoice;
		String apellidosChoice;
		try
		{
			conectar();
			ejecutar();
			while (rs.next())
			{
				datosChoice =rs.getInt("idCliente");
				nombreChoice = rs.getString("nombreCliente");
				apellidosChoice = rs.getString("apellidosCliente");
				v2.clienteC.addItem(datosChoice+" - "+nombreChoice+" - "+apellidosChoice);
			}
		}
		catch (SQLException sqle)
		{
			v2.incorrecto();
			System.out.println("Error 2: "+sqle.getMessage());
			System.out.println("Error B");
		}

		finally
		{
			desconectar();
		}				
	}

	private void desconectar() {
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			v2.incorrecto();
			System.out.println("Error 3: "+e.getMessage());
		}	
	}

	private void conectar() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);	
		}catch(ClassNotFoundException cnfe) {
			v2.incorrecto();
			System.out.println("Error 1: "+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			v2.incorrecto();
			System.out.println("Error 2: "+sqle.getMessage());
			System.out.println("Error C");
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(v1.isActive()) {
			v1.setVisible(false);
		}
		if(v2.isActive()) {
			v2.setVisible(false);
		}
		if(v3.isActive()) {
			v3.setVisible(false);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
