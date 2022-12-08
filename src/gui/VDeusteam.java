package gui;

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

import gui.customComponents.*;

public class VDeusteam extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static final int GAP = 10;
	
	public static Container cp;
	public static DMenuBar menuBar;
	public static DPanelBiblioteca pBiblioteca;
	public static DPanelTienda pTienda;
	
	public VDeusteam() {
		
		cp = this.getContentPane();
		cp.setLayout(new BorderLayout(GAP, GAP));
		
		menuBar = new DMenuBar(0);
		
		pBiblioteca = new DPanelBiblioteca();
		pTienda = new DPanelTienda();
		//
		
		cp.add(menuBar, BorderLayout.NORTH);
		cp.add(pBiblioteca, BorderLayout.CENTER);
		//cp.add(pTienda, BorderLayout.CENTER);
		
		this.setTitle("Deusteam");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		
	}
	
}
