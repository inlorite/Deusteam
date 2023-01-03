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

import gui.VDeusteam;
import gui.VLogin;
import negocio.*;

public class DPanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// West Lista Amigos
	protected JList<User> listAmigos;
	protected DefaultListModel<User> dlmAmigos;
	protected JButton bBusqueda;
	protected JTextField tBuscador;
	
	
	// Center Perfil Usuario
	protected JLabel lPerfil;
	
	//Botones modificar
	protected JButton bEditar = new JButton("Editar");
	protected JButton bCrear = new JButton("Crear");
	protected JButton bBorrar = new JButton("Borrar");

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
		 JLabel lTitulo = new JLabel("Modificacion juegos: ");
		 
		 pBoton.add(bBorrar);
		 pBoton.add(bCrear);
		 pBoton.add(bEditar);
		 
		 panel.add(lTitulo, BorderLayout.NORTH);
		 panel.add(pBoton, BorderLayout.CENTER);
		 
		 bCrear.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
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
		panelBusqueda.setBorder(new TitledBorder("Buscador de amigos"));
		tBuscador = new JTextField("Introduce un termino de busqueda");
		panelBusqueda.add(tBuscador);
		bBusqueda = new JButton("Buscar");
		panelBusqueda.add(bBusqueda);
		
		panel.add(panelAmigos);
		panel.add(panelBusqueda);
		return panel;
	}
	
	public JPanel getCenterPerfilUsuario() {
		JPanel panel = new JPanel(new GridLayout(7,1));
		JLabel lNombre = new JLabel();
		JLabel lCountry = new JLabel();
		JLabel lLast= new JLabel();
		JLabel lTotal = new JLabel();
		JLabel lFriends = new JLabel();
		JLabel lGames = new JLabel();
		JLabel lFavGen = new JLabel();
		
		panel.setBorder(new TitledBorder("Perfil"));
		
//		muestra el perfil propio si no se ha seleccionado ningun amigo
		lNombre.setText("Nombre: " + VLogin.loggedUser.getUsername());
		lCountry.setText("Pais: " + VLogin.loggedUser.getCountry());
		lLast.setText("Ultima vez jugado: " + VLogin.loggedUser.getLastTimePlayed());
		lTotal.setText("Total de horas jugadas: " + VLogin.loggedUser.getTotalTimePlayed());
		lFriends.setText("Numero de amigos: " + VLogin.loggedUser.getFriends());
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
						lFriends.setText("Numero de amigos: " + u.getFriends());
						
						if (u.getGames().isEmpty()) {
							lGames.setText(u.getUsername().toUpperCase() + " NO HA COMPRADO NINGUN JUEGO");
							lFavGen.setText("NO SE PUEDE CALCULAR EL GENERO FAVORITO SIN HABER COMPRADO AL MENOS 1 JUEGO");
						} else {
							lGames.setText("Juegos comprados: " + u.getGames());
							lFavGen.setText("Genero favorito: " + generoFav(u.getGames()));
						}
						
						
						revalidate();
						repaint();
					}
				}
			}
		};
		
		listAmigos.addListSelectionListener(lsl);
		
		panel.add(lNombre);
		panel.add(lCountry);
		panel.add(lLast);
		panel.add(lTotal);
		panel.add(lFriends);
		panel.add(lGames);
		panel.add(lFavGen);
		
		return panel;
	}
	
	public void loadListaAmigos() {
		dlmAmigos.clear();
	
		ArrayList<Integer> friendsList = VLogin.dbManager.obtainDataFriendsUser(VLogin.loggedUser.getId());
		
		for (Integer friend : friendsList) {
			dlmAmigos.addElement(VLogin.dbManager.getUser(friend));
		}
		
	}
	
	public GameGenre generoFav(ArrayList<Game> list) {
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
