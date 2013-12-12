package plateforme;

import plateforme.interaction.Performatif;
import plateforme.interaction.PerformatifContainer;

public enum DefaultPerformatifs implements PerformatifContainer {
	INFORM,
	CANCEL,
	REQUEST,
	AGREE,
	REFUSE,
	PROPOSE;

	@Override
	public Performatif getPerformatif() {
		// TODO Auto-generated method stub
		return null;
	}
}
