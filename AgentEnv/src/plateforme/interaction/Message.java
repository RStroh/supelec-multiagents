package plateforme.interaction;

import plateforme.Agent;


public class Message {
	
	private Agent expediteur;
	private Agent destinataire;
	private Performatif performatif;
	private Contenu contenu;
	
	public Message(Agent expediteur, Agent destinataire, Performatif performatif, Contenu contenu) {
		super();
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.performatif = performatif;
		this.contenu = contenu;
	}
	public Performatif getPerformatif() {
		return performatif;
	}
	public void setPerformatif(Performatif performatif) {
		this.performatif = performatif;
	}
	public Contenu getContenu() {
		return contenu;
	}
	public void setContenu(Contenu contenu) {
		this.contenu = contenu;
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
