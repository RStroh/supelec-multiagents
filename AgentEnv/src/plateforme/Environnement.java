package plateforme;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class Environnement implements EnvironnementI {
	
	private ArrayList<Agent> agents = new ArrayList<>();

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public List<Agent> getAgents(AgentType agentType){
		List<Agent> res= new ArrayList<>();
		Class<? extends Agent> agentClass = agentType.getAgentDescriptionClass();
		for (Agent agent : getAgents()) {
			if (agent.getClass() == agentClass) res.add(agent);
		}
		return Collections.unmodifiableList(res);
	};
	
	public void addAgent(Agent agent){
		agents.add(agent);
	};
	

}
