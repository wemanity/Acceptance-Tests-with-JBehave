/**
 * 
 */
package poc.jbehave;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML_TEMPLATE;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.ConsoleOutput;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;

import poc.jbehave.plumbing.Springs;
import poc.jbehave.plumbing.UTF8StoryLoader;
import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

/**
 * <p>
 * Configuration de l'environnement d'exécution des scénarii de test avec
 * JBehave. On choisit JUnitReportingRunner comme lanceur JUnit, issu de
 * jbehave-junit-runner.
 * </p>
 * <p>
 * La classe {@link AbstractAllStoriesTest} peut être exécutée de la même
 * manière qu'un quelconque test JUnit.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@RunWith(JUnitReportingRunner.class)
public abstract class AbstractAllStoriesTest extends JUnitStories {

    private static final String PATH_TO_BE_EXCLUDED = "**/fail/*";
    private static final String BASE_PACKAGES = "poc.jbehave.steps";
    private final CrossReference xref = new CrossReference();

    public AbstractAllStoriesTest() {
        // Environnement global d’exécution des tests JBehave
        configuredEmbedder() //
                .embedderControls() //
                .doGenerateViewAfterStories(true) //
                .doIgnoreFailureInStories(false) //
                .doIgnoreFailureInView(true) //
                .doVerboseFailures(true) //
                .useThreads(2) // 1 par défaut
                .useStoryTimeoutInSecs(6000);
    }

    /**
     * <p>
     * Configuration de l'environnement d'exécution de JBehave :
     * <ul>
     * <li>CONSOLE : sortie des rapports en console pour facilitrer la phase de
     * développement dans l'IDE</li>
     * <li>HTML_TEMPLATE : génération de rapports HTML pour une meilleure
     * lisibilité au niveau de la PIC</li>
     * <li>Stratégie transmise à useStepMonitor() :
     * <ul>
     * <li>Par défaut : stratégie qui veut que les étapes du test à PENDING
     * soient ignorées, ainsi le test passe.</li>
     * <li>new FailingUponPendingStep() : stratégie qui veut que les étapes du
     * test à PENDING fassent échouer le test.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     */
    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        URL codeLocation = codeLocationFromClass(embeddableClass);

        Keywords keywords = keywords();

        StoryReporterBuilder storyReporter = new StoryReporterBuilder() //
                .withCodeLocation(codeLocation) //
                .withDefaultFormats() //
                .withFormats(CONSOLE, HTML_TEMPLATE) //
                .withFailureTrace(true) //
                .withFailureTraceCompression(true) //
                .withCrossReference(xref)//
                .withKeywords(keywords);

        Configuration configuration = new MostUsefulConfiguration() //
                .useKeywords(keywords) //
                .useStepCollector(new MarkUnmatchedStepsAsPending(keywords)) //
                .useStoryParser(new RegexStoryParser(keywords)) //
                .useStoryLoader(new UTF8StoryLoader(embeddableClass)) //
                .useDefaultStoryReporter(new ConsoleOutput(keywords)) //
                .useStoryReporterBuilder(storyReporter) //
                .usePendingStepStrategy(new FailingUponPendingStep()) //
                .useStepMonitor(xref.getStepMonitor());

        return configuration;
    }

    /**
     * <p>
     * Découverte des fichiers *.story à exécuter.
     * </p>
     * <p>
     * Points de vigilance :
     * <ul>
     * <li>Les fichiers story seront chargés comme ressources Java, ce qui
     * implique qu'il faut fournir des chemins relatifs au classpath.</li>
     * <li>La découverte des fichiers story se fonde sur l'emplacement de la
     * classe de tests {@link AbstractAllStoriesTest}.</li>
     * <ul>
     * <li>Si le lanceur JBehave {@link AbstractAllStoriesTest} se trouve dans
     * src/test/java, alors les fichiers *.story doivent se trouver dans
     * src/test/resources (copiées par Maven dans target/test-classes).</li>
     * <li>Si le lanceur JBehave {@link AbstractAllStoriesTest} se trouve dans
     * src/main/java, alors les fichiers *.story doivent se trouver dans
     * src/main/resources (copiées par Maven dans target/classes).</li>
     * </ul>
     * </ul>
     * </p>
     */
    @Override
    protected List<String> storyPaths() {
        URL searchInURL = codeLocationFromClass(this.getClass());
        return new StoryFinder().findPaths(searchInURL, storyPattern(), PATH_TO_BE_EXCLUDED);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), Springs.createAnnotatedContextFromBasePackages(BASE_PACKAGES));
    }

    /**
     * <p>
     * Implémentation par défaut pour la fourniture des mots-clés de JBehave.
     * </p>
     * <p>
     * À surcharger si un bundle personnalisé est choisi.
     * </p>
     * 
     * @return un objet {@link Keywords}
     */
    protected Keywords keywords() {
        return new LocalizedKeywords(locale());
    }

    /**
     * Indiquer la langue, afin de choisir un bundle JBehave en fonction de la
     * langue.
     * 
     * @return un objet {@link Locale} correspond à la langue
     */
    protected abstract Locale locale();

    /**
     * Indiquer l'expression régulière pour la recherche des fichiers stories.
     * 
     * @return l'expression régulière du chemin relatif des fichiers stories
     */
    protected abstract String storyPattern();
}
