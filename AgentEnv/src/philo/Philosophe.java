package philo;

import philo.EnvironnementPhilo.Perceptions;
import plateforme.Agent;
import plateforme.Environnement;
import plateforme.EtatType;

public class Philosophe extends Agent{
	
	private EnvironnementPhilo env;
	
	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
	}
	
	private String nom;
	private int faim;
	private EtatPhilosophe etat;
	
	//Demande sa position à l'environnement.
	public int getPosition(){
		return percept(Perceptions.POSITION);
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
	
	
}
