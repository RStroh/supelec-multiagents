package philo;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		
		EnvironnementPhilo env = new EnvironnementPhilo();
		
	    	//Création des agents
			Philosophe p0 = env.ajouterPhilosophe("p0");
	    	Philosophe p1 = env.ajouterPhilosophe("p1");
	    	Philosophe p2 = env.ajouterPhilosophe("p2");
	    	Philosophe p3 = env.ajouterPhilosophe("p3");
	    	Philosophe p4 = env.ajouterPhilosophe("p4");
	    	
	    	p0.start();
	    	p1.start();
	    	p2.start();
	    	p3.start();
	    	p4.start();
	    	
	}

}
