package plateforme.interaction;

import plateforme.agent.Agent;


public class Message {
	
	private PerformatifContainer performatif;
	private String contenu;
	private Agent expediteur;
	private Agent destinataire;

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
	public Message(Agent expediteur, Agent destinataire, PerformatifContainer performatif, String contenu) {
		super();
		this.performatif = performatif;
		this.contenu = contenu;
		this.destinataire = destinataire;
		this.expediteur = expediteur;
	}
	public PerformatifContainer getPerformatif() {
		return performatif;
	}
	public void setPerformatif(PerformatifContainer performatif) {
		this.performatif = performatif;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	
}
