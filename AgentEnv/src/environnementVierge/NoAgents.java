package environnementVierge;

import plateforme.agent.Agent;
import plateforme.agent.AgentTypeContainer;

public enum NoAgents implements AgentTypeContainer{
	;

	@Override
	public Class<? extends Agent> getAgentClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
