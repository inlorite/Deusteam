package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gui.customComponents.DPanelTienda;
import negocio.*;

public class VRecomendar extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JTable tRecomendado;
	DefaultTableModel dtmRecomendado;
	
	public VRecomendar() {
		
		JPanel cp = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel center = getCenterTable();
		//JPanel south = getSouthSender();
		
		cp.add(center, BorderLayout.CENTER);
		//cp.add(south, BorderLayout.SOUTH);
		
		this.setContentPane(cp);
		
		this.setTitle("Deusteam recomendado");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	public JPanel getCenterTable() {
		JPanel panel = new JPanel(new BorderLayout());
		
		dtmRecomendado = new DefaultTableModel(new Object[] { "Combinacion" }, 0);
		
		ArrayList<Game> lista = new ArrayList<>();
		
		for (Game game : DPanelTienda.listJuegos) {
//			if (DPanelTienda.highlighted(game, game.getName())) {
				lista.add(game);
//			}
		}
		
		List<List<Game>> listaCombinaciones = combinaciones(lista, VLogin.loggedUser.getBalance());
		
		for (List<Game> list : listaCombinaciones) {
			String nombres = getNombresJuegos(list);
			dtmRecomendado.addRow(new Object[] {nombres});
		}
		
		tRecomendado = new JTable(dtmRecomendado);
		panel.add(new JScrollPane(tRecomendado), BorderLayout.CENTER);
		
		return panel;
	}
	
	public JPanel getSouthButton() {
		JPanel panel = new JPanel(new BorderLayout());
		
		return panel;
	}
	
	public static List<List<Game>> combinaciones(List<Game> elementos, double importe) {

    	List<List<Game>> result = new ArrayList<>();

    	combinacionesGames(result, elementos, importe, new ArrayList<>());
    	
    	return result;
    }
	
	public static void combinacionesGames(List<List<Game>> result, List<Game> elementos, double importe, List<Game> temp) {
		if (importe <= 0) {
			if (!result.contains(temp) && !temp.isEmpty()) {
				result.add(new ArrayList<>(temp));
			}
			
			return;
		} else {
			System.out.println(importe);
			for (Game g : elementos) {
				if (g.getPrice() <= importe) {
					if (!temp.contains(g)) {
						temp.add(g);
						combinacionesGames(result, elementos, importe - g.getPrice(), temp);
					}
				}
//				if (g.getPrice() <= importe) {
//					if (!temp.contains(g)) {
//						temp.remove(temp.size() - 1);
//					}
//				}
			}
		}
		
		//result.add(temp);
	}
	
	public String getNombresJuegos(List<Game> listaJuegos) {
		String result = "";
		
		for (Game game : listaJuegos) {
			result += game.getName() + ", ";
		}
		
		return result.substring(0, result.length()-2);
	}

}
