package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import gui.customComponents.DPanelTienda;
import negocio.*;

public class VRecomendar extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static VRecomendar vRecomendar;
	
	JTable tRecomendado;
	DefaultTableModel dtmRecomendado;
	List<List<Game>> listaCombinacionesGames;
	List<List<Merch>> listaCombinacionesMerch;
	
	protected JLabel tSaldo;
	protected JButton bCancelar;
	protected JButton bComprar;
	
	/** True si la tienda esta en modo juegos, false si esta en modo merch
	 */
	public static boolean modo = true;
	
	public VRecomendar() {
		
		JPanel cp = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel center = getCenterTable();
		JPanel south = getSouthPanel();
		
		cp.add(center, BorderLayout.CENTER);
		cp.add(south, BorderLayout.SOUTH);
		
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
					index++;
				}
				
			}
			
			if (lista.isEmpty()) {
				for (Game game : DPanelTienda.listJuegos) {
					game.setIdAux(index);
					lista.add(game);
					index++;
				}
			}
			
			listaCombinacionesGames = combinacionesGames(lista, VLogin.loggedUser.getBalance());
			
			for (List<Game> list : listaCombinacionesGames) {
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
					index++;
				}
				
			}
			
			if (lista.isEmpty()) {
				for (Merch merch : DPanelTienda.listMerch) {
					merch.setIdAux(index);
					lista.add(merch);
					index++;
				}
			}
		
			listaCombinacionesMerch = combinacionesMerch(lista, VLogin.loggedUser.getBalance());
						
			for (List<Merch> list : listaCombinacionesMerch) {
				String nombres = getNombresMerch(list);
				dtmRecomendado.addRow(new Object[] {nombres});
			}
			
		}
		
		tRecomendado = new JTable(dtmRecomendado);
		
		tRecomendado.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				bComprar.setEnabled(true);
				
			}
			
		});
		
		panel.add(new JScrollPane(tRecomendado), BorderLayout.CENTER);
		
		return panel;
	}
	
	public JPanel getSouthPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		tSaldo = new JLabel("Saldo actual: " + VLogin.loggedUser.getBalance() + "$");
		panel.add(tSaldo, BorderLayout.NORTH);
		
		JPanel panelBotones = new JPanel(new GridLayout(1, 2));
		
		bComprar = new JButton("Comprar recomendacion");
		bComprar.setEnabled(false);
		panelBotones.add(bComprar, BorderLayout.SOUTH);
		
		bCancelar = new JButton("Cancelar");
		panelBotones.add(bCancelar, BorderLayout.SOUTH);
		
		panel.add(panelBotones);
		
		// Listeners
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				vRecomendar = null;
			}
		});
		
		bCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				vRecomendar = null;
			}
		});
		
		bComprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tRecomendado.getSelectedRow() != -1) {
					if (modo) {
						for (Game game : listaCombinacionesGames.get(tRecomendado.getSelectedRow())) {
							VLogin.loggedUser.addGame(game);
						}
					} else {
						for (Merch merch : listaCombinacionesMerch.get(tRecomendado.getSelectedRow())) {
							VLogin.loggedUser.addMerch(merch);
						}
					}
				}
				
				DPanelTienda.bSaldo.setText("Saldo: " + VLogin.loggedUser.getBalance() + "$");
				
				DPanelTienda.bSaldo.revalidate();
				DPanelTienda.bSaldo.repaint();
				
				dispose();
				vRecomendar = null;
				
			}
		});
		
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
