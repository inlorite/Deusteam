package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import negocio.User;

public class DPanelPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// West Lista Amigos
	protected JList<User> listAmigos;
	protected DefaultListModel<User> dlmAmigos;
	protected JButton bBusqueda;
	protected JTextField tBuscador;
	
	
	// Center Perfil Usuario
	protected JLabel lPerfil;
	
	public DPanelPerfil() {
		super();
		
		setup();
	}
	
	public void setup() {
		this.setLayout(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		this.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel pListaAmigos = getWestListaAmigos();
		JPanel pInfoUsuario = getCenterPerfilUsuario();
		
		this.add(pListaAmigos, BorderLayout.WEST);
		this.add(pInfoUsuario, BorderLayout.CENTER);
	}
	
	public JPanel getWestListaAmigos() {
		JPanel panel = new JPanel(new GridLayout(2,1)); // modificar
		
		
		JPanel panelAmigos = new JPanel(new BorderLayout());
		panelAmigos.setBorder(new TitledBorder("Lista de amigos"));
		dlmAmigos = new DefaultListModel<>();
		listAmigos = new JList<>(dlmAmigos);
		panelAmigos.add(new JScrollPane(listAmigos), BorderLayout.CENTER);
		
		JPanel panelBusqueda = new JPanel(new GridLayout(2,1));
		panelBusqueda.setBorder(new TitledBorder("Buscador de amigos"));
		tBuscador = new JTextField("Introduce un termino de busqueda");
		panelBusqueda.add(tBuscador);
		bBusqueda = new JButton("Buscar");
		panelBusqueda.add(bBusqueda);
		
		panel.add(panelAmigos);
		panel.add(panelBusqueda);
		return panel;
	}
	
	public JPanel getCenterPerfilUsuario() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		panel.setBorder(new TitledBorder("Perfil"));
		
		return panel;
	}
	
	public static void main(String[] args) {
		VLogin.vDeusteam = new VDeusteam();
	}
	
}
