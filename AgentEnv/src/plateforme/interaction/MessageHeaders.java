package plateforme.interaction;

import plateforme.Agent;

/**
 * En-têtes d'un message.
 * 
 * @author thomas
 *
 */
public class MessageHeaders {
	private Agent expediteur;
	private Agent destinataire;
	public MessageHeaders(Agent expediteur, Agent destinataire) {
		super();
		this.expediteur = expediteur;
		this.destinataire = destinataire;
	}	
	public Agent getExpediteur() {
		return expediteur;
	}
	public void setExpediteur(Agent expediteur) {
		this.expediteur = expediteur;
	}
	public Agent getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(Agent destinataire) {
		this.destinataire = destinataire;
	}
	
	
	
}
