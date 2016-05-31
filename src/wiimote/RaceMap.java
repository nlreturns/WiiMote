
package wiimote;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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

public class SpeedTest extends JFrame {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Opdracht 2");
		JPanel panel = new TestPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public SpeedTest() {

	}

}

class TestPanel extends JPanel implements WiimoteListener, ActionListener {
	Wiimote[] wiimotes;
	AccelerationPanel aPanel;
	Wiimote wiimote;
	JPanel panel;
	RawAcceleration rawAcc;
	Timer timer = new Timer(1, this);
	ArrayList<RawAcceleration> values;
	int player1;
	int player2;
	int player3;
	int player4;
	int xTurns;
	int yTurns;
	int zTurns;
	boolean xTurned;
	boolean yTurned;
	boolean zTurned;
	Image img;
	Image imgPlayer;
	Image imgPlayer2;
	ArrayList<Image> horse;
	ArrayList<Image> horseGray;
	int horseTimer;
	int repaintTimer;

	public TestPanel() {
		horse = new ArrayList<>();
		horseGray = new ArrayList<>();
		xTurned = false;
		yTurned = false;
		zTurned = false;
		xTurns = 0;
		yTurns = 0;
		zTurns = 0;
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
		try {
			img = ImageIO.read(new File("src/wiimote/lol.png"));
			horseGray.add(ImageIO.read(new File("src/wiimote/spriteGrijsPaard.png")));
			horseGray.add(ImageIO.read(new File("src/wiimote/spriteGrijsPaard2.png")));
			horseGray.add(ImageIO.read(new File("src/wiimote/spriteGrijsPaard3.png")));
			horseGray.add(ImageIO.read(new File("src/wiimote/spriteGrijsPaard4.png")));
			horse.add(ImageIO.read(new File("src/wiimote/spriteBruinPaard.png")));
			horse.add(ImageIO.read(new File("src/wiimote/spriteBruinPaard2.png")));
			horse.add(ImageIO.read(new File("src/wiimote/spriteBruinPaard3.png")));
			horse.add(ImageIO.read(new File("src/wiimote/spriteBruinPaard4.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		horseTimer = 0;
		imgPlayer = horse.get(horseTimer);
		imgPlayer2 = horseGray.get(horseTimer);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
		Graphics2D g2 = (Graphics2D) g;
		// System.out.println(xTurns + " " + yTurns + " " + zTurns);
		RawAcceleration rAcc = values.get(values.size() - 1);
		int player1 = (xTurns * 3);
		int player2 = (yTurns * 3);
		int player3 = (zTurns * 3);
		int player4 = (xTurns + yTurns + zTurns);

		// Shape s = new Ellipse2D.Double(player1, 100, 5, 5);
		g2.drawImage(imgPlayer, player1, 100, null);
		// Shape s2 = new Ellipse2D.Double(player2, 300, 5, 5);
		g2.drawImage(imgPlayer2, player2, 250, null);
		// Shape s3 = new Ellipse2D.Double(player3, 500, 5, 5);
		g2.drawImage(imgPlayer, player3, 400, null);
		// Shape s4 = new Ellipse2D.Double(player4, 700, 5, 5);
		g2.drawImage(imgPlayer2, player4, 550, null);

		// g2.setColor(Color.RED);
		// g2.fill(s);
		// g2.setColor(Color.YELLOW);
		// g2.fill(s2);
		// g2.setColor(Color.BLUE);
		// g2.fill(s3);
		// g2.setColor(Color.GREEN);
		// g2.fill(s4);
		short xShort = rAcc.getX();
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
		short yShort = rAcc.getY();
		if (yTurned) {
			if (yShort > 150) {
				yTurns++;
				yTurned = false;
			}
		} else {
			if (yShort > 75) {
				yTurned = true;
			}
		}
		short zShort = rAcc.getZ();
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

		g2.setColor(Color.RED);
		if (player1 > getWidth()) {
			g2.drawString("Player 1 wint", getWidth() / 2, getHeight() / 2);
		} else if (player2 > getWidth()) {
			g2.drawString("Player 2 wint", getWidth() / 2, getHeight() / 2);
		} else if (player3 > getWidth()) {
			g2.drawString("Player 3 wint", getWidth() / 2, getHeight() / 2);
		} else if (player4 > getWidth()) {
			g2.drawString("Player 4 wint", getWidth() / 2, getHeight() / 2);
		}

		repaintTimer++;

		if (repaintTimer > 25) {
			horseTimer++;
			imgPlayer = horse.get(horseTimer);
			imgPlayer2 = horseGray.get(horseTimer);
			if (horseTimer >= (horse.size() - 1)) {
				horseTimer = 0;
			}
			repaintTimer = 0;
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
			values.clear();
		}
		RawAcceleration rawAcceleration = aPanel.getRawAccelerationValue(arg0);

		if (rawAcceleration != null) {
			values.add(rawAcceleration);
		}

		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		timer.restart();
	}

}
