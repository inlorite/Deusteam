package gui;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
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
		
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				loadFriends();
			}
		});
		
		this.setMinimumSize(new Dimension(400, 500));
		this.setTitle("Deusteam chat");
		this.setIconImage(new ImageIcon("data/icon.png").getImage());
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
					
					loadMessages();
					
				}
			}
		});
		
		return panel;
	}
	
	public JPanel getCenterChatbox() {
		JPanel panel = new JPanel(new BorderLayout(VDeusteam.GAP, VDeusteam.GAP));
		
		dlmChatbox = new DefaultListModel<>();
		lChatbox = new JList<>(dlmChatbox);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		lChatbox.setCellRenderer(new ListCellRenderer<Message>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index, boolean isSelected, boolean cellHasFocus) {
				
				JLabel label = new JLabel(message.getFrom().getUsername() + ": " + message.getMessage());
				
				label.setToolTipText(sdf.format(new Date(message.getDate())));
				
				if (message.getFrom().equals(VLogin.loggedUser)) {
					label.setBackground(new Color(172, 242, 215));
				} else {
					label.setBackground(Color.WHITE);
				}

				label.setOpaque(true);
				
				return label;
			}
		});
		
		loadMessages();
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
					
					VLogin.dbManager.insertDataMessages(message);
					VDeusteam.client.sendMessage(message);
					
					tfMessage.setText("");
				}
			}
		});
		
		return panel;
	}
	
	public void loadMessages() {
		dlmChatbox.clear();
		
		List<Message> messages = VLogin.dbManager.obtainDataUserMessages(VLogin.loggedUser.getId(), friends.get(cbAmigos.getSelectedItem()));
		
		dlmChatbox.addAll(messages);
	}
	
	public void loadFriends() {
		cbAmigos.removeAllItems();
		
		friends = VLogin.dbManager.obtainDataFriendsUserMap(VLogin.loggedUser.getId());
		
		for (String friend : friends.keySet()) {
			cbAmigos.addItem(friend);
		}
	}

}
