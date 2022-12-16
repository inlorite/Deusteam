package gui;

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

import gui.customComponents.*;

public class VDeusteam extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static final int GAP = 10;
	
	public static Container cp;
	public static JPanel mainPanel;
	public static CardLayout cl;
	public static DMenuBar menuBar;
	public static DPanelBiblioteca pBiblioteca;
	public static DPanelTienda pTienda;
	public static DPanelPerfil pPerfil;
	
	public VDeusteam() {
		
		cp = this.getContentPane();
		cp.setLayout(new BorderLayout(GAP, GAP));
		
		mainPanel = new JPanel();
		cl = new CardLayout(GAP, GAP);
		mainPanel.setLayout(cl);
		
		menuBar = new DMenuBar(0);
		
		pBiblioteca = new DPanelBiblioteca();
		pTienda = new DPanelTienda();
		pPerfil = new DPanelPerfil();
		
		cp.add(menuBar, BorderLayout.NORTH);
		
		mainPanel.add(pBiblioteca, "BIBLIOTECA");
		mainPanel.add(pTienda, "TIENDA");
		mainPanel.add(pPerfil, "PERFIL");
		
		cl.show(mainPanel, "TIENDA");
		
		cp.add(mainPanel, BorderLayout.CENTER);
//		cp.add(pBiblioteca, BorderLayout.CENTER);
//		cp.add(pTienda, BorderLayout.CENTER);
//		cp.add(pPerfil, BorderLayout.CENTER);
		
		this.setTitle("Deusteam - Tienda");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		
	}
	
}
