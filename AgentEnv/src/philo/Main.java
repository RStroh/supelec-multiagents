package philo;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

public class Main {

	public static final MetricRegistry metricsRegistry = new MetricRegistry();

	public static final Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
	public static final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricsRegistry)
			.prefixedWith("multiagents.philosophes")
			.convertRatesTo(TimeUnit.SECONDS)
			.convertDurationsTo(TimeUnit.MILLISECONDS)
			.filter(MetricFilter.ALL)
			.build(graphite);

	public static void main(String[] args) {
		
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
