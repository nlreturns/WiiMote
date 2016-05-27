
import java.io.Serializable;
import java.util.ArrayList;

public class AccountBase implements Serializable {
	
	private ArrayList<Account> accounts;
	
	public AccountBase() {
		accounts = new ArrayList<Account>();
	}
	
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
}
