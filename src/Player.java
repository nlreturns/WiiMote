
import java.awt.Image;

public class Player {
	int yTurns;
	Image skin;
	int jump;
	int raceHeight;
	int minimalSpeed = 1;
	int movement;
	int player;
	int maxHeight;
	String name;
	boolean isFinished;
	String place;

	public Player(Image skin, int player) {
		name = "Player " + (player + 1);
		isFinished = false;
		if (player == 0) {
			jump = 100;
			raceHeight = 100;
			maxHeight = 100 - 70;
		} else if (player == 1) {
			jump = 250;
			raceHeight = 250;
			maxHeight = 250 - 70;
		} else if (player == 2) {
			jump = 420;
			raceHeight = 420;
			maxHeight = 420 - 70;
		} else {
			jump = 560;
			raceHeight = 560;
			maxHeight = 560 - 70;
		}
	}

	public void setMovement(int newMovement) {
		movement = newMovement;
		if (movement > 4610) {
			movement = 4610;
		}
	}

	public int getMovement() {
		return movement;
	}

	public void setJump(int newJump) {
		jump = newJump;
	}

	public int getJump() {
		return jump;
	}

	public void setMinimalspeed(int newMinimalSpeed) {
		minimalSpeed = newMinimalSpeed;
	}

	public int getMinimalSpeed() {
		return minimalSpeed;
	}

	public void setSkin(Image newSkin) {
		skin = newSkin;
	}

	public Image getSkin() {
		return skin;
	}

	public void setPlayer(int newPlayer) {
		player = newPlayer;
	}

	public int getPlayer() {
		return player;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public int getRaceHeight() {
		return raceHeight;
	}

	public String getName() {
		return name;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean finished) {
		isFinished = finished;
	}

	public void setPlace(String endPlace) {
		place = endPlace;
	}

	public String getPlace() {
		return place;
	}

}
