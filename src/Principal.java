import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtPlaca;
	private JTextField txtIngreso;
	private Parqueadero parking;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		parking = new Parqueadero();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbAsignar = new JLabel("REGISTRO", JLabel.CENTER);
		lbAsignar.setBounds(10, 25, 540, 30);
		contentPane.add(lbAsignar);

		JLabel lbTipo = new JLabel("TIPO:", JLabel.RIGHT);
		lbTipo.setBounds(40, 70, 160, 25);
		contentPane.add(lbTipo);

		JRadioButton rdbtnCarro = new JRadioButton("CARRO");
		rdbtnCarro.setBounds(210, 70, 80, 25);
		contentPane.add(rdbtnCarro);

		JRadioButton rdbtnMoto = new JRadioButton("MOTO");
		rdbtnMoto.setBounds(300, 70, 80, 25);
		contentPane.add(rdbtnMoto);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnCarro);
		group.add(rdbtnMoto);

		JLabel lblNewLabel = new JLabel("NOMBRE:", JLabel.RIGHT);
		lblNewLabel.setBounds(40, 120, 160, 23);
		contentPane.add(lblNewLabel);

		txtNombre = new JTextField();
		txtNombre.setBounds(210, 120, 200, 25);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("PLACA:", JLabel.RIGHT);
		lblNewLabel_1.setBounds(40, 170, 160, 25);
		contentPane.add(lblNewLabel_1);

		txtPlaca = new JTextField();
		txtPlaca.setBounds(210, 170, 200, 25);
		contentPane.add(txtPlaca);
		txtPlaca.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("FECHA Y HORA DE INGRESO:", JLabel.RIGHT);
		lblNewLabel_2.setBounds(40, 220, 160, 25);
		contentPane.add(lblNewLabel_2);

		txtIngreso = new JTextField();
		txtIngreso.setBounds(210, 220, 200, 25);
		txtIngreso.setEditable(false);
		txtIngreso.setColumns(10);

		// Mostrar Hora y Fecha en Tiempo Real
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date now = new Date();
				txtIngreso.setText(formatter.format(now));
			}
		});
		timer.start();
		contentPane.add(txtIngreso);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rdbtnCarro.isSelected() && !rdbtnMoto.isSelected()) {
					JOptionPane.showMessageDialog(null, "Se debe seleccionar un tipo de Vehículo", "Error de Registro",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (txtNombre.getText().isEmpty() || txtPlaca.getText().isEmpty()
						|| txtIngreso.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos", "Error de Registro",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					String tipo = rdbtnMoto.isSelected() ? "Moto" : "Carro";
					try {
						parking.agregarVehiculo(tipo, txtNombre.getText(), txtPlaca.getText(),
								formatter.parse(txtIngreso.getText()));
						// Limpiar campos después de registrar
						txtNombre.setText("");
						txtPlaca.setText("");
						group.clearSelection();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnRegistrar.setBounds(100, 270, 150, 25);
		contentPane.add(btnRegistrar);

		JButton btnEspacios = new JButton("Espacios");
		btnEspacios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ocultar la ventana principal y abrir la ventana de asignamientos
				setVisible(false);
				new Asignamientos(parking, Principal.this); // Pasar la instancia actual de Principal
			}
		});
		btnEspacios.setBounds(310, 270, 150, 25);
		contentPane.add(btnEspacios);
	}
}
