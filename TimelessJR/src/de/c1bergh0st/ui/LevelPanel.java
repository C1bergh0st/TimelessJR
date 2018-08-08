package de.c1bergh0st.ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LevelPanel extends JPanel {
	private Window parent;
	private JTextField textField;
	/**
	 * Create the panel.
	 */
	public LevelPanel(Window parent) {
		this.parent = parent;
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(910, 1039, 100, 30);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.showLayout("Menu");
			}
		});
		setLayout(null);
		add(btnNewButton);
		
		JPanel world1 = new JPanel();
		world1.setBounds(30, 30, 350, 950);
		add(world1);
		
		JLabel lblWorld = new JLabel("World1");
		world1.add(lblWorld);
		
		JPanel world2 = new JPanel();
		world2.setBounds(390, 30, 350, 950);
		add(world2);
		
		JLabel lblWorld_1 = new JLabel("World2");
		world2.add(lblWorld_1);
		
		JPanel world3 = new JPanel();
		world3.setBounds(750, 30, 350, 950);
		add(world3);
		
		JLabel lblWorld_2 = new JLabel("World3");
		world3.add(lblWorld_2);
		
		JPanel world4 = new JPanel();
		world4.setBounds(1110, 30, 350, 950);
		add(world4);
		
		JLabel lblWorld_3 = new JLabel("World4");
		world4.add(lblWorld_3);
		
		JPanel panel = new JPanel();
		panel.setBounds(1470, 30, 440, 950);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblEditor = new JLabel("Editor");
		lblEditor.setBounds(206, 5, 28, 14);
		panel.add(lblEditor);
		
		JButton openEditorButton = new JButton("Open Editor");
		openEditorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.showLayout("Editor");
			}
		});
		openEditorButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		openEditorButton.setBounds(10, 30, 420, 111);
		panel.add(openEditorButton);
	}
}
