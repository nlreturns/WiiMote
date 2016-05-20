import java.io.Serializable;

public class Account implements Serializable {
	
	private String user, pass;
	private int points;
	
	public Account(String user, String pass, int points) {
		this.user = user;
		this.pass = pass;
		this.points = points;
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
	
}
