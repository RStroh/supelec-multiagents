package plateforme;

/**
 * Décrit un type d'agent.
 * 
 * @author thomas
 *
 */
public interface AgentType {
	
	public Class<? extends Agent> getAgentDescriptionClass(); 
}
