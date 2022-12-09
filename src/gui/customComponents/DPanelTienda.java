package gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


import gui.VDeusteam;
import gui.VLogin;
import negocio.Game;
import negocio.Merch;

public class DPanelTienda extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// West Selector
	protected JTextField tBuscador;
	protected JCheckBox cCategoria;
		
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
		tBuscador = new JTextField("Introduce un termino de busqueda");
		panelBuscador.add(tBuscador, BorderLayout.CENTER);
		
		JPanel panelCategoria = new JPanel(new BorderLayout());
		panelCategoria.setBorder(new TitledBorder("Selector de categorias"));
		cCategoria = new JCheckBox("Ejemplo");
		panelCategoria.add(cCategoria, BorderLayout.CENTER);
		
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
		panelListaJuegos.add(new JScrollPane(lMerch), BorderLayout.CENTER);
		
		List<Merch> listMerch = VLogin.dbManager.obtainDataMerch();
		for (Merch merch : listMerch) {
			dlmMerch.addElement(merch);
		}
		
		panel.add(panelInfo, BorderLayout.CENTER);
		
		// PanelCompras
		
		bComprar = new JButton("Comprar: 0,00$"); // Aqui hay que traer el dato desde la BD
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
				
				revalidate();
				repaint();
			}
		});
		
		bMerch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInfo.remove(panelListaJuegos);
				panelInfo.add(panelListaMerch);
				
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
