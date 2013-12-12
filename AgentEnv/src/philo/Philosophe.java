package philo;

import static com.codahale.metrics.MetricRegistry.*;
import static philo.ActionsPhilosophesEnum.*;
import static philo.EtatsPhilosophe.*;
import static philo.PerceptionsPhilosophes.*;

import java.util.ArrayList;
import java.util.List;

import philo.Fourchette.EtatFourchette;
import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.WrongActionException;
import plateforme.agent.Agent;
import plateforme.agent.Etat;
import plateforme.interaction.Message;
import plateforme.perception.PerceptionEnum;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;

public class Philosophe extends Agent<EnvironnementPhilo, EtatsPhilosophe, PerceptionsPhilosophes, ActionsPhilosophesEnum>{

	static int SEUIL_FAIM = -3;
	static int SEUIL_CREVE_DE_FAIM = -10;
	static int SEUIL_RASSASIE = 3;

	private String nom;
	final Counter faim;
	final Meter penseeProduite;

	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
		faim = Main.metricsRegistry.counter(name("faim -"+nom, "faim"));
		penseeProduite = Main.metricsRegistry.meter(name(Philosophe.class, nom, "pensee"));
	}

	public String percevoir() {
		StringBuilder out = new StringBuilder();

		//Perception
		out.append(String.format("Position %s : %s", this, 
				percept(POSITION)));

		return out.toString();
	}

	@Override
	public String decider() {
		StringBuilder out = new StringBuilder();
		//Decision-->Action
		out.append(" // etat:" + getAgentState());

		switch (getAgentState()) {
		case PENSE:

			try {
				//On ajoute la pensée produite par le philosophe aux stats.
				penseeProduite.mark((long) act(PENSER));
				faim.inc();
			} catch (WrongActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.append((aFaim()) ? " // J'ai faim" : " // J'ai pas faim.");
			if (aFaim()){
//				try {
//					if (fourchettesLibres()) manger();
//					else setAgentState(ATTEND);
//				} catch (WrongActionException e2) {
//					out.append("// Quelqu'un a pris la fourchette plus vite que moi !");
					setAgentState(ATTEND);
//				}
			}
			break;

		case ATTEND:
			if (fourchettesLibres()){
				try {
					manger();
				} catch (WrongActionException e1) {
					
				}
			} else if (creveDeFaim()) {
//				percept(p)
//				
//				send(new Message(this, destinataire, performatif, contenu));
			}
			break;

		case MANGE:
			faim.dec();
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
			break;

		default:
			break;
		}

		return out.toString();
	}

	private boolean creveDeFaim() {
		return faim.getCount() <= SEUIL_CREVE_DE_FAIM;
	}

	private boolean aLesFourchettes() {
		return ((boolean)percept(A_FOURCHETTE_G)) && ((boolean)percept(A_FOURCHETTE_D));
	}

	private boolean fourchettesLibres() {
		return (percept(ETAT_FOURCHETTE_D) == EtatFourchette.LIBRE) &&
				(percept(ETAT_FOURCHETTE_G) == EtatFourchette.LIBRE);
	}

	private boolean estRassasie() {
		return faim.getCount() <= SEUIL_FAIM;
	}

	private boolean aFaim() {
		return faim.getCount() > SEUIL_RASSASIE;
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
	protected Class<? extends ActionContainer> actionContainerClass() {
		return ActionsPhilosophesEnum.class;
	}

	@Override
	public List<Action> getActions() {
		List<Action> actions = new ArrayList<>();
		for (ActionContainer ac : ActionsPhilosophesEnum.values()) {
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
