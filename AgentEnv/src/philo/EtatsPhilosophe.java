package philo;

import plateforme.Etat;


public enum EtatsPhilosophe implements Etat<Philosophe>{
		MANGE,
		PENSE,
		ATTEND;
}
