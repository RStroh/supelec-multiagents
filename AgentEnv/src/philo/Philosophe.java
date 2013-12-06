package philo;

import static com.codahale.metrics.MetricRegistry.*;
import static philo.ActionsPhilosophes.*;
import static philo.EtatsPhilosophe.*;
import static philo.PerceptionsPhilosophes.*;
import plateforme.AMS;
import plateforme.Agent;
import plateforme.Etat;
import plateforme.action.ActionContainer;
import plateforme.action.WrongActionException;
import plateforme.perception.PerceptionContainer;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;

public class Philosophe extends Agent<EnvironnementPhilo>{

	static int SEUIL_FAIM_INF = -3;
	static int SEUIL_FAIM_SUP = 3;

	public Philosophe(EnvironnementPhilo env, String nom) {
		super(env);
		this.nom = nom;
		Main.metricsRegistry.register(name(Philosophe.class, "faim-philo-"+nom),faim);
	}

	private String nom;
//	private int faim = 0;
	private EtatsPhilosophe etat = PENSE;

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
		out.append(" // etat:" + etat);

		switch (etat) {
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
			if (aFaim()) etat = MANGE;
			break;

		case ATTEND:
			
			try {
				tryManger();
			} catch (WrongActionException e1) {
			}
			break;

		case MANGE:
			faim.dec();
			if (!aFaim()) {
				//poser les fourchettes.
				try {
					if (percept(A_FOURCHETTE_D))	act(POSER_FOURCHETTE_D);
					if (percept(A_FOURCHETTE_G))	act(POSER_FOURCHETTE_G);
				} catch (WrongActionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				etat = PENSE;
			}
			break;

		default:
			break;
		}

		return out.toString();
	}

	private boolean aFaim() {
		return faim.getCount() > SEUIL_FAIM_SUP;
	}

	private void tryManger() throws WrongActionException {
		//Si j'ai pas les 2 fourchettes, je prends les 2.
		act(PRENDRE_LES_2_FOURCHETTES);

		if (percept(A_FOURCHETTE_G)) {
			if (percept(A_FOURCHETTE_D)){
				etat = MANGE;
			}
		}
	}

	//Demande sa position à l'environnement.
	private int getPosition(){
		return percept(POSITION);
	}

	@Override
	public Etat<Philosophe> etatInitial() {
		return EtatsPhilosophe.PENSE;
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
	
//	public void incrFaim(int i){
//		faim = faim -i;
//		faimCounter.dec(i);
//	}
//	
//	public void decrFaim(int i){
//		faim = faim-i;
//		faimCounter.inc(i);
//	}
}
