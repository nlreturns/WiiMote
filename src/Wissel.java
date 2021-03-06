
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Wissel extends JFrame {
	private int waarde;
	private JPanel startPanel, shopPanel, racePanel;
	private Aantalscherm aantalPanel;
	private ScoreScherm scorePanel;
	private int playerAmount;
	private ArrayList<Account> accounts, sortedAccounts;
	private RaceMap race;

	public static void main(String s[]) {
		Wissel wissel = new Wissel();
	}

	public Wissel() {
		super("Need for Beast");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768);
		setVisible(true);
		switchcase(1);
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
			break;

		case 2:
			startPanel = null;
			scorePanel = null;
			aantalPanel = new Aantalscherm(this);
			this.getContentPane().removeAll();
			this.getContentPane().add(aantalPanel);
			this.pack();
			break;

		case 3:
			accounts = new ArrayList<Account>(aantalPanel.getAccounts());
			playerAmount = accounts.size();
			shopPanel = new ShopGUI(accounts, this);
			aantalPanel = null;
			this.getContentPane().removeAll();
			this.getContentPane().add(shopPanel);

			this.pack();
			break;

		case 4:
			shopPanel = null;
			race = new RaceMap(playerAmount, accounts, this);
			this.getContentPane().removeAll();
			this.getContentPane().add(race);
			this.pack();
			break;

		case 5:
			sortedAccounts = new ArrayList<Account>(race.getSortedAccounts());
			race = null;
			shopPanel = null;
			scorePanel = new ScoreScherm(sortedAccounts, accounts, this);
			this.getContentPane().removeAll();
			this.getContentPane().add(scorePanel);
			this.pack();
			break;

		}

	}

}
