package philo;

import plateforme.EtatType;

public enum EtatPhilosophe implements EtatType<Philosophe>{
		MANGE,
		PENSE,
		ATTEND;
}
