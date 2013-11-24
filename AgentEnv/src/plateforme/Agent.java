package plateforme;

public abstract class Agent<E extends Environnement> extends Thread{

	protected E env;

	private EtatType<Agent<E>> etat;
	
	public Agent(E env) {
		this.setEnv(env);
		this.etat = etatInitial();
	}
	
	public abstract EtatType etatInitial();
	
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
	
	public <T> T act(ActionContainer ac) {
		try {
			if (ac.getAction() == null) throw new UndefinedActionException(String.format("L'action %s n'est pas implémentée.", ac));
			return (T) getEnv().doAction(this, (Action<Agent, T>) ac.getAction());
		} catch (UndefinedActionException e) {
			throw new RuntimeException(e);
		}
	}
	
	public E getEnv() {
		return env;
	}	
	
	public void setEnv(E env) {
		this.env = env;
	}
	
	@Override
	public final void run() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			StringBuilder out = new StringBuilder();
			out.append(percevoir());
			out.append(decider());
			System.out.println(out);
		}
	}

	public abstract String percevoir();
	public abstract String decider();
}
