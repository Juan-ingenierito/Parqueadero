import java.util.Date;

public class Espacio {
	private boolean estado;
	private Vehiculo vehiculo;

	public Espacio(String Tipo, String Nombre, String Placa, Date Ingreso) {
		this.estado = true;
		this.vehiculo = new Vehiculo(Tipo, Nombre, Placa, Ingreso);
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String Tipo, String Nombre, String Placa, Date Ingreso) {
		Vehiculo vehiculo = new Vehiculo(Tipo, Nombre, Placa, Ingreso);
		this.vehiculo = vehiculo;
	}

}
