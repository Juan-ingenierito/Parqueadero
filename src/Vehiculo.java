import java.util.Date;

public class Vehiculo {
	private String tipo;
	private String nombre;
	private String placa;
	private Date ingreso;

	public Vehiculo(String tipo, String nombre, String placa, Date ingreso) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.placa = placa;
		this.ingreso = ingreso;
	}

	public String getTipo() {
		return tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPlaca() {
		return placa;
	}

	public Date getIngreso() {
		return ingreso;
	}

}
