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
import negocio.GameGenre;
import negocio.Main;
import negocio.Pegi;
import negocio.User;

public class VCrear extends JFrame{
	public static DBManager dbManager;
	public static VDeusteam vDeusteam;
	public static VRegister vRegister;
	private static final long serialVersionUID = 1L;

	JLabel lLogo;
	ImageIcon iiLogo;
	
	JPanel pData;
	JPanel pButton;
	JLabel lID;
	JTextField tfNombre;
	JLabel lPropietario;
	JPasswordField tfPassword;
	JPasswordField tfPasswordConfirm;
	JButton bRegister;
	JButton bBack;
	JComboBox<Pegi> cbPegi;
	JComboBox<GameGenre> cbGenre1;
	JComboBox<GameGenre> cbGenre2;

	public VCrear(DBManager dbmanager) {
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
		
		pButton = new JPanel();
		pButton.setLayout(new GridLayout(1, 3));
		
		pData.add(new JLabel("ID: "));
		lID = new JLabel(" ");
		int temp = dbmanager.obtainDataGames().size() + 1;
		lID.setText(String.valueOf(temp));
		pData.add(lID);
		pData.add(new JLabel("Nombre: "));
		tfNombre = new JTextField();
		pData.add(tfNombre);
		pData.add(new JLabel("Propietario: "));
		lPropietario = new JLabel(" ");
		lPropietario.setText(VLogin.loggedUser.getUsername());
		pData.add(lPropietario);
		pData.add(new JLabel("Pegi: "));
		cbPegi = new JComboBox<Pegi>(Pegi.values());
		pData.add(cbPegi);
		pData.add(new JLabel("Genre1: "));
		cbGenre1 = new JComboBox<GameGenre>(GameGenre.values());
		pData.add(cbGenre1);
		pData.add(new JLabel("Genre2: "));
		cbGenre2 = new JComboBox<GameGenre>(GameGenre.values());
		pData.add(cbGenre2);
		pData.add(new JLabel("Precio: "));
		
		bBack = new JButton("Back");
		pButton.add(bBack);
		pButton.add(new JLabel(" "));
		bRegister = new JButton("Register");
		pButton.add(bRegister);
		pData.add(pButton);
		cp.add(pData);
		
		this.setTitle("Deusteam Login");
		this.pack();
		this.setLocationRelativeTo(null); // centers window on execution
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	}

}