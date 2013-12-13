package philo;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricRegistry;

public class Main {

	public static final MetricRegistry metricsRegistry = new MetricRegistry();

	/**
	 * Objet Reporter qui permet de voir l'évolution des paramètres du problème au cours du temps.
	 */
	public static final CsvReporter reporter = CsvReporter.forRegistry(metricsRegistry)
			.formatFor(Locale.FRANCE)
			.convertRatesTo(TimeUnit.MILLISECONDS)
			.convertDurationsTo(TimeUnit.MILLISECONDS)
			.build(new File("stats/"));
	public static void main(String[] args) throws IOException {
		FileUtils.cleanDirectory(new File("stats"));
		EnvironnementPhilo env = new EnvironnementPhilo();

		//Création des agents
		Philosophe p0 = env.ajouterPhilosophe("p0");
		Philosophe p1 = env.ajouterPhilosophe("p1");
		Philosophe p2 = env.ajouterPhilosophe("p2");
		Philosophe p3 = env.ajouterPhilosophe("p3");
		Philosophe p4 = env.ajouterPhilosophe("p4");

		p0.start();
		p1.start();
		p2.start();
		p3.start();
		p4.start();

	}

}
