package philo;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import philo.Fourchette.EtatFourchette;
import plateforme.Action;
import plateforme.ActionContainer;
import plateforme.Agent;
import plateforme.Environnement;
import plateforme.EtatType;
import plateforme.Perception;
import plateforme.PerceptionContainer;
import plateforme.UndefinedActionException;

public class EnvironnementPhilo extends Environnement {

	private List<Philosophe> philosophes = new ArrayList<Philosophe>();
	private List<Fourchette> fourchettes = new ArrayList<Fourchette>();

	private Agent<EnvironnementPhilo> scribe = new Agent<EnvironnementPhilo>(this) {

		@Override
		public EtatType etatInitial() {
			return null;
			
		}

		@Override
		public String percevoir() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String decider() {
			return "Pensée Produite : "+getPenseeProduite();
		}

	};

	private int penseeProduite;

	public enum PerceptionsPhilosophes implements PerceptionContainer{
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
		ETAT_FOURCHETTE_D(new Perception<Philosophe, EtatFourchette>(){

			@Override
			public EtatFourchette getValue(Philosophe p) {
				return null;
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
	
	
	public enum ActionsPhilosophes implements ActionContainer{
		MANGER(null),
		PENSER(new Action<Philosophe, Void>(){

			@Override
			public Void doAction(Philosophe p) {
				p.getEnv().produirePensee(p);
				return null;
			}

		}),
		PRENDRE_LES_2_FOURCHETTES(new Action<Philosophe, Boolean>(){

			@Override
			synchronized public Boolean doAction(Philosophe p) {
				if (p.getEnv().fourchetteDroite(p).estLibre() //TODO ou possedée par moi
						&& p.getEnv().fourchetteGauche(p).estLibre()){
					p.getEnv().fourchetteDroite(p).setPossesseur(p);
					p.getEnv().fourchetteGauche(p).setPossesseur(p);
				}
				
				return new Boolean(true);
			}
		}),
		POSER_FOURCHETTE_G(null),
		POSER_FOURCHETTE_D(null),
		PRENDRE_FOURCHETTE_G(null),
		PRENDRE_FOURCHETTE_D(null);

		private Action<Philosophe, ?> action;

		private <T> ActionsPhilosophes(Action<Philosophe, T> a) {
			this.action = a;
		}

		@Override
		public <T> Action<Philosophe, T> getAction() {
			return (Action<Philosophe, T>) action;
		}
	}

	@Override
	public boolean doAction(ActionContainer a) {
		// TODO Auto-generated method stub
		return false;
	}

	protected int getPenseeProduite() {
		return penseeProduite;
	}

	protected void produirePensee(Philosophe p) {
		penseeProduite++;
	}

	@Override
	public ActionContainer[] getActions() {
		return ActionsPhilosophes.values();
	}

	@Override
	public <A extends Agent> Set<Perception<A, ?>> getPerceptions(Class<A> agentClass) {
		Set<Perception<A, ?>> perceptions = new HashSet<>();

		if(agentClass.equals(PerceptionsPhilosophes.class)){
			for (PerceptionsPhilosophes p : EnumSet.allOf(PerceptionsPhilosophes.class)) {
				perceptions.add((Perception<A, ?>) p.getPerception());
			}
		}
		return perceptions;
	}

	public Philosophe ajouterPhilosophe(String nom) {
		//TODO Ajouter le philosophe et les fourchettes.
		Philosophe p = new Philosophe(this, nom);
		if (philosophes.isEmpty()) {
			//On ajoute la fourchette zero si jamais on n'a pas encore mis de philosophe.
			fourchettes.add(new Fourchette());
		}
		philosophes.add(p);
		fourchettes.add(new Fourchette());
		return p;
	}

	public int getPosition(Philosophe p) {
		return philosophes.indexOf(p);
	}

	public Fourchette fourchetteGauche(Philosophe p){
		return fourchettes.get(getPosition(p));
	}

	public Fourchette fourchetteDroite(Philosophe p){
		return fourchettes.get((getPosition(p) + 1) % philosophes.size());
	}

	@Override
	public <A extends Agent, T> T getPerception(A agent, Perception<A, T> p) {
		return p.getValue(agent);
	}

	@Override
	public <A extends Agent, T> T doAction(A agent, Action<A, T> a) throws UndefinedActionException {
		return a.doAction(agent);
	}

}
