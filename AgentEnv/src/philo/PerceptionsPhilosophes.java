package philo;

import philo.Fourchette.EtatFourchette;
import plateforme.perception.Perception;
import plateforme.perception.PerceptionContainer;

public enum PerceptionsPhilosophes implements PerceptionContainer<Philosophe>{
	POSITION(new Perception<Philosophe,Integer>(){

		@Override
		public Integer getValue(Philosophe p) {
			return p.getEnv().getPosition(p);

		}

	}),
	A_FOURCHETTE_G(	new Perception<Philosophe,Boolean>() {

		@Override
		public Boolean getValue(Philosophe p) {
			return p == p.getEnv().fourchetteGauche(p).getPossesseur();
		}

	}),
	A_FOURCHETTE_D(new Perception<Philosophe,Boolean>() {

		@Override
		public Boolean getValue(Philosophe p) {
			return p == p.getEnv().fourchetteDroite(p).getPossesseur();
		}

	}),
	ETAT_FOURCHETTE_G(new Perception<Philosophe, EtatFourchette>(){

		@Override
		public EtatFourchette getValue(Philosophe p) {
			return p.getEnv().fourchetteGauche(p).getEtat();
		}
	}),
	ETAT_FOURCHETTE_D(new Perception<Philosophe, EtatFourchette>(){

		@Override
		public EtatFourchette getValue(Philosophe p) {
			return p.getEnv().fourchetteDroite(p).getEtat();
		}
	});

	private Perception<Philosophe,?> perception;
	private <T> PerceptionsPhilosophes(Perception<Philosophe,T> p) {
		this.perception = p;
	}

	@Override
	public <T> Perception<Philosophe,T> getPerception(){
		return (Perception<Philosophe,T>) perception;
	}
}
