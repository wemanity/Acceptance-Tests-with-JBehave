/**
 * 
 */
package poc.jbehave;

import java.util.List;
import java.util.Locale;

import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

/**
 * <p>
 * Définition des étapes JBehave en Français, afin d'utiliser la même langue que
 * les tests d'acceptation fournis avec les User Stories.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@RunWith(JUnitReportingRunner.class)
public class AllFrenchStoriesTest extends AbstractAllStoriesTest {

    private static final String FRENCH_LANGUAGE = "fr";
    private static final String PATH_TO_BE_INCLUDED = "**/*.histoire";

    @Override
    protected List<String> storyPaths() {
        return super.storyPaths();
    }

    @Override
    protected Locale locale() {
        return new Locale(FRENCH_LANGUAGE);
    }

    @Override
    protected String storyPattern() {
        return PATH_TO_BE_INCLUDED;
    }
}
