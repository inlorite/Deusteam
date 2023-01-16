package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import gui.customComponents.DPanelPerfil;
import gui.customComponents.DPanelTienda;
import negocio.*;

public class VModificar extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	public static List<Game> listJuegos;
	JButton bBorrar = new JButton("Borrar");
	JButton bEditar = new JButton("Editar");
	public static List<Game> listJuegosOwner;
	public static DefaultTableModel dtmJuegos;
	public static JTable tJuegos;
	public static JPanel panelTablaJuegos;
	
	public static VEditar vEditar;
	
	public static boolean modo = true;
	
	public VModificar() {
		
		JPanel cp = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		JPanel pBoton = new JPanel(new GridLayout(1, 2));
		pBoton.add(bBorrar);
		pBoton.add(bEditar);
		
		
		JPanel panelInfo = new JPanel(new GridLayout(1, 1));
		
		panelTablaJuegos = new JPanel(new BorderLayout());
		panelTablaJuegos.setBorder(new TitledBorder("Juegos disponibles"));
		dtmJuegos = new DefaultTableModel(new Object[] { "Nombre", "Creador", "Precio" }, 0);
		tJuegos = new JTable(dtmJuegos);
		panelTablaJuegos.add(new JScrollPane(tJuegos), BorderLayout.CENTER);
		panelInfo.add(panelTablaJuegos);
		listJuegos = VLogin.dbManager.obtainDataGames();
		listJuegosOwner = new ArrayList<Game>();
		
		for (Game game : listJuegos) {
			if(game.getOwner().equals(VLogin.loggedUser.getUsername())) {
				listJuegosOwner.add(game);
				String nombre = game.getName();
				String creador = game.getOwner();
				Double precio = game.getPrice();
				dtmJuegos.addRow(new Object[] {nombre, creador, precio});
			}
		}
		
		
		cp.add(panelInfo, BorderLayout.CENTER);
		cp.add(pBoton,BorderLayout.SOUTH);
		
		this.setContentPane(cp);
		
		this.setTitle("Deusteam modificacion");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		bBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tJuegos.getSelectedRow() != -1) {
					Game game = listJuegos.get(tJuegos.getSelectedRow());
					Game game2 = listJuegosOwner.get(tJuegos.getSelectedRow());
					VLogin.dbManager.deleteDataGames(game2);
					DPanelPerfil.vModificar.setVisible(false);
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione el juego que quiere borrar");
				}
			}
		});
		
		bEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {			
				if(tJuegos.getSelectedRow() != -1) {
					vEditar = new VEditar();				
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione el juego a editar");
				}
				
			}
		});
	}
}
