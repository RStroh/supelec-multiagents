package plateforme;

import java.util.List;

import javax.security.auth.callback.Callback;

import philo.EnvironnementPhilo;
import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.UndefinedActionException;
import plateforme.action.WrongActionException;
import plateforme.interaction.Mailbox;
import plateforme.interaction.Message;
import plateforme.interaction.SendMessageException;
import plateforme.interaction.SimpleMailbox;
import plateforme.perception.Perception;
import plateforme.perception.PerceptionContainer;

/**
 * S est un type Enum dévrivant les états possibles pour cet agent.
 * 
 * @author thomas
 *
 * @param <E>
 * @param <StateEnum>
 */
public abstract class Agent<E extends Environnement,
		StateEnum extends Enum<? extends Etat<? extends Agent>>,
		PerceptionEnum extends Enum<? extends PerceptionContainer>,
		ActionsEnum extends Enum<? extends ActionContainer<? extends Agent, ?>>> extends Thread implements AgentI{
	//<? extends Perception<? extends Agent<E, ?, ?>, ?>>
	protected E env;
	protected Mailbox<Agent<E,?,?,?>> mailbox = new SimpleMailbox<>();
	private Class<? extends PerceptionContainer> perceptionContainer;
	private StateEnum agentState;

	public Agent(E env) {
		this.setEnv(env);
		this.setAgentState(etatInitial());
	}

	//Abstrait
	public abstract StateEnum etatInitial();
	public abstract String percevoir();
	public abstract String decider();
	protected abstract Class<? extends PerceptionContainer> perceptionContainerClass();
	protected abstract Class<? extends ActionContainer> actionContainerClass();
	abstract public Class<? extends Enum<? extends Etat>> getAgentStates();

	/**
	 * Retourne toutes les actions que l'agent est capable d'exécuter.
	 * 
	 * @return
	 */
	public abstract List<Action> getActions();


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
				System.out.println("Courrier");
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
	 * L'environnement lui renvoie sa propre perception. Pour cela,
	 * l'environnement doit savoir qui percoit, d'où le this passé en paramètre.
	 * 
	 */
	public <T> T percept(PerceptionContainer p) {
		return (T) getEnv().getPerception(this, (Perception<Agent, T>) p.getPerception());
	}

	public <T> T act(ActionContainer ac) throws WrongActionException {
		try {
			if (ac.getAction() == null) throw new UndefinedActionException(String.format("L'action %s n'est pas implémentée.", ac));
			return (T) getEnv().executeAction(this, (Action<Agent, T>) ac.getAction());
		} catch (UndefinedActionException e) {
			throw new WrongActionException("Action invalide.", e);
		}
	}

	public Mailbox<Agent<E, ?, ?, ?>> getMailbox() {
		return mailbox;
	}

	public void setMailbox(Mailbox<Agent<E, ?, ?, ?>> mailbox) {
		this.mailbox = mailbox;
	}

	public E getEnv() {
		return env;
	}	

	public void setEnv(E env) {
		this.env = env;
	}

	@Override
	public AMS getAms() {
		return env.getAms();
	}

	@Override
	public void send(Message m) throws SendMessageException {
		getAms().send(m);
	}

	@Override
	public void sendAsync(Message m, Callback c) throws SendMessageException {
		getAms().send(m);
	}

	public StateEnum getAgentState() {
		return agentState;
	}

	public void setAgentState(StateEnum agentState) {
		this.agentState = agentState;
	}

}
