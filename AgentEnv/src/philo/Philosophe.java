package philo;

import static com.codahale.metrics.MetricRegistry.*;
import static philo.ActionsPhilosophes.*;
import static philo.EtatsPhilosophe.*;
import static philo.PerceptionsPhilosophes.*;

import java.util.ArrayList;
import java.util.List;

import philo.Fourchette.EtatFourchette;
import plateforme.Agent;
import plateforme.Etat;
import plateforme.action.Action;
import plateforme.action.ActionContainer;
import plateforme.action.WrongActionException;
import plateforme.perception.PerceptionContainer;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;

public class Philosophe extends Agent<EnvironnementPhilo, EtatsPhilosophe, PerceptionsPhilosophes, ActionsPhilosophes>{

	static int SEUIL_FAIM_INF = -3;
	static int SEUIL_FAIM_SUP = 3;

	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
		Main.metricsRegistry.register(name(Philosophe.class, "faim-philo-"+nom),faim);
	}

	private String nom;
	//	private int faim = 0;

	final Counter faim = Main.metricsRegistry.counter(name("faim -"+nom, "faim"));
	final Meter penseeProduiteRate = Main.metricsRegistry.meter(name(this.getClass(), nom, "pensee"));

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
				act(PENSER);
				faim.inc();
			} catch (WrongActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			out.append(" // ");
			out.append((aFaim()) ? "J'ai faim" : "J'ai pas faim.");
			if (aFaim()){
				try {
					if (fourchettesLibres()) manger();
					else setAgentState(ATTEND);
				} catch (WrongActionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			break;

		case ATTEND:
			try {
				if (fourchettesLibres())	manger();
			} catch (WrongActionException e1) {

			}
			break;

		case MANGE:
			faim.dec();
			if (estRassasie()) {
				//poser les fourchettes.
				try {
					if (percept(A_FOURCHETTE_D))	act(POSER_FOURCHETTE_D);
					if (percept(A_FOURCHETTE_G))	act(POSER_FOURCHETTE_G);
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

	private boolean aLesFourchettes() {
		return ((boolean)percept(A_FOURCHETTE_G)) && ((boolean)percept(A_FOURCHETTE_D));
	}

	private boolean fourchettesLibres() {
		return (percept(ETAT_FOURCHETTE_D) == EtatFourchette.LIBRE) &&
				(percept(ETAT_FOURCHETTE_G) == EtatFourchette.LIBRE);
	}

	private boolean estRassasie() {
		return faim.getCount() <= SEUIL_FAIM_INF;
	}

	private boolean aFaim() {
		return faim.getCount() > SEUIL_FAIM_SUP;
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
	protected Class<? extends PerceptionContainer> perceptionContainerClass() {
		return PerceptionsPhilosophes.class;
	}

	@Override
	protected Class<? extends ActionContainer> actionContainerClass() {
		return ActionsPhilosophes.class;
	}

	@Override
	public List<Action> getActions() {
		List<Action> actions = new ArrayList<>();
		for (ActionContainer ac : ActionsPhilosophes.values()) {
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
