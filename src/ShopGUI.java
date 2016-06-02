import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class ShopGUI extends JPanel implements ActionListener {
	
	private ArrayList<Skin> skins;
	
	public static void main(String s[]) {
		new ShopGUI();
	}

	public ShopGUI() {
		setPreferredSize(new Dimension(1000,600));
		Timer timer = new Timer(1000/50, this);
		timer.start();
		setVisible(true);
		skins = new ArrayList<Skin>();
		makeSkins();
	}
	
	/**
	* Timer actionperformed. (20 fps)
	*/
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		AffineTransform skin1 = new AffineTransform();
		skin1.translate(100, 100);
		g2d.drawImage(skins.get(0).getSkinImage(), skin1, null);
		
	}
	
	public void makeSkins() {
		skins.add(new Skin("Paard", 500, "paard.jpg"));
	}
	
}

