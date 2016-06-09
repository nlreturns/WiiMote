
import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

	private String user, pass;
	private int points;
	private ArrayList<Skin> skins;
	private Skin selectedSkin;

	public Account(String user, String pass, int points) {
		this.user = user;
		this.pass = pass;
		this.points = points;
		skins = new ArrayList<Skin>();
		addSkin(new Skin("Paard", 0, "src/spriteGrijsPaard.png"));
		selectedSkin = skins.get(0);
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public void subtractPoints(int points) {
		this.points -= points;
	}

	public ArrayList<Skin> getSkins() {
		return skins;
	}

	public void addSkin(Skin skin) {
		skins.add(skin);
	}

	public Skin getSelectedSkin() {
		return selectedSkin;
	}

	public void setSelectedSkin(Skin skin) {
		selectedSkin = skin;
	}

}
