package gui.customComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.VChat;
import gui.VDeusteam;
import gui.VLogin;

public class DMenuBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected JButton bBiblioteca;
	protected JButton bTienda;
	protected JButton bPerfil;
	protected JButton bChat;
	
	/* Nota: No quiero tocar esto todavia pero vendria bien pasar un 
	argumento INT para elegir cual setup usar en lugar de hacer otra 
	clase/constructor
	*/
	public DMenuBar(int mode) {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new GridLayout(1, 4, VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, 0, VDeusteam.GAP));
		
		bBiblioteca = new JButton("Biblioteca");
		bTienda = new JButton("Tienda");
		bPerfil = new JButton("Perfil");
		bChat = new JButton("Chat");
		
		bTienda.setEnabled(false);
		
		bBiblioteca.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bTienda.setEnabled(true);
				bBiblioteca.setEnabled(false);
				bPerfil.setEnabled(true);
				
				DPanelBiblioteca.loadDataModels();
				VDeusteam.cl.show(VDeusteam.mainPanel, "BIBLIOTECA");
				VLogin.vDeusteam.setTitle("Deusteam - Biblioteca");
			}
		});
		
		bTienda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bTienda.setEnabled(false);
				bBiblioteca.setEnabled(true);
				bPerfil.setEnabled(true);
				
				DPanelTienda.lBanner.setIcon(null);
				DPanelTienda.lInfo.setText("");
				DPanelTienda.bComprar.setEnabled(false);
				
				DPanelTienda.loadTablaJuegos();
				VDeusteam.cl.show(VDeusteam.mainPanel, "TIENDA");
				VLogin.vDeusteam.setTitle("Deusteam - Tienda");
			}
		});
		
		bPerfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bTienda.setEnabled(true);
				bBiblioteca.setEnabled(true);
				bPerfil.setEnabled(false);
				
				DPanelPerfil.loadListaAmigos();
				DPanelPerfil.getCenterPerfilUsuario();
				VDeusteam.cl.show(VDeusteam.mainPanel, "PERFIL");
				VLogin.vDeusteam.setTitle("Deusteam - Perfil");
			}
		});
		
		bChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (VDeusteam.networking) {
					VDeusteam.vChat.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Error al cargar el chat.\n(Probablemente servidor no inicializado)", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		this.add(bBiblioteca);
		this.add(bTienda);
		this.add(bPerfil);
		this.add(bChat);
	}

}
