import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Asignamientos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnEditar;
	private JButton btnGuardar;
	private JButton btnVolver;
	private JTable table;
	private JTextField txtEspacios;
	private DefaultTableModel tableModel;

	public Asignamientos(Parqueadero parking, Principal principalFrame) { // Agregué el parámetro principalFrame
		JFrame frame = new JFrame("Asignamientos de Espacios");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(650, 500);
		setLocationRelativeTo(null);
		new JPanel(new BorderLayout());

		JLabel label1 = new JLabel("Cantidad de Espacios Totales en el Parqueadero:", JLabel.CENTER);
		txtEspacios = new JTextField(20);
		txtEspacios.setHorizontalAlignment(JTextField.CENTER);
		txtEspacios.setText(String.valueOf(parking.getCantEspacios()));
		txtEspacios.setEnabled(false);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtEspacios.setEnabled(true);
				btnGuardar.setVisible(true);
				btnEditar.setVisible(false);
			}
		});

		btnGuardar = new JButton("Guardar");
		btnGuardar.setVisible(false);
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int nuevaCantidad = Integer.parseInt(txtEspacios.getText());
				int diferencia = nuevaCantidad - parking.getCantEspacios();

				// Actualizar la cantidad de espacios en el objeto Parqueadero
				parking.setCantEspacios(nuevaCantidad);
				parking.gestionarEspacios();

				// Actualizar la tabla
				actualizarTabla(parking, diferencia);

				txtEspacios.setEnabled(false);
				btnGuardar.setVisible(false);
				btnEditar.setVisible(true);
			}
		});

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Hacer visible la ventana principal y cerrar la ventana de asignamientos
				principalFrame.setVisible(true);
				frame.dispose();
			}
		});

		JPanel info = new JPanel();
		info.add(label1);
		info.add(txtEspacios);
		info.add(btnEditar);
		info.add(btnGuardar);
		info.add(btnVolver); // Añadir el botón "Volver" al panel

		cargarTabla(parking);
		// Agregar un MouseListener para detectar el doble clic
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.rowAtPoint(e.getPoint());
					if ("Disponible".equals(table.getValueAt(row, 1))) {
						JOptionPane.showMessageDialog(null, "No se puede dar salida a este espacio", "Error de Salida",
								JOptionPane.ERROR_MESSAGE);
					} else {
						new Cobro(parking, row);
						frame.dispose();
					}
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(info, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		// Hacer visible el JFrame
		frame.setVisible(true);
	}

	public void cargarTabla(Parqueadero parking) {
		String[] columnNames = { "Espacio", "Estado", "Placa", "Ingreso" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		actualizarTabla(parking, 0);
	}

	private void actualizarTabla(Parqueadero parking, int diferencia) {
		// Limpiar la tabla
		tableModel.setRowCount(0);

		// Cargar los datos desde el ArrayList a la tabla
		List<Espacio> espacios = parking.getEspacios();
		int i = 0;
		for (Espacio espacio : espacios) {
			i++;
			String estado = espacio.isEstado() ? "Ocupado" : "Disponible";
			Object[] row = { i, estado, espacio.getVehiculo().getPlaca(), espacio.getVehiculo().getIngreso() };
			tableModel.addRow(row);
		}

		// Si se ha añadido más espacios, se deben agregar nuevas filas "disponibles"
		for (int j = 0; j < diferencia; j++) {
			Object[] row = { espacios.size() + j + 1, "Disponible", "", "" };
			tableModel.addRow(row);
		}

		// Si se han reducido espacios, se deben eliminar filas
		if (diferencia < 0) {
			int rowsToRemove = -diferencia;
			for (int j = 0; j < rowsToRemove; j++) {
				tableModel.removeRow(tableModel.getRowCount() - 1);
			}
		}
	}
}
