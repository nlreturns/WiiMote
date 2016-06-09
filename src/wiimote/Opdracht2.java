package wiimote;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.utils.AccelerationPanel;
import wiiusej.utils.AccelerationWiimoteEventPanel;
import wiiusej.values.RawAcceleration;
import wiiusej.wiiusejevents.GenericEvent;
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

public class Opdracht2 extends JFrame {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Opdracht 2");
		JPanel panel = new Panel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public Opdracht2() {

	}

}

class Panel extends JPanel implements WiimoteListener, ActionListener {
	Wiimote[] wiimotes;
	AccelerationPanel aPanel;
	Wiimote wiimote;
	JPanel panel;
	RawAcceleration rawAcc;
	float yaw;
	int x;
	int y;
	int z;
	int xx;
	int yy;
	int zz;
	short time;
	short oldTime;
	Timer timer = new Timer(1, this);
	ArrayList<RawAcceleration> values;
	Font bigFont;
	Font smallFont;
	int xTurns;
	int zTurns;
	boolean xTurned;
	boolean zTurned;

	public Panel() {
		xTurned = false;
		zTurned = false;
		xTurns = 0;
		zTurns = 0;
		yaw = 0;
		time = 0;
		oldTime = 0;
		setPreferredSize(new Dimension(1366, 768));
		timer.start();
		System.loadLibrary("WiiuseJ");
		wiimotes = WiiUseApiManager.getWiimotes(1, false);
		wiimote = wiimotes[0];
		values = new ArrayList<>();
		wiimote.activateMotionSensing();
		wiimote.addWiiMoteEventListeners(this);
		aPanel = new AccelerationWiimoteEventPanel();
		rawAcc = new RawAcceleration();

		values.add(rawAcc);
		x = 0;
		y = 0;
		z = 0;
		xx = 0;
		yy = 0;
		zz = 0;
		smallFont = new Font("Arial", 0, 10);
		bigFont = new Font("Arial", 0, 12);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);

		// x lijnen
		g2.drawLine(0, 163, getWidth(), 163);
		g2.drawLine(0, 363, getWidth(), 363);
		g2.drawLine(0, 575, getWidth(), 575);

		// hulp x lijnen voor x RawAcc
		g2.setColor(Color.GREEN);
		g2.drawLine(0, 100, getWidth(), 100);
		g2.drawLine(0, 137, getWidth(), 137);
		g2.drawLine(0, 200, getWidth(), 200);

		// hulp x lijnen voor y RawAcc
		g2.drawLine(0, 337, getWidth(), 337);
		g2.drawLine(0, 400, getWidth(), 400);

		// hulp x lijnen voor z RawAcc
		g2.drawLine(0, 500, getWidth(), 500);
		g2.drawLine(0, 550, getWidth(), 550);
		g2.drawLine(0, 625, getWidth(), 625);

		// y lijnen
		g2.setColor(Color.BLACK);
		for (int yl = 100; yl < 200; yl = yl + 300) {
			g2.drawLine(yl, 100, yl, 200);
			g2.drawLine(yl, 337, yl, 400);
			g2.drawLine(yl, 500, yl, 625);
		}

		// font kleiner maken
		g2.setFont(smallFont);

		// x waardes
		for (int xw = 105; xw < 200; xw = xw + 300) {
			g2.drawString("0", xw, 100);
			g2.drawString("75", xw, 137);
			g2.drawString("125", xw, 163);
			g2.drawString("200", xw, 200);
		}

		// y waardes
		for (int yw = 105; yw < 200; yw = yw + 300) {
			g2.drawString("75", yw, 337);
			g2.drawString("125", yw, 363);
			g2.drawString("200", yw, 400);
		}

		// z waardes
		for (int zw = 105; zw < 200; zw = zw + 300) {
			g2.drawString("0", zw, 500);
			g2.drawString("100", zw, 550);
			g2.drawString("150", zw, 575);
			g2.drawString("250", zw, 625);
		}

		// font groter maken
		g2.setFont(bigFont);

		// telkens als er een nieuwe waarde in de arraylist wordt toegevoegd
		// wordt deze toegevoegd en dus getekend.
		for (int i = 0; i < values.size() && i < getWidth(); i++) {
			// Uitlezen van RawAcceleration waarde
			RawAcceleration r = values.get(i);
			xx = x;
			yy = y;
			zz = z;
			Short xShort = r.getX();
			Short yShort = r.getY();
			Short zShort = r.getZ();
			x = (int) xShort / 2;
			y = (int) yShort / 2;
			z = (int) zShort / 2;

			// hulp printline voor de waarde niet gedeeld door 2.
			// System.out.println(xShort + " " + yShort + " " + zShort);

			// RawAcceleration lijnen voor de x, y en z waarde van de RawAcc
			g2.setColor(Color.RED);
			g2.drawString("X lijn", 50, 75);
			g2.drawString(xTurns + " ", 200, 75);
			g2.drawLine(i - 1, xx + 100, i, x + 100);
			g2.setColor(Color.MAGENTA);
			g2.drawString("Y lijn", 50, 275);
			g2.drawLine(i - 1, yy + 300, i, y + 300);
			g2.setColor(Color.BLUE);
			g2.drawString("Z lijn", 50, 475);
			g2.drawString(zTurns + " ", 200, 475);
			g2.drawLine(i - 1, zz + 500, i, z + 500);
		}
		System.out.println(xTurns + " " + zTurns);
		// 95 155, 100 200
		RawAcceleration rA = values.get(values.size() - 1);
		short xShort = rA.getX();
		if (xTurned) {
			if (xShort > 165) {
				xTurns++;
				xTurned = false;
			}
		} else {
			if (xShort > 85) {
				xTurned = true;
			}
		}
		short zShort = rA.getZ();
		if (zTurned) {
			if (zShort > 200) {
				zTurns++;
				zTurned = false;
			}
		} else {
			if (zShort > 100) {
				zTurned = true;
			}
		}

	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {

	}

	@Override
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {

	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {

	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		values.clear();
		repaint();
	}

	@Override
	public void onExpansionEvent(ExpansionEvent arg0) {
		draw(arg0);
	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {

	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {

	}

	@Override
	public void onIrEvent(IREvent arg0) {

	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		draw(arg0);
	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {

	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {

	}

	@Override
	public void onStatusEvent(StatusEvent arg0) {

	}

	private void draw(GenericEvent arg0) {
		if (values.size() >= getWidth()) {
			// als de grafiek buiten beeld gaat de waarde verwijderen zodat
			// de lijn weer vanaf links begint
			values.clear();
		}
		RawAcceleration rawAcceleration = aPanel.getRawAccelerationValue(arg0);
		// System.out.println(turns + " " + yaw);

		if (rawAcceleration != null) {
			// toevoegen van waarde om te tekenen
			values.add(rawAcceleration);
		}

		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		time++;
		repaint();
		timer.restart();
	}

}
