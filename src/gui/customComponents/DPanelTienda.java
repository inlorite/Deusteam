package gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import gui.VDeusteam;
import gui.VLogin;
import gui.VSaldo;
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
	protected static DefaultTableModel dtmJuegos;
	protected static JTable tJuegos;
	protected static JPanel panelTablaJuegos;
	protected static DefaultTableModel dtmMerch;
	protected static JTable tMerch;
	protected static JPanel panelTablaMerch;
	public static JButton bSaldo;
	protected JButton bComprar;
	protected JButton bJuegos;
	protected JButton bMerch;
	protected JLabel lBanner;
	protected JLabel lInfo;
	protected JPanel panelDatos;
	
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
		tBuscador.setColumns(25);
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
		
		panelTablaJuegos = new JPanel(new BorderLayout());
		panelTablaJuegos.setBorder(new TitledBorder("Juegos disponibles"));
		dtmJuegos = new DefaultTableModel(new Object[] { "Nombre", "Creador", "Precio" }, 0);
		tJuegos = new JTable(dtmJuegos);
		panelTablaJuegos.add(new JScrollPane(tJuegos), BorderLayout.CENTER);
		panelInfo.add(panelTablaJuegos);
		
		List<Game> listJuegos = VLogin.dbManager.obtainDataGames();
		
		ActionListener comprarJuegoListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listJuegos.get(tJuegos.getSelectedRow()).getPrice() <= VLogin.loggedUser.getBalance()) {
					VLogin.loggedUser.setBalance(VLogin.loggedUser.getBalance() - listJuegos.get(tJuegos.getSelectedRow()).getPrice());
					//VLogin.loggedUser.addGame(listJuegos.get(tJuegos.getSelectedRow()));
				}
				
				bSaldo.setText("Saldo: " + VLogin.loggedUser.getBalance() + "$");
				
				revalidate();
				repaint();
			}
		};
		for (Game game : listJuegos) {
			JButton bComprarJuego = new JButton(game.getPrice() + "$");
			bComprarJuego.addActionListener(comprarJuegoListener);
			dtmJuegos.addRow( new Object[] { game.getName(), game.getOwner(), bComprarJuego } );
		}
		
		panelTablaMerch = new JPanel(new BorderLayout());
		panelTablaMerch.setBorder(new TitledBorder("Merch disponible"));
		dtmMerch = new DefaultTableModel(new Object[] { "Nombre", "Precio" }, 0);
		tMerch = new JTable(dtmMerch);
		panelTablaMerch.add(new JScrollPane(tMerch), BorderLayout.CENTER);
		
		List<Merch> listMerch = VLogin.dbManager.obtainDataMerch();
		
		ActionListener comprarMerchListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listMerch.get(tMerch.getSelectedRow()).getPrice() <= VLogin.loggedUser.getBalance()) {
					VLogin.loggedUser.setBalance(VLogin.loggedUser.getBalance() - listMerch.get(tMerch.getSelectedRow()).getPrice());
					//VLogin.loggedUser.addMerch(listMerch.get(tMerch.getSelectedRow()));
				}
				
				bSaldo.setText("Saldo: " + VLogin.loggedUser.getBalance() + "$");
				
				revalidate();
				repaint();
			}
		};
		for (Merch merch : listMerch) {
			JButton bComprarMerch = new JButton(merch.getPrice() + "$");
			bComprarMerch.addActionListener(comprarMerchListener);
			dtmMerch.addRow( new Object[] { merch.getName(), comprarMerchListener } );
		}
		
		// Info del elemento seleccionado
		
		panelDatos = new JPanel(new GridLayout(2, 1));
		
		lBanner = new JLabel("");
		lInfo = new JLabel("");
		
		panelDatos.add(lBanner);
		panelDatos.add(lInfo);
		
		tJuegos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tJuegos.getSelectedRow() != -1) {
					if (listJuegos.get(tJuegos.getSelectedRow()) != null) {
						Game g = listJuegos.get(tJuegos.getSelectedRow());
						
						ImageIcon ii = new ImageIcon(g.getImgLink());
						lBanner.setIcon(ii);
						lInfo.setText(g.getDescription());
						
						revalidate();
						repaint();
					}
				}
			}
		});
		
		tMerch.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tMerch.getSelectedRow() != -1) {
					if (listMerch.get(tMerch.getSelectedRow()) != null) {
						Merch m = listMerch.get(tMerch.getSelectedRow());
						
						lBanner.setIcon(null);
						lInfo.setText(m.toString());
						
						revalidate();
						repaint();
					}
				}
			}
		});
		
		panelInfo.add(panelDatos);
		
		panel.add(panelInfo, BorderLayout.CENTER);
		
		// PanelCompras
		
		bComprar = new JButton("Comprar: 0,00$"); // Aqui hay que traer el dato desde la BD, cambiar a modo JTable
		bSaldo = new JButton("Saldo: " + VLogin.loggedUser.getBalance() + "$");
		
		JPanel panelCompras = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.add(bComprar, BorderLayout.EAST);
		panelCompras.add(bSaldo, BorderLayout.WEST);
		
		panel.add(panelCompras, BorderLayout.SOUTH);
		
		// Listeners
		
		bJuegos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInfo.remove(panelTablaMerch);
				panelInfo.remove(panelDatos);
				
				if (panelInfo.getComponents().length==0) {
					// si cambia de merch a juegos se ejecuta
					lBanner.setIcon(null);
					lInfo.setText("");
					tJuegos.clearSelection();
				}
				
				panelInfo.add(panelTablaJuegos);
				panelInfo.add(panelDatos);
				
				revalidate();
				repaint();
			}
		});
		
		bMerch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInfo.remove(panelTablaJuegos);
				panelInfo.remove(panelDatos);
				
				if (panelInfo.getComponents().length==0) {
					// si cambia de juegos a merch se ejecuta
					lBanner.setIcon(null);
					lInfo.setText("");
					tMerch.clearSelection();
				}
				
				panelInfo.add(panelTablaMerch);
				panelInfo.add(panelDatos);
				
				revalidate();
				repaint();
			}
		});
		/*
		bComprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listJuegos.get(tJuegos.getSelectedRow()) != null) {
					if (listJuegos.get(tJuegos.getSelectedRow()).getPrice() <= VLogin.loggedUser.getBalance()) {
						VLogin.loggedUser.setBalance(VLogin.loggedUser.getBalance() - listJuegos.get(tJuegos.getSelectedRow()).getPrice());
						//VLogin.loggedUser.addGame(listJuegos.get(tJuegos.getSelectedRow()));
					}
				} else if (listMerch.get(tMerch.getSelectedRow()) != null) {
					if (listMerch.get(tMerch.getSelectedRow()).getPrice() <= VLogin.loggedUser.getBalance()) {
						VLogin.loggedUser.setBalance(VLogin.loggedUser.getBalance() - listMerch.get(tMerch.getSelectedRow()).getPrice());
						//VLogin.loggedUser.addMerch(listMerch.get(tMerch.getSelectedRow()));
					}
				}
				
				bSaldo.setText("Saldo: " + VLogin.loggedUser.getBalance() + "$");
				
				revalidate();
				repaint();
			}
		});
		*/
		bSaldo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (VSaldo.vSaldo == null) {
					VSaldo.vSaldo = new VSaldo();
				}
				
			}
		});
		
		return panel;
	}
	
}