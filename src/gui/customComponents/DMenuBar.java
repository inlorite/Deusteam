package gui.customComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.VDeusteam;

public class DMenuBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected JButton bBiblioteca;
	protected JButton bTienda;
	protected JButton bPerfil;
	
	/* Nota: No quiero tocar esto todavia pero vendria bien pasar un 
	argumento INT para elegir cual setup usar en lugar de hacer otra 
	clase/constructor
	*/
	public DMenuBar(int mode) {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new GridLayout(1, 3, VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, 0, VDeusteam.GAP));
		
		bBiblioteca = new JButton("Biblioteca");
		bTienda = new JButton("Tienda");
		bPerfil = new JButton("Perfil");
		
		bBiblioteca.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VDeusteam.cl.show(VDeusteam.mainPanel, "BIBLIOTECA");
			}
		});
		
		bTienda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VDeusteam.cl.show(VDeusteam.mainPanel, "TIENDA");
			}
		});
		
		bPerfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VDeusteam.cl.show(VDeusteam.mainPanel, "PERFIL");
			}
		});
		
		this.add(bBiblioteca);
		this.add(bTienda);
		this.add(bPerfil);
	}

}
