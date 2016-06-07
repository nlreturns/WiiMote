import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Skin implements Serializable {

	private String name;
	private int cost;
	private String image;
	
	public Skin(String name, int cost, String image) {
		this.name = name;
		this.cost = cost;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public Image getSkinImage() {
		return new ImageIcon(image).getImage();
	}
	
}

