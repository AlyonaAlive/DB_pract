import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;

public class Start extends JFrame {

	private static final long serialVersionUID = 1L;
	private Login loginPane;

	

	public Start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("DataBaseManager");
		setFont(new Font("Arial", Font.PLAIN, 14));
		setBounds(100, 100, 1200, 700);
		loginPane = new Login();
		setContentPane(loginPane);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Start frame = new Start();
					
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
