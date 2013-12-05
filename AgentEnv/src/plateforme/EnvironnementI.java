package plateforme;

import java.util.List;
import java.util.Set;

import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.UndefinedActionException;
import plateforme.action.WrongActionException;
import plateforme.perception.Perception;

public interface EnvironnementI {
	
	public <A extends Agent> Set<Perception<A, ?>> getPerceptions(Class<A> agentClass);
	public ActionContainer[] getActions();

	public <A extends Agent, T> T getPerception(A agent, Perception<A,T> p);
	
	public boolean doAction(ActionContainer a);
	public <A extends Agent, T> T doAction(A agent, Action<A, T> a) throws UndefinedActionException, WrongActionException;

	/**
	 * Retourne tous les types d'agents existant dans l'environnement.
	 * 
	 * @return
	 */
	List<AgentType> getAgentTypes();
	
}
