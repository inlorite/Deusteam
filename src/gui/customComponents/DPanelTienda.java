package gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import gui.VDeusteam;
import negocio.Game;
import negocio.Merch;

public class DPanelTienda extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// West Selector
	protected JTextField tBuscador;
	protected JCheckBox cCategoria;
		
	// Center Info Juego
	protected JList<Game> lJuegos;
	protected DefaultListModel<Game> dlmJuegos;
	protected JList<Merch> lMerch;
	protected DefaultListModel<Merch> dlmMerch;
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
		JPanel panel = new JPanel(new GridLayout(2, 1));
		
		JPanel panelBuscador = new JPanel(new BorderLayout());
		panelBuscador.setBorder(new TitledBorder("Buscador"));
		tBuscador = new JTextField("Introduce un término de busqueda");
		panelBuscador.add(tBuscador, BorderLayout.CENTER);
		
		JPanel panelCategoria = new JPanel(new BorderLayout());
		panelCategoria.setBorder(new TitledBorder("Selector de categorias"));
		cCategoria = new JCheckBox("Ejemplo");
		panelCategoria.add(cCategoria, BorderLayout.CENTER);
		
		panel.add(panelBuscador);
		panel.add(panelCategoria);
		
		return panel;
	}
	
	public JPanel getCenterResultado() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		DMenuBar menuBar = new DMenuBar(1);
		panel.add(menuBar, BorderLayout.NORTH);
		
		bComprar = new JButton("Comprar: 0,00€"); // Aqui hay que traer el dato desde la BD
		bSaldo = new JButton("Saldo: 0,00€");
		
		JPanel panelCompras = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		panelCompras.add(bComprar, BorderLayout.EAST);
		panelCompras.add(bSaldo, BorderLayout.WEST);
		
		panel.add(panelCompras, BorderLayout.SOUTH);
		
		return panel;
	}
	
}
