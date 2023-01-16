package gui.customComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.VCrear;
import gui.VDeusteam;
import gui.VLogin;
import gui.VModificar;
import gui.VRecomendar;
import negocio.*;

public class DPanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// West Lista Amigos
	protected static JList<User> listAmigos;
	protected static DefaultListModel<User> dlmAmigos;
	protected JButton bBusqueda;
	protected JTextField tBuscador;
	
	
	// Center Perfil Usuario
	protected JLabel lPerfil;
	protected static JPanel panelPerfil = new JPanel(new GridLayout(7,1));
	
	//Botones modificar
	protected JButton bCrear = new JButton("Crear");
	protected JButton bModificar = new JButton("Modificar");
	protected JButton bLogout = new JButton("Log out");
	
	public static VCrear vCrear;
	public static VModificar vModificar;

	public DPanelPerfil() {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel pListaAmigos = getWestListaAmigos();
		JPanel pInfoUsuario = getCenterPerfilUsuario();
		JPanel pBotones = getSouthBotones();
		
		this.add(pListaAmigos, BorderLayout.WEST);
		this.add(pInfoUsuario, BorderLayout.CENTER);
		this.add(pBotones, BorderLayout.SOUTH);
	}
	
	public JPanel getSouthBotones() {
		JPanel panel = new JPanel(new GridLayout(2,1)); // modificar
		JPanel pBoton = new JPanel(new GridLayout(1,3));
		JPanel pTitulo = new JPanel(new GridLayout(1,3));
		 JLabel lTitulo = new JLabel("Modificacion y creacion de juegos: ");
		 JLabel lLogout = new JLabel("Log out: ");
		 
		 pBoton.add(bModificar);		
		 pBoton.add(bCrear);
		 pBoton.add(bLogout);
		 pTitulo.add(lTitulo);
		 pTitulo.add(new JLabel(" "));
		 pTitulo.add(lLogout);
		 
		 panel.add(pTitulo, BorderLayout.NORTH);
		 panel.add(pBoton, BorderLayout.CENTER);
		 
		 bCrear.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					vCrear = new VCrear(VLogin.dbManager);
					
				}
			});
		 
		 bModificar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					vModificar = new VModificar();
					
				}
			});
		 
		 bLogout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					VLogin.vLogin.setVisible(true);
					VLogin.vDeusteam.setVisible(false);
					
				}
			});
		
		return panel;
	}
	
	public JPanel getWestListaAmigos() {
		JPanel panel = new JPanel(new GridLayout(2,1)); // modificar
		
		
		JPanel panelAmigos = new JPanel(new BorderLayout());
		panelAmigos.setBorder(new TitledBorder("Lista de amigos"));
		dlmAmigos = new DefaultListModel<>();
		listAmigos = new JList<>(dlmAmigos);
		listAmigos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelAmigos.add(new JScrollPane(listAmigos), BorderLayout.CENTER);
		
		loadListaAmigos();
		
		JPanel panelBusqueda = new JPanel(new GridLayout(2,1));
		panelBusqueda.setBorder(new TitledBorder("Añadir amigo"));
		tBuscador = new JTextField();
		tBuscador.setColumns(10);
		panelBusqueda.add(tBuscador);
		bBusqueda = new JButton("Añadir");
		panelBusqueda.add(bBusqueda);
		
		bBusqueda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (VLogin.dbManager.getUser(tBuscador.getText()) != null) {
					if (VLogin.dbManager.getUser(tBuscador.getText()).equals(VLogin.loggedUser)) {
						JOptionPane.showMessageDialog(null, "No te puedes añadir a ti mismo.");
					} else if(VLogin.loggedUser.getFriends().contains((Object) VLogin.dbManager.getUser(tBuscador.getText()).getId())) {
						JOptionPane.showMessageDialog(null, "El usuario " + tBuscador.getText() + " ya es tu amigo.");
					} else if (VLogin.dbManager.getUser(tBuscador.getText()) != null) {
						VLogin.dbManager.insertDataFriends(VLogin.loggedUser, VLogin.dbManager.getUser(tBuscador.getText()));
						loadListaAmigos();
					}
				} else {
					JOptionPane.showMessageDialog(null,"El usuario " + tBuscador.getText() + " no se ha encontrado.");
				}
				
			}
		});
		
		panel.add(panelAmigos);
		panel.add(panelBusqueda);
		return panel;
	}
	
	public static JPanel getCenterPerfilUsuario() {
		panelPerfil.removeAll();
		
		JLabel lNombre = new JLabel();
		JLabel lCountry = new JLabel();
		JLabel lLast= new JLabel();
		JLabel lTotal = new JLabel();
		JLabel lFriends = new JLabel();
		JLabel lGames = new JLabel();
		JLabel lFavGen = new JLabel();

		panelPerfil.setBorder(new TitledBorder("Perfil"));
		
//		muestra el perfil propio si no se ha seleccionado ningun amigo
		lNombre.setText("Nombre: " + VLogin.loggedUser.getUsername());
		lCountry.setText("Pais: " + VLogin.loggedUser.getCountry());
		lLast.setText("Ultima vez jugado: " + VLogin.loggedUser.getLastTimePlayed());
		lTotal.setText("Total de horas jugadas: " + VLogin.loggedUser.getTotalTimePlayed());
		
		String amigos = "";
        for (Integer i : VLogin.loggedUser.getFriends()) {
            User u = VLogin.dbManager.getUser(i);
            amigos += u.getUsername() + ", ";
        }
        amigos = amigos.substring(0, amigos.length()-2);

        lFriends.setText("Numero de amigos: " + amigos);
        
		if (VLogin.loggedUser.getGames().isEmpty()) {
			lGames.setText("NO HAS COMPRADO NINGUN JUEGO");
			lFavGen.setText("NO SE PUEDE CALCULAR EL GENERO FAVORITO SIN HABER COMPRADO AL MENOS 1 JUEGO");
		} else {
			lGames.setText("Juegos comprados: " + VLogin.loggedUser.getGames());
			lFavGen.setText("Genero favorito: " + generoFav(VLogin.loggedUser.getGames()));
			
		}
		
//		muestra el perfil del amigo seleccionado
		ListSelectionListener lsl = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (!e.getValueIsAdjusting()){
					JList<User> list = (JList<User>) e.getSource();
					
					if (list.getSelectedIndex() > -1) {
						User u = list.getSelectedValue();
						
						lNombre.setText("Nombre: " + u.getUsername());
						lCountry.setText("Pais: " + u.getCountry());
						lLast.setText("Ultima vez jugado: " + u.getLastTimePlayed());
						lTotal.setText("Total de horas jugadas: " + u.getTotalTimePlayed());
						String amigos = "";
				        for (Integer i : u.getFriends()) {
				            User user = VLogin.dbManager.getUser(i);
				            amigos += user.getUsername() + ", ";
				        }
				        amigos = amigos.substring(0, amigos.length()-2);

				        lFriends.setText("Amigos: " + amigos);
						
						if (u.getGames().isEmpty()) {
							lGames.setText(u.getUsername().toUpperCase() + " NO HA COMPRADO NINGUN JUEGO");
							lFavGen.setText("NO SE PUEDE CALCULAR EL GENERO FAVORITO SIN HABER COMPRADO AL MENOS 1 JUEGO");
						} else {
							lGames.setText("Juegos comprados: " + u.getGames());
							lFavGen.setText("Genero favorito: " + generoFav(u.getGames()));
						}
					}
				}
			}
		};
		
		listAmigos.addListSelectionListener(lsl);

		panelPerfil.add(lNombre);
		panelPerfil.add(lCountry);
		panelPerfil.add(lLast);
		panelPerfil.add(lTotal);
		panelPerfil.add(lFriends);
		panelPerfil.add(lGames);
		panelPerfil.add(lFavGen);
		
		return panelPerfil;
	}
	
	public static void loadListaAmigos() {
		dlmAmigos.clear();
	
		ArrayList<Integer> friendsList = VLogin.dbManager.obtainDataFriendsUser(VLogin.loggedUser.getId());
		
		for (Integer friend : friendsList) {
			dlmAmigos.addElement(VLogin.dbManager.getUser(friend));
		}
		
	}
	
	public static GameGenre generoFav(ArrayList<Game> list) {
		HashMap<GameGenre, Integer> mapa = new HashMap<>();
		GameGenre fGenre = list.get(0).getGenre1();
		for (int i = 0; i < list.size(); i++) {
			
			if (mapa.keySet().contains(list.get(i).getGenre1())) {
				mapa.put(list.get(i).getGenre1(), mapa.get(list.get(i).getGenre1())+1);
			} else {
				mapa.put(list.get(i).getGenre1(), 1);
			}
			if (mapa.keySet().contains(list.get(i).getGenre2())) {
				mapa.put(list.get(i).getGenre2(), mapa.get(list.get(i).getGenre2())+1);
			} else {
				mapa.put(list.get(i).getGenre2(), 1);
			}
		}
		for (GameGenre genre : mapa.keySet()) {
			if (mapa.get(genre) >= mapa.get(fGenre)) {
				fGenre = genre;
			}
		}
		return fGenre;
	}
	
	
}
