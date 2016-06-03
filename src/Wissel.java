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
        case 2: 
        		aantal = new aantalscherm();

                 break;
        case 3: 
        		
        		RaceMap race = new RaceMap(1);
        		System.out.println("HALOOOOOOOOOO");
        		
                 break;
        case 4: 
                 break;
}
}}
