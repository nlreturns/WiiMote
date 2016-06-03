
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

public class RaceMap extends JPanel implements WiimoteListener, ActionListener {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Need For Beast");
		JPanel panel = new RaceMap(1);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setSize(1366, 768);
		frame.setVisible(true);
	}

	Wiimote[] wiimotes;
	AccelerationPanel aPanel;
	Wiimote wiimote;
	JPanel panel;
	RawAcceleration rawAcc;
	Timer timer = new Timer(1, this);
	ArrayList<RawAcceleration> values;
	ArrayList<RawAcceleration> values1;
	ArrayList<RawAcceleration> values2;
	ArrayList<RawAcceleration> values3;
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
	ArrayList<Image> drake;
	int horseTimer;
	int repaintTimer;
	int minimalSpeed = 1;
	ArrayList<Player> players;
	ArrayList<String> placement;

	int playerAmount;

	public RaceMap(int playerAmount) {
		players = new ArrayList<>();
		horse = new ArrayList<>();
		horseGray = new ArrayList<>();
		drake = new ArrayList<>();
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
		wiimotes = WiiUseApiManager.getWiimotes(playerAmount, false);
		for (int i = 0; i > playerAmount; i++) {
			wiimote = wiimotes[i];
			wiimote.activateMotionSensing();
			wiimote.addWiiMoteEventListeners(this);
		}

		values = new ArrayList<>();
		values1 = new ArrayList<>();
		values2 = new ArrayList<>();
		values3 = new ArrayList<>();
		aPanel = new AccelerationWiimoteEventPanel();
		rawAcc = new RawAcceleration();
		values.add(rawAcc);
		try {
			hurdle = ImageIO.read(new File("WiiMote/src/hurdle.png"));
			img = ImageIO.read(new File("WiiMote/src/lol.png"));
			horseGray.add(ImageIO.read(new File("WiiMote/src/spriteGrijsPaard.png")));
			horseGray.add(ImageIO.read(new File("WiiMote/src/spriteGrijsPaard2.png")));
			horseGray.add(ImageIO.read(new File("WiiMote/src/spriteGrijsPaard3.png")));
			horseGray.add(ImageIO.read(new File("WiiMote/src/spriteGrijsPaard4.png")));
			horse.add(ImageIO.read(new File("WiiMote/src/spriteBruinPaard.png")));
			horse.add(ImageIO.read(new File("WiiMote/src/spriteBruinPaard2.png")));
			horse.add(ImageIO.read(new File("WiiMote/src/spriteBruinPaard3.png")));
			horse.add(ImageIO.read(new File("WiiMote/src/spriteBruinPaard4.png")));
			drake.add(ImageIO.read(new File("WiiMote/src/spriteRodeDraak.png")));
			drake.add(ImageIO.read(new File("WiiMote/src/spriteRodeDraak2.png")));
			drake.add(ImageIO.read(new File("WiiMote/src/spriteRodeDraak3.png")));
			drake.add(ImageIO.read(new File("WiiMote/src/spriteRodeDraak4.png")));
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

		int loop = 0;
		for (Player p : players) {
			g2.drawImage(p.getSkin(), p.getMovement(), p.getJump(), null);
			RawAcceleration rAcc;
			if (loop == 0) {
				rAcc = values.get(values.size() - 1);
				player1 = (yTurns * 3) + minimalSpeed;
				p.setMovement(player1);
			} else if (loop == 1) {
				rAcc = values1.get(values1.size() - 1);
				player2 = (yTurns * 3) + minimalSpeed;
				p.setMovement(player2);
			} else if (loop == 2) {
				rAcc = values2.get(values2.size() - 1);
				player3 = (yTurns * 3) + minimalSpeed;
				p.setMovement(player3);
			} else {
				rAcc = values3.get(values3.size() - 1);
				player4 = (yTurns * 3) + minimalSpeed;
				p.setMovement(player4);
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
			//loop++;
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

		// x 165 85 z 200 100
		repaintTimer++;
		if (repaintTimer > 25) {
			horseTimer++;
			for (Player p : players) {
				p.setSkin(drake.get(horseTimer));
			}
			imgPlayer = horse.get(horseTimer);
			imgPlayer2 = horseGray.get(horseTimer);
			if (horseTimer >= (drake.size() - 1)) {
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
			int wiimoteID = arg0.getWiimoteId();
			Player p = players.get(wiimoteID);
			int jump = p.getJump();
			for (int i = 0; jump >= p.getMaxHeight(); i++)
				jump--;
			p.setJump(jump);

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
		int wiimoteID = arg0.getWiimoteId();
		if (wiimoteID == 0) {
			if (values.size() >= getWidth()) {
				values.clear();
			}
			RawAcceleration rawAcceleration = aPanel.getRawAccelerationValue(arg0);

			if (rawAcceleration != null) {
				values.add(rawAcceleration);
			} else if (wiimoteID == 1) {
				if (values1.size() >= getWidth()) {
					values1.clear();
				}
				RawAcceleration rawAcceleration1 = aPanel.getRawAccelerationValue(arg0);

				if (rawAcceleration1 != null) {
					values1.add(rawAcceleration1);
				}
			} else if (wiimoteID == 2) {
				if (values2.size() >= getWidth()) {
					values2.clear();
				}
				RawAcceleration rawAcceleration2 = aPanel.getRawAccelerationValue(arg0);

				if (rawAcceleration2 != null) {
					values2.add(rawAcceleration2);
				}

			} else {
				if (values3.size() >= getWidth()) {
					values3.clear();
				}
				RawAcceleration rawAcceleration3 = aPanel.getRawAccelerationValue(arg0);

				if (rawAcceleration3 != null) {
					values3.add(rawAcceleration3);
				}

			}

			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		timer.restart();
	}

}
