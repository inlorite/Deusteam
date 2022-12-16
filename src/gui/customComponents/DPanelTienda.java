package gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.VDeusteam;
import gui.VLogin;
import negocio.Game;
import negocio.GameGenre;
import negocio.Merch;
import negocio.MerchType;
import negocio.Pegi;

public class DPanelTienda extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// West Selector
	protected JTextField tBuscador;
	protected JComboBox<GameGenre> cCat1;
	protected DefaultComboBoxModel<GameGenre> dcmCat1;
	protected JComboBox<GameGenre> cCat2;
	protected DefaultComboBoxModel<GameGenre> dcmCat2;
	protected JComboBox<MerchType> cCatMerch;
	protected DefaultComboBoxModel<MerchType> dcmCatMerch;
	protected JComboBox<Pegi> cPegi;
	protected DefaultComboBoxModel<Pegi> dcmPegi;
		
	// Center Info Juego
	protected static JList<Game> lJuegos;
	protected static DefaultListModel<Game> dlmJuegos;
	protected static JPanel panelListaJuegos;
	protected static JList<Merch> lMerch;
	protected static DefaultListModel<Merch> dlmMerch;
	protected static JPanel panelListaMerch;
	protected JButton bSaldo;
	protected JButton bComprar;
	protected JButton bJuegos;
	protected JButton bMerch;
	protected JLabel lBanner;
	protected JLabel lInfo;
	protected static JPanel panelDatos;
	
	public DPanelTienda() {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel pBuscador = getWestBuscador();
		JPanel pResultado = getCenterResultado();
		
		this.add(pBuscador, BorderLayout.WEST);
		this.add(pResultado, BorderLayout.CENTER);
	}
	
	public JPanel getWestBuscador() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel panelBuscador = new JPanel(new BorderLayout());
		panelBuscador.setBorder(new TitledBorder("Buscador"));
		tBuscador = new JTextField();
		tBuscador.setColumns(15);
		panelBuscador.add(tBuscador, BorderLayout.CENTER);
		
		JPanel panelCategoria = new JPanel(new GridLayout(3, 1));
		panelCategoria.setBorder(new TitledBorder("Selector avanzado"));
		
		dcmCat1 = new DefaultComboBoxModel<>();
		cCat1 = new JComboBox<GameGenre>(dcmCat1);
		for (GameGenre genre : GameGenre.values()) {
			dcmCat1.addElement(genre);
		}
		cCat1.setBorder(new TitledBorder("Genero principal:"));
		
		dcmCat2 = new DefaultComboBoxModel<>();
		cCat2 = new JComboBox<GameGenre>(dcmCat2);
		for (GameGenre genre : GameGenre.values()) {
			dcmCat2.addElement(genre);
		}
		cCat2.setBorder(new TitledBorder("Genero secundario:"));
		
		dcmPegi = new DefaultComboBoxModel<>();
		cPegi = new JComboBox<Pegi>(dcmPegi);
		for (Pegi genre : Pegi.values()) {
			dcmPegi.addElement(genre);
		}
		cPegi.setBorder(new TitledBorder("Pegi:"));
		
		panelCategoria.add(cCat1);
		panelCategoria.add(cCat2);
		panelCategoria.add(cPegi);
		
		panel.add(panelBuscador, BorderLayout.NORTH);
		panel.add(panelCategoria, BorderLayout.CENTER);
		
		return panel;
	}
	
	public JPanel getCenterResultado() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		// PanelMenu
		
		JPanel panelMenu = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		panelMenu.setLayout(new GridLayout(1, 2, VDeusteam.GAP, VDeusteam.GAP));
		panelMenu.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, 0, VDeusteam.GAP));
		
		bJuegos = new JButton("Juegos");
		bMerch = new JButton("Merch");
		
		panelMenu.add(bJuegos);
		panelMenu.add(bMerch);
		panel.add(panelMenu, BorderLayout.NORTH);
		
		// PanelInfo
		
		// Lista juegos/merch
		
		JPanel panelInfo = new JPanel(new GridLayout(2, 1));
		
		panelListaJuegos = new JPanel(new BorderLayout());
		panelListaJuegos.setBorder(new TitledBorder("Juegos disponibles"));
		dlmJuegos = new DefaultListModel<>();
		lJuegos = new JList<>(dlmJuegos);
		panelListaJuegos.add(new JScrollPane(lJuegos), BorderLayout.CENTER);
		panelInfo.add(panelListaJuegos);
		
		List<Game> listJuegos = VLogin.dbManager.obtainDataGames();
		for (Game game : listJuegos) {
			dlmJuegos.addElement(game);
		}
		
		panelListaMerch = new JPanel(new BorderLayout());
		panelListaMerch.setBorder(new TitledBorder("Merch disponible"));
		dlmMerch = new DefaultListModel<>();
		lMerch = new JList<>(dlmMerch);
		panelListaMerch.add(new JScrollPane(lMerch), BorderLayout.CENTER);
		
		List<Merch> listMerch = VLogin.dbManager.obtainDataMerch();
		for (Merch merch : listMerch) {
			dlmMerch.addElement(merch);
		}
		
		// Info del elemento seleccionado
		
		panelDatos = new JPanel(new GridLayout(2, 1));
		
		lBanner = new JLabel("");
		lInfo = new JLabel("");
		
		panelDatos.add(lBanner);
		panelDatos.add(lInfo);
		
		lJuegos.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (lJuegos.getSelectedValue() != null) {
					Game g = lJuegos.getSelectedValue();
					
					ImageIcon ii = new ImageIcon("data/game_banners/" + g.getId() + ".jpg");
					lBanner.setIcon(ii);
					lInfo.setText(g.getDescription());
					
					revalidate();
					repaint();
				}
			}
		});
		
		panelInfo.add(panelDatos);
		
		panel.add(panelInfo, BorderLayout.CENTER);
		
		// PanelCompras
		
		bComprar = new JButton("Comprar: 0,00$"); // Aqui hay que traer el dato desde la BD, cambiar a modo JTable
		bSaldo = new JButton("Saldo: 0,00$");
		
		JPanel panelCompras = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.add(bComprar, BorderLayout.EAST);
		panelCompras.add(bSaldo, BorderLayout.WEST);
		
		panel.add(panelCompras, BorderLayout.SOUTH);
		
		// Listeners
		
		bJuegos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInfo.remove(panelListaMerch);
				panelInfo.add(panelListaJuegos);
				
				panelInfo.remove(panelDatos);
				panelInfo.add(panelDatos);
				lBanner.setIcon(null);
				lInfo.setText("");
				lMerch.clearSelection();
				
				revalidate();
				repaint();
			}
		});
		
		bMerch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInfo.remove(panelListaJuegos);
				panelInfo.add(panelListaMerch);
				
				panelInfo.remove(panelDatos);
				panelInfo.add(panelDatos);
				lBanner.setIcon(null);
				lInfo.setText("");
				lJuegos.clearSelection();
				
				revalidate();
				repaint();
			}
		});
		
		bComprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				try {
					if (lJuegos.getSelectedValue().getPrice() <= usuarioActual.getSaldo()) {
						usuarioActual.setSaldo(usuarioActual.getSaldo() - lJuegos.getSelectedValue().getPrice());
						usuarioActual.addGame(lJuegos.getSelectedValue());
					}
				} catch (Exception e2) {
					//
				}
				try {
					if (lMerch.getSelectedValue().getPrice() <= usuarioActual.getSaldo()) {
						usuarioActual.setSaldo(usuarioActual.getSaldo() - lMerch.getSelectedValue().getPrice());
						usuarioActual.addGame(lMerch.getSelectedValue());
					}
				} catch (Exception e2) {
					//
				}
				*/
				
				revalidate();
				repaint();
			}
		});
		
		return panel;
	}
	
}