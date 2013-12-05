package plateforme;

import java.util.List;

import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.UndefinedActionException;
import plateforme.action.WrongActionException;
import plateforme.interaction.AMS;
import plateforme.interaction.Mailbox;
import plateforme.interaction.Message;
import plateforme.interaction.SimpleMailbox;
import plateforme.perception.Perception;
import plateforme.perception.PerceptionContainer;

public abstract class Agent<E extends Environnement> extends Thread implements AgentI{

	protected E env;
	protected Mailbox<Agent<E>> mailbox = new SimpleMailbox<>();
	private Etat<Agent<E>> etat;
	
	public Agent(E env) {
		this.setEnv(env);
		this.etat = etatInitial();
	}
	
	public abstract Etat etatInitial();
	public abstract String percevoir();
	public abstract String decider();
	
	@Override
	public final void run() {
		while(true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

			//TODO Process messages if mailbox not empty
			List<Message> courrierReleve = mailbox.relever();
			for (Message message : courrierReleve) {
				//TODO Process message.
			}
			
			//Percept & decide
			StringBuilder out = new StringBuilder();
			out.append(percevoir());
			out.append(decider());
			System.out.println(out);
		}
	}
	
	/**
	 * @param p
	 * @return
	 * 
	 * Un agent ne peut avoir de perception que de son point de vue. 
	 * L'environnement lui renvoie sa propre perception. D'où le this.
	 * 
	 */
	public <T> T percept(PerceptionContainer p) {
		return (T) getEnv().getPerception(this, (Perception<Agent, T>) p.getPerception());
	}
	
	public <T> T act(ActionContainer ac) throws WrongActionException {
		try {
			if (ac.getAction() == null) throw new UndefinedActionException(String.format("L'action %s n'est pas implémentée.", ac));
			return (T) getEnv().doAction(this, (Action<Agent, T>) ac.getAction());
		} catch (UndefinedActionException e) {
			throw new RuntimeException(e);
		}
	}
	
//	@Override
//	public AMS getAMS() {
//		return env.getAMS();
//	}
	
	
	
	public Mailbox<Agent<E>> getMailbox() {
		return mailbox;
	}

	public void setMailbox(Mailbox<Agent<E>> mailbox) {
		this.mailbox = mailbox;
	}
	
	public E getEnv() {
		return env;
	}	
	
	public void setEnv(E env) {
		this.env = env;
	}
	
	
}
