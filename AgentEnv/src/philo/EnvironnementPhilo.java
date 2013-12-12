package philo;
import static com.codahale.metrics.MetricRegistry.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plateforme.DefaultPerformatifs;
import plateforme.Environnement;
import plateforme.NoPerceptions;
import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.NoActions;
import plateforme.agent.Agent;
import plateforme.agent.AgentTypeContainer;
import plateforme.agent.Etat;
import plateforme.agent.NoStates;
import plateforme.perception.PerceptionEnum;

import com.codahale.metrics.Meter;

public class EnvironnementPhilo extends Environnement<PhiloAgentsEnum, DefaultPerformatifs> {

	/**
	 * L'agent défini ci-après n'est pas référencé dans les agents de l'environnement, 
	 * il a simplement pour but d'écrire des informations dans la console.
	 */
	private Agent<EnvironnementPhilo,?,?,?> scribe = new Agent<EnvironnementPhilo, NoStates, NoPerceptions, NoActions>(this) {

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
	final Meter penseeProduite = Main.metricsRegistry.meter(name(this.getClass(), "penseeTotale"));

	public EnvironnementPhilo() {
		scribe.start();

	}	

	protected int getPenseeProduite() {
		return (int) penseeProduite.getCount();
	}

	protected int produirePensee(Philosophe p) {
		int incr = 1;
		penseeProduite.mark(incr);
		return incr;
	}

	public Philosophe ajouterPhilosophe(String nom) {
		//TODO Ajouter le philosophe et les fourchettes.
		Philosophe p = new Philosophe(this, nom);
		if (getAgents(PhiloAgentsEnum.PHILOSOPHE).isEmpty()) {
			//On ajoute la fourchette zero si jamais on n'a pas encore mis de philosophe.
			fourchettes.add(new Fourchette());
		}
		addAgent(p);
		fourchettes.add(new Fourchette());
		return p;
	}

	public int getPosition(Philosophe p) {
		return getAgents(PhiloAgentsEnum.PHILOSOPHE).indexOf(p);
	}

	public Fourchette fourchetteGauche(Philosophe p){
		return fourchettes.get(getPosition(p));
	}

	public Fourchette fourchetteDroite(Philosophe p){
		return fourchettes.get((getPosition(p) + 1) % getAgents(PhiloAgentsEnum.PHILOSOPHE).size());
	}

	@Override
	public List<AgentTypeContainer> getAgentTypes() {
		return new ArrayList<AgentTypeContainer>(Arrays.asList(PhiloAgentsEnum.values()));
	}
}
