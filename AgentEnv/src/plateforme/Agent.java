package plateforme;

public abstract class Agent extends Thread{

	public Agent(Environnement env) {
		this.setEnv(env);
		this.etat = etatInitial();
	}
	
	protected EtatType etat;
	
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
	
	public abstract Environnement getEnv();
	public abstract void setEnv(Environnement env);

}
