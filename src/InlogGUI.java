
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystemNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InlogGUI extends JFrame {
	
	private JTextField user, pass;
	private AccountBase accounts;
	private Account account = null;
	
	// Main 
	public static void main(String s[]) {
		new InlogGUI();
	}
	
	// Constructor
	public InlogGUI() {
		super("Log in");
		setSize(300, 140);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		makeFrame();
//		accounts = new AccountBase();
//		accounts.addAccount(new Account("admin", "admin", 10000));
		try {
			accounts = loadAccounts();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Make frame 
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
				login();
			}
		});
		JButton reg = new JButton("Registreer");
		southBorder.add(reg);
		reg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeRegisterFrame();
			}
		});
		content.add(southBorder, BorderLayout.SOUTH);
		
		setContentPane(content);
		setVisible(true);
	}
	
	@SuppressWarnings("deprecation")
	public void makeRegisterFrame() {
		
		JTextField field1 = new JTextField();
		JPasswordField field2 = new JPasswordField(10);
		JPasswordField field3 = new JPasswordField(10);

		String user, pass, passConf;
		// Construct register pop-up
		Object[] message = { "Username:", field1, "Wachtwoord:", field2, "Wachtwoord:", field3};
		int option = JOptionPane.showConfirmDialog(null, message, "Maak een nieuwe account",
				JOptionPane.OK_CANCEL_OPTION);
		// OK pressed actions
		if (option == JOptionPane.OK_OPTION) {
			user = field1.getText();
			pass = field2.getText();
			passConf = field3.getText();
			boolean duplicateUser = false, correctPass = true;
			for(Account a : accounts.getAccounts()) {
				if(user.equals(a.getUser())) {
					duplicateUser = true;
					JOptionPane.showMessageDialog(null, "Username bestaat al", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if(!pass.equals(passConf)) {
				correctPass = false;
				JOptionPane.showMessageDialog(null, "Wachtwoorden komen niet overeen", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
			if((correctPass) && (!duplicateUser)) {
				accounts.addAccount(new Account(user, pass, 500));
				saveAccounts();
			}
		}
		
	}
	
	// Check log in values
	public void login() {
		Account temp = null;
		for(Account a : accounts.getAccounts()) {
			if(a.getUser().equals(user.getText()))
				temp = a;
		}
		if(temp == null) 
			JOptionPane.showMessageDialog(null, "Wachtwoord en/of username klopt niet", "Error", JOptionPane.INFORMATION_MESSAGE);
		else if(temp.getPass().equals(pass.getText())) {
			System.out.println("Ingelogd");
			account = temp;
		} else  
			JOptionPane.showMessageDialog(null, "Wachtwoord en/of username klopt niet", "Error", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Save accounts database
	public void saveAccounts()throws FileSystemNotFoundException {
		try {
			FileOutputStream fos = new FileOutputStream("accounts");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accounts);
			oos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Load account database
	public static AccountBase loadAccounts()throws FileNotFoundException {
		try {
			FileInputStream fis = new FileInputStream("accounts");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object object = ois.readObject();
			ois.close();
			if(object instanceof AccountBase) {
				System.out.println("Accounts have been loaded.");
				AccountBase temp = (AccountBase) object;
								
				return temp;
			}
			return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public Account getAccount() {
		return account;
	}
	
}
