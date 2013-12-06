package plateforme.agent;

import java.util.List;

/**
 * Permet de décrire les objets qui sont au courant des types d'agents disponibles et des actions qu'ils peuvent entreprendre.
 * Typiquement l'environnement et les agents eux mêmes implémentent cette interface.
 * 
 * @author thomas
 *
 */
public interface AgentAware {
//	public <A extends Agent> Set<Perception<A, ?>> getPerceptions(Class<A> agentClass);
//	public ActionContainer[] getActions();
//
//	public <A extends Agent, T> T getPerception(A agent, Perception<A,T> p);
//	
//	public <A extends Agent, T> T doAction(A agent, Action<A, T> a) throws UndefinedActionException, WrongActionException;

	/**
	 * Retourne tous les types d'agents existant dans l'environnement.
	 * 
	 * @return
	 */
	List<AgentTypeContainer> getAgentTypes();
}
