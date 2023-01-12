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
	
	public static boolean modo = true;
	
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
		
		if (modo) {
			ArrayList<Game> lista = new ArrayList<>();
			int index = 1;
			for (Game game : DPanelTienda.listJuegos) {
				if (DPanelTienda.highlighted(game, (String) DPanelTienda.tJuegos.getModel().getValueAt(DPanelTienda.listJuegos.indexOf(game), 0))) {
					game.setIdAux(index);
					lista.add(game);
					index ++;
				}
				
			}
			
			System.out.println("Lista juegos: " + lista);
			
			List<List<Game>> listaCombinaciones = combinacionesGames(lista, VLogin.loggedUser.getBalance());
			
			for (List<Game> list : listaCombinaciones) {
				String nombres = getNombresJuegos(list);
				dtmRecomendado.addRow(new Object[] {nombres});
			}
		} else {
			ArrayList<Merch> lista = new ArrayList<>();
			int index = 1;
			for (Merch merch : DPanelTienda.listMerch) {
				if (DPanelTienda.highlighted(merch, (String) DPanelTienda.tMerch.getModel().getValueAt(DPanelTienda.listMerch.indexOf(merch), 0))) {
					merch.setIdAux(index);
					lista.add(merch);
					index ++;
				}
			}
		
			List<List<Merch>> listaCombinaciones = combinacionesMerch(lista, VLogin.loggedUser.getBalance());
						
			for (List<Merch> list : listaCombinaciones) {
				String nombres = getNombresMerch(list);
				dtmRecomendado.addRow(new Object[] {nombres});
			}
		}
		
		tRecomendado = new JTable(dtmRecomendado);
		panel.add(new JScrollPane(tRecomendado), BorderLayout.CENTER);
		
		return panel;
	}
	
	public JPanel getSouthButton() {
		JPanel panel = new JPanel(new BorderLayout());
		
		return panel;
	}
	
	public static List<List<Game>> combinacionesGames(List<Game> elementos, double importe) {
    	List<List<Game>> result = new ArrayList<>();

    	combinacionesGames(result, elementos, importe, new ArrayList<>());
    	
    	return result;
    }
	
	public static List<List<Merch>> combinacionesMerch(List<Merch> elementos, double importe) {
    	List<List<Merch>> result = new ArrayList<>();

    	combinacionesMerch(result, elementos, importe, new ArrayList<>());
    	
    	return result;
    }
	
	public static void combinacionesGames(List<List<Game>> result, List<Game> elementos, double importe, List<Game> temp) {
		if (importe <= 0) {
			if (!temp.isEmpty()) {
				List<Game> newList = new ArrayList<>(temp);
				
				if (importe < 0) {
					newList.remove(newList.size() - 1);
				}
				
				Collections.sort(newList);
				
				if (!result.contains(newList)) {
					result.add(newList);
				}
			}
		} else {
			for (Game g : elementos) {
				System.out.println(g.getIdAux());
				if (!temp.contains(g)) {
					temp.add(g);
					combinacionesGames(result, elementos, importe - g.getPrice(), temp);
					temp.remove(temp.size() - 1);
				}
				if(g.getIdAux() ==elementos.size()) {
					combinacionesGames(result, elementos, 0, temp);
				}
			}
		}
	}
	
	public static void combinacionesMerch(List<List<Merch>> result, List<Merch> elementos, double importe, List<Merch> temp) {
		if (importe <= 0) {
			if (!temp.isEmpty()) {
				List<Merch> newList = new ArrayList<>(temp);
				
				if (importe < 0) {
					newList.remove(newList.size() - 1);
				}
				
				Collections.sort(newList);
				
				if (!result.contains(newList)) {
					result.add(newList);
				}
			}
		} else {
			for (Merch m : elementos) {
				System.out.println(m.getIdAux());
				if (!temp.contains(m)) {
					temp.add(m);
					combinacionesMerch(result, elementos, importe - m.getPrice(), temp);
					temp.remove(temp.size() - 1);
				}
				if(m.getIdAux() ==elementos.size()) {
					combinacionesMerch(result, elementos, 0, temp);
				}
			}
		}
	}
	
	public String getNombresJuegos(List<Game> listaJuegos) {
		String result = "";
		
		for (Game game : listaJuegos) {
			result += game.getName() + ", ";
		}
		
		return result.substring(0, result.length()-2);
	}
	
	public String getNombresMerch(List<Merch> listaMerch) {
		String result = "";
		
		for (Merch merch : listaMerch) {
			result += merch.getName() + ", ";
		}
		
		return result.substring(0, result.length()-2);
	}

}
