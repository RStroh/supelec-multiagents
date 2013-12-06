package plateforme;

import plateforme.perception.Perception;

public enum NoPerceptions implements Perception<Agent, Void>{
	;

	@Override
	public Void getValue(Agent agent) {
		return null;
	}

}
