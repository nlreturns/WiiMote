import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import wiiusej.values.IRSource;
import wiiusej.wiiusejevents.physicalevents.IREvent;


class WiiMoteView extends JPanel implements ActionListener  {
	
	static final long serialVersionUID = 101;

	private IRSource[] irs = null;
	private Timer render;
	private GeneralPath path = new GeneralPath();
	
	public WiiMoteView() {
		setPreferredSize( new Dimension(1024,768) );
		
		// setup render interval
		render = new Timer(100,this);
		render.start();
	}
	
	// Called by 
	public void update(IREvent arg0)
	{
		irs = arg0.getIRPoints();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// Teken assenstelsel
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));		
		g2.translate(1024/2, 768/2);
		g2.drawLine(-200, 0, 200,0);	// y-as
		g2.drawLine(0, -200, 0, 200 );	// x-as

		g2.setColor(Color.RED);		
		g2.draw(path);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		// calc hoek
		if(irs != null)
		{
			if( irs.length == 4 )
			{
				//Draw Pad
				path.reset();
				path = new GeneralPath();
				path.moveTo(irs[0].getX()/4, irs[0].getY()/4);
				path.lineTo(irs[1].getX()/4, irs[1].getY()/4);
				path.lineTo(irs[2].getX()/4, irs[2].getY()/4);
				path.lineTo(irs[3].getX()/4, irs[3].getY()/4);
				path.closePath();

			}
		}

		// Update every 100ms
		repaint();
	}
}
		
		
		
		// Test

/*
 		IRSource[] irs = myWiiIR.irEvent.getIRPoints();
		if( irs.length == 2 )
		{
			
			Shape line = new Line2D.Double( irs[0].getRx(), irs[0].getRy(), 
											irs[1].getRx(), irs[1].getRy());
			
			float rotation = (float)Math.atan2(irs[0].getRy() - irs[1].getRy(), irs[0].getRx()- irs[1].getRx());
			
			Stroke stroke = new BasicStroke(20, BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL);
			g2.setStroke(stroke);
			g2.draw(line);
					}
*/			
			
			
			


		
/*		short idx = 0;
		for(IRSource irs : myWiiIR.irEvent.getIRPoints())
		{
			String s = "id: " + idx++;
			g2.drawString(s, 10, 10+idx*50);
					
			s = "rx: " + irs.getRx();
			g2.drawString(s, 15, 20+idx*50);
			
			s = "rx: " + irs.getRx();
			g2.drawString(s, 15, 30+idx*50);	
			
			s = "rx: " + myWiiIR.irEvent.getDistance();
			g2.drawString(s, 15, 40+idx*50);
		}	
		
		g2.translate(100, 100);
		Shape rect = new Rectangle2D.Double(0, 0, 1024, 768);	
		g2.draw(rect);
*/
/*		
		g2.setColor(Color.GREEN);
		for(IRSource irs : myWiiIR.irEvent.getIRPoints())
		{
			double x = irs.getRx();
			double y = irs.getRy();
			Shape dot = new Ellipse2D.Double(x, y, 10, 10);
			g2.fill(dot);
		}		
		
*/
		
		
//	}