import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

public class ScoreScherm extends JPanel implements ActionListener, WiimoteListener {

	private ArrayList<Account> accounts;
	private Wissel wissel;
	private Wiimote[] wiimotes;
	private Wiimote wiimote;
	private boolean ready1 = false, ready2 = false, ready3 = false, ready4 = false;
	private AccountBase base;
	private short count = 0;
	private Timer timer;
	private ArrayList<Account> volgordeAccounts;

	public ScoreScherm(ArrayList<Account> accounts, ArrayList<Account> volgordeAccounts, Wissel wissel) {
		this.accounts = new ArrayList<Account>(accounts);
		this.volgordeAccounts = new ArrayList<Account>(volgordeAccounts);
		setPreferredSize(new Dimension(1024, 768));
		this.wissel = wissel;
		timer = new Timer(1000 / 50, this);
		timer.start();
		WiiUseApiManager.shutdown();
		wiimotes = WiiUseApiManager.getWiimotes(accounts.size(), false);
		for (int i = 0; i < wiimotes.length; i++) {
			wiimote = wiimotes[i];
			wiimote.addWiiMoteEventListeners(this);
		}
		ready1 = false;
		ready2 = false;
		ready3 = false;
		ready4 = false;
		count = 0;
		addPoints();
		setVisible(true);
	}

	// Timer 20 FPS
	public void actionPerformed(ActionEvent e) {
		if (wissel.getWaarde() == 5) {
			if (count == accounts.size()) {
				wissel.switchcase(3);
				ready1 = false;
				ready2 = false;
				ready3 = false;
				ready4 = false;
			}
			repaint();
		}
	}

	// Paint scherm
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		// First place
		g2d.setFont(new Font("Arial", Font.BOLD, 30));
		g2d.drawString("Eerste plek: " + accounts.get(0).getUser() + "   +500 punten", 250, 200);
		g2d.setColor(Color.RED);
		g2d.drawOval(200, 170, 30, 30);
		if (ready1) {
			System.out.println("poepdi ng");
			g2d.fillOval(200, 170, 30, 30);
		}
		// Second place
		g2d.setColor(Color.BLACK);
		g2d.drawString("Tweede plek: " + accounts.get(1).getUser() + "   +250 punten", 250, 275);
		g2d.setColor(Color.RED);
		g2d.drawOval(200, 245, 30, 30);
		if (ready2)
			g2d.fillOval(200, 245, 30, 30);
		// Third place
		if (accounts.size() > 2) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("Derde plek: " + accounts.get(2).getUser() + "   +100 punten", 250, 350);
			g2d.setColor(Color.RED);
			g2d.drawOval(200, 320, 30, 30);
			if (ready3)
				g2d.fillOval(200, 320, 30, 30);
		}
		// Fourth place
		if (accounts.size() > 3) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("Vierde plek: " + accounts.get(3).getUser(), 250, 425);
			g2d.setColor(Color.RED);
			g2d.drawOval(200, 395, 30, 30);
			if (ready2)
				g2d.fillOval(200, 395, 30, 30);
		}
	}

	// Wiimote actions
	public void onButtonsEvent(WiimoteButtonsEvent e) {
		if (wissel.getWaarde() == 5) {
			System.out.println(ready2);
			if (e.getWiimoteId() == 1) {
				if (e.isButtonAJustPressed()) {
					if (!ready1)
						count++;
					ready1 = true;
				}
			}

			if (e.getWiimoteId() == 2) {
				if (e.isButtonAJustPressed()) {
					if (!ready2)
						count++;
					ready2 = true;
				}
			}

			if (e.getWiimoteId() == 3) {
				if (e.isButtonAJustPressed()) {
					if (!ready3)
						count++;
					ready3 = true;
				}
			}

			if (e.getWiimoteId() == 4) {
				if (e.isButtonAJustPressed()) {
					if (!ready4)
						count++;
					ready4 = true;
				}
			}
		}
	}

	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {
	}

	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
	}

	public void onExpansionEvent(ExpansionEvent arg0) {
	}

	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
	}

	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
	}

	public void onIrEvent(IREvent arg0) {
	}

	public void onMotionSensingEvent(MotionSensingEvent arg0) {
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
	}

	public void onStatusEvent(StatusEvent arg0) {
	}

	// Save accounts database
	public void saveAccounts() throws FileSystemNotFoundException {
		try {
			FileOutputStream fos = new FileOutputStream("accounts");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(base);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Load account database
	public static AccountBase loadAccounts() throws FileNotFoundException {
		try {
			FileInputStream fis = new FileInputStream("accounts");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object object = ois.readObject();
			ois.close();
			if (object instanceof AccountBase) {
				System.out.println("Accounts have been loaded.");
				AccountBase temp = (AccountBase) object;
				return temp;
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// Voegt punten toe aan accounts
	public void addPoints() {
		volgordeAccounts.get(0).addPoints(500);
		volgordeAccounts.get(1).addPoints(250);
		if (volgordeAccounts.size() > 2)
			volgordeAccounts.get(2).addPoints(100);
		// Saved de nieuwe account gegevens in de account base
		try {
			base = loadAccounts();
			for (Account a : volgordeAccounts) {
				for (int i = 0; i < base.getAccounts().size(); i++) {
					if (base.getAccounts().get(i).getUser().equals(a.getUser()))
						base.getAccounts().set(i, a);
				}
			}
			saveAccounts();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Account> getAccounts() {
		return volgordeAccounts;
	}

}
