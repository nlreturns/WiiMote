import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class ShopGUI extends JPanel implements ActionListener {

	public ShopGUI(Account account) {
		setPreferredSize(new Dimension(1000,600));
		Timer timer = new Timer(1000/50, this);
		timer.start();
		setVisible(true);
	}
	
	/**
	* Timer actionperformed. (20 fps)
	*/
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.drawRect(100, 100, 100, 100);
		
	}
	
}
