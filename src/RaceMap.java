
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
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
		JPanel panel = new RacePanel(2);

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
	ArrayList<RawAcceleration> values1;
	ArrayList<RawAcceleration> values2;
	ArrayList<RawAcceleration> values3;
	int player1;
	int player2;
	int player3;
	int player4;
	int yTurns, yTurns2, yTurns3, yTurns4;
	int place;
	boolean yTurned, yTurned2, yTurned3, yTurned4;
	Image img;
	Image imgPlayer;
	Image imgPlayer2;
	Image hurdle;
	ArrayList<Image> horse;
	ArrayList<Image> horseGray;
	ArrayList<Image> drake;
	int horseTimer;
	int repaintTimer;
	int minimalSpeed = 100;
	ArrayList<Player> players;
	ArrayList<String> placement;
	int newCX;
	int newCY;

	int playerAmount;

	public RacePanel(int playerAmount) {
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
		yTurned = false;
		yTurned2 = false;
		yTurned3 = false;
		yTurned4 = false;
		yTurns = 0;
		yTurns2 = 0;
		yTurns3 = 0;
		yTurns4 = 0;
		setPreferredSize(new Dimension(1366, 768));
		timer.start();
		System.loadLibrary("WiiuseJ");
		wiimotes = WiiUseApiManager.getWiimotes(playerAmount, false);
		for (int i = 0; i < playerAmount; i++) {
			wiimote = wiimotes[i];
			wiimote.activateMotionSensing();
			wiimote.addWiiMoteEventListeners(this);
		}

		values = new ArrayList<>();
		values1 = new ArrayList<>();
		values2 = new ArrayList<>();
		values3 = new ArrayList<>();
		aPanel = new AccelerationWiimoteEventPanel();
		try {
			hurdle = ImageIO.read(new File("src/hurdle.png"));
			img = ImageIO.read(new File("src/map.png"));
			horseGray.add(ImageIO.read(new File("src/spriteGrijsPaard.png")));
			horseGray.add(ImageIO.read(new File("src/spriteGrijsPaard2.png")));
			horseGray.add(ImageIO.read(new File("src/spriteGrijsPaard3.png")));
			horseGray.add(ImageIO.read(new File("src/spriteGrijsPaard4.png")));
			horse.add(ImageIO.read(new File("src/spriteBruinPaard.png")));
			horse.add(ImageIO.read(new File("src/spriteBruinPaard2.png")));
			horse.add(ImageIO.read(new File("src/spriteBruinPaard3.png")));
			horse.add(ImageIO.read(new File("src/spriteBruinPaard4.png")));
			drake.add(ImageIO.read(new File("src/spriteRodeDraak.png")));
			drake.add(ImageIO.read(new File("src/spriteRodeDraak2.png")));
			drake.add(ImageIO.read(new File("src/spriteRodeDraak3.png")));
			drake.add(ImageIO.read(new File("src/spriteRodeDraak4.png")));
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
		Graphics2D g2 = (Graphics2D) g;
		// System.out.println(xTurns + " " + yTurns + " " + zTurns);

		AffineTransform camera = getCamera();
		g2.setTransform(camera);
		g2.drawImage(img, camera, this);

		int loop = 0;
		for (Player p : players) {
			g2.drawImage(p.getSkin(), p.getMovement(), p.getJump(), null);
			// RawAcceleration rAcc;

			if (loop == 0) {
				RawAcceleration rAcc = values.get(values.size() - 1);
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
				player1 = (yTurns * 25) + minimalSpeed;
				p.setMovement(player1);
			} else if (loop == 1) {
				RawAcceleration rAcc = values1.get(values1.size() - 1);
				short yShort = rAcc.getY();
				if (yTurned2) {
					if (yShort > 150) {
						yTurns2++;
						yTurned2 = false;
					}
				} else {
					if (yShort > 75) {
						yTurned2 = true;
					}
				}
				player2 = (yTurns2 * 25) + minimalSpeed;
				p.setMovement(player2);
			} else if (loop == 2) {
				RawAcceleration rAcc = values2.get(values2.size() - 1);
				short yShort = rAcc.getY();
				if (yTurned3) {
					if (yShort > 150) {
						yTurns3++;
						yTurned3 = false;
					}
				} else {
					if (yShort > 75) {
						yTurned3 = true;
					}
				}
				player3 = (yTurns3 * 25) + minimalSpeed;
				p.setMovement(player3);
			} else {
				RawAcceleration rAcc = values3.get(values3.size() - 1);
				short yShort = rAcc.getY();
				if (yTurned4) {
					if (yShort > 150) {
						yTurns4++;
						yTurned4 = false;
					}
				} else {
					if (yShort > 75) {
						yTurned4 = true;
					}
				}
				player4 = (yTurns4 * 25) + minimalSpeed;
				p.setMovement(player4);
			}

			loop++;
		}

		for (Player p : players) {
			{
				if (repaintTimer % 3 == 0) {
					minimalSpeed = minimalSpeed + (int) Math.random() * 500;
					p.setMinimalspeed(minimalSpeed);
				}
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
		if (repaintTimer > 10) {
			horseTimer++;
			for (Player p : players) {
				p.setSkin(drake.get(horseTimer));
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
			if (p.getMovement() > 4550 && p.isFinished == false) {
				p.setPlace(placement.get(place));
				p.setFinished(true);
				place++;
			}
			if (p.isFinished()) {
				g2.drawString(p.getName() + " is " + p.getPlace(), 4600 - 700, p.getRaceHeight());
			}
		}
	}

	private int getLeader() {
		int leader = 0;
		for (Player p : players) {
			if (p.getMovement() > leader) {
				leader = p.getMovement();
			}
		}
		return leader;
	}

	public void makeHurdles(Graphics2D g2) {

	}

	public AffineTransform getCamera() {
		AffineTransform camera = new AffineTransform();
		camera.translate(newCX, newCY);
		boolean cameraMovement = false;
		if (getLeader() > 1000) {
			cameraMovement = true;
		}

		if (newCX > -3300 && cameraMovement) {
			newCX = -getLeader() + getWidth() / 2;

		}

		return camera;

	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		if (arg0.isButtonTwoJustPressed() || arg0.isButtonAJustPressed()) {
			int wiimoteID = arg0.getWiimoteId();
			Player p = players.get(wiimoteID - 1);
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
		values1.clear();
		values2.clear();
		values3.clear();
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
		// System.out.println(arg0.getWiimoteId());
		RawAcceleration rawAcceleration = aPanel.getRawAccelerationValue(arg0);
		if (wiimoteID == 1) {
			if (values.size() >= getWidth()) {
				values.clear();
			}

			if (rawAcceleration != null) {
				values.add(rawAcceleration);
			}
		}
		if (wiimoteID == 2) {
			if (values1.size() >= getWidth()) {
				values1.clear();
			}

			if (rawAcceleration != null) {
				values1.add(rawAcceleration);
			}
		}
		if (wiimoteID == 3) {
			if (values2.size() >= getWidth()) {
				values2.clear();
			}

			if (rawAcceleration != null) {
				values2.add(rawAcceleration);
			}
		}
		if (wiimoteID == 4) {
			if (values3.size() >= getWidth()) {
				values3.clear();
			}

			if (rawAcceleration != null) {
				values3.add(rawAcceleration);
			}
		}

		repaint();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		timer.restart();
	}

}
