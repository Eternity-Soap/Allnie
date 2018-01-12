package com.soap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.*;
import java.awt.*;

public class GUITests implements ActionListener
{
	public void start()
	{
		JFrame frame=new JFrame("GUI TEST");
		JLabel label=new JLabel("Test Label");
		JTextField field = new JTextField("Maybe");
		field.addActionListener(this);
		frame.add(label);
		frame.add(field);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) 
	{
		
	}
}
