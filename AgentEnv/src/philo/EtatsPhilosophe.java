package philo;

import plateforme.agent.Etat;


public enum EtatsPhilosophe implements Etat<Philosophe>{
		MANGE,
		PENSE,
		ATTEND;
}
