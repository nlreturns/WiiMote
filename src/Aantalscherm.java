import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

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

public class Aantalscherm extends JPanel implements ActionListener, WiimoteListener {

	private Wiimote wiimote, wiimote2, wiimote3, wiimote4;
	private int x = 0, y = 0;
	private int aantal;
	private Wiimote[] wiimotes;
	private ArrayList<Account> accounts;
	private Wissel wissel;

	private Random random = new Random();
	private boolean leftUp, rightUp, leftDown, rightDown, aHeld, bHeld, changeColors;

	public Aantalscherm(Wissel wissel) {
		this.wissel = wissel;
		System.loadLibrary("WiiuseJ");
		wiimotes = WiiUseApiManager.getWiimotes(1, true);
		this.wiimote = wiimotes[0];
		wiimotes[0].addWiiMoteEventListeners(this);
	}

	public int getAantal() {
		return aantal;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		if (changeColors) {

			g2D.setColor(Color.RED);
			wiimote2.setLeds(true, true, true, true);
		} else
			g2D.setColor(Color.RED);

		g2D.fillRect(100, 100, 250, 100);
		g2D.setColor(Color.GREEN);
		g2D.fillRect(450, 100, 250, 100);
		g2D.setColor(Color.BLUE);
		g2D.fillRect(800, 100, 250, 100);
		g2D.setColor(Color.BLACK);
		g2D.setFont(new Font("Arial", Font.BOLD, 18));
		g2D.drawString("2 Spelers", 150, 150);
		g2D.drawString("3 Spelers", 500, 150);
		g2D.drawString("4 Spelers", 850, 150);
		g2D.setStroke(new BasicStroke(5));

		if (x == 0)
			g2D.drawRect(100, 100, 250, 100);
		if (x == 1)
			g2D.drawRect(450, 100, 250, 100);
		if (x == 2)
			g2D.drawRect(800, 100, 250, 100);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent e) {

		if (wissel.getWaarde() == 2) {

			if (e.getWiimoteId() == 1) {

				// A
				if (e.isButtonAPressed()) {
					if (x == 0)
						aantal = 2;

					if (x == 1)
						aantal = 3;

					if (x == 2)
						aantal = 4;

					inlogScreens(aantal);
					System.out.println(wiimotes.length);
				}

				if (e.isButtonRightPressed()) {
					x++;
					if (x > 2)
						x = 2;
					repaint();
				}
				if (e.isButtonLeftPressed()) {
					x--;
					if (x < 0)
						x = 0;
					repaint();
				}
			}
		}
	}

	@Override
	public void onIrEvent(IREvent irEvent) {

	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent motionSensingEvent) {

	}

	@Override
	public void onExpansionEvent(ExpansionEvent expansionEvent) {

	}

	@Override
	public void onStatusEvent(StatusEvent statusEvent) {

	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent disconnectionEvent) {

	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent nunchukInsertedEvent) {

	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent nunchukRemovedEvent) {

	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent guitarHeroInsertedEvent) {

	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent guitarHeroRemovedEvent) {

	}

	@Override
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent classicControllerInsertedEvent) {

	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent classicControllerRemovedEvent) {

	}

	public ArrayList<Account> inlogScreens(int aantal) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		for (int i = 0; i < aantal; i++) {
			InlogGUI temp = new InlogGUI();
			boolean ready = false;
			while (ready == false) {
				ready = temp.ready();
			}
			accounts.add(temp.getAccount());
			temp.dispose();
		}
		System.out.println(accounts.size() + "poep");
		this.accounts = new ArrayList<Account>(accounts);
		wissel.switchcase(3);
		return accounts;
	}

	/*
	 * public void inlogScreens(int aantal) { ArrayList<Account> accounts = new
	 * ArrayList<Account>(); for (int i = 0; i < aantal; i++) { InlogGUI temp =
	 * new InlogGUI(); boolean ready = false; while (ready == false) { ready =
	 * temp.ready(); System.out.println("poep"); }
	 * accounts.add(temp.getAccount()); System.out.println("poep2");
	 * temp.dispose();
	 * 
	 * } System.out.println(accounts.size()); wissel.switchcase(3);
	 * this.accounts = new ArrayList<Account>(accounts); }
	 */
	public ArrayList<Account> getAccounts() {
		return accounts;
	}

}