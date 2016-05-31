package wiimote;

import java.awt.Image;

public class Player {
	int yTurns;
	Image skin;
	int jump;
	int minimalSpeed = 1;
	int movement;
	int player;
	int maxHeight;

	public Player(Image skin, int player) {
		if (player == 0) {
			jump = 100;
			maxHeight = 100 - 70;
		} else if (player == 1) {
			jump = 250;
			maxHeight = 250 - 70;
		} else if (player == 2) {
			jump = 400;
			maxHeight = 400 - 70;
		} else {
			jump = 550;
			maxHeight = 550 - 70;
		}
	}

	public void setMovement(int newMovement) {
		movement = newMovement;
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

	public int maxHeight() {
		return maxHeight;
	}

}
