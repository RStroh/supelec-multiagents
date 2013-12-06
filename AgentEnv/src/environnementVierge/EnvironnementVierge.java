package environnementVierge;

import java.util.ArrayList;
import java.util.List;

import philo.Philosophe;
import plateforme.Agent;
import plateforme.AgentTypeContainer;
import plateforme.Environnement;

public class EnvironnementVierge extends Environnement{

	@Override
	public List<AgentTypeContainer> getAgentTypes() {
		return new ArrayList<AgentTypeContainer>(){{add(new AgentTypeContainer() {
			
			@Override
			public Class<? extends Agent> getAgentClass() {
				return Philosophe.class;
			}
		});}};
	}
	
	
	
}
