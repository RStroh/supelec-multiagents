package plateforme;

import java.util.List;

import javax.security.auth.callback.Callback;

import plateforme.interaction.Message;
import plateforme.interaction.SendMessageException;

public interface AMS {
	void send(Message m) throws SendMessageException;
	void sendAsync(Message m, Callback c) throws SendMessageException;
	void addAgent(Agent a);
	List<Agent> getAgents();
}
