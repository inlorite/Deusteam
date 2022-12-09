package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import datos.DBManager;
import negocio.Country;
import negocio.Game;
import negocio.Main;
import negocio.User;

public class VRegister extends JFrame{
	public static DBManager dbManager;
	public static VDeusteam vDeusteam;
	public static VRegister vRegister;
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
	JComboBox<Country> cbCountry;

	public VRegister(DBManager dbmanager) {
		DBManager manager = new DBManager();
		VRegister.dbManager = dbmanager;
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		// LOGO
		iiLogo = new ImageIcon("data/logo.png");
		lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);
		
		// FORM
		pData = new JPanel();
		pData.setLayout(new GridLayout(11, 1, 5, 10));
		pData.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pData.setBackground(new Color(255, 255, 255));
		
		pData.add(new JLabel("Username: "));
		tfUser = new JTextField();
		pData.add(tfUser);
		pData.add(new JLabel("Email: "));
		tfEmail = new JTextField();
		pData.add(tfEmail);
		pData.add(new JLabel("Country: "));
		cbCountry = new JComboBox<Country>(Country.values());
		pData.add(cbCountry);
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
		this.setLocationRelativeTo(null); // centers window on execution
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		bRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = tfUser.getText();
				String email = tfEmail.getText();
				Country country = (Country) cbCountry.getSelectedItem();
				String password = tfPassword.getText();
				String confirmPassword = tfPasswordConfirm.getText();
				User userVerification = VRegister.dbManager.getUser(username);
				if (userVerification != null ) {
					JOptionPane.showMessageDialog(null, "Usuario ocupado");				
					
					// New user creation
					
					
				} else if (!password.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");				
				} 	else {
					User user = new User();
					user.setUsername(username);
					user.setEmail(email);
					user.setPassword(password.toString());
					user.setCountry(country);
					user.setLastTimePlayed(new Date());
					user.setTotalTimePlayed(0);
					user.setFriends(new ArrayList<Integer>());
					user.setGames(new ArrayList<Game>());
					Main.gestor.insertDataUsers(user);
					
					for (User u : Main.gestor.obtainDataUsers()) {
						System.out.println(u);
					}
					VLogin.vRegister.setVisible(false);
					VLogin.vLogin.setVisible(true);
				}
					
				
					
				
			}
		});
	}

}