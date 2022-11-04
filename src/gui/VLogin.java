package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JLabel lLogo;
	ImageIcon iiLogo;
	
	JPanel pDatos;
	JPanel pBotones;
	JTextField tfUsuario;
	JPasswordField tfPassword;
	JButton bRegister;
	JButton bLogin;
	
	public VLogin() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		// LOGO
		iiLogo = new ImageIcon("logo.png");
		lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);
		
		// FORMULARIO
		pDatos = new JPanel();
		pDatos.setLayout(new GridLayout(5, 1, 5, 10));
		pDatos.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pDatos.setBackground(new Color(255, 255, 255));
		pBotones = new JPanel();
		pBotones.setLayout(new GridLayout(1, 2, 5, 10));
		
		pDatos.add(new JLabel("Usuario: "));
		tfUsuario = new JTextField();
		pDatos.add(tfUsuario);
		pDatos.add(new JLabel("Contraseña: "));
		tfPassword = new JPasswordField();
		pDatos.add(tfPassword);
		bRegister = new JButton("Register");
		bLogin = new JButton("Login");
		pBotones.add(bRegister);
		pBotones.add(bLogin);
		pDatos.add(pBotones);
		cp.add(pDatos);
		
		this.setTitle("Deusteam Login");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		VLogin v = new VLogin();
	}

}
