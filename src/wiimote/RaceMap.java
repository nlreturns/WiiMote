
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

public class RaceMap extends JFrame {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Need For Beast");
		JPanel panel = new RacePanel(4);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public RaceMap() {

	}

}

class RacePanel extends JPanel implements WiimoteListener, ActionListener {
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
	int place;
	boolean xTurned;
	boolean yTurned;
	boolean zTurned;
	Image img;
	Image imgPlayer;
	Image imgPlayer2;
	Image hurdle;
	ArrayList<Image> horse;
	ArrayList<Image> horseGray;
	int horseTimer;
	int repaintTimer;
	int minimalSpeed = 1;
	ArrayList<Player> players;
	ArrayList<String> placement;

	int playerAmount;

	public RacePanel(int playerAmount) {
		players = new ArrayList<>();
		horse = new ArrayList<>();
		horseGray = new ArrayList<>();
		placement = new ArrayList<>();
		placement.add("eerste");
		placement.add("tweede");
		placement.add("derde");
		placement.add("vierde");
		place = 0;
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
			hurdle = ImageIO.read(new File("src/wiimote/hurdle.png"));
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
		for (int i = 0; i < playerAmount; i++) {
			players.add(new Player(horse.get(0), i));
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

		int loop = 0;
		for (Player p : players) {
			g2.drawImage(p.getSkin(), p.getMovement(), p.getJump(), null);
			if (loop == 0) {
				player1 = (xTurns * 3) + minimalSpeed;
				p.setMovement(player1);
			} else if (loop == 1) {
				player2 = (yTurns * 3) + minimalSpeed;
				p.setMovement(player2);
			} else if (loop == 2) {
				player3 = (zTurns * 3) + minimalSpeed;
				p.setMovement(player3);
			} else {
				player4 = (xTurns + yTurns + zTurns) + minimalSpeed;
				p.setMovement(player4);

			}
			loop++;
		}

		for (Player p : players) {
			if (repaintTimer % 3 == 0) {
				minimalSpeed++;
				p.setMinimalspeed(minimalSpeed);
			}
		}

		for (Player p : players) {
			int jump = p.getJump();
			if (jump <= p.getRaceHeight()) {
				jump = p.getJump() + 5;
				p.setJump(jump);
			}
		}

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

		repaintTimer++;
		if (repaintTimer > 25) {
			horseTimer++;
			for (Player p : players) {
				p.setSkin(horse.get(horseTimer));
			}
			imgPlayer = horse.get(horseTimer);
			imgPlayer2 = horseGray.get(horseTimer);
			if (horseTimer >= (horse.size() - 1)) {
				horseTimer = 0;
			}
			repaintTimer = 0;
		}

		winner(g2);
	}

	public void winner(Graphics2D g2) {
		g2.setColor(Color.RED);
		for (Player p : players) {
			if (p.getMovement() > getWidth() && p.isFinished == false) {
				p.setPlace(placement.get(place));
				p.setFinished(true);
				place++;
			}
			if (p.isFinished()) {
				g2.drawString(p.getName() + " is " + p.getPlace(), getWidth() / 2, p.getRaceHeight());
			}
		}
	}

	public void makeHurdles(Graphics2D g2) {

	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		if (arg0.isButtonTwoJustPressed() || arg0.isButtonAJustPressed()) {
			for (Player p : players) {
				int jump = p.getJump();
				for (int i = 0; jump >= p.getMaxHeight(); i++)
					jump--;
				p.setJump(jump);
			}
			repaint();

		}
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
