package gui.customComponents;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.VDeusteam;
import gui.VLogin;
import negocio.Game;

public class DPanelBiblioteca extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// West Lista Juegos
	protected JList<Game> lJuegosInstalados;
	protected DefaultListModel<Game> dlmJuegosInstalados;
	protected JList<Game> lJuegosDisponibles;
	protected DefaultListModel<Game> dlmJuegosDisponibles;
	
	// Center Info Juego
	protected JLabel lBanner;
	protected JLabel lInfo;
	protected JButton bInstalar;
	
	public DPanelBiblioteca() {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel pListaJuegos = getWestListaJuegos();
		JPanel pInfoJuego = getCenterInfoJuego();
		
		this.add(pListaJuegos, BorderLayout.WEST);
		this.add(pInfoJuego, BorderLayout.CENTER);
	}
	
	public JPanel getWestListaJuegos() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		
		JPanel panelInstalados = new JPanel(new BorderLayout());
		panelInstalados.setBorder(new TitledBorder("Juegos instalados"));
		dlmJuegosInstalados = new DefaultListModel<>();
		lJuegosInstalados = new JList<>(dlmJuegosInstalados);
		lJuegosInstalados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelInstalados.add(new JScrollPane(lJuegosInstalados), BorderLayout.CENTER);
		
		JPanel panelDisponibles = new JPanel(new BorderLayout());
		panelDisponibles.setBorder(new TitledBorder("Juegos Disponibles"));
		dlmJuegosDisponibles = new DefaultListModel<>();
		lJuegosDisponibles = new JList<>(dlmJuegosDisponibles);
		lJuegosDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelDisponibles.add(new JScrollPane(lJuegosDisponibles), BorderLayout.CENTER);
		
		Map<Game, Boolean> userGames = VLogin.dbManager.obtainDataPropertyUserInstalledGames(VLogin.loggedUser.getId());
		
		for (Game game : userGames.keySet()) {
			if (userGames.get(game)) {
				dlmJuegosInstalados.addElement(game);
			} else {
				dlmJuegosDisponibles.addElement(game);
			}
		}
		
		ArrayList<JList<Game>> lists = new ArrayList<>();
		lists.add(lJuegosDisponibles);
		lists.add(lJuegosInstalados);
		
		ListSelectionListener lsl = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				for (JList<Game> list : lists) {
					if (list != e.getSource()) {
						list.clearSelection();
					}
					
					if (list == e.getSource()) {
						if (!e.getValueIsAdjusting()){
							Game g = list.getSelectedValue();
							
							ImageIcon ii = new ImageIcon("data/game_banners/" + g.getId() + ".jpg");
							lBanner.setIcon(ii);
							lInfo.setText(g.getDescription());
							
							revalidate();
							repaint();
						}
					}
				}
			}
		};
		
		lJuegosInstalados.addListSelectionListener(lsl);
		lJuegosDisponibles.addListSelectionListener(lsl);
		
		panel.add(panelInstalados);
		panel.add(panelDisponibles);
		
		return panel;
	}
	
	public JPanel getCenterInfoJuego() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		lBanner = new JLabel("");
		lInfo = new JLabel("info");
		bInstalar = new JButton("Instalar");
		
		panel.add(lBanner, BorderLayout.NORTH);
		panel.add(lInfo, BorderLayout.CENTER);
		panel.add(bInstalar, BorderLayout.SOUTH);
		
		return panel;
	}

}
