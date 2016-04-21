import java.awt.*;
import java.awt.Event.*;
import javax.swing.*;

import wiiusej.values.IRSource;

import java.awt.geom.*;

public class WiiMoteJApp {
	
	static final long serialVersionUID = 100;
	
	public static void main(String s[])	{
		JFrame frame = new JFrame("Wii Infrarood camera, tracking 4 dots");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JPanel panel = new WiiIRPanel();
		
		WiiMoteView view = new WiiMoteView();
		WiiMoteModel model = new WiiMoteModel();
		WiiMoteController controller = new WiiMoteController(view,model);
		
		frame.getContentPane().add(view);
		frame.pack();
		frame.setVisible(true);
	}
}
