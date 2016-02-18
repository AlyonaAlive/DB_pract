
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Login extends JPanel {
	
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField txtRoot;
	private final Action action = new SwingAction();


	public Login() {
		setLayout(null);		

		JButton btnConnect = new JButton("З'єднати");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnConnect.setAction(action);
		btnConnect.setBounds(92, 150, 150, 150);
		add(btnConnect);

	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Connect");
		}

		public void actionPerformed(ActionEvent e) {
			try{
				DatabaseConnection myDb = new DatabaseConnection();
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Login.this);
				topFrame.setContentPane(new GUI(myDb));
				topFrame.revalidate(); 
				topFrame.repaint();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
