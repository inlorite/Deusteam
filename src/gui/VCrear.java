package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import datos.DBManager;
import gui.customComponents.DPanelPerfil;
import negocio.Country;
import negocio.Game;
import negocio.GameGenre;
import negocio.Main;
import negocio.Pegi;
import negocio.User;

public class VCrear extends JFrame{
	
	private static final long serialVersionUID = 1L;

	JLabel lLogo;
	ImageIcon iiLogo;
	
	JPanel pData;
	JPanel pButton;
	JLabel lID;
	JTextField tfNombre;
	JLabel lPropietario;
	JTextField tfPrecio;
	JTextField tfDescription;
	JTextField tfImglink;
	JButton bAceptar;
	JButton bBack;
	JComboBox<Pegi> cbPegi;
	JComboBox<GameGenre> cbGenre1;
	JComboBox<GameGenre> cbGenre2;

	public VCrear(DBManager dbmanager) {
		DBManager manager = new DBManager();
		VRegister.dbManager = dbmanager;
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		this.setMinimumSize(new Dimension(800, 600));
		// LOGO
		iiLogo = new ImageIcon("data/logo.png");
		lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);
		cp.setBackground(new Color(255,255,255));
		// FORM
		pData = new JPanel();
		pData.setLayout(new GridLayout(6, 3, 5, 10));
		pData.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pData.setBackground(new Color(255, 255, 255));
		
		pButton = new JPanel();
		pButton.setLayout(new GridLayout(1, 2));
		
		pData.add(new JLabel("ID: "));
		pData.add(new JLabel("Nombre: "));
		pData.add(new JLabel("Propietario: "));
		lID = new JLabel(" ");
		int temp = dbmanager.obtainDataGames().size() + 1;
		lID.setText(String.valueOf(temp));
		pData.add(lID);		
		tfNombre = new JTextField();
		pData.add(tfNombre);		
		lPropietario = new JLabel(" ");
		lPropietario.setText(VLogin.loggedUser.getUsername());
		pData.add(lPropietario);
		pData.add(new JLabel("Pegi: "));
		pData.add(new JLabel("Genre1: "));
		pData.add(new JLabel("Genre2: "));
		cbPegi = new JComboBox<Pegi>(Pegi.values());
		pData.add(cbPegi);		
		cbGenre1 = new JComboBox<GameGenre>(GameGenre.values());
		pData.add(cbGenre1);		
		cbGenre2 = new JComboBox<GameGenre>(GameGenre.values());
		pData.add(cbGenre2);
		pData.add(new JLabel("Precio: "));
		pData.add(new JLabel("Descripcion: "));
		pData.add(new JLabel("imgLink: "));
		tfPrecio = new JTextField();
		pData.add(tfPrecio);
		tfDescription = new JTextField();
		pData.add(tfDescription);
		tfImglink = new JTextField();
		pData.add(tfImglink);
		bAceptar = new JButton("Aceptar");
		bBack = new JButton("Volver");
		pButton.add(bBack);
		pButton.add(bAceptar);
			
		cp.add(pData);
		cp.add(pButton, BorderLayout.SOUTH);
		
		
		this.setTitle("Deusteam Games");
		this.pack();
		this.setLocationRelativeTo(null); // centers window on execution
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		
		bBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DPanelPerfil.vCrear.setVisible(false);
				
			}
		});
		
		bAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String id = lID.getText();
				String nombre = tfNombre.getText();
				String propietario = lPropietario.getText();
				Pegi pegi = (Pegi) cbPegi.getSelectedItem();
				GameGenre genre1 = (GameGenre) cbGenre1.getSelectedItem();
				GameGenre genre2 = (GameGenre) cbGenre2.getSelectedItem();
				String precio = tfPrecio.getText();
				String descripcion = tfDescription.getText();
				String imglink = tfImglink.getText();
				
				boolean error = false;
				String errores = "";
				
				Game game = new Game();
				game.setId(Integer.parseInt(id));
				game.setName(nombre);
				if (VLogin.dbManager.getGame(nombre) != null) {
					errores += "Nombre ocupado\n";
					error = true;
				}
				game.setOwner(propietario);
				game.setPegi(pegi);
				if (pegi.equals(Pegi.NULL)) {
					errores += "PEGI incorrecto\n";
					error = true;
				}
				game.setGenre1(genre1);
				if (genre1.equals(GameGenre.NULL)) {
					errores += "Introduce al menos un genero\n";
					error = true;
				}
				game.setGenre2(genre2);
				try {
					game.setPrice(Double.parseDouble(precio));
				} catch(NumberFormatException e1) {
					errores += "Precio incorrecto\n";
					error = true;
				}
				game.setDescription(descripcion);
				game.setImgLink(imglink);
				
				if (!error) {
					VLogin.dbManager.insertDataGames(game);
					
					DPanelPerfil.vCrear.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, errores);	
				}
				
				for (Game g : VLogin.dbManager.obtainDataGames()) {
					System.out.println(g);
				}
			}
		});
		
	}

}