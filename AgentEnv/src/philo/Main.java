package philo;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		
		EnvironnementPhilo env = new EnvironnementPhilo();
		
	    	//Création des agents
	    	env.ajouterPhilosophe("P1");
	    	env.ajouterPhilosophe("P2");
	    	env.ajouterPhilosophe("P3");
	    	env.ajouterPhilosophe("P4");
	    	env.ajouterPhilosophe("P5");
	}

}
