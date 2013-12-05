package philo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import plateforme.Agent;
import plateforme.AgentType;
import plateforme.Environnement;
import plateforme.Etat;
import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.UndefinedActionException;
import plateforme.action.WrongActionException;
import plateforme.interaction.AMS;
import plateforme.perception.Perception;

public class EnvironnementPhilo extends Environnement {

//	private List<Philosophe> philosophes = new ArrayList<Philosophe>();
	public enum AgentTypes implements AgentType{
		PHILOSOPHE(Philosophe.class);

		private Class<? extends Agent> agentDescriptionClass;
		
		AgentTypes(Class<? extends Agent> agentDescriptionClass){
			this.agentDescriptionClass = agentDescriptionClass;
		}
		
		@Override
		public Class<? extends Agent> getAgentDescriptionClass() {
			return agentDescriptionClass;
		}
		
	}
	
	private List<Fourchette> fourchettes = new ArrayList<Fourchette>();

	private Agent<EnvironnementPhilo> scribe = new Agent<EnvironnementPhilo>(this) {

		@Override
		public Etat<Philosophe> etatInitial() {
			return null;
		}

		@Override
		public String percevoir() {
			// TODO Auto-generated method stub
			return "Scribe: ";
		}

		@Override
		public String decider() {
			return "Pensée Produite : "+getPenseeProduite();
		}

		@Override
		public AMS getAMS() {
			// TODO Auto-generated method stub
			return null;
		}

	};

	private int penseeProduite;
	
	public EnvironnementPhilo() {
		scribe.start();
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
		if (getAgents(AgentTypes.PHILOSOPHE).isEmpty()) {
			//On ajoute la fourchette zero si jamais on n'a pas encore mis de philosophe.
			fourchettes.add(new Fourchette());
		}
		addAgent(p);
		fourchettes.add(new Fourchette());
		return p;
	}

	public int getPosition(Philosophe p) {
		return getAgents(AgentTypes.PHILOSOPHE).indexOf(p);
	}

	public Fourchette fourchetteGauche(Philosophe p){
		return fourchettes.get(getPosition(p));
	}

	public Fourchette fourchetteDroite(Philosophe p){
		return fourchettes.get((getPosition(p) + 1) % getAgents(AgentTypes.PHILOSOPHE).size());
	}

	@Override
	public <A extends Agent, T> T getPerception(A agent, Perception<A, T> p) {
		return p.getValue(agent);
	}

	@Override
	public <A extends Agent, T> T doAction(A agent, Action<A, T> a) throws UndefinedActionException, WrongActionException {
		return a.doAction(agent);
	}
	
	@Override
	public List<AgentType> getAgentTypes() {
		return new ArrayList<AgentType>(Arrays.asList(AgentTypes.values()));
	}
}
