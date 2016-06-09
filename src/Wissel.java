
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Wissel extends JFrame {
	private int waarde;
	private JPanel startPanel, shopPanel, racePanel, scorePanel;
	private Aantalscherm aantalPanel;
	private int playerAmount;

	public static void main(String s[]) {
		Wissel wissel = new Wissel();
	}

	public Wissel() {
		super("Need for Beast");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		switchcase(5);
	}

	public int getWaarde() {
		return waarde;
	}

	public void switchcase(int waarde) {

		this.waarde = waarde;

		switch (this.waarde) {
		case 1:
			startPanel = new Startscherm(this);
			this.getContentPane().removeAll();
			this.getContentPane().add(startPanel);
			this.pack();
			this.setSize(1920, 1080);
			break;

		case 2:
			startPanel = null;
			aantalPanel = new Aantalscherm(this);
			this.getContentPane().removeAll();
			this.getContentPane().add(aantalPanel);
			this.setSize(1920, 1080);
			this.pack();
			break;

		case 3:
			shopPanel = new ShopGUI(aantalPanel.getAccounts(), this);
			playerAmount = aantalPanel.getAccounts().size();
			aantalPanel = null;
			this.getContentPane().removeAll();
			this.getContentPane().add(shopPanel);
			this.setSize(1920, 1080);
			this.pack();
			break;

		case 4:
			shopPanel = null;
			racePanel = new RaceMap(playerAmount);
			this.getContentPane().removeAll();
			this.getContentPane().add(racePanel);
			this.pack();
			break;

		case 5:
			shopPanel = null;
			ArrayList<Account> accounts = new ArrayList<Account>();
			accounts.add(new Account("Rico", "poep", 500));
			accounts.add(new Account("Justin", "poep", 500));
			accounts.add(new Account("Jairo", "poep", 500));
			accounts.add(new Account("Wim", "poep", 500));
			scorePanel = new ScoreScherm(accounts, this);
			this.getContentPane().removeAll();
			this.getContentPane().add(scorePanel);
			this.pack();
			this.setSize(1024, 768);
			break;

		}

	}

}
