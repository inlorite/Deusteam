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
	
	protected JButton bJuegos;
	protected JButton bMerch;
	
	/* Nota: No quiero tocar esto todavia pero vendria bien pasar un 
	argumento INT para elegir cual setup usar en lugar de hacer otra 
	clase/constructor
	*/
	public DMenuBar(int mode) {
		super();
		
		if (mode == 0) {
			setup();
		} else {
			setup2();
		}
	}
	
	public void setup() {
		this.setLayout(new GridLayout(1, 3, VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, 0, VDeusteam.GAP));
		
		bBiblioteca = new JButton("Biblioteca");
		bTienda = new JButton("Tienda");
		bPerfil = new JButton("Perfil");
		
		bTienda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// VDeusteam.cp.remove(VDeusteam.pBiblioteca);
				} catch (Exception e2) {
					//VDeusteam.cp.remove(VDeusteam.pPerfil);
				}
				
				VDeusteam.cp.add(VDeusteam.pTienda, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		
		this.add(bBiblioteca);
		this.add(bTienda);
		this.add(bPerfil);
	}
	
	public void setup2() {
		this.setLayout(new GridLayout(1, 2, VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, 0, VDeusteam.GAP));
		
		bJuegos = new JButton("Juegos");
		bMerch = new JButton("Merch");
		
		this.add(bJuegos);
		this.add(bMerch);
	}

}
