package es.studium.ejerciciofk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controlador implements WindowListener, ActionListener{
	
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejemplofk?autoReconnect=true&useSSL=false";
	String login = "root";
	String password = "Studium2018;";
	String sentencia = "";
	Connection connection = null;
	java.sql.Statement statement = null;
	ResultSet rs = null;
	
	String elegido;
	String numeroElegido;
	String fechaTactica;
	String fechaNormal;
	
	Vista1 v1 = new Vista1();
	Vista2 v2 = new Vista2();
	Vista3 v3 = new Vista3();
	Modelo m = new Modelo();
	
	Controlador(){
		v1.addWindowListener(this);
		v1.altaMenu.addActionListener(this);
		v1.setVisible(true);
		v3.addWindowListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(v1.altaMenu.equals(arg0.getSource())) {
			v2.addWindowListener(this);
			v2.siguienteAlta.addActionListener(this);
			v2.cancelarAlta.addActionListener(this);
			v2.okCorrecto.addActionListener(this);
			v2.dxIncorrecto.addActionListener(this);
			v1.setVisible(false);
			MeterDatos();
			v2.fechaFacturaT.setText(m.actualFecha());
			v2.setVisible(true);
		} else if(v2.siguienteAlta.equals(arg0.getSource())) {
			elegido = v2.clienteC.getSelectedItem();
			numeroElegido = m.Elegir(elegido);
			fechaNormal = fechaTactica;
			fechaTactica = v2.fechaFacturaT.getText();
			fechaTactica = m.americanizacionFecha(fechaTactica);
			InsertarDatos();
			v2.setVisible(false);
		} else if(v2.cancelarAlta.equals(arg0.getSource())) {
			v2.setVisible(false);
		} else if (v2.okCorrecto.equals(arg0.getSource())) {
			v2.correcto.setVisible(false);
			v3.setVisible(true);
			v3.addWindowListener(this);
		} else if(v2.dxIncorrecto.equals(arg0.getSource())) {
			v2.incorrecto.setVisible(false);
			v2.setVisible(true);
		}
		
	}

	private void InsertarDatos() {
		conectar();
		sentencia="INSERT INTO ejemplofk.facturas VALUES(null,"+fechaTactica+","+numeroElegido+");";
		try {
		statement = connection.createStatement();
		statement.executeUpdate(sentencia);
		v2.correcto();
		} catch(SQLException error)
		{
			v2.incorrecto();
			System.out.println("SQl te dio el siguiente error:");
			System.out.println(error);
		}		
	}

	private void MeterDatos() {
		sentencia="Select * from ejemplofk.clientes";
		int datosChoice;
		String nombreChoice;
		String apellidosChoice;
		try
		{
			conectar();
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
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
		//Cargar los controladores para el acceso a la BD
		Class.forName(driver);
		//Establecer la conexión con la BD Empresa
		connection = DriverManager.getConnection(url, login, password);	
		}catch(ClassNotFoundException cnfe) {
			v2.incorrecto();
			System.out.println("Error 1: "+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			v2.incorrecto();
			System.out.println("Error 2: "+sqle.getMessage());
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
