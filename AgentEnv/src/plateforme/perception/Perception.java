package plateforme.perception;

import plateforme.Agent;

/**
 * 
 * Perception avec son type de retour T, et le type de l'agent A capable de percevoir.
 * 
 * @author thomas
 *
 * @param <A,T>
 */
public interface Perception<A extends Agent,T> {
	/**
	 * 
	 * Retourne la valeur du résultat de la perception pour l'agent qui demande.
	 * 
	 * @return
	 */
	public abstract T getValue(A agent);
	
}