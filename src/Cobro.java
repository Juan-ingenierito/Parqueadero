import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cobro extends JFrame {
	public Cobro(Parqueadero parking, int index) {
		JFrame frame = new JFrame("Ventana de Cobro");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(560, 400);
		frame.setLocationRelativeTo(null);

		frame.add(new JLabel("Registro de Salida", JLabel.CENTER), BorderLayout.NORTH);
		JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

		JLabel lblTipo = new JLabel("TIPO VEHICULO:", JLabel.RIGHT);
		JLabel lblNombre = new JLabel("NOMBRE:", JLabel.RIGHT);
		JLabel lblPlaca = new JLabel("PLACA:", JLabel.RIGHT);
		JLabel lblIngreso = new JLabel("HORA Y FECHA INGRESO:", JLabel.RIGHT);
		JLabel lblSalida = new JLabel("HORA Y FECHA SALIDA", JLabel.RIGHT);
		JLabel lblValor = new JLabel("VALOR:", JLabel.RIGHT);

		JTextField txtTipo = new JTextField();
		txtTipo.setEnabled(false);
		txtTipo.setText(parking.getEspacios().get(index).getVehiculo().getTipo());
		JTextField txtNombre = new JTextField();
		txtNombre.setEnabled(false);
		txtNombre.setText(parking.getEspacios().get(index).getVehiculo().getNombre());
		JTextField txtPlaca = new JTextField();
		txtPlaca.setEnabled(false);
		txtPlaca.setText(parking.getEspacios().get(index).getVehiculo().getPlaca());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		JTextField txtIngreso = new JTextField();
		txtIngreso.setEnabled(false);
		Date ingreso = parking.getEspacios().get(index).getVehiculo().getIngreso();
		txtIngreso.setText(formatter.format(ingreso));
		JTextField txtSalida = new JTextField();
		txtSalida.setEnabled(false);
		Date now = new Date();
		txtSalida.setText(formatter.format(now));
		JTextField txtValor = new JTextField();
		txtValor.setEnabled(false);
		txtValor.setText(calcularTotal(parking.getValorParqueo(), ingreso, now));

		panel.add(lblTipo);
		panel.add(txtTipo);
		panel.add(lblNombre);
		panel.add(txtNombre);
		panel.add(lblPlaca);
		panel.add(txtPlaca);
		panel.add(lblIngreso);
		panel.add(txtIngreso);
		panel.add(lblSalida);
		panel.add(txtSalida);
		panel.add(lblValor);
		panel.add(txtValor);
		frame.add(panel, BorderLayout.CENTER);

		JButton btnCobrar = new JButton("Cobrar");
		btnCobrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"La Placa " + txtPlaca.getText() + " cancelo: $" + txtValor.getText() + "\n SALIDA EXITOSA",
						"Registro de Salida", JOptionPane.INFORMATION_MESSAGE);
				parking.getEspacios().get(index).setEstado(false);
				parking.getEspacios().get(index).setVehiculo("", "", "", null);
				frame.dispose();
			}
		});
		frame.add(btnCobrar, BorderLayout.SOUTH);

		// Mostrar la nueva ventana
		frame.setVisible(true);
	}

	public String calcularTotal(Double vm, Date ingreso, Date salida) {
		long diferenciaTiempo = TimeUnit.MILLISECONDS.toMinutes(salida.getTime() - ingreso.getTime());

		double valor = vm * diferenciaTiempo;

		return Double.toString(valor);
	}
}
