package plateforme.agent;


/**
 * Décrit un type d'agent.
 * 
 * @author thomas
 *
 */
public interface AgentTypeContainer {
	
	/**
	 * Retourne la classe qui décrit les agents de ce type.
	 * 
	 * @return
	 */
	Class<? extends Agent> getAgentClass();
}
