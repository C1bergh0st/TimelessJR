package de.c1bergh0st.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JPanel {
	private Window parent;
	/**
	 * Create the panel.
	 */
	public Menu(Window parent) {
		this.parent = parent;
		ImageIcon image = null;
		try {
			image = new ImageIcon(ImageIO.read(Menu.class.getResourceAsStream("/res/MenuBackdrop.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(null);
		
		JPanel middle = new JPanel();
		middle.setBounds(640, 0, 640, 1080);
		add(middle);
		middle.setBackground(new Color(0,0,0,0));
		middle.setLayout(null);
		
		JButton button1 = new JButton("Start");
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.showLayout("Game");
			}
		});
		button1.setEnabled(false);
		button1.setLocation(200, 260);
		button1.setSize(240, 60);
		button1.setBackground(new Color(59, 89, 182));
		button1.setForeground(Color.WHITE);
		button1.setFocusPainted(false);
		button1.setFont(new Font("Tahoma", Font.BOLD, 18));//http://answers.yahoo.com/question/index?qid=20070906133202AAOvnIP
        
		middle.add(button1);
		
		JButton button2 = new JButton("Settings");
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.showLayout("Settings");
			}
		});
		button2.setForeground(Color.WHITE);
		button2.setFont(new Font("Tahoma", Font.BOLD, 18));
		button2.setFocusPainted(false);
		button2.setEnabled(false);
		button2.setBackground(new Color(59, 89, 182));
		button2.setBounds(200, 540, 240, 60);
		middle.add(button2);
		
		JButton button3 = new JButton("About");
		button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openWebpage(de.c1bergh0st.main.MainEngine.ABOUTURL);
			}
		});
		button3.setForeground(Color.WHITE);
		button3.setFont(new Font("Tahoma", Font.BOLD, 18));
		button3.setFocusPainted(false);
		button3.setEnabled(false);
		button3.setBackground(new Color(59, 89, 182));
		button3.setBounds(200, 680, 240, 60);
		middle.add(button3);
		
		JButton button4 = new JButton("Exit");
		button4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/**
				 * DONT EVEN ASK ME ABOUT THIS SYNTAX
				 */
				int n = JOptionPane.showConfirmDialog(parent, "Do you want to quit the game?", "", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, (Icon)new EmptyIcon());
				if(n == 0){
					System.exit(1);
				}
			}
		});
		button4.setForeground(Color.WHITE);
		button4.setFont(new Font("Tahoma", Font.BOLD, 18));
		button4.setFocusPainted(false);
		button4.setEnabled(false);
		button4.setBackground(new Color(59, 89, 182));
		button4.setBounds(200, 820, 240, 60);
		middle.add(button4);
		
		JButton btnLevelEditor = new JButton("Level Editor");
		btnLevelEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.showLayout("LevelPanel");
			}
		});
		btnLevelEditor.setForeground(Color.WHITE);
		btnLevelEditor.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLevelEditor.setFocusPainted(false);
		btnLevelEditor.setEnabled(false);
		btnLevelEditor.setBackground(new Color(59, 89, 182));
		btnLevelEditor.setBounds(200, 400, 240, 60);
		middle.add(btnLevelEditor);
		
		JLabel label = new JLabel("", image, JLabel.CENTER);
		label.setBackground(Color.BLACK);
		label.setBounds(0, 0, 1920, 1080);
		
		JPanel background = new JPanel();
		background.setBounds(0, 0, 1920, 1080);
		background.setLayout(null);
		background.add(label );
		add(background);

	}
	
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
		}
	}
}
