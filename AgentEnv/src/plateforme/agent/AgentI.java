package plateforme.agent;

import javax.security.auth.callback.Callback;

import plateforme.AMS;
import plateforme.interaction.Message;
import plateforme.interaction.SendMessageException;

/**
 * Interface décrivant le comportement d'un agent. Un agent peut envoyer des messages, peut accéder à l'AMS.
 * 
 * @author thomas
 *
 */
public interface AgentI {
	
	/**
	 * Retourne l'Agent Management Service
	 * @return
	 */
	AMS getAms();
	
	/**
	 * 
	 * Méthode synchrone pour envoyer des messages
	 * 
	 * @param m
	 * @throws SendMessageException
	 */
	
	void send(Message m) throws SendMessageException;
	
	/**
	 * 
	 * Méthode asynchrone pour envoyer des messages
	 * 
	 * @param m
	 * @param c
	 * @throws SendMessageException
	 */
	void sendAsync(Message m, Callback c) throws SendMessageException;
}
