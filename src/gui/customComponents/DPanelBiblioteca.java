package gui.customComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import gui.VDeusteam;
import gui.VLogin;
import negocio.*;

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
	
	// Center Merch
	protected JTable tMerch;
	protected static DefaultTableModel dtmMerch;
	protected static JButton bArtbook;
	protected static JButton bSoundtrack;
	
	public DPanelBiblioteca() {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(VDeusteam.GAP/2, VDeusteam.GAP/2, VDeusteam.GAP/2, VDeusteam.GAP/2));
		
		JTabbedPane tpBiblioteca = new JTabbedPane();
		
		JPanel pJuegos = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		pJuegos.setBorder(new EmptyBorder(VDeusteam.GAP/2, VDeusteam.GAP/2, VDeusteam.GAP/2, VDeusteam.GAP/2));
		
		JPanel pInfoJuego = getCenterInfoJuego();
		JPanel pListaJuegos = getWestListaJuegos();
		pJuegos.add(pListaJuegos, BorderLayout.WEST);
		pJuegos.add(pInfoJuego, BorderLayout.CENTER);
		
		JPanel pMerch = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel pCenterMerch = getCenterMerch();
		pMerch.add(pCenterMerch, BorderLayout.CENTER);
		
		tpBiblioteca.addTab("Juegos", pJuegos);
		tpBiblioteca.addTab("Merch", pMerch);
		
		loadDataModels();
		
		this.add(tpBiblioteca, BorderLayout.CENTER);
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
						lBanner.setHorizontalAlignment(SwingConstants.CENTER);
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
		
		if (lJuegosInstalados.getModel().getSize() > 0) {
			lJuegosInstalados.setSelectedIndex(0);
		} else if (lJuegosDisponibles.getModel().getSize() > 0) {
			lJuegosDisponibles.setSelectedIndex(0);
		}
		
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
		dtmMerch.setRowCount(0);
		
		Map<Game, Boolean> userGames = VLogin.dbManager.obtainDataPropertyUserInstalledGames(VLogin.loggedUser.getId());
		
		for (Game game : userGames.keySet()) {
			if (userGames.get(game)) {
				dlmJuegosInstalados.addElement(game);
			} else {
				dlmJuegosDisponibles.addElement(game);
			}
		}
		
		List<Merch> userMerch = VLogin.dbManager.obtainDataPropertyMerchUser(VLogin.loggedUser.getId());
		
		bArtbook = new JButton("Mirar artbook");
		bArtbook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Cargando artbook...", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		bSoundtrack = new JButton("Escuchar soundtrack");
		bSoundtrack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Reproduciendo soundtrack...", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		for (Merch merch : userMerch) {
			if (merch.getType().equals(MerchType.Soundtrack)) {
				dtmMerch.addRow(new Object[] {merch.getName(), merch.getOwner(), merch.getType().toString(), bSoundtrack});
			} else if (merch.getType().equals(MerchType.Artbook)) {
				dtmMerch.addRow(new Object[] {merch.getName(), merch.getOwner(), merch.getType().toString(), bArtbook});
			} else {
				dtmMerch.addRow(new Object[] {merch.getName(), merch.getOwner(), merch.getType().toString(), "---"});
			}
		}
	}
	
	public JPanel getCenterMerch() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP/2, VDeusteam.GAP/2));
		
		dtmMerch = new DefaultTableModel(new Object[] { "Nombre", "Creador", "Tipo", "Opcion" }, 0);
		tMerch = new JTable(dtmMerch) {
			private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		panel.add(new JScrollPane(tMerch), BorderLayout.CENTER);
		
		DefaultTableCellRenderer buttonRenderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JButton button = (JButton) value;
												
				return button;
			}
		};
		
		tMerch.getColumnModel().getColumn(3).setCellRenderer(buttonRenderer);
		
		tMerch.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tMerch.rowAtPoint(e.getPoint());
				int col = tMerch.columnAtPoint(e.getPoint());
				
				if (col == 3) {
					JButton boton = (JButton) tMerch.getValueAt(row, col);
					boton.doClick();
				}
			}
		});
		
		return panel;
	}

}
