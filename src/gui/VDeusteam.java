package gui;

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

import gui.customComponents.*;

public class VDeusteam extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static final int GAP = 10;
	
	// North Menu Bar
	protected JButton bBiblioteca;
	protected JButton bTienda;
	protected JButton bPerfil;
	
	public VDeusteam() {
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout(GAP, GAP));
		
		DMenuBar menuBar = new DMenuBar();
		
		DPanelBiblioteca pBiblioteca = new DPanelBiblioteca();
		//
		//
		
		cp.add(menuBar, BorderLayout.NORTH);
		cp.add(pBiblioteca, BorderLayout.CENTER);
		
		this.setTitle("Deusteam");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		VDeusteam v = new VDeusteam();
	}
	
}
