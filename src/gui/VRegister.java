package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VRegister extends JFrame{

	private static final long serialVersionUID = 1L;

	JLabel lLogo;
	ImageIcon iiLogo;
	
	JPanel pData;
	JPanel pButton;
	JTextField tfUser;
	JTextField tfEmail;
	JPasswordField tfPassword;
	JPasswordField tfPasswordConfirm;
	JButton bRegister;

	
	
	public VRegister() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		// LOGO
		iiLogo = new ImageIcon("data/logo.png");
		lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);
		
		// FORMULARIO
		pData = new JPanel();
		pData.setLayout(new GridLayout(9, 1, 5, 10));
		pData.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pData.setBackground(new Color(255, 255, 255));
		
		pData.add(new JLabel("Username: "));
		tfUser = new JTextField();
		pData.add(tfUser);
		pData.add(new JLabel("Email: "));
		tfEmail = new JTextField();
		pData.add(tfEmail);
		pData.add(new JLabel("Password: "));
		tfPassword = new JPasswordField();
		pData.add(tfPassword);
		pData.add(new JLabel("Password verification: "));
		tfPasswordConfirm = new JPasswordField();
		pData.add(tfPasswordConfirm);
		bRegister = new JButton("Register");
		pData.add(bRegister);
		cp.add(pData);
		
		this.setTitle("Deusteam Login");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		bRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = tfUser.getText();
				String email = tfEmail.getText();
				char[] password = tfPassword.getPassword();
				char[] confirmPassword = tfPasswordConfirm.getPassword();
				
				if (password.length==confirmPassword.length) {
					for (int i = 0; i < confirmPassword.length; i++) {
						if (password[i]!=confirmPassword[i]) {
							JOptionPane.showMessageDialog(null, "Passwords do not match");
							break;
						}
					}				
				} else {
					JOptionPane.showMessageDialog(null, "Passwords do not match");
				}
					
				
			}
		});
	}
	public static void main(String[] args) {
		VRegister vr = new VRegister();
		
	}
}
