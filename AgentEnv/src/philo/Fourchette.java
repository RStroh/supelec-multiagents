package philo;

public class Fourchette {

	Philosophe possesseur = null;
	
	public Fourchette() {
	}
	
	public boolean estLibre() {
		return (possesseur == null);
	}
	
	public void poser() {
		this.setPossesseur(null);
	}
	
	public void setPossesseur(Philosophe p) {
		possesseur = p;
	}
	
	public Philosophe getPossesseur() {
		return possesseur;
	}
	
	EtatFourchette getEtat(){
		return (possesseur == null) ? EtatFourchette.LIBRE : EtatFourchette.PRISE;
	}
	
	public enum EtatFourchette{
		PRISE,
		LIBRE;
	}
	
}
