package philo;

import static philo.EtatPhilosophe.*;
import static philo.EnvironnementPhilo.PerceptionsPhilosophes.*;
import static philo.Fourchette.EtatFourchette.*;
import plateforme.Agent;
import plateforme.Environnement;
import plateforme.EtatType;

public class Philosophe extends Agent{

	private EnvironnementPhilo env;

	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
		run();
	}

	private String nom;
	private int faim;
	private EtatPhilosophe etat = PENSE;

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			//Perception
			System.out.println(String.format("Position %s : %s", this, 
					percept(POSITION)));

			//Decision
//			switch (etat) {
//			case PENSE:
//				
//				if (faim > 0) etat = ATTEND;
//				break;
//
//			case ATTEND:
//				
//				
//				if (percept(A_FOURCHETTE_G)) {
//					if (percept(A_FOURCHETTE_D)) etat = MANGE;
//				}
//				
//				break;
//				
//			case MANGE:
//				faim--;
//				break;
//
//			default:
//				break;
//			}

			//Action

		}
	}

	//Demande sa position à l'environnement.
	private int getPosition(){
		return percept(POSITION);
	}

	@Override
	public EtatType etatInitial() {
		return EtatPhilosophe.PENSE;
	}

	@Override
	public EnvironnementPhilo getEnv() {
		return env;
	}

	@Override
	public void setEnv(Environnement env) {
		this.env = (EnvironnementPhilo) env;
	}
	
	@Override
	public String toString() {
		return nom;
	}


}
