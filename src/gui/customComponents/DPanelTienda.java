package gui.customComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import gui.VDeusteam;
import gui.VLogin;
import gui.VRecomendar;
import gui.VSaldo;
import negocio.Game;
import negocio.GameGenre;
import negocio.Merch;
import negocio.MerchType;
import negocio.Pegi;

public class DPanelTienda extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// West Selector
	protected static JTextField tBuscador;
	protected static JComboBox<GameGenre> cCat1;
	protected static DefaultComboBoxModel<GameGenre> dcmCat1;
	protected static JComboBox<GameGenre> cCat2;
	protected static DefaultComboBoxModel<GameGenre> dcmCat2;
	protected static JComboBox<MerchType> cCatMerch;
	protected static DefaultComboBoxModel<MerchType> dcmCatMerch;
	protected static JComboBox<Pegi> cPegi;
	protected static DefaultComboBoxModel<Pegi> dcmPegi;
	protected static JCheckBox cbPosesion;
	protected static JPanel panelCategoria;
		
	// Center Info Juego
	public static DefaultTableModel dtmJuegos;
	public static JTable tJuegos;
	public static JPanel panelTablaJuegos;
	public static DefaultTableModel dtmMerch;
	public static JTable tMerch;
	public static JPanel panelTablaMerch;
	public static JButton bSaldo;
	protected JButton bRecomendar;
	protected static JButton bComprar;
	protected JButton bJuegos;
	protected JButton bMerch;
	protected static JLabel lBanner;
	protected static JLabel lInfo;
	protected JPanel panelDatos;
	
	public static List<Game> listJuegos;
	public static List<Merch> listMerch;
	
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
		
		panelCategoria = new JPanel(new GridLayout(4, 1));
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
		
		dcmCatMerch = new DefaultComboBoxModel<>();
		cCatMerch = new JComboBox<MerchType>(dcmCatMerch);
		for (MerchType type : MerchType.values()) {
			dcmCatMerch.addElement(type);
		}
		cCatMerch.setBorder(new TitledBorder("Tipo de merch:"));
		
		cbPosesion = new JCheckBox("Mostrar los que no posees");
		
		panelCategoria.add(cCat1);
		panelCategoria.add(cCat2);
		panelCategoria.add(cPegi);
		panelCategoria.add(cbPosesion);
		
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
		
		listJuegos = VLogin.dbManager.obtainDataGames();
		/*
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
		};*/
		//loadTablaJuegos();
		for (Game game : listJuegos) {
			dtmJuegos.addRow( new Object[] { game.getName(), game.getOwner(), game.getPrice() + "$" } );
		}
		
		panelTablaMerch = new JPanel(new BorderLayout());
		panelTablaMerch.setBorder(new TitledBorder("Merch disponible"));
		dtmMerch = new DefaultTableModel(new Object[] { "Nombre", "Creador", "Precio" }, 0);
		tMerch = new JTable(dtmMerch);
		panelTablaMerch.add(new JScrollPane(tMerch), BorderLayout.CENTER);
		
		listMerch = VLogin.dbManager.obtainDataMerch();
		/*
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
		};*/
		for (Merch merch : listMerch) {
			dtmMerch.addRow( new Object[] { merch.getName(), merch.getOwner(), merch.getPrice() + "$" } );
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
						lInfo.setText("<html><p>" + g.getDescription() + "</p></html>");
						bComprar.setEnabled(true);
						
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
						lInfo.setText("<html><p>" + m.getDescription() + "</p></html>");
						bComprar.setEnabled(true);
						
						revalidate();
						repaint();
					}
				}
			}
		});
		
		panelInfo.add(panelDatos);
		
		panel.add(panelInfo, BorderLayout.CENTER);
		
		// Renders
		
		DefaultTableCellRenderer gameRenderer = new DefaultTableCellRenderer() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel resultado = new JLabel(value.toString());
				String nombre = (String) table.getModel().getValueAt(row, 0);
				Game game = listJuegos.get(row);
				
				if (highlighted(game, nombre)) {
					resultado.setBackground(Color.PINK);
				}
				
				if (isSelected) {
					resultado.setBackground(Color.CYAN);
				}
				
				resultado.setOpaque(true);
				
				return resultado;
			}
			
		};
		
		for (int i = 0; i < tJuegos.getColumnCount(); i++) {
			tJuegos.getColumnModel().getColumn(i).setCellRenderer(gameRenderer);
		}
		
		DefaultTableCellRenderer merchRenderer = new DefaultTableCellRenderer() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel resultado = new JLabel(value.toString());
				String nombre = (String) table.getModel().getValueAt(row, 0);
				Merch merch = listMerch.get(row);
				
				if (highlighted(merch, nombre)) {
					resultado.setBackground(Color.PINK);
				}
				
				if (isSelected) {
					resultado.setBackground(Color.CYAN);
				}
				
				resultado.setOpaque(true);
				
				return resultado;
			}
			
		};
		
		for (int i = 0; i < tMerch.getColumnCount(); i++) {
			tMerch.getColumnModel().getColumn(i).setCellRenderer(merchRenderer);
		}
		
		// PanelCompras
		
		bComprar = new JButton("Comprar"); // Aqui hay que traer el dato desde la BD, cambiar a modo JTable
		bComprar.setEnabled(false);
		bRecomendar = new JButton("Recomendar");
		bSaldo = new JButton("Saldo: " + VLogin.loggedUser.getBalance() + "$");
		
		JPanel panelCompras = new JPanel(new GridLayout(1, 3, 10, 10));
		panelCompras.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.add(bComprar);
		panelCompras.add(bRecomendar);
		panelCompras.add(bSaldo);
		
		panel.add(panelCompras, BorderLayout.SOUTH);
		
		// Listeners
		
		bJuegos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInfo.remove(panelTablaMerch);
				panelInfo.remove(panelDatos);
				
				panelCategoria.remove(cCatMerch);
				panelCategoria.remove(cbPosesion);
				
				if (panelInfo.getComponents().length==0) {
					// si cambia de merch a juegos se ejecuta
					lBanner.setIcon(null);
					lInfo.setText("");
					tJuegos.clearSelection();
					
					VRecomendar.modo = true;
				}
				
				panelCategoria.add(cCat1);
				panelCategoria.add(cCat2);
				panelCategoria.add(cPegi);
				panelCategoria.add(cbPosesion);
				
				panelInfo.add(panelTablaJuegos);
				panelInfo.add(panelDatos);
				
				bComprar.setEnabled(false);
				
				revalidate();
				repaint();
			}
		});
		
		bMerch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInfo.remove(panelTablaJuegos);
				panelInfo.remove(panelDatos);
				
				panelCategoria.remove(cCat1);
				panelCategoria.remove(cCat2);
				panelCategoria.remove(cPegi);
				panelCategoria.remove(cbPosesion);
				
				if (panelInfo.getComponents().length==0) {
					// si cambia de juegos a merch se ejecuta
					lBanner.setIcon(null);
					lInfo.setText("");
					tMerch.clearSelection();
					
					VRecomendar.modo = false;
				}
				
				panelCategoria.add(cCatMerch);
				panelCategoria.add(cbPosesion);
				
				panelInfo.add(panelTablaMerch);
				panelInfo.add(panelDatos);
				
				bComprar.setEnabled(false);
				
				revalidate();
				repaint();
			}
		});
		
		bComprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (VRecomendar.modo) {
						if (listJuegos.get(tJuegos.getSelectedRow()) != null) {
							if (listJuegos.get(tJuegos.getSelectedRow()).getPrice() <= VLogin.loggedUser.getBalance()) {
								VLogin.loggedUser.addGame(listJuegos.get(tJuegos.getSelectedRow()));
							}
						}
					} else {
						if (listMerch.get(tMerch.getSelectedRow()) != null) {
							if (listMerch.get(tMerch.getSelectedRow()).getPrice() <= VLogin.loggedUser.getBalance()) {
								VLogin.loggedUser.addMerch(listMerch.get(tMerch.getSelectedRow()));
							}
						}
					}
				} catch (Exception e2) {
					System.err.println("No se ha seleccionado ningun elemento");
				}
				
				bSaldo.setText("Saldo: " + VLogin.loggedUser.getBalance() + "$");
				
				revalidate();
				repaint();
			}
		});
		
		bRecomendar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (VRecomendar.vRecomendar == null) {
					VRecomendar.vRecomendar = new VRecomendar();
				}
			}
		});
		
		bSaldo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (VSaldo.vSaldo == null) {
					VSaldo.vSaldo = new VSaldo();
				}else {
					VSaldo.vSaldo.setVisible(true);
				}
				
			}
		});
		
		tBuscador.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				revalidate();
				repaint();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				revalidate();
				repaint();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				revalidate();
				repaint();
			}
		});
		
		ActionListener listenerSearch = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				revalidate();
				repaint();
			}
		};
		
		cCat1.addActionListener(listenerSearch);
		
		cCat2.addActionListener(listenerSearch);

		cPegi.addActionListener(listenerSearch);
		
		cCatMerch.addActionListener(listenerSearch);
		
		cbPosesion.addActionListener(listenerSearch);
		
		return panel;
	}
	
	/** Verifies if the game verifies the advanced search chart
	 * @param game			Game class object
	 * @param nombre		String of the name
	 * 
	 * @return true if the game verifies the advanced search conditions, else false
	 */
	public static boolean highlighted(Game game, String nombre) {
		
		if (cbPosesion.isSelected()) {
			
			boolean isProperty = VLogin.dbManager.isGamePropertyOfUser(VLogin.loggedUser.getId(), game.getId());
			
			if (!tBuscador.getText().equals("")) {
				
				if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1())))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre1()) || cCat2.getSelectedItem().equals(game.getGenre2()))
							&& nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2()))
							&& nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1())))
							&& nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre2()) || cCat2.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				}
				
			} else {
				
				if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1())))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (cPegi.getSelectedItem().equals(game.getPegi()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre1()) || cCat2.getSelectedItem().equals(game.getGenre2())) && !isProperty) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2())) && !isProperty) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1()))) && !isProperty) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre1()) || cCat2.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && !isProperty) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (!isProperty) {
						return true;
					}
				}
				
			}
			
		} else {
			
			if (!tBuscador.getText().equals("")) {
				
				if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1())))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre1()) || cCat2.getSelectedItem().equals(game.getGenre2()))
							&& nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2()))
							&& nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1())))
							&& nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre2()) || cCat2.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi()) && nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (nombre.toString().startsWith(tBuscador.getText())) {
						return true;
					}
				}
				
			} else {
				
				if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1())))
							&& cPegi.getSelectedItem().equals(game.getPegi())) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (cPegi.getSelectedItem().equals(game.getPegi())) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre1()) || cCat2.getSelectedItem().equals(game.getGenre2()))) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2()))) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if (((cCat1.getSelectedItem().equals(game.getGenre1()) && cCat2.getSelectedItem().equals(game.getGenre2()))
							|| (cCat1.getSelectedItem().equals(game.getGenre2()) && cCat2.getSelectedItem().equals(game.getGenre1())))) {
						return true;
					}
				} else if (!cCat1.getSelectedItem().equals(GameGenre.NULL) && cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat1.getSelectedItem().equals(game.getGenre1()) || cCat1.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi())) {
						return true;
					}
				} else if (cCat1.getSelectedItem().equals(GameGenre.NULL) && !cCat2.getSelectedItem().equals(GameGenre.NULL) && !cPegi.getSelectedItem().equals(Pegi.NULL)) {
					if ((cCat2.getSelectedItem().equals(game.getGenre1()) || cCat2.getSelectedItem().equals(game.getGenre2()))
							&& cPegi.getSelectedItem().equals(game.getPegi())) {
						return true;
					}
				}
				
			}
			
		}
		
		return false;
		
	}
	
	/** Verifies if the merch verifies the advanced search chart
	 * @param merch			Merch class object
	 * @param nombre		String of the name
	 * 
	 * @return true if the merch verifies the advanced search conditions, else false
	 */
	public static boolean highlighted(Merch merch, String nombre) {
		
		if (cbPosesion.isSelected()) {
			
			boolean isProperty = VLogin.dbManager.isMerchPropertyOfUser(VLogin.loggedUser.getId(), merch.getId());
			
			if (cCatMerch.getSelectedItem().equals(MerchType.NULL)) {
				if (!tBuscador.getText().equals("")) {
					if (nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else {
					if (!isProperty) {
						return true;
					}
				}
			} else {
				if (!tBuscador.getText().equals("")) {
					if (cCatMerch.getSelectedItem().equals(merch.getType()) && nombre.toString().startsWith(tBuscador.getText()) && !isProperty) {
						return true;
					}
				} else {
					if (cCatMerch.getSelectedItem().equals(merch.getType()) && !isProperty) {
						return true;
					}
				}
			}
			
		} else {
			
			if (cCatMerch.getSelectedItem().equals(MerchType.NULL)) {
				if (nombre.toString().startsWith(tBuscador.getText()) && !tBuscador.getText().equals("")) {
					return true;
				}
			} else {
				if (nombre.toString().startsWith(tBuscador.getText()) && !tBuscador.getText().equals("") && cCatMerch.getSelectedItem().equals(merch.getType())) {
					return true;
				} else if (tBuscador.getText().equals("") && cCatMerch.getSelectedItem().equals(merch.getType())) {
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	public static void loadTablaJuegos() {
		dtmJuegos.setRowCount(0);
		listJuegos = VLogin.dbManager.obtainDataGames();
		
		for (Game game : listJuegos) {
			dtmJuegos.addRow( new Object[] { game.getName(), game.getOwner(), game.getPrice() + "$" } );
		}
		
	}
	
}