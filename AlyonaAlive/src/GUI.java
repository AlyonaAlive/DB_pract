import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class GUI extends JPanel {
	private boolean tableShow = false;
	
	private DatabaseConnection jdbc = null;
	private JTable table;
	private final Action action = new SwingAction();
	final JComboBox<String> comboBox = new JComboBox<String>();
	private final Action action_1 = new SwingAction_1();
	private final Action action_2 = new SwingAction_2();

	
	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Delete Row");
		}

		public void actionPerformed(ActionEvent e) {
			if (tableShow) {
				
				if(comboBox.getSelectedItem().toString().toLowerCase().equals("client")){
					int[] rows = table.getSelectedRows();

					List<String> passports = new ArrayList<String>();

					for (int row: rows) {
						String passport = table.getValueAt(row, 2).toString();
						System.out.println(passport);
						passports.add(passport);
					}
					DatabaseConnection.deleteClient(passports);
				}
					
				else if(comboBox.getSelectedItem().toString().toLowerCase().equals("score")){
					int[] rows = table.getSelectedRows();

					List<String> passports = new ArrayList<String>();

					for (int row: rows) {
						String passport = table.getValueAt(row, 0).toString();
						
						passports.add(passport);
					}
					DatabaseConnection.deleteScore(passports);
				}

			} else {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(GUI.this);
				JOptionPane.showMessageDialog(topFrame, "Choose the table!");
			}
		}
	}

	private void showAddRowPopup(String tableName) {
		switch (tableName) {
		case "client":
			showAddClientPopup();
			break;
		case "score":
			showAddScorePopup();
			break;
		case "visit":
			showAddVisitPopup();
			break;
		default:
			break;
		}
	}

	private void showAddClientPopup() {
		JLabel tableName = new JLabel("Table client");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(tableName);
		panel.add(new JLabel("Client name:"));
		panel.add(field1);
		panel.add(new JLabel("Client passport:"));
		panel.add(field2);
		panel.add(new JLabel("Client address:"));
		panel.add(field3);
		int result = JOptionPane.showConfirmDialog(null, panel, "Test", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			if (field1.getText().length() > 5 && field2.getText().length() > 10 && field3.getText().length() > 10) {
				if (DatabaseConnection.checkIfClientExist(field2.getText())) {
					DatabaseConnection.addClient(field1.getText(), field2.getText(), field3.getText());
					JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(GUI.this), "Success");

				} else {
					JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(GUI.this),
							"Client already exist");
				}
			} else {
				JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(GUI.this),
						"Incorect data");
			}
		} else {
			System.out.println("Cancelled");
		}

	}

	private void tableShow(String tableName) {
		Standart model = null;
		Vector<String> fields = DatabaseConnection.getFields(tableName);
		if (fields != null) {
			Vector<Vector<String>> data = DatabaseConnection.getAllData(tableName);
			model = new Standart(data, fields);
			if (!tableShow) {
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 96, 430, 193);
				add(scrollPane);
				table = new JTable();
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				scrollPane.setViewportView(table);
				scrollPane.revalidate();
				scrollPane.repaint();
				tableShow = true;
			}

			table.setModel(model);
			model.fireTableDataChanged();

		}

	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "ok");
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println(comboBox.getSelectedItem().toString().toLowerCase());
			tableShow(comboBox.getSelectedItem().toString().toLowerCase());
		}
	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Add Row");
		}

		public void actionPerformed(ActionEvent e) {
			if (tableShow) {
				showAddRowPopup(GUI.this.comboBox.getSelectedItem().toString().toLowerCase());
			} else {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(GUI.this);
				JOptionPane.showMessageDialog(topFrame, "Choose the table!");
			}
		}
	}

	

	private void showAddScorePopup() {
		JLabel tableName = new JLabel("Table score");
		Vector<Car> cl = DatabaseConnection.getClientsNames();

		JComboBox<Car> clients = new JComboBox<Car>();
		for (Car s : cl) {
			clients.addItem(s);
		}
		JTextField field2 = new JTextField();
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(tableName);
		panel.add(new JLabel("Client name:"));
		panel.add(clients);
		panel.add(new JLabel("Score:"));
		panel.add(field2);
		int result = JOptionPane.showConfirmDialog(null, panel, "Test", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			if (field2.getText().length() > 3) {
				if (DatabaseConnection.checkIfClientExist(field2.getText())) {
					DatabaseConnection.addScore(((Car) clients.getSelectedItem()).getPassport(),
							Integer.parseInt(field2.getText().toString()));
					JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(GUI.this), "Success");

				} else {
					JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(GUI.this),
							"Client already exist");
				}
			} else {
				JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(GUI.this),
						"Incorect data");
			}
		} else {
			System.out.println("Cancelled");
		}

	}
	
	public GUI(DatabaseConnection jdbc) {

		setLayout(null);

		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Car", "Store", "Shop" }));
		comboBox.setBounds(35, 53, 200, 27);
		add(comboBox);
		this.jdbc = jdbc;

		JLabel lblChooseTable = new JLabel("Таблиця:");
		lblChooseTable.setBounds(25, 28, 113, 14);
		add(lblChooseTable);

		JButton btnOk = new JButton("OK");
		btnOk.setAction(action);
		btnOk.setBounds(262, 55, 54, 23);
		add(btnOk);

		JButton btnAddRow = new JButton("Додати ");
		btnAddRow.setAction(action_1);
		btnAddRow.setBounds(67, 428, 89, 23);
		add(btnAddRow);

		JButton btnDeleteRow = new JButton("Видалити");
		btnDeleteRow.setAction(action_2);
		btnDeleteRow.setBounds(250, 428, 100, 23);
		add(btnDeleteRow);

	}

	private void showAddVisitPopup() {
		JLabel tableName = new JLabel("Table visit");
		Vector<Car> cl = DatabaseConnection.getClientsNames();
		final JComboBox<Car> clients = new JComboBox<Car>();
		final JComboBox<Score> score = new JComboBox<Score>();
		for (Car s : cl) {
			clients.addItem(s);
		}

		clients.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Vector<Score> scores = DatabaseConnection
						.getScoresByNameClient(((Car) clients.getSelectedItem()).getPassport());
				score.removeAllItems();
				for (Score s : scores) {
					score.addItem(s);
				}
			}

		});

		JTextField field2 = new JTextField();
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(tableName);
		panel.add(new JLabel("Client name:"));
		panel.add(clients);
		panel.add(new JLabel("Score:"));
		panel.add(score);
		panel.add(new JLabel("Deposit:"));
		panel.add(field2);
		int result = JOptionPane.showConfirmDialog(null, panel, "Test", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			if (field2.getText().length() > 0) {
				DatabaseConnection.addVisit(((Car) clients.getSelectedItem()).getPassport().toString(),
						Integer.parseInt(((Score) score.getSelectedItem()).getScoreId().toString()),
						Integer.parseInt(field2.getText().toString()));
			} else {
				JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(GUI.this),
						"Incorect data");
			}
		} else {
			System.out.println("Cancelled");
		}

	}
}
