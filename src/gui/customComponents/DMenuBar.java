package gui.customComponents;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.VDeusteam;

public class DMenuBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected JButton bBiblioteca;
	protected JButton bTienda;
	protected JButton bPerfil;
	
	public DMenuBar() {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new GridLayout(1, 3, VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, 0, VDeusteam.GAP));
		
		bBiblioteca = new JButton("Biblioteca");
		bTienda = new JButton("Tienda");
		bPerfil = new JButton("Perfil");
		
		this.add(bBiblioteca);
		this.add(bTienda);
		this.add(bPerfil);
	}

}
