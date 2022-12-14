package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import negocio.User;
import negocio.chat.Message;

public class VChat extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// NORTH
	HashMap<String, Integer> friends;
	JComboBox<String> cbAmigos;
	
	// CENTER
	JList<Message> lChatbox;
	DefaultListModel<Message> dlmChatbox;
	
	// SOUTH
	JTextField tfMessage;
	JButton bSend;
	
	public VChat() {
		
		System.out.println("VChat created.");
		
		JPanel cp = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		cp.setBorder(new EmptyBorder(VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP, VDeusteam.GAP));
		
		JPanel north = getNorthListaAmigos();
		JPanel center = getCenterChatbox();
		JPanel south = getSouthSender();
		
		cp.add(north, BorderLayout.NORTH);
		cp.add(center, BorderLayout.CENTER);
		cp.add(south, BorderLayout.SOUTH);
		
		this.setContentPane(cp);
		
		this.setTitle("Deusteam chat");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(false);
		
	}
	
	public JPanel getNorthListaAmigos() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		panel.setBorder(new TitledBorder("Amig@s"));
		
		friends = VLogin.dbManager.obtainDataFriendsUserMap(VLogin.loggedUser.getId());
		
		String[] friendNames = new String[friends.keySet().size()];
		int k = 0;
		for (String friend : friends.keySet()) {
			friendNames[k] = friend;
			k++;
		}
		
		cbAmigos = new JComboBox<>(friendNames);
		panel.add(cbAmigos, BorderLayout.CENTER);
		
		cbAmigos.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					dlmChatbox.clear();
					
					// aqui leeria los mensajes previos con ese usuario concreto y los cargaria desde la base de datos
				}
			}
		});
		
		return panel;
	}
	
	public JPanel getCenterChatbox() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		dlmChatbox = new DefaultListModel<>();
		lChatbox = new JList<>(dlmChatbox);
		JScrollPane sp = new JScrollPane(lChatbox);
		
		panel.add(sp, BorderLayout.CENTER);
		panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
		
		return panel;
	}
	
	public JPanel getSouthSender() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		tfMessage = new JTextField();
		bSend = new JButton("Enviar");
		
		panel.add(tfMessage, BorderLayout.CENTER);
		panel.add(bSend, BorderLayout.EAST);
		
		bSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = tfMessage.getText();
				
				if (!text.equals("")) {
					User to = VLogin.dbManager.getUser((String) cbAmigos.getSelectedItem());
					Message message = new Message(VLogin.loggedUser, to, text, new Date().getTime());
					
					VDeusteam.client.sendMessage(message);
					
					tfMessage.setText("");
				}
			}
		});
		
		return panel;
	}

}
