package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.Connect;
import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class EnterData extends JFrame {

	private JPanel mainframe;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField personNumber;
	private JTextField phoneNumber;
	private JTextField email;
	private JTextField apartmentNumber;
	private JLabel lblPersonnummer;
	private JLabel lblMobilnummer;
	private JLabel lblEmail;
	private JLabel lblLagenhetsnummer;
	private JButton btnSkrivUtGster;
	private JTable table;
	private JTable table_1;
	private JScrollPane scrollPane;
	Connection conn = Connect.getConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterData frame = new EnterData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public EnterData() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 800);
		mainframe = new JPanel();
		mainframe.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainframe);
		mainframe.setLayout(null);

		JLabel lblRegistreraNyHyresgast = new JLabel("Registrera Ny Hyresgast");
		lblRegistreraNyHyresgast.setBounds(45, 46, 174, 16);
		mainframe.add(lblRegistreraNyHyresgast);

		firstName = new JTextField();
		firstName.setBounds(176, 100, 160, 26);
		mainframe.add(firstName);
		firstName.setColumns(10);

		lastName = new JTextField();
		lastName.setBounds(176, 150, 160, 26);
		mainframe.add(lastName);
		lastName.setColumns(10);

		personNumber = new JTextField();
		personNumber.setBounds(176, 200, 160, 26);
		mainframe.add(personNumber);
		personNumber.setColumns(10);

		phoneNumber = new JTextField();
		phoneNumber.setBounds(176, 250, 160, 26);
		mainframe.add(phoneNumber);
		phoneNumber.setColumns(10);

		email = new JTextField();
		email.setBounds(176, 300, 160, 26);
		mainframe.add(email);
		email.setColumns(10);

		apartmentNumber = new JTextField();
		apartmentNumber.setBounds(176, 350, 160, 26);
		mainframe.add(apartmentNumber);
		apartmentNumber.setColumns(10);

		JLabel lblNamn = new JLabel("Namn");
		lblNamn.setBounds(45, 104, 61, 16);
		mainframe.add(lblNamn);

		JLabel lblEfternamn = new JLabel("Efternamn");
		lblEfternamn.setBounds(45, 154, 88, 16);
		mainframe.add(lblEfternamn);

		lblPersonnummer = new JLabel("Personnummer");
		lblPersonnummer.setBounds(45, 204, 99, 16);
		mainframe.add(lblPersonnummer);

		lblMobilnummer = new JLabel("Mobilnummer");
		lblMobilnummer.setBounds(45, 254, 99, 16);
		mainframe.add(lblMobilnummer);

		lblEmail = new JLabel("Email");
		lblEmail.setBounds(45, 304, 61, 16);
		mainframe.add(lblEmail);

		lblLagenhetsnummer = new JLabel("Lagenhetsnummer");
		lblLagenhetsnummer.setBounds(45, 354, 130, 16);
		mainframe.add(lblLagenhetsnummer);

		DefaultTableModel model = new DefaultTableModel();

		// Create a couple of columns
		model.addColumn("Col1");
		model.addColumn("Col2");

		// Append a row
		model.addRow(new Object[] { "v1", "v2" });

		JButton btnSpara = new JButton("Spara");
		btnSpara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (
						Socket socket = new Socket("localhost", 2277);
						OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
						PrintWriter writer = new PrintWriter(out, true);
						InputStreamReader input = new InputStreamReader(socket.getInputStream());
						BufferedReader reader = new BufferedReader(input);) {
					writer.println(firstName.getText());
					writer.println(lastName.getText());
					writer.println(personNumber.getText());
					writer.println(phoneNumber.getText());
					writer.println(email.getText());
					writer.println(apartmentNumber.getText());
					String response=reader.readLine();
					if (response.equals("ok")) {
						JOptionPane.showMessageDialog(null, "G�sten �r registrerad");
						firstName.setText("");
						lastName.setText("");
						personNumber.setText("");
						phoneNumber.setText("");
						email.setText("");
						apartmentNumber.setText("");
					}
					else {
						JOptionPane.showMessageDialog(null, "N�got gick fel");
					}
					

				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
		btnSpara.setBounds(176, 411, 130, 29);
		mainframe.add(btnSpara);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(371, 106, 838, 515);
		mainframe.add(scrollPane);

		table_1 = new JTable();
		scrollPane.setViewportView(table_1);

		JButton btnSkrivUtHyresgster = new JButton("Skriv ut hyresgäster");
		btnSkrivUtHyresgster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String query = "select * from hyresgaster";
				try {

					Connection conn = Connect.getConnection();
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSkrivUtHyresgster.setBounds(696, 60, 184, 29);
		mainframe.add(btnSkrivUtHyresgster);

	}
}
