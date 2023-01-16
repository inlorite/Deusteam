package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gui.customComponents.DPanelTienda;

public class VSaldo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static VSaldo vSaldo;
	
	protected JLabel lTexto;
	protected JTextField tCantidad;
	protected JButton bCancelar;
	protected JButton bPagar;
	
	public VSaldo() {
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel pDatos = new JPanel(new GridLayout(2, 1, 5, 10));
		JPanel pBotones = new JPanel(new GridLayout(1, 2, 5, 10));
		
		pDatos.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pBotones.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		lTexto = new JLabel("Introduce una cantidad de saldo");
		tCantidad = new JTextField();
		tCantidad.setColumns(30);
		
		pDatos.add(lTexto);
		pDatos.add(tCantidad);
		
		bCancelar = new JButton("Cancelar");
		bPagar = new JButton("Pagar");
		
		pBotones.add(bCancelar);
		pBotones.add(bPagar);
		
		cp.add(pDatos, BorderLayout.CENTER);
		cp.add(pBotones, BorderLayout.SOUTH);
		
		this.setTitle("Deusteam - Recargar saldo");
		this.setIconImage(new ImageIcon("data/icon.png").getImage());
		this.pack();
		this.setLocationRelativeTo(null); // centers window on execution
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				vSaldo = null;
			}
		});
		
		bCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				vSaldo = null;
			}
		});
		
		bPagar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					VLogin.loggedUser.setBalance(VLogin.loggedUser.getBalance() + Double.parseDouble(tCantidad.getText()));
					
					DPanelTienda.bSaldo.setText("Saldo: " + VLogin.loggedUser.getBalance() + "$");
					VSaldo.vSaldo.setVisible(false);
				} catch (Exception e1) {
					System.err.println("* Error al procesar el pago");
				}
			}
		});
		
	}

}
