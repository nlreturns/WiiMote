<<<<<<< HEAD
import java.util.ArrayList;

=======
>>>>>>> master
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Wissel {
	private int waarde;
	aantalscherm aantal;

	public Wissel(int waarde){
		this.waarde = waarde;
		switchcase();
		
	}
	
	public void switchcase(){
		
        switch (waarde) {
        case 1:  Startscherm strt = new Startscherm();
                 break;
<<<<<<< HEAD
        case 2:  Aantalscherm aantal = new Aantalscherm();
                 break;
        case 3:  JFrame frame = new JFrame("ShopGUI");
				 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 ArrayList<Account> temp = new ArrayList<Account>();
				 temp.add(new Account("Rico", "pass", 500));
				 temp.add(new Account("Justin", "pass", 500));
				 temp.add(new Account("Jairo", "pass", 500));
				 JPanel panel = new ShopGUI(temp);
				 frame.getContentPane().add(panel);
				 frame.pack();
				 frame.setVisible(true);
=======
        case 2: 
        		aantal = new aantalscherm();

                 break;
        case 3: 
        		
        		RaceMap race = new RaceMap();
        		System.out.println("HALOOOOOOOOOO");
        		
>>>>>>> master
                 break;
        case 4: 
                 break;
}
}}
