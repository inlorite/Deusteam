package gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
	protected static JList<Merch> lMerch;
	protected static DefaultListModel<Merch> dlmMerch;
	protected JButton bSaldo;
	protected JButton bComprar;
	
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
		
		DMenuBar menuBar = new DMenuBar(1);
		panel.add(menuBar, BorderLayout.NORTH);
		
		JPanel panelInfo = new JPanel(new GridLayout(2, 1));
		// Panel Info
		JPanel panelLista = new JPanel(new BorderLayout());
		panelLista.setBorder(new TitledBorder("Juegos disponibles"));
		dlmJuegos = new DefaultListModel<>();
		lJuegos = new JList<>(dlmJuegos);
		panelLista.add(new JScrollPane(lJuegos), BorderLayout.CENTER);
		panelInfo.add(panelLista);
		
		List<Game> l = VLogin.dbManager.obtainDataGames();
		for (Game game : l) {
			dlmJuegos.addElement(game);
		}
		
		panel.add(panelInfo, BorderLayout.CENTER);
		
		bComprar = new JButton("Comprar: 0,00$"); // Aqui hay que traer el dato desde la BD
		bSaldo = new JButton("Saldo: 0,00$");
		
		JPanel panelCompras = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.add(bComprar, BorderLayout.EAST);
		panelCompras.add(bSaldo, BorderLayout.WEST);
		
		panel.add(panelCompras, BorderLayout.SOUTH);
		
		return panel;
	}
	
}
