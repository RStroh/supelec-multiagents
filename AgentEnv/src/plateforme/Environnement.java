package plateforme;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.Callback;

import plateforme.action.Action;
import plateforme.action.UndefinedActionException;
import plateforme.action.WrongActionException;
import plateforme.interaction.Message;
import plateforme.interaction.SendMessageAble;
import plateforme.interaction.SendMessageException;
import plateforme.perception.Perception;


public abstract class Environnement implements SendMessageAble, AgentAware {
	
	/**
	 * Agent Management Service.
	 */
	private AMS ams;
	
	public List<Agent> getAgents() {
		return getAms().getAgents();
	}
	
	public Environnement() {
		//Initialise l'Agent Management Service.
		setAms(new SimpleAMS());
	}
	
	public List<Agent> getAgents(AgentTypeContainer agentType){
		List<Agent> res= new ArrayList<>();
		Class<? extends Agent> agentClass = agentType.getAgentClass();
		for (Agent agent : getAgents()) {
			if (agent.getClass() == agentClass) res.add(agent);
		}
		return Collections.unmodifiableList(res);
	};
	
//	public enum 
	
	/**
	 * Méthode générique pour exécuter l'action d'un acteur dans l'environnement.
	 * 
	 * @param agent
	 * @param a
	 * @return
	 * @throws UndefinedActionException
	 * @throws WrongActionException
	 */
	protected <A extends Agent, T> T executeAction(A agent, Action<A, T> a) throws UndefinedActionException, WrongActionException {
		return a.doAction(agent);
	}
	
	/**
	 * Méthode générique pour retourner la perception d'un acteur.
	 * 
	 * @param agent
	 * @param p
	 * @return
	 */
	public <A extends Agent, T> T getPerception(A agent, Perception<A, T> p) {
		return p.getValue(agent);
	}
	
	public void addAgent(Agent agent){
		getAms().addAgent(agent);
	}

	public AMS getAms() {
		return ams;
	}

	public void setAms(AMS ams) {
		this.ams = ams;
	}
	
	@Override
	public void send(Message m) throws SendMessageException {
		getAms().send(m);
	}
	
	@Override
	public void sendAsync(Message m, Callback c) throws SendMessageException {
		getAms().send(m);
	}

}
