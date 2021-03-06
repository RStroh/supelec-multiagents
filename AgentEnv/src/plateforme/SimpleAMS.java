package plateforme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.Callback;

import plateforme.action.Action;
import plateforme.agent.Agent;
import plateforme.interaction.Message;
import plateforme.interaction.SendMessageException;

/**
 * Un AMS simple.
 * 
 * @author thomas
 *
 */
public class SimpleAMS implements AMS {
	private ArrayList<Agent> agents = new ArrayList<>();
	
	@Override
	public void send(Message m) throws SendMessageException {
		m.getDestinataire().getMailbox().deposer(m);
	}
	@Override
	public void addAgent(Agent a) {
		agents.add(a);
	}

	@Override
	public List<Agent> getAgents() {
		//On ne peut pas modifier la liste des agents. La seule façon est d'appeler addAgent() ou removeAgent().
		return Collections.unmodifiableList(agents);
	}
	@Override
	public void sendAsync(Message m, Callback c) throws SendMessageException {
		
	}
	@Override
	public <A extends Agent> List<Action<A, ?>> getActions(A agent) {
		return agent.getActions();
	}

}
