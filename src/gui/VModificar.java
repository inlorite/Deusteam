package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import gui.customComponents.DPanelTienda;
import negocio.*;

public class VModificar extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	List<Game> listJuegos;
	public static DefaultTableModel dtmJuegos;
	public static JTable tJuegos;
	public static JPanel panelTablaJuegos;
	
	public static boolean modo = true;
	
	public VModificar() {
		
		JPanel cp = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		
		
		JPanel panelInfo = new JPanel(new GridLayout(1, 1));
		
		panelTablaJuegos = new JPanel(new BorderLayout());
		panelTablaJuegos.setBorder(new TitledBorder("Juegos disponibles"));
		dtmJuegos = new DefaultTableModel(new Object[] { "Nombre", "Creador", "Precio" }, 0);
		tJuegos = new JTable(dtmJuegos);
		panelTablaJuegos.add(new JScrollPane(tJuegos), BorderLayout.CENTER);
		panelInfo.add(panelTablaJuegos);
		listJuegos = VLogin.dbManager.obtainDataGames();
		
		for (Game game : listJuegos) {
			if(game.getOwner().equals(VLogin.loggedUser.getUsername())) {
				
				String nombre = game.getName();
				String creador = game.getOwner();
				Double precio = game.getPrice();
				dtmJuegos.addRow(new Object[] {nombre, creador, precio});
			}
		}
		
		
		cp.add(panelInfo, BorderLayout.CENTER);
		
		this.setContentPane(cp);
		
		this.setTitle("Deusteam modificacion");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	}
}
