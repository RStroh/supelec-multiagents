package plateforme.interaction;

import java.util.List;

import plateforme.Agent;

public interface Mailbox<A extends Agent> {
	void deposer(Message m);
	List<Message> relever();
}
