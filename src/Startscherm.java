import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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

public class Startscherm extends JPanel implements ActionListener, MouseListener, WiimoteListener {

	private Wiimote wiimote, wiimote2;
	private Wissel wissel;
	private Image img;
	private Image img2;
	private int waarde = 1;
	private boolean apres = false;

	public Startscherm(Wissel wissel) {
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, false);
		this.wiimote = wiimotes[0];
		this.wissel = wissel;
		wiimotes[0].addWiiMoteEventListeners(this);
		setPreferredSize(new Dimension(1024, 768));
		addMouseListener(this);
		SoundEffect.MUSICMAIN.play();

		try {
			img = ImageIO.read(new File("src/skins/newNFB.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		AffineTransform af = new AffineTransform();

		g2D.drawImage(img, af, null);

		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

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

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent e) {
		// TODO Auto-generated method stub

		if (wissel.getWaarde() == 1) {

			if (e.getWiimoteId() == 1) {

				// A
				if (e.isButtonAPressed() && apres == false) {
					waarde = 2;
					wissel.switchcase(2);
					apres = true;
				}
			}

		}

	}
}