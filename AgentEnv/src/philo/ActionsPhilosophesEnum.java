package philo;

import plateforme.action.Action;
import plateforme.action.ActionEnum;
import plateforme.action.WrongActionException;

public enum ActionsPhilosophesEnum implements ActionEnum<Philosophe>{
	MANGER(new Action<Philosophe, Void>(){

		@Override
		public Void doAction(Philosophe p) {
			//Implémenter si nécessaire
			return null;
		}

	}),
	PENSER(
	/**
	 * DoAction Renvoie le surplus de pensee produite par ce philosophe.
	 * 
	 * @author thomas
	 *
	 */
	new Action<Philosophe, Long>(){

		@Override
		public Long doAction(Philosophe p) {
			return (long) p.getEnv().produirePensee(p);
		}

	}),
	PRENDRE_LES_2_FOURCHETTES(new Action<Philosophe, Void>(){

		@Override
		synchronized public Void doAction(Philosophe p) throws WrongActionException{
			if ((p.getEnv().fourchetteDroite(p).estLibre() 
					|| p.getEnv().fourchetteDroite(p).possesseur == p)
					&& (p.getEnv().fourchetteGauche(p).estLibre()
							|| p.getEnv().fourchetteGauche(p).possesseur == p)){
				p.getEnv().fourchetteDroite(p).setPossesseur(p);
				p.getEnv().fourchetteGauche(p).setPossesseur(p);
			}
			else throw new WrongActionException(p+" ne pouvait pas prendre les fourchettes.");
			return null;
		}
	}),
	POSER_FOURCHETTE_G(new Action<Philosophe, Void>(){

		@Override
		synchronized public Void doAction(Philosophe p) throws WrongActionException {
			if (p.getEnv().fourchetteGauche(p).possesseur == p){
				p.getEnv().fourchetteGauche(p).poser();
			}
			else throw new WrongActionException(p+" ne possède pas la fourchette gauche.");
			return null;
		}
	}),
	POSER_FOURCHETTE_D(new Action<Philosophe, Void>(){

		@Override
		synchronized public Void doAction(Philosophe p) throws WrongActionException {
			if (p.getEnv().fourchetteDroite(p).possesseur == p){
				p.getEnv().fourchetteDroite(p).poser();
			}
			else throw new WrongActionException(p+" ne possède pas la fourchette située à sa droite.");
			return null;
		}
	}),
	PRENDRE_FOURCHETTE_G(new Action<Philosophe, Boolean>(){

		@Override
		synchronized public Boolean doAction(Philosophe p) {
			if (p.getEnv().fourchetteGauche(p).estLibre()
					|| p.getEnv().fourchetteGauche(p).possesseur == p){
				p.getEnv().fourchetteGauche(p).setPossesseur(p);
				return new Boolean(true);
			}
			return new Boolean(false);
		}
	}),
	PRENDRE_FOURCHETTE_D(new Action<Philosophe, Boolean>(){

		@Override
		synchronized public Boolean doAction(Philosophe p) {
			if (p.getEnv().fourchetteDroite(p).estLibre()
					|| p.getEnv().fourchetteDroite(p).possesseur == p){
				p.getEnv().fourchetteDroite(p).setPossesseur(p);
				return new Boolean(true);
			}
			return new Boolean(false);
		}
	});

	private Action<Philosophe, ?> action;

	private <T> ActionsPhilosophesEnum(Action<Philosophe, T> a) {
		this.action = a;
	}

	@Override
	public <T> Action<Philosophe, T> getAction() {
		return (Action<Philosophe, T>) action;
	}
}
