import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Parqueadero {
	private int CantEspacios;
	private ArrayList<Espacio> Espacios;
	private double valorParqueo;

	public Parqueadero() {
		CantEspacios = 10;
		valorParqueo = 10.0;
		Espacios = new ArrayList<>();
		for (int i = 0; i < CantEspacios; i++) {
			Espacio vacio = new Espacio("", "", "", null);
			vacio.setEstado(false);
			Espacios.add(vacio);
		}
	}

	public void agregarVehiculo(String Tipo, String Nombre, String Placa, Date Ingreso) {
		if (Disponibles()) {
			int pos = 0;
			while (pos < getCantEspacios()) {
				Espacio space = getEspacios().get(pos);
				if (!space.isEstado()) {
					space.setEstado(true);
					space.setVehiculo(Tipo, Nombre, Placa, Ingreso);
					JOptionPane.showMessageDialog(null, "Registro Exitoso", "Registrando", JOptionPane.PLAIN_MESSAGE);
					pos = getCantEspacios();
				} else {
					pos++;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "No hay cupos disponibles de parqueo", "Error Registro",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public boolean Disponibles() {
		int j = 0;
		for (int i = 0; i < getCantEspacios(); i++) {
			if (getEspacios().get(i).isEstado()) {
				j++;
			}
		}
		if (j == getCantEspacios()) {
			return false;
		} else {
			return true;
		}
	}

	public void gestionarEspacios() {
		if (getEspacios().size() < getCantEspacios()) {
			for (int i = getEspacios().size(); i < CantEspacios; i++) {
				Espacio vacio = new Espacio("", "", "", null);
				vacio.setEstado(false);
				Espacios.add(vacio);
			}
		} else {
			int aux = getEspacios().size() - getCantEspacios();
			for (int i = 0; i < aux; i++) {
				Espacios.remove(getEspacios().size() - 1 - i);
			}
		}
	}

	public int getCantEspacios() {
		return CantEspacios;
	}

	public void setCantEspacios(int cantEspacios) {
		CantEspacios = cantEspacios;
	}

	public ArrayList<Espacio> getEspacios() {
		return Espacios;
	}

	public void setEspacios(ArrayList<Espacio> espacios) {
		Espacios = espacios;
	}

	public double getValorParqueo() {
		return valorParqueo;
	}

}
