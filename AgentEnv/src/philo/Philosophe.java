package philo;

import static philo.EnvironnementPhilo.ActionsPhilosophes.*;
import static philo.EnvironnementPhilo.PerceptionsPhilosophes.*;
import static philo.EtatPhilosophe.*;
import plateforme.Agent;
import plateforme.EtatType;

public class Philosophe extends Agent<EnvironnementPhilo>{

	static int SEUIL_FAIM_INF = -3;
	static int SEUIL_FAIM_SUP = 3;
	
	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
	}

	private String nom;
	private int faim = 0;
	private EtatPhilosophe etat = PENSE;
	
	public String percevoir() {
			StringBuilder out = new StringBuilder();
			
			//Perception
			out.append(String.format("Position %s : %s", this, 
					percept(POSITION)));

			return out.toString();
	}
	
	@Override
	public String decider() {
		StringBuilder out = new StringBuilder();
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
			else {
				act(PENSER);
				faim++;
			}
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
		
		return out.toString();
	}

	private boolean aFaim() {
		return faim > SEUIL_FAIM_SUP;
	}

	private boolean tryManger(){
		//Si j'ai pas les 2 fourchettes, je repose les 2.
		act(PRENDRE_LES_2_FOURCHETTES);
		
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
