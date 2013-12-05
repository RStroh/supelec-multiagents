package plateforme.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import plateforme.Agent;

public class SimpleMailbox<A extends Agent> implements Mailbox<A>{

	PriorityBlockingQueue<Message> messages = new PriorityBlockingQueue<Message>();
	
	@Override
	public void deposer(Message m) {
		//TODO Vérifier que le message est adapté à ce type d'agent.
		messages.add(m);
	}

	@Override
	public List<Message> relever() {
		ArrayList<Message> res = new ArrayList<>();
		messages.drainTo(res);
		return res;
	}
}
