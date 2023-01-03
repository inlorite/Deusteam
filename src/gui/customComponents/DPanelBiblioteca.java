package gui.customComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	protected static DefaultListModel<Game> dlmJuegosInstalados;
	protected JList<Game> lJuegosDisponibles;
	protected static DefaultListModel<Game> dlmJuegosDisponibles;
	protected JList<Game> selectedList;
	
	// Center Info Juego
	protected JLabel lBanner;
	protected JLabel lInfo;
	protected JProgressBar pbInstalar;
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
		
		loadDataModels();
		
		MouseAdapter ml = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedList = (JList<Game>) e.getSource();
				
				if (selectedList == lJuegosDisponibles) {
					lJuegosInstalados.clearSelection();
				} else if (selectedList == lJuegosInstalados) {
					lJuegosDisponibles.clearSelection();
				}
			}
		};
		
		ListSelectionListener lsl = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (!e.getValueIsAdjusting()){
					JList<Game> list = (JList<Game>) e.getSource();
					
					if (list.getSelectedIndex() > -1) {
						Game g = list.getSelectedValue();
						
						ImageIcon ii = new ImageIcon("data/game_banners/" + g.getId() + ".jpg");
						lBanner.setIcon(ii);
						lInfo.setText(g.getDescription());
						
						if (list == lJuegosDisponibles) {
							bInstalar.setText("Instalar");
						} else if (list == lJuegosInstalados) {
							bInstalar.setText("Desinstalar");
						}
						
						if (!bInstalar.isEnabled()) {
							bInstalar.setEnabled(true);
						}
						
						revalidate();
						repaint();
					}
				}
			}
		};
		
		lJuegosInstalados.addMouseListener(ml);
		lJuegosDisponibles.addMouseListener(ml);
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
		
		JPanel pInst = new JPanel(new GridLayout(1, 2, VDeusteam.GAP, VDeusteam.GAP));
		pbInstalar = new JProgressBar();
		pbInstalar.setValue(0);
		pbInstalar.setStringPainted(true);
		pbInstalar.setForeground(Color.GREEN);
		pbInstalar.setVisible(false);
		bInstalar = new JButton("Instalar");
		bInstalar.setEnabled(false);
		pInst.add(pbInstalar);
		pInst.add(bInstalar);
		
		panel.add(lBanner, BorderLayout.NORTH);
		panel.add(lInfo, BorderLayout.CENTER);
		panel.add(pInst, BorderLayout.SOUTH);
		
		bInstalar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread barThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						JList<Game> list = selectedList;
						Game game = list.getSelectedValue();
						
						lJuegosDisponibles.setEnabled(false);
						lJuegosInstalados.setEnabled(false);
						bInstalar.setEnabled(false);
						pbInstalar.setVisible(true);
						
						try {
							for (int i = 1; i <= 100; i++) {
								pbInstalar.setValue(i);
								Thread.sleep(100);
							}
							
							if (list == lJuegosDisponibles) {
								VLogin.dbManager.updatePropertyGamesInstalled(VLogin.loggedUser.getId(), game.getId(), 1);
								loadDataModels();
								JOptionPane.showMessageDialog(null, game.getName() + " instalado correctamente.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
							} else if (list == lJuegosInstalados) {
								VLogin.dbManager.updatePropertyGamesInstalled(VLogin.loggedUser.getId(), game.getId(), 0);
								loadDataModels();
								JOptionPane.showMessageDialog(null, game.getName() + " desinstalado correctamente.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						lJuegosDisponibles.setEnabled(true);
						lJuegosInstalados.setEnabled(true);
						pbInstalar.setVisible(false);
					}
				});
				barThread.start();
			}
		});
		
		return panel;
	}
	
	public static void loadDataModels() {
		dlmJuegosInstalados.clear();
		dlmJuegosDisponibles.clear();
		
		Map<Game, Boolean> userGames = VLogin.dbManager.obtainDataPropertyUserInstalledGames(VLogin.loggedUser.getId());
		
		for (Game game : userGames.keySet()) {
			if (userGames.get(game)) {
				dlmJuegosInstalados.addElement(game);
			} else {
				dlmJuegosDisponibles.addElement(game);
			}
		}
	}

}
