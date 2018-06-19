package de.c1bergh0st.gamecode;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.c1bergh0st.debug.Debug;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class EditorPanel extends JPanel {
	private static final long serialVersionUID = 2917225140861028593L;
	
	private EditorWindow parent;
	private JComboBox<String> comboBox;
	
	private String[] statictypes/* = {"DevFloor","Nothing","3","2"}*/;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel imgLabel;
	private JTextField textField_3;
	private JComboBox<String> decorationbox;
	private JComboBox<String> InteractableSelector;
	
	public EditorPanel(EditorWindow editorWindow) {
		this.parent = editorWindow;
		statictypes = this.parent.parent.imgload.tiletexnames;
		setLayout(null);
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Debug.send((String)comboBox.getSelectedItem());
				parent.parent.currBlock = (String)comboBox.getSelectedItem();
				try {
					imgLabel.setIcon(new ImageIcon(getScaledImage(ImageIO.read(new File("src/res/tiles/"+(String)comboBox.getSelectedItem()+".png")),96,96)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(statictypes));
		comboBox.setBounds(20, 36, 163, 30);
		add(comboBox);
		//comboBox.setSelectedItem("DevFloor");
		
		JLabel lblNewLabel = new JLabel("Texture - Block");
		lblNewLabel.setBounds(20, 11, 163, 14);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 259, 400, 30);
		add(panel);
		
		JLabel lblLoadLevelBy = new JLabel("Load Level by Name");
		panel.add(lblLoadLevelBy);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnLoad = new JButton("Load");
		panel.add(btnLoad);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(20, 218, 400, 30);
		add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("LevelName");
		panel_1.add(lblNewLabel_1);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		panel_1.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.parent.export(textField.getText());
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.parent.loadLevel(textField_1.getText());
			}
		});
		
		imgLabel = new JLabel("");
		imgLabel.setBackground(Color.GRAY);
		try {
			imgLabel.setIcon(new ImageIcon(ImageIO.read(new File("src/res/tiles/DevFloor.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		imgLabel.setBounds(193, 36, 96, 96);
		imgLabel.setBorder(null);
		imgLabel.setText(null);
		add(imgLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(299, 11, 141, 170);
		add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblInteractabletype = new JLabel("InteractableType");
		panel_2.add(lblInteractabletype);
		InteractableSelector= new JComboBox<String>();
		InteractableSelector.setModel(new DefaultComboBoxModel<String>(new String[]{"LevelLoad", "Na"}));
		panel_2.add(InteractableSelector);
		
		JLabel label1 = new JLabel("Arguments");
		panel_2.add(label1);
		
		textField_3 = new JTextField();
		panel_2.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.parent.currInteractable = (String)InteractableSelector.getSelectedItem();
				parent.parent.currInteractableArgs = textField_3.getText();
			}
		});
		panel_2.add(btnApply);
		
		JButton btnRemoveLast = new JButton("Remove Last");
		btnRemoveLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(parent.parent.interactables.size() > 0){
					parent.parent.interactables.removeLast();
				}
				else{
					Debug.sendErr("There are no more Interactables to remove");
				}
			}
		});
		panel_2.add(btnRemoveLast);
		
		JLabel lblTextureDecoration = new JLabel("Texture - Decoration");
		lblTextureDecoration.setBounds(20, 77, 163, 14);
		add(lblTextureDecoration);
		
		decorationbox = new JComboBox<String>();
		decorationbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Debug.send((String)decorationbox.getSelectedItem());
				parent.parent.currBlock = "DECO"+(String)decorationbox.getSelectedItem();
				try {
					imgLabel.setIcon(new ImageIcon(ImageIO.read(new File("src/res/deco/"+(String)decorationbox.getSelectedItem()+".png"))));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		decorationbox.setModel(new DefaultComboBoxModel<String>(parent.parent.decoimgload.texnames));
		decorationbox.setBounds(20, 102, 163, 30);
		
		add(decorationbox);
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
}
