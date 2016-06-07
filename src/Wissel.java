
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
			aantalPanel = new Aantalscherm(this);
			this.getContentPane().removeAll();
			this.getContentPane().add(aantalPanel);
			this.setSize(1920, 1080);
			this.pack();
			break;

		case 3:
			shopPanel = new ShopGUI(aantalPanel.getAccounts(), this);
			this.getContentPane().removeAll();
			this.getContentPane().add(shopPanel);
			this.setSize(1920, 1080);
			this.pack();
			break;

		case 4:
			RaceMap race = new RaceMap(aantalPanel.getAccounts().size());
			this.getContentPane().removeAll();
			this.getContentPane().add(race);
			this.pack();
			break;

		case 5:
			break;

		}

	}

}
