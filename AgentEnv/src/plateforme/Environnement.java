package plateforme;
import java.util.ArrayList;
import java.util.Set;


public abstract class Environnement {
	
	private ArrayList<Agent> agents;
	
	public abstract Set<Perception<? extends Agent, ?>> getPerceptions();
	public abstract ActionType[] getActions();

	public abstract <A extends Agent,T> T getPerception(A agent, Perception<A,T> p);
	
	public abstract boolean doAction(ActionType a);
	
}
