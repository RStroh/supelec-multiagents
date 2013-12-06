package philo;

import plateforme.Agent;
import plateforme.AgentTypeContainer;

public enum PhiloAgentsEnum implements AgentTypeContainer{
		PHILOSOPHE(Philosophe.class);
		//...
		//Ajouter des nouveaux types d'agents ici...
		
		
		//In a new environment, if enumeration is chosen, then please DO NOT change the following code in enum.
		private Class<? extends Agent> agentClass;

		<T extends Agent> PhiloAgentsEnum(Class<T> agentClass){
			this.agentClass = agentClass;
		}
		
		@Override
		public Class<? extends Agent> getAgentClass() {
			return agentClass;
		}
}
