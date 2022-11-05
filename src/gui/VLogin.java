package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;




public class VLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JLabel lLogo;
	ImageIcon iiLogo;
	JPanel pData;
	JPanel pButton;
	JTextField tfUser;
	JPasswordField tfPassword;
	JButton bRegister;
	JButton bLogin;
	public static VLogin v;

	
	
	
	public VLogin() {
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		// LOGO
		iiLogo = new ImageIcon("data/logo.png");
		lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);
		
		// FORMULARIO
		pData = new JPanel();
		pData.setLayout(new GridLayout(5, 1, 5, 10));
		pData.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pData.setBackground(new Color(255, 255, 255));
		pButton = new JPanel();
		pButton.setLayout(new GridLayout(1, 2, 5, 10));
		
		pData.add(new JLabel("Usuario: "));
		tfUser = new JTextField();
		pData.add(tfUser);
		pData.add(new JLabel("Contraseña: "));
		tfPassword = new JPasswordField();
		pData.add(tfPassword);
		bRegister = new JButton("Register");
		bLogin = new JButton("Login");
		pButton.add(bRegister);
		pButton.add(bLogin);
		pData.add(pButton);
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
				VRegister vr = new VRegister();
				v.setVisible(false);
			}
		});

	}

	
	
	
	public static void main(String[] args) {
		 v = new VLogin();
	}

}
