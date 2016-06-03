import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;
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

public class ShopGUI extends JPanel implements ActionListener, WiimoteListener {
	
	private ArrayList<Skin> skins;
	private ArrayList<Account> accounts;
	private int i1 = 1, i2 = 0, i3 = 0, i4 = 0;
	private boolean ready1 = false, ready2 = false, ready3 = false, ready4 = false;
	private Wiimote wiimote, wiimote2, wiimote3, wiimote4;
	private Wiimote[] wiimotes;
	
	public static void main(String s[]) {
		JFrame frame = new JFrame("ShopGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ArrayList<Account> temp = new ArrayList<Account>();
		temp.add(new Account("Rico", "pass", 500));
		temp.add(new Account("Justin", "pass", 500));
		temp.add(new Account("Jairo", "pass", 500));
		JPanel panel = new ShopGUI(temp);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public ShopGUI(ArrayList<Account> accounts) {
		setPreferredSize(new Dimension(1000,600));
		Timer timer = new Timer(1000/50, this);
		timer.start();
		wiimotes = WiiUseApiManager.getWiimotes(accounts.size(), true);
		for(Wiimote w : wiimotes) {
			w.addWiiMoteEventListeners(this);
		}
		setVisible(true);
		this.accounts = new ArrayList<Account>(accounts);
		skins = new ArrayList<Skin>();
		makeSkins();
	}
	
	/**
	* Timer actionperformed. (20 fps)
	*/
	public void actionPerformed(ActionEvent arg0) {
		if((ready1)&&(ready2)&&(ready3)&&(ready4)) {
			new Wissel(4);
		}
			
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(new Font("Arial", Font.BOLD, 13));
		
		// Player 1 skin
		g2d.drawRect(100, 100, 150, 150);
		g2d.drawString(accounts.get(0).getUser()+" punten: "+accounts.get(0).getPoints(), 100, 70);
		g2d.drawString(skins.get(i1).getName(), 100, 90);
		if(skinUnlocked(accounts.get(0), skins.get(i1))) {
			if(ready1)
				g2d.drawString("Klaar!", 150, 270);
			else 
				g2d.drawString("Selecteer", 145, 270);
		} else
			g2d.drawString("Kost: "+skins.get(i1).getCost(), 145, 270);
		AffineTransform skin1 = new AffineTransform();
		skin1.translate(175 - skins.get(i1).getSkinImage().getWidth(getFocusCycleRootAncestor())/2, 
				175 - skins.get(i1).getSkinImage().getHeight(getFocusCycleRootAncestor())/2);
		g2d.drawImage(skins.get(i1).getSkinImage(), skin1, null);
		// Player 2 skin
		g2d.drawRect(400, 100, 150, 150);		
		g2d.drawString(accounts.get(1).getUser()+" punten: "+accounts.get(1).getPoints(), 400, 70);
		g2d.drawString(skins.get(i2).getName(), 400, 90);
		if(skinUnlocked(accounts.get(1), skins.get(i2))) {	
			if(ready2)
				g2d.drawString("Klaar!", 450, 270);
			else 
				g2d.drawString("Selecteer", 445, 270);
		} else
			g2d.drawString("Kost: "+skins.get(i2).getCost(), 445, 270);
		AffineTransform skin2 = new AffineTransform();
		skin2.translate(475 - skins.get(i2).getSkinImage().getWidth(getFocusCycleRootAncestor())/2, 
				175 - skins.get(i2).getSkinImage().getHeight(getFocusCycleRootAncestor())/2);
		g2d.drawImage(skins.get(i2).getSkinImage(), skin2, null);
		// Player 3 skin
		if(accounts.size() > 2) { 
			g2d.drawRect(100, 400, 150, 150);
			g2d.drawString(accounts.get(2).getUser()+" punten: "+accounts.get(2).getPoints(), 100, 370);
			g2d.drawString(skins.get(i3).getName(), 100, 390);
			if(skinUnlocked(accounts.get(2), skins.get(i3))) {
				if(ready3)
					g2d.drawString("Klaar!", 150, 570);
				else 
					g2d.drawString("Selecteer", 145, 570);
			} else
				g2d.drawString("Kost: "+skins.get(i3).getCost(), 145, 570);
			AffineTransform skin3 = new AffineTransform();
			skin3.translate(175 - skins.get(i3).getSkinImage().getWidth(getFocusCycleRootAncestor())/2, 
					475 - skins.get(i3).getSkinImage().getHeight(getFocusCycleRootAncestor())/2);
			g2d.drawImage(skins.get(i3).getSkinImage(), skin3, null);
		}
		// Player 4 skin
		if(accounts.size() > 3) {	
			g2d.drawRect(400, 400, 150, 150);
			g2d.drawString(accounts.get(3).getUser()+" punten: "+accounts.get(3).getPoints(), 400, 370);
			g2d.drawString(skins.get(i4).getName(), 400, 390);
			if(skinUnlocked(accounts.get(3), skins.get(i4))) {
				if(ready4)
					g2d.drawString("Klaar!", 450, 570);
				else 
					g2d.drawString("Selecteer", 445, 570);
		} else
				g2d.drawString("Kost: "+skins.get(i4).getCost(), 445, 570);
			AffineTransform skin4 = new AffineTransform();
			skin4.translate(475 - skins.get(i4).getSkinImage().getWidth(getFocusCycleRootAncestor())/2, 
					475 - skins.get(i4).getSkinImage().getHeight(getFocusCycleRootAncestor())/2);
			g2d.drawImage(skins.get(i4).getSkinImage(), skin4, null);
		}
	}
	
	public boolean skinUnlocked(Account account, Skin skin) {
		boolean unlocked = false;
		for(Skin s : account.getSkins()) {
			if(s.getName().equals(skin.getName()))
				unlocked = true;
		}
		return unlocked;
	}
	
	public void makeSkins() {
		skins.add(new Skin("Paard", 0, "src/spriteGrijsPaard.png"));
		skins.add(new Skin("Mooi paard", 500, "src/spriteBruinPaard.png"));
		skins.add(new Skin("Rode draak", 10000, "src/spriteRodeDraak3.png"));
	}
	
	public void onButtonsEvent(WiimoteButtonsEvent e) {

        if(e.getWiimoteId() == 1) {
        	if(!ready1) {
	        	if(e.isButtonLeftJustPressed()) 
	        		i1 = (i1 - 1) % skins.size();
	        		if (i1 < 0 ){
	        			i1 = skins.size()-1;
	        		
	        		}
	        	
	        	if(e.isButtonRightJustPressed()) 
	        		i1 = (i1 + 1) % skins.size();
        	}
        	if(e.isButtonAJustPressed()) {
        		if(skinUnlocked(accounts.get(0), skins.get(i1))) {
        			accounts.get(0).setSelectedSkin(skins.get(i1));
        			ready1 = true;
        		} else {
	        		accounts.get(0).addSkin(skins.get(i1));
	        		accounts.get(0).subtractPoints(skins.get(i1).getCost());
        		}
        	}
        }
        
        if(e.getWiimoteId() == 2) {
        	if(!ready2) {
	        	if(e.isButtonLeftJustPressed()) 
	        		i2 = (i2 - 1) % skins.size();
	        	if (i2 < 0 ){
        			i2 = skins.size()-1;
	        	}
        			
	        	if(e.isButtonRightJustPressed()) 
	        		i2 = (i2 + 1) % skins.size();
        	}
        	if(e.isButtonAJustPressed()) {
        		if(skinUnlocked(accounts.get(1), skins.get(i2))) {
        			accounts.get(1).setSelectedSkin(skins.get(i2));
        			ready1 = true;
        		} else {
	        		accounts.get(1).addSkin(skins.get(i2));
	        		accounts.get(1).subtractPoints(skins.get(i2).getCost());
        		}
        	}
        }
        
        if(e.getWiimoteId() == 3) {
        	if(!ready3) {
	        	if(e.isButtonLeftJustPressed()) 
	        		i3 = (i3 - 1) % skins.size();
	        	if (i3 < 0 ){
        			i3 = skins.size()-1;
	        	}
	        	
	        	if(e.isButtonRightJustPressed()) 
	        		i3 = (i3 + 1) % skins.size();
        	}
        	if(e.isButtonAJustPressed()) {
        		if(skinUnlocked(accounts.get(2), skins.get(i3))) {
        			accounts.get(2).setSelectedSkin(skins.get(i3));
        			ready1 = true;
        		} else {
	        		accounts.get(2).addSkin(skins.get(i3));
	        		accounts.get(2).subtractPoints(skins.get(i3).getCost());
        		}
        	}
        }
        
        if(e.getWiimoteId() == 4) {
        	if(!ready4) {
	        	if(e.isButtonLeftJustPressed()) 
	        		i4 = (i4 - 1) % skins.size();
	        	
	        	if(e.isButtonRightJustPressed()) 
	        		i4 = (i4 + 1) % skins.size();
        	}
        	if(e.isButtonAJustPressed()) {
        		if(skinUnlocked(accounts.get(3), skins.get(i4))) {
        			accounts.get(3).setSelectedSkin(skins.get(i4));
        			ready1 = true;
        		} else {
	        		accounts.get(3).addSkin(skins.get(i4));
	        		accounts.get(3).subtractPoints(skins.get(i4).getCost());
        		}
        	}
        }

    }
	
    public void onIrEvent(IREvent irEvent) {}
    public void onMotionSensingEvent(MotionSensingEvent motionSensingEvent) {}
    public void onExpansionEvent(ExpansionEvent expansionEvent) {}
    public void onStatusEvent(StatusEvent statusEvent) {}
    public void onDisconnectionEvent(DisconnectionEvent disconnectionEvent) {}
    public void onNunchukInsertedEvent(NunchukInsertedEvent nunchukInsertedEvent) {}
    public void onNunchukRemovedEvent(NunchukRemovedEvent nunchukRemovedEvent) {}
    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent guitarHeroInsertedEvent) {}
    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent guitarHeroRemovedEvent) {}
    public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent classicControllerInsertedEvent) {}
    public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent classicControllerRemovedEvent) {}
	
}

