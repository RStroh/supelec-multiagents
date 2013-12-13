package philo;

import philo.Fourchette.EtatFourchette;
import plateforme.perception.Perception;
import plateforme.perception.PerceptionEnum;

public enum PerceptionsPhilosophes implements PerceptionEnum<Philosophe>{
	/**
	 * Fournit la position d'un agent.
	 */
	POSITION(new Perception<Philosophe,Integer>(){

		@Override
		public Integer getValue(Philosophe p) {
			return p.getEnv().getPosition(p);
		}
	}),
	/**
	 * Retourne vrai si l'agent a la fourchette à sa gauche.
	 */
	A_FOURCHETTE_G(	new Perception<Philosophe,Boolean>() {

		@Override
		public Boolean getValue(Philosophe p) {
			return p == p.getEnv().fourchetteGauche(p).getPossesseur();
		}

	}),
	/**
	 * Retourne vrai si l'agent a la fourchette à sa droite.
	 */
	A_FOURCHETTE_D(new Perception<Philosophe,Boolean>() {

		@Override
		public Boolean getValue(Philosophe p) {
			return p == p.getEnv().fourchetteDroite(p).getPossesseur();
		}

	}),
	/**
	 * Retourne l'état (prise ou libre) de la fourchette gauche.
	 */
	ETAT_FOURCHETTE_G(new Perception<Philosophe, EtatFourchette>(){

		@Override
		public EtatFourchette getValue(Philosophe p) {
			return p.getEnv().fourchetteGauche(p).getEtat();
		}
	}),
	/**
	 * Retourne l'état (prise ou libre) de la fourchette droite.
	 */
	ETAT_FOURCHETTE_D(new Perception<Philosophe, EtatFourchette>(){

		@Override
		public EtatFourchette getValue(Philosophe p) {
			return p.getEnv().fourchetteDroite(p).getEtat();
		}
	}),
	/**
	 * Retourne le possesseur de la fourchette droite.
	 */
	PROPRIETAIRE_FOURCHETTE_D(new Perception<Philosophe, Philosophe>(){

		@Override
		public Philosophe getValue(Philosophe p) {
			return p.getEnv().fourchetteDroite(p).getPossesseur();
		}
	}),
	/**
	 * Retourne le possesseur de la fourchette gauche.
	 */
	PROPRIETAIRE_FOURCHETTE_G(new Perception<Philosophe, Philosophe>(){

		@Override
		public Philosophe getValue(Philosophe p) {
			return p.getEnv().fourchetteGauche(p).getPossesseur();
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
