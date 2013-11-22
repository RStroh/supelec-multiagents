package philo;

public class Fourchette {

	Philosophe possesseur = null;
	
	public Fourchette() {
	}
	
	public boolean estLibre() {
		return (possesseur == null);
	}
	
	public void setPossesseur(Philosophe p) {
		possesseur = p;
	}
	
	public Philosophe getPossesseur() {
		return possesseur;
	}
	
	public enum EtatFourchette{
		PRISE,
		LIBRE;
	}
	
}
