package philo;

import static com.codahale.metrics.MetricRegistry.*;
import static philo.ActionsPhilosophesEnum.*;
import static philo.EtatsPhilosophe.*;
import static philo.PerceptionsPhilosophes.*;
import static plateforme.DefaultPerformatifs.*;

import java.util.ArrayList;
import java.util.List;

import philo.Fourchette.EtatFourchette;
import plateforme.DefaultPerformatifs;
import plateforme.action.Action;
import plateforme.action.ActionEnum;
import plateforme.action.WrongActionException;
import plateforme.agent.Agent;
import plateforme.agent.Etat;
import plateforme.interaction.Message;
import plateforme.interaction.SendMessageException;
import plateforme.perception.PerceptionEnum;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;

public class Philosophe extends Agent<EnvironnementPhilo, EtatsPhilosophe, PerceptionsPhilosophes, ActionsPhilosophesEnum>{

	static int SEUIL_FAIM = 10;
	static int SEUIL_CREVE_DE_FAIM = 20;
	static int SEUIL_RASSASIE = -20;

	/**
	 * Si un voisin meurt de faim est vrai. Est mis à jour à la lecture du courrier.
	 */
	private boolean voisinMortDeFaim = false;

	private String nom;
	final Counter faim;
	final Meter penseeProduite;

	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
		faim = Main.metricsRegistry.counter(name("faim -"+nom, "faim"));
		penseeProduite = Main.metricsRegistry.meter(name(Philosophe.class, nom, "pensee"));
	}

	@Override
	public void lireCourrier(List<Message> courrierReleve) {
		//Traitement des messages, stockage de l'information dans l'état interne.
		for (Message message : courrierReleve) {
			if (message.getPerformatif() == DefaultPerformatifs.INFORM){
				if(message.getContenu() == "Gargouillis!"){
					voisinMortDeFaim = true;
				}
			}
		}
	}

	@Override
	public String percevoirEtDecider() {
		StringBuilder out = new StringBuilder();
		out.append(String.format("Position %s : %s", this, 
				percept(POSITION)));
		//Decision-->Action
		out.append(" // etat:" + getAgentState());
		out.append(" // faim: "+faim.getCount());

		switch (getAgentState()) {
		case PENSE:
			try {
				//On ajoute la pensée produite par le philosophe aux stats.
				penseeProduite.mark((long) act(PENSER));
				faim.inc(1);
			} catch (WrongActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//			out.append((aFaim()) ? " // J'ai faim" : " // J'ai pas faim.");
			if (aFaim()){
				setAgentState(ATTEND);
			}
			break;

		case ATTEND:
			faim.inc(1);
			if (fourchettesLibres()){
				try {
					manger();
				} catch (WrongActionException e1) {

				}
			} else if (creveDeFaim()) {
				faim.inc(10);
				List<Philosophe> dests = new ArrayList<Philosophe>();	//Destinataires
				//Faire des gargouillis

				Philosophe pGauche = percept(PROPRIETAIRE_FOURCHETTE_G);
				Philosophe pDroit = percept(PROPRIETAIRE_FOURCHETTE_D);

				if (!(pGauche == null) && !pGauche.equals(this)) dests.add(pGauche);
				if (!(pDroit == null) && !pDroit.equals(this)) dests.add(pDroit);

				for (Philosophe dest : dests) {
					try {
						send(new Message(this, dest, INFORM, "Gargouillis!"));
					} catch (SendMessageException e) {
						e.printStackTrace();
					}
				}
			}
			break;

		case MANGE:
			if (creveDeFaim()) faim.dec(20);
			else if (aFaim()) faim.dec(5);
			else faim.dec(1);
			if (estRassasie()) {
				//poser les fourchettes.
				try {
					act(POSER_FOURCHETTE_D);
					act(POSER_FOURCHETTE_G);
				} catch (WrongActionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setAgentState(PENSE);
			} 
//			else if (voisinMortDeFaim){
//				if(!this.creveDeFaim()){
					out.append(" // JE POSE LES FOURCHETTES");
					//On accepte de poser les fourchettes.
//					try {
//						act(POSER_FOURCHETTE_D);
//						act(POSER_FOURCHETTE_G);
//					} catch (WrongActionException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					setAgentState(PENSE);
//				}
//			}
			break;

		default:
			break;
		}
		voisinMortDeFaim = false; //Sera réactualisé à la prochaine lecture du courrier
		return out.toString();
	}

	private boolean creveDeFaim() {
		return faim.getCount() >= SEUIL_CREVE_DE_FAIM;
	}

	private boolean aLesFourchettes() {
		return ((boolean)percept(A_FOURCHETTE_G)) && ((boolean)percept(A_FOURCHETTE_D));
	}

	private boolean fourchettesLibres() {
		return (percept(ETAT_FOURCHETTE_D) == EtatFourchette.LIBRE) &&
				(percept(ETAT_FOURCHETTE_G) == EtatFourchette.LIBRE);
	}

	private boolean estRassasie() {
		return faim.getCount() <= SEUIL_RASSASIE;
	}

	private boolean aFaim() {
		return faim.getCount() > SEUIL_FAIM;
	}

	private void manger() throws WrongActionException {
		//Si j'ai pas les 2 fourchettes, je prends les 2.
		act(PRENDRE_LES_2_FOURCHETTES);

		if (aLesFourchettes()) {
			setAgentState(MANGE);
		}
		else{
			setAgentState(ATTEND);
		}
	}

	//Demande sa position à l'environnement.
	private int getPosition(){
		return percept(POSITION);
	}

	@Override
	public String toString() {
		return nom;
	}

	@Override
	protected Class<? extends PerceptionEnum> perceptionContainerClass() {
		return PerceptionsPhilosophes.class;
	}

	@Override
	protected Class<? extends ActionEnum> actionContainerClass() {
		return ActionsPhilosophesEnum.class;
	}

	@Override
	public List<Action> getActions() {
		List<Action> actions = new ArrayList<>();
		for (ActionEnum ac : ActionsPhilosophesEnum.values()) {
			actions.add(ac.getAction());
		}
		return actions;
	}

	@Override
	public Class<? extends Enum<? extends Etat>> getAgentStates() {
		return EtatsPhilosophe.class;
	}

	@Override
	public EtatsPhilosophe etatInitial() {
		return PENSE;
	}




}
