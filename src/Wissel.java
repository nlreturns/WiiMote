
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Wissel extends JFrame {
	private int waarde;
	private JPanel startPanel, shopPanel, racePanel;
	private Aantalscherm aantalPanel;

	public static void main(String s[]) {
		new Wissel();
	}

	public Wissel() {
		super("Startscherm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		switchcase(1);
	}

	public void switchcase(int waarde) {

		switch (waarde) {
		case 1:
			startPanel = new Startscherm(this);
			this.getContentPane().removeAll();
			this.getContentPane().add(startPanel);
			this.pack();
			break;

		case 2:
			aantalPanel = new Aantalscherm(this);
			this.getContentPane().removeAll();
			this.getContentPane().add(startPanel);
			this.pack();
			break;

		case 3:
			shopPanel = new ShopGUI(aantalPanel.getAccounts(), this);
			this.getContentPane().removeAll();
			this.getContentPane().add(shopPanel);
			this.pack();
			break;

		case 4:
			RaceMap race = new RaceMap(1);
			break;

		case 5:
			break;

		}

	}

}
