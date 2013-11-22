package plateforme;

/**
 * @author thomas
 *
 *	Une interface qui décrit un conteneur de Action.
 *
 *	Cette interface a une méthode qui renvoie l'Action.
 *	Elle est particulièrement utile si on veut mettre les actions
 *	dans une énumération Actions de l'environnement.
 *
 */
public interface ActionContainer {

	public <T> Action<? extends Agent,T> getAction();
	
}
