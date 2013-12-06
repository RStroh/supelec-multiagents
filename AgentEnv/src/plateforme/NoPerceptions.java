package plateforme;

import plateforme.agent.Agent;
import plateforme.perception.Perception;
import plateforme.perception.PerceptionEnum;

public enum NoPerceptions implements PerceptionEnum<Agent>{
	;

	@Override
	public <T> Perception<Agent, T> getPerception() {
		// TODO Auto-generated method stub
		return null;
	}

}
