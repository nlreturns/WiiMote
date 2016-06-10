import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.Timer;

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

public class ShopGUI extends JPanel implements ActionListener, WiimoteListener {

	private ArrayList<Skin> skins;
	private Wissel wissel;
	private int count = 0;
	private ArrayList<Account> accounts;
	private int i1 = 1, i2 = 0, i3 = 0, i4 = 0;
	private boolean ready1, ready2, ready3, ready4 = false;
	private Wiimote[] wiimotes;
	private Wiimote wiimote;

	public ShopGUI(ArrayList<Account> accounts, Wissel wissel) {
		this.wissel = wissel;
		setPreferredSize(new Dimension(1024, 768));
		Timer timer = new Timer(1000 / 50, this);
		timer.start();
		this.accounts = new ArrayList<Account>(accounts);
		System.loadLibrary("WiiuseJ");
		WiiUseApiManager.shutdown();
		wiimotes = WiiUseApiManager.getWiimotes(accounts.size(), false);
		System.out.println(wiimotes.length);
		for (int i = 0; i < wiimotes.length; i++) {
			wiimote = wiimotes[i];
			wiimote.addWiiMoteEventListeners(this);
		
		}
		SoundEffect.MUSICRACE1.stop();
		SoundEffect.MUSICRACE2.stop();
		SoundEffect.MUSICMAIN.stop();
		SoundEffect.SELECT.play();
		
		setVisible(true);
		ready1 = false;
		ready2 = false;
		ready3 = false;
		ready4 = false;
		skins = new ArrayList<Skin>();
		makeSkins();
	}

	/**
	 * Timer actionperformed. (20 fps)
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (wissel.getWaarde() == 3) {

			boolean temp = true;
			if (count == accounts.size()) {
				wissel.switchcase(4);
				count = 0;
			}

			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("Arial", Font.BOLD, 13));

		// Player 1 skin
		g2d.drawRect(150, 150, 150, 150);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(150, 150, 150, 150);
		g2d.setColor(Color.BLACK);
		g2d.drawString(accounts.get(0).getUser() + " punten: " + accounts.get(0).getPoints(), 150, 120);
		g2d.drawString(skins.get(i1).getName(), 150, 140);
		if (skinUnlocked(accounts.get(0), skins.get(i1))) {
			if (ready1) {
				g2d.setColor(Color.GREEN);
				g2d.drawString("Klaar!", 200, 320);
				g2d.setColor(Color.BLACK);
			} else
				g2d.drawString("Selecteer", 200, 320);
		} else
			g2d.drawString("Kost: " + skins.get(i1).getCost(), 200, 320);
		AffineTransform skin1 = new AffineTransform();
		skin1.translate(225 - skins.get(i1).getSkinImage().getWidth(getFocusCycleRootAncestor()) / 2,
				225 - skins.get(i1).getSkinImage().getHeight(getFocusCycleRootAncestor()) / 2);
		g2d.drawImage(skins.get(i1).getSkinImage(), skin1, null);
		// Player 2 skin
		g2d.drawRect(650, 150, 150, 150);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(650, 150, 150, 150);
		g2d.setColor(Color.BLACK);
		g2d.drawString(accounts.get(1).getUser() + " punten: " + accounts.get(1).getPoints(), 650, 120);
		g2d.drawString(skins.get(i2).getName(), 650, 140);
		if (skinUnlocked(accounts.get(1), skins.get(i2))) {
			if (ready2) {
				g2d.setColor(Color.GREEN);
				g2d.drawString("Klaar!", 700, 320);
				g2d.setColor(Color.BLACK);
			} else
				g2d.drawString("Selecteer", 695, 320);
		} else
			g2d.drawString("Kost: " + skins.get(i2).getCost(), 695, 320);
		AffineTransform skin2 = new AffineTransform();
		skin2.translate(725 - (skins.get(i2).getSkinImage().getWidth(getFocusCycleRootAncestor()) / 2),
				225 - skins.get(i2).getSkinImage().getHeight(getFocusCycleRootAncestor()) / 2);
		g2d.drawImage(skins.get(i2).getSkinImage(), skin2, null);
		// Player 3 skin
		if (accounts.size() > 2) {
			g2d.drawRect(150, 500, 150, 150);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(150, 500, 150, 150);
			g2d.setColor(Color.BLACK);
			g2d.drawString(accounts.get(2).getUser() + " punten: " + accounts.get(2).getPoints(), 150, 470);
			g2d.drawString(skins.get(i3).getName(), 150, 490);
			if (skinUnlocked(accounts.get(2), skins.get(i3))) {
				if (ready3) {
					g2d.setColor(Color.GREEN);
					g2d.drawString("Klaar!", 200, 670);
					g2d.setColor(Color.BLACK);
				} else
					g2d.drawString("Selecteer", 195, 670);
			} else
				g2d.drawString("Kost: " + skins.get(i3).getCost(), 195, 670);
			AffineTransform skin3 = new AffineTransform();
			skin3.translate(225 - skins.get(i3).getSkinImage().getWidth(getFocusCycleRootAncestor()) / 2,
					575 - skins.get(i3).getSkinImage().getHeight(getFocusCycleRootAncestor()) / 2);
			g2d.drawImage(skins.get(i3).getSkinImage(), skin3, null);
		}
		// Player 4 skin
		if (accounts.size() > 3) {
			g2d.drawRect(650, 500, 150, 150);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(650, 500, 150, 150);
			g2d.setColor(Color.BLACK);
			g2d.drawString(accounts.get(3).getUser() + " punten: " + accounts.get(3).getPoints(), 650, 470);
			g2d.drawString(skins.get(i4).getName(), 650, 490);
			if (skinUnlocked(accounts.get(3), skins.get(i4))) {
				if (ready4) {
					g2d.setColor(Color.GREEN);
					g2d.drawString("Klaar!", 700, 670);
					g2d.setColor(Color.BLACK);
				} else
					g2d.drawString("Selecteer", 695, 670);
			} else
				g2d.drawString("Kost: " + skins.get(i4).getCost(), 695, 670);
			AffineTransform skin4 = new AffineTransform();
			skin4.translate(725 - skins.get(i4).getSkinImage().getWidth(getFocusCycleRootAncestor()) / 2,
					575 - skins.get(i4).getSkinImage().getHeight(getFocusCycleRootAncestor()) / 2);
			g2d.drawImage(skins.get(i4).getSkinImage(), skin4, null);
		}
	}

	public boolean skinUnlocked(Account account, Skin skin) {
		boolean unlocked = false;
		for (Skin s : account.getSkins()) {
			if (s.getName().equals(skin.getName()))
				unlocked = true;
		}
		return unlocked;
	}

	public void makeSkins() {
		skins.add(new Skin("Paard", 0, "src/skins/spriteGrijsPaard.png"));
		skins.add(new Skin("Mooi paard", 500, "src/skins/spriteBruinPaard.png"));
		skins.add(new Skin("Rode draak", 1000, "src/skins/spriteRodeDraak3.png"));
		skins.add(new Skin("Lucio", 1000, "src/skins/spriteLucioEen.png"));
		skins.add(new Skin("Explosie", 5000, "src/skins/spriteExplosionEen.png"));
		skins.add(new Skin("Pikachu", 2000, "src/skins/pikatsjoe1.png"));
		skins.add(new Skin("Phoenix", 1000, "src/skins/spritePhoenixEen.png"));
	}

	public void onButtonsEvent(WiimoteButtonsEvent e) {

		if (wissel.getWaarde() == 3) {

			if (e.getWiimoteId() == 1) {
				if (!ready1) {
					if (e.isButtonLeftJustPressed())
						i1 = (i1 - 1) % skins.size();
					if (i1 < 0)
						i1 = skins.size() - 1;
					if (e.isButtonRightJustPressed())
						i1 = (i1 + 1) % skins.size();
				}
				if (e.isButtonAJustPressed()) {
					if (skinUnlocked(accounts.get(0), skins.get(i1))) {
						accounts.get(0).setSelectedSkin(skins.get(i1));
						if (!ready1)
							count++;
						ready1 = true;
					} else if(accounts.get(0).getPoints() >= skins.get(i1).getCost()) {
						accounts.get(0).addSkin(skins.get(i1));
						accounts.get(0).subtractPoints(skins.get(i1).getCost());
					}
				}
				if(e.isButtonBJustPressed()) {
					if(ready1)
						ready1 = false;
				}
			}

			if (e.getWiimoteId() == 2) {
				if (!ready2) {
					if (e.isButtonLeftJustPressed())
						i2 = (i2 - 1) % skins.size();
					if (i2 < 0) {
						i2 = skins.size() - 1;
					}

					if (e.isButtonRightJustPressed())
						i2 = (i2 + 1) % skins.size();
				}
				if (e.isButtonAJustPressed()) {
					if (skinUnlocked(accounts.get(1), skins.get(i2))) {
						accounts.get(1).setSelectedSkin(skins.get(i2));
						if (!ready2)
							count++;
						ready2 = true;
					} else if(accounts.get(1).getPoints() >= skins.get(i2).getCost()){
						accounts.get(1).addSkin(skins.get(i2));
						accounts.get(1).subtractPoints(skins.get(i2).getCost());
					}
				}
				if(e.isButtonBJustPressed()) {
					if(ready2)
						ready2 = false;
				}
			}

			if (e.getWiimoteId() == 3) {
				if (!ready3) {
					if (e.isButtonLeftJustPressed())
						i3 = (i3 - 1) % skins.size();
					if (i3 < 0) {
						i3 = skins.size() - 1;
					}

					if (e.isButtonRightJustPressed())
						i3 = (i3 + 1) % skins.size();
				}
				if (e.isButtonAJustPressed()) {
					if (skinUnlocked(accounts.get(2), skins.get(i3))) {
						accounts.get(2).setSelectedSkin(skins.get(i3));
						if (!ready3)
							count++;
						ready3 = true;
					} else if(accounts.get(2).getPoints() >= skins.get(i3).getCost()) {
						accounts.get(2).addSkin(skins.get(i3));
						accounts.get(2).subtractPoints(skins.get(i3).getCost());
					}
				}
				if(e.isButtonBJustPressed()) {
					if(ready3)
						ready3 = false;
				}
			}

			if (e.getWiimoteId() == 4) {
				if (!ready4) {
					if (e.isButtonLeftJustPressed())
						i4 = (i4 - 1) % skins.size();

					if (e.isButtonRightJustPressed())
						i4 = (i4 + 1) % skins.size();
				}
				if (e.isButtonAJustPressed()) {
					if (skinUnlocked(accounts.get(3), skins.get(i4))) {
						accounts.get(3).setSelectedSkin(skins.get(i4));
						if (!ready4)
							count++;
						ready4 = true;
					} else if(accounts.get(3).getPoints() >= skins.get(i4).getCost()) {
						accounts.get(3).addSkin(skins.get(i4));
						accounts.get(3).subtractPoints(skins.get(i4).getCost());
					}
				}
				if(e.isButtonBJustPressed()) {
					if(ready4)
						ready4 = false;
				}
			}
		}
	}

	public void onIrEvent(IREvent irEvent) {
	}

	public void onMotionSensingEvent(MotionSensingEvent motionSensingEvent) {
	}

	public void onExpansionEvent(ExpansionEvent expansionEvent) {
	}

	public void onStatusEvent(StatusEvent statusEvent) {
	}

	public void onDisconnectionEvent(DisconnectionEvent disconnectionEvent) {
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent nunchukInsertedEvent) {
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent nunchukRemovedEvent) {
	}

	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent guitarHeroInsertedEvent) {
	}

	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent guitarHeroRemovedEvent) {
	}

	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent classicControllerInsertedEvent) {
	}

	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent classicControllerRemovedEvent) {
	}

}
