import java.awt.Image;
import javax.swing.ImageIcon;

public class Skin {

	private String name;
	private int cost;
	private Image image;
	
	public Skin(String name, int cost, String image) {
		this.name = name;
		this.cost = cost;
		this.image = new ImageIcon(image).getImage();
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public Image getSkinImage() {
		return image;
	}
	
}

