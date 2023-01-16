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

public class VEditar extends JFrame{
	
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

	public VEditar() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		Game game = VModificar.listJuegosOwner.get(VModificar.tJuegos.getSelectedRow());
						
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
		int temp = game.getId();
		lID.setText(String.valueOf(temp));
		pData.add(lID);		
		tfNombre = new JTextField(game.getName());
		pData.add(tfNombre);		
		lPropietario = new JLabel(" ");
		lPropietario.setText(VLogin.loggedUser.getUsername());
		pData.add(lPropietario);
		pData.add(new JLabel("Pegi: "));
		pData.add(new JLabel("Genre1: "));
		pData.add(new JLabel("Genre2: "));
		cbPegi = new JComboBox<Pegi>(Pegi.values());
		cbPegi.setSelectedItem(game.getPegi());
		pData.add(cbPegi);		
		cbGenre1 = new JComboBox<GameGenre>(GameGenre.values());
		cbGenre1.setSelectedItem(game.getGenre1());
		pData.add(cbGenre1);		
		cbGenre2 = new JComboBox<GameGenre>(GameGenre.values());
		cbGenre2.setSelectedItem(game.getGenre2());
		pData.add(cbGenre2);
		pData.add(new JLabel("Precio: "));
		pData.add(new JLabel("Descripcion: "));
		pData.add(new JLabel("imgLink: "));
		tfPrecio = new JTextField(String.valueOf(game.getPrice()));
		pData.add(tfPrecio);
		tfDescription = new JTextField(game.getDescription());
		tfDescription.setColumns(20);
		pData.add(tfDescription);
		tfImglink = new JTextField(game.getImgLink());
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
				VModificar.vEditar.setVisible(false);
				
			}
		});
		
		bAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DPanelPerfil.vModificar.setVisible(false);
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
				Double precioDouble = 0.0;
				
				if(VLogin.dbManager.getGame(nombre) != null && !nombre.equals(game.getName())) {
					JOptionPane.showMessageDialog(null, "Nombre ocupado");
					error = true;
				} 
				
				try
				{
					precioDouble = Double.parseDouble(precio);
				}
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(null, "Precio incorrecto");
					error = true;
				}
				
				if (pegi.equals(Pegi.NULL)) {
					JOptionPane.showMessageDialog(null, "Selecciona un PEGI");
					error = true;
				}
				
				if (genre1.equals(GameGenre.NULL)) {
					JOptionPane.showMessageDialog(null, "Introduce un genero principal");
					error = true;
				}
				if(!error) {
					VLogin.dbManager.updateGameName(game, nombre);
					VLogin.dbManager.updateGamePegi(game, pegi.toString());
					VLogin.dbManager.updateGameGenre1(game, genre1.toString());
					VLogin.dbManager.updateGameGenre2(game, genre2.toString());
					VLogin.dbManager.updateGamePrice(game, precioDouble);
					VLogin.dbManager.updateGameDescription(game, descripcion);
					VLogin.dbManager.updateGameImgLink(game, imglink);
					VModificar.vEditar.setVisible(false);
					JOptionPane.showMessageDialog(null, "Juego modificado");
				}

				//VLogin.dbManager.insertDataGames(game);
				
				for (Game g : VLogin.dbManager.obtainDataGames()) {
					System.out.println(g);
				}
				
				
				
				
			}
		});
		
	}

}