package philo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import plateforme.AMS;
import plateforme.Agent;
import plateforme.AgentTypeContainer;
import plateforme.Environnement;
import plateforme.Etat;
import plateforme.NoActions;
import plateforme.NoPerceptions;
import plateforme.NoStates;
import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.UndefinedActionException;
import plateforme.action.WrongActionException;
import plateforme.perception.Perception;
import plateforme.perception.PerceptionEnum;

public class EnvironnementPhilo extends Environnement {

	public enum AgentTypes  implements AgentTypeContainer{
		PHILOSOPHE(Philosophe.class);
		//...
		//Ajouter des nouveaux types d'agents ici...
		
		
		//In a new environment, if enumeration is chosen, then please DO NOT change the following code in enum.
		private Class<? extends Agent> agentClass;

		<T extends Agent> AgentTypes(Class<T> agentClass){
			this.agentClass = agentClass;
		}
		@Override
		public Class<? extends Agent> getAgentClass() {
			return agentClass;
		}
	}


	/**
	 * L'agent défini ci-après n'est pas référencé dans les agents de l'environnement, 
	 * il a simplement pour but d'écrire des informations dans la console.
	 */
	private Agent<EnvironnementPhilo, ?, ?,?> scribe = new Agent<EnvironnementPhilo, NoStates, NoPerceptions, NoActions>(this) {

		@Override
		public String percevoir() {
			return "Scribe: ";
		}

		@Override
		public String decider() {
			return "Pensée Produite : "+getPenseeProduite();
		}

		@Override
		protected Class<? extends PerceptionEnum> perceptionContainerClass() {
			return null;	//Pas de perceptions
		}

		@Override
		protected Class<? extends ActionContainer> actionContainerClass() {
			return null;	//Pas d'actions
		}

		@Override
		public List<Action> getActions() {
			return null;	//Pas d'actions
		}

		@Override
		public Class<? extends Enum<? extends Etat>> getAgentStates() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NoStates etatInitial() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private List<Fourchette> fourchettes = new ArrayList<Fourchette>();
	private int penseeProduite;

	public EnvironnementPhilo() {
		scribe.start();
	}	

	protected int getPenseeProduite() {
		return penseeProduite;
	}

	protected void produirePensee(Philosophe p) {
		penseeProduite++;
	}

	//	@Override
	//	public ActionContainer[] getActions() {
	//		return ActionsPhilosophes.values();
	//	}
	//
	//	@Override
	//	public <A extends Agent> Set<Perception<A, ?>> getPerceptions(Class<A> agentClass) {
	//		Set<Perception<A, ?>> perceptions = new HashSet<>();
	//
	//		if(agentClass.equals(PerceptionsPhilosophes.class)){
	//			for (PerceptionsPhilosophes p : EnumSet.allOf(PerceptionsPhilosophes.class)) {
	//				perceptions.add((Perception<A, ?>) p.getPerception());
	//			}
	//		}
	//		return perceptions;
	//	}

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
	public List<AgentTypeContainer> getAgentTypes() {
		return new ArrayList<AgentTypeContainer>(Arrays.asList(AgentTypes.values()));
	}
}
