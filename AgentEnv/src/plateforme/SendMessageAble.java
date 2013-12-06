package plateforme;

import javax.security.auth.callback.Callback;

import plateforme.interaction.Message;
import plateforme.interaction.SendMessageException;

/**
 * Décrit les objets capables d'envoyer des messages.
 * Typiquement les Agents ici.
 * 
 * @author thomas
 *
 */
public interface SendMessageAble {
	
	/**
	 * Accède à l'agent management service.
	 * Pour pouvoir, par exemple, chercher un destinataire, ou savoir si un destinataire est capable de faire telle
	 * ou telle action.
	 * 
	 * @return
	 */
	AMS getAms();
	
	void send(Message m) throws SendMessageException;
	void sendAsync(Message m, Callback c) throws SendMessageException;
	
}
