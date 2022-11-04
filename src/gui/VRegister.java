package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VRegister extends JFrame{

	private static final long serialVersionUID = 1L;

	JLabel lLogo;
	ImageIcon iiLogo;
	
	JPanel pDatos;
	JPanel pBotones;
	JTextField tfUsuario;
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
		pDatos = new JPanel();
		pDatos.setLayout(new GridLayout(9, 1, 5, 10));
		pDatos.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pDatos.setBackground(new Color(255, 255, 255));
		
		pDatos.add(new JLabel("Usuario: "));
		tfUsuario = new JTextField();
		pDatos.add(tfUsuario);
		pDatos.add(new JLabel("Email: "));
		tfEmail = new JTextField();
		pDatos.add(tfEmail);
		pDatos.add(new JLabel("Contraseña: "));
		tfPassword = new JPasswordField();
		pDatos.add(tfPassword);
		pDatos.add(new JLabel("Confirmacion de contraseña: "));
		tfPasswordConfirm = new JPasswordField();
		pDatos.add(tfPasswordConfirm);
		bRegister = new JButton("Register");
		pDatos.add(bRegister);
		cp.add(pDatos);
		
		this.setTitle("Deusteam Login");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		bRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String usuario = tfUsuario.getText();
				String email = tfEmail.getText();
				char[] password = tfPassword.getPassword();
				char[] confirmPassword = tfPasswordConfirm.getPassword();
				
				if (password.length==confirmPassword.length) {
					for (int i = 0; i < confirmPassword.length; i++) {
						if (password[i]!=confirmPassword[i]) {
							JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
							break;
						}
					}				
				}else {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
				}
					
				
			}
		});
	}
	public static void main(String[] args) {
		VRegister vr = new VRegister();
		
	}
}
