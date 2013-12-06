package philo;

import java.net.InetSocketAddress;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;

public class Main {

	public static final MetricRegistry metricsRegistry = new MetricRegistry();

	public static final Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
	//	public static final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricsRegistry)
	//			.prefixedWith("multiagents.philosophes")
	//			.convertRatesTo(TimeUnit.SECONDS)
	//			.convertDurationsTo(TimeUnit.MILLISECONDS)
	//			.filter(MetricFilter.ALL)
	//			.withClock(new CpuTimeClock())
	//			.build(graphite);
	/**
	 * Objet Reporter qui permet de voir l'évolution des paramètres du problème au cours du temps.
	 */
//	public static final CsvReporter reporter = CsvReporter.forRegistry(metricsRegistry)
//			.formatFor(Locale.FRANCE)
//			.convertRatesTo(TimeUnit.SECONDS)
//			.convertDurationsTo(TimeUnit.MILLISECONDS)
//			.build(new File("stats/"));
	public static final JmxReporter reporter = JmxReporter.forRegistry(metricsRegistry)
			.build();
	public static void main(String[] args) {
		//On reporte l'état du système périodiquement.
		reporter.start();
//		final Counter evictions = metricsRegistry.counter(name("newCounter", "cache-evictions"));

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

//		while (true){
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			evictions.inc();
//			reporter.report();
//		}
	}

}
