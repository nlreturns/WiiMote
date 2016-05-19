package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class InlogGUI extends JFrame {
	
	private JTextField user, pass;
	
	public static void main(String s[]) {
		new InlogGUI();
	}
	
	public InlogGUI() {
		super("Log in");
		setSize(300, 140);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		makeFrame();
	}
	
	public void makeFrame() {
		
		JPanel content = new JPanel(new BorderLayout());
		
		// Username
		JPanel northBorder = new JPanel(new FlowLayout());
		northBorder.add(new JLabel("Username:"));
		northBorder.add(user = new JTextField(10));
		content.add(northBorder, BorderLayout.NORTH);
		// Password
		JPanel centerBorder = new JPanel(new FlowLayout());
		centerBorder.add(new JLabel("Password:"));
		centerBorder.add(pass = new JPasswordField(10));
		content.add(centerBorder, BorderLayout.CENTER);
		// Buttons
		JPanel southBorder = new JPanel(new FlowLayout());
		JButton login = new JButton("Log in");
		southBorder.add(login);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		JButton reg = new JButton("Registreer");
		southBorder.add(reg);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		content.add(southBorder, BorderLayout.SOUTH);
		
		setContentPane(content);
		setVisible(true);
	}
	
}
