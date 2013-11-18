package philo;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import plateforme.ActionType;
import plateforme.Agent;
import plateforme.Environnement;
import plateforme.Perception;
import plateforme.PerceptionContainer;


public class EnvironnementPhilo extends Environnement {

	public enum EtatFourchette{
		PRISE,
		LIBRE;
	}

	private HashMap<Integer, EtatFourchette> etatFourchettes = new HashMap<Integer, EtatFourchette>(){{
		put(0, EtatFourchette.LIBRE);
		put(1, EtatFourchette.LIBRE);
		put(2, EtatFourchette.LIBRE);
		put(3, EtatFourchette.LIBRE);
		put(4, EtatFourchette.LIBRE);
	}};

	private ArrayList<Philosophe> philosophes;

	enum Perceptions implements PerceptionContainer{
		POSITION(
				new Perception<Philosophe,Integer>() {
					@Override
					public Integer getValue(Philosophe a) {
						return a.getEnv().getPosition(a);
					}
				}
				),
				ETAT_FOURCHETTE_D(new Perception<Philosophe, EtatFourchette>(){

					@Override
					public EtatFourchette getValue(Philosophe p) {
						return null;
					}

				});

		private Perception<Philosophe,?> perception;
		private <T> Perceptions(Perception<Philosophe,T> p) {
			this.perception = p;
		}

		@Override
		public <T> Perception<Philosophe,T> getPerception(){
			return (Perception<Philosophe,T>) perception;
		}
	}
	public enum Action implements ActionType{
		MANGER,
		POSER_FOURCHETTE_G,
		POSER_FOURCHETTE_D,
		PRENDRE_FOURCHETTE_G,
		PRENDRE_FOURCHETTE_D;
	}

	@Override
	public boolean doAction(ActionType a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ActionType[] getActions() {
		return Action.values();
	}

	@Override
	public Set<Perception<A extends Agent, ?>> getPerceptions(Class<A> klass) {

		for (Perception<Agent, T> iterable_element : iterable) {
			
		}
		return EnumSet.allOf(Perceptions.class);
	}

	public void ajouterPhilosophe(Philosophe philosophe) {
		//TODO Ajouter le philosophe et les fourchettes.
	}

	private int getPosition(Philosophe p) {
		return philosophes.indexOf(p);
	}

	@Override
	public <A extends Agent, T> T getPerception(A agent, Perception<A, T> p) {
		return p.getValue(agent);
	}
	
}
