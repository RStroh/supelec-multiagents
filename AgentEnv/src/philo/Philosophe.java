package philo;

import static philo.EnvironnementPhilo.ActionsPhilosophes.*;
import static philo.EnvironnementPhilo.PerceptionsPhilosophes.*;
import static philo.EtatPhilosophe.*;
import philo.Fourchette.EtatFourchette;
import plateforme.Agent;
import plateforme.EtatType;

public class Philosophe extends Agent<EnvironnementPhilo>{

	static int SEUIL_FAIM_INF = -10;
	static int SEUIL_FAIM_SUP = 10;
	
	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
	}

	private String nom;
	private int faim = 0;
	private EtatPhilosophe etat = PENSE;
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			StringBuilder out = new StringBuilder();
			//Perception
			out.append(String.format("Position %s : %s", this, 
					percept(POSITION)));

			//Decision-->Action
			out.append(" // etat:" + etat);
			switch (etat) {
			case PENSE:
				out.append(" // ");
				out.append((aFaim()) ? "J'ai faim" : "J'ai pas faim.");
				if (aFaim()) {
					//Tenter de manger, en cas d'échec, ATTENDRE.
					if (tryManger()) break;
					else etat = ATTEND;
				}
				else act(PENSER);
				break;

			case ATTEND:
				
				if (percept(A_FOURCHETTE_G)) {
					if (percept(A_FOURCHETTE_D)) etat = MANGE;
				}
				
				break;
				
			case MANGE:
				faim--;
				if (faim < SEUIL_FAIM_INF) etat = PENSE;
				break;

			default:
				break;
			}
			System.out.println(out);
		}
	}
	
	private boolean aFaim() {
		return faim > SEUIL_FAIM_SUP;
	}

	private boolean tryManger(){
		if (percept(A_FOURCHETTE_G)) {
			if (percept(A_FOURCHETTE_D)) etat = MANGE;
			act(MANGER);
		}
		return false;
	}

	//Demande sa position à l'environnement.
	private int getPosition(){
		return percept(POSITION);
	}

	@Override
	public EtatType etatInitial() {
		return EtatPhilosophe.PENSE;
	}

}
