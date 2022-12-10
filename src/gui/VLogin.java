package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import datos.DBManager;
import negocio.User;

public class VLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static DBManager dbManager;
	
	public static VLogin vLogin;
	public static VDeusteam vDeusteam;
	public static VRegister vRegister;
	public static User loggedUser;
	
	JLabel lLogo;
	ImageIcon iiLogo;
	JPanel pData;
	JPanel pButton;
	JTextField tfUser;
	JPasswordField tfPassword;
	JButton bRegister;
	JButton bLogin;

	public VLogin(DBManager dbManager) {
		
		VLogin.dbManager = dbManager;
		
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
		
		pData.add(new JLabel("Username: "));
		tfUser = new JTextField();
		pData.add(tfUser);
		pData.add(new JLabel("Password: "));
		tfPassword = new JPasswordField();
		pData.add(tfPassword);
		bRegister = new JButton("Register");
		bLogin = new JButton("Login");
		pButton.add(bRegister);
		pButton.add(bLogin);
		pData.add(pButton);
		cp.add(pData);
		
		vLogin = this;
		
		bRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				vRegister = new VRegister(VLogin.dbManager);
				vLogin.setVisible(false);
			}
		});

		bLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = tfUser.getText();
				String password = tfPassword.getText();
				
				boolean login = VLogin.dbManager.verifyUser(user, password);
				
				if (login) {
					vLogin.setVisible(false);
					loggedUser = VLogin.dbManager.getUser(user);
					System.out.println(loggedUser);
					vDeusteam = new VDeusteam();
				} else {
					System.out.println("datos incorrectos");
				}
				
			}
			
		});
		
		this.setTitle("Deusteam Login");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	}

}
