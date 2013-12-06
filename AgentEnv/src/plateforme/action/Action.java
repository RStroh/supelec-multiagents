package plateforme.action;

import plateforme.Agent;

/**
 * 
 * Action avec son type de retour T, et le type de l'agent A capable de produire cette action.
 * 
 * @author thomas
 *
 * @param <A,T>
 */
public interface Action<A extends Agent, ReturnType> {
	/**
	 * Ac
	 * Retourne la valeur du résultat de l'action (s'il existe) pour l'agent qui demande.
	 * 
	 * @return
	 * @throws WrongActionException 
	 */
	public abstract ReturnType doAction(A agent) throws WrongActionException;
	
}