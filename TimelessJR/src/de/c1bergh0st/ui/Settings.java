package de.c1bergh0st.ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;

import de.c1bergh0st.debug.Debug;

import javax.swing.JSlider;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Settings extends JPanel {
	private Window parent;
	private JTextField textField;
	private JCheckBox shadowbox;
	/**
	 * Create the panel.
	 */
	public Settings(Window parent) {
		this.parent = parent;
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(910, 32, 100, 30);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.showLayout("Menu");
			}
		});
		setLayout(null);
		add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(710, 76, 500, 993);
		add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 480, 30);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSetting = new JLabel("Name");
		panel_1.add(lblSetting, BorderLayout.WEST);
		
		textField = new JTextField();
		panel_1.add(textField, BorderLayout.EAST);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 52, 480, 30);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		shadowbox = new JCheckBox("");
		panel_2.add(shadowbox, BorderLayout.EAST);
		
		JLabel lblSetting_1 = new JLabel("Shadows");
		panel_2.add(lblSetting_1, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 93, 480, 30);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblLow = new JLabel("Low");
		lblLow.setBounds(278, 11, 46, 14);
		panel_3.add(lblLow);
		
		JLabel label = new JLabel("Medium");
		label.setBounds(369, 11, 46, 14);
		panel_3.add(label);
		
		JLabel label_1 = new JLabel("High");
		label_1.setBounds(455, 9, 25, 19);
		panel_3.add(label_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 134, 480, 30);
		panel.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL,0,2,1);
		panel_4.add(slider, BorderLayout.EAST);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		
		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.st.shadowsactive = shadowbox.isSelected();
				parent.st.playername = textField.getText();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBounds(200, 959, 100, 23);
		panel.add(btnNewButton_1);
	}
}
