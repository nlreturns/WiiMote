
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

public class RaceMap extends JPanel implements WiimoteListener, ActionListener {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Need For Beast");
		ArrayList<Account> accountsTest = new ArrayList<Account>();
		accountsTest.add(new Account("p", "p", 10000));
		accountsTest.get(0).setSelectedSkin((new Skin("Rode draak", 1000, "src/skins/spritePhoenixEen.png")));
		JPanel panel = new RaceMap(1, accountsTest, null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	private Wiimote[] wiimotes;
	private AccelerationPanel aPanel;
	private Wiimote wiimote;
	private Timer timer = new Timer(1000 / 50, this);
	private ArrayList<RawAcceleration> values;
	private ArrayList<RawAcceleration> values1;
	private ArrayList<RawAcceleration> values2;
	private ArrayList<RawAcceleration> values3;
	private int player1;
	private int player2;
	private int player3;
	private int player4;
	private int yTurns, yTurns2, yTurns3, yTurns4;
	private int place;
	private int ticks = 0;
	private boolean yTurned, yTurned2, yTurned3, yTurned4;
	private Image img;
	private ArrayList<Image> horse, horseGray, drake, lucio, explosion, phoenix, pikachu, counter;
	private int horseTimer;
	private int repaintTimer;
	private int minimalSpeed = 100;
	private ArrayList<Player> players;
	private ArrayList<String> placement;
	private ArrayList<Account> accounts;
	private ArrayList<Account> sortedAccounts;
	private int newCX;
	private int newCY;
	private boolean countdown;
	private boolean switchedScreen;
	private Wissel wissel;

	int playerAmount;

	public RaceMap(int playerAmount, ArrayList<Account> accounts, Wissel wissel) {
		this.wissel = wissel;
		this.playerAmount = playerAmount;
		countdown = false;
		switchedScreen = false;
		players = new ArrayList<>();
		horse = new ArrayList<>();
		horseGray = new ArrayList<>();
		drake = new ArrayList<>();
		lucio = new ArrayList<>();
		pikachu = new ArrayList<>();
		explosion = new ArrayList<>();
		phoenix = new ArrayList<>();
		counter = new ArrayList<>();
		placement = new ArrayList<>();
		this.accounts = accounts;
		sortedAccounts = new ArrayList<>();
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
		setPreferredSize(new Dimension(1024, 768));
		timer.start();
		System.loadLibrary("WiiuseJ");
		WiiUseApiManager.shutdown();
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
			img = ImageIO.read(new File("src/skins/map.png"));
			horseGray.add(ImageIO.read(new File("src/skins/spriteGrijsPaard.png")));
			horseGray.add(ImageIO.read(new File("src/skins/spriteGrijsPaard2.png")));
			horseGray.add(ImageIO.read(new File("src/skins/spriteGrijsPaard3.png")));
			horseGray.add(ImageIO.read(new File("src/skins/spriteGrijsPaard4.png")));
			horse.add(ImageIO.read(new File("src/skins/spriteBruinPaard.png")));
			horse.add(ImageIO.read(new File("src/skins/spriteBruinPaard2.png")));
			horse.add(ImageIO.read(new File("src/skins/spriteBruinPaard3.png")));
			horse.add(ImageIO.read(new File("src/skins/spriteBruinPaard4.png")));
			drake.add(ImageIO.read(new File("src/skins/spriteRodeDraak.png")));
			drake.add(ImageIO.read(new File("src/skins/spriteRodeDraak2.png")));
			drake.add(ImageIO.read(new File("src/skins/spriteRodeDraak3.png")));
			drake.add(ImageIO.read(new File("src/skins/spriteRodeDraak4.png")));
			lucio.add(ImageIO.read(new File("src/skins/spriteLucioEen.png")));
			lucio.add(ImageIO.read(new File("src/skins/spriteLucioTwee.png")));
			lucio.add(ImageIO.read(new File("src/skins/spriteLucioDrie.png")));
			lucio.add(ImageIO.read(new File("src/skins/spriteLucioVier.png")));
			explosion.add(ImageIO.read(new File("src/skins/spriteExplosionEen.png")));
			explosion.add(ImageIO.read(new File("src/skins/spriteExplosionTwee.png")));
			explosion.add(ImageIO.read(new File("src/skins/spriteExplosionDrie.png")));
			explosion.add(ImageIO.read(new File("src/skins/spriteExplosionVier.png")));
			pikachu.add(ImageIO.read(new File("src/skins/pikatsjoe1.png")));
			pikachu.add(ImageIO.read(new File("src/skins/pikatsjoe2.png")));
			pikachu.add(ImageIO.read(new File("src/skins/pikatsjoe3.png")));
			pikachu.add(ImageIO.read(new File("src/skins/pikatsjoe4.png")));
			phoenix.add(ImageIO.read(new File("src/skins/spritePhoenixEen.png")));
			phoenix.add(ImageIO.read(new File("src/skins/spritePhoenixTwee.png")));
			phoenix.add(ImageIO.read(new File("src/skins/spritePhoenixDrie.png")));
			phoenix.add(ImageIO.read(new File("src/skins/spritePhoenixVier.png")));
<<<<<<< HEAD
			SoundEffect.MUSICMAIN.stop();
			SoundEffect.MUSICRACE1.play();
=======
			counter.add(ImageIO.read(new File("src/skins/vier.png")));
			counter.add(ImageIO.read(new File("src/skins/drie.png")));
			counter.add(ImageIO.read(new File("src/skins/twee.png")));
			counter.add(ImageIO.read(new File("src/skins/een.png")));
			counter.add(ImageIO.read(new File("src/skins/go.png")));
>>>>>>> refs/remotes/origin/master
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < playerAmount; i++) {
			players.add(new Player(horse.get(0), i));
		}
		horseTimer = 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		AffineTransform camera = getCamera();
		g2.setTransform(camera);
		g2.drawImage(img, camera, this);

		if (ticks > 20 && ticks < 40) {
			g2.drawImage(counter.get(0), getWidth() / 2, getHeight() / 2, null);
		}
		if (ticks > 40 && ticks < 60) {
			g2.drawImage(counter.get(1), getWidth() / 2, getHeight() / 2, null);
		}
		if (ticks > 60 && ticks < 80) {
			g2.drawImage(counter.get(2), getWidth() / 2, getHeight() / 2, null);
		}
		if (ticks > 80 && ticks < 100) {
			g2.drawImage(counter.get(3), getWidth() / 2, getHeight() / 2, null);
		}
		if (ticks > 100 && ticks < 120) {
			g2.drawImage(counter.get(4), getWidth() / 2, getHeight() / 2, null);
		}

		int loop = 0;
		for (Player p : players) {
			g2.drawImage(p.getSkin(), p.getMovement(), p.getJump(), null);
			if (countdown) {
				try {

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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
			int i = 0;
			for (Account a : accounts) {
				if (a.getSelectedSkin().getName().equals("Paard")) {
					players.get(i).setSkin(horseGray.get(horseTimer));
				}
				if (a.getSelectedSkin().getName().equals("Phoenix")) {
					players.get(i).setSkin(phoenix.get(horseTimer));
				}
				if (a.getSelectedSkin().getName().equals("Mooi paard")) {
					players.get(i).setSkin(horse.get(horseTimer));
				}
				if (a.getSelectedSkin().getName().equals("Explosie")) {
					players.get(i).setSkin(explosion.get(horseTimer));
				}
				if (a.getSelectedSkin().getName().equals("Lucio")) {
					players.get(i).setSkin(lucio.get(horseTimer));
				}
				if (a.getSelectedSkin().getName().equals("Pikachu")) {
					players.get(i).setSkin(pikachu.get(horseTimer));
				}
				if (a.getSelectedSkin().getName().equals("Rode draak")) {
					players.get(i).setSkin(drake.get(horseTimer));
				}
				i++;
			}
			if (horseTimer >= (horse.size() - 1)) {
				horseTimer = 0;
			}
			repaintTimer = 0;
		}

		winner(g2);
		switchScreen();
	}

	public void winner(Graphics2D g2) {
		g2.setColor(Color.RED);
		int i = 0;
		for (Player p : players) {
			if (p.getMovement() > 4350 && p.isFinished == false) {
				p.setPlace(placement.get(place));
				p.setFinished(true);
				place++;
			}
			if (p.isFinished()) {
				g2.drawString(accounts.get(i).getUser() + " is " + p.getPlace(), 4600 - 700, p.getRaceHeight());
			}
			if (p.isSwitched() == false && p.isFinished()) {
				sortedAccounts.add(accounts.get(i));
				p.setSwitched(true);
			}
			i++;
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

	public void switchScreen() {
		if (playerAmount == sortedAccounts.size() && switchedScreen == false) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			wissel.switchcase(5);
			switchedScreen = true;
		}
	}

	public ArrayList<Account> getSortedAccounts() {
		return sortedAccounts;
	}

	public AffineTransform getCamera() {
		AffineTransform camera = new AffineTransform();
		camera.translate(newCX, newCY);
		boolean cameraMovement = false;
		if (getLeader() > 1000) {
			cameraMovement = true;
		}

		if (newCX > -3450 && cameraMovement) {
			newCX = -getLeader() + getWidth() / 2;

		}

		return camera;

	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		if (wissel.getWaarde() == 4) {

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
		if (wissel.getWaarde() == 4) {
			draw(arg0);
		}
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
		if (wissel.getWaarde() == 4) {
			draw(arg0);
		}
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
		if (countdown) {
			int wiimoteID = arg0.getWiimoteId();
			try {
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

			catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (wissel.getWaarde() == 4) {
			repaint();
			ticks++;
			if (ticks == 100) {
				countdown = true;
			}
			timer.restart();
		}
	}

}
