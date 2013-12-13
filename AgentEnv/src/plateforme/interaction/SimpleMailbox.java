package plateforme.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import plateforme.agent.Agent;

public class SimpleMailbox<A extends Agent> implements Mailbox<A>{

	//BlockingQueue implementations are thread-safe.
	private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();
	
	@Override
	public void deposer(Message m) throws SendMessageException {
		//TODO Vérifier que le message est adapté à ce type d'agent.
		messages.add(m);
	}

	@Override
	public List<Message> relever() {
		ArrayList<Message> res = new ArrayList<>();
		messages.drainTo(res);	//On 'draine' les messages vers le courrier relevé.
		return res;
	}
}
