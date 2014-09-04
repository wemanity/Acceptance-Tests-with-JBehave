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
 * Définition des étapes JBehave en Anglais, afin d'apporter le support des
 * tests d'acceptation qui ne seraient pas écrits en Français.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@RunWith(JUnitReportingRunner.class)
public class AllEnglishStoriesTest extends AbstractAllStoriesTest {

    private static final String PATH_TO_BE_INCLUDED = "**/*.story";

    @Override
    protected List<String> storyPaths() {
        return super.storyPaths();
    }

    @Override
    protected Locale locale() {
        return Locale.ENGLISH; // equivalent to: new Locale("en")
    }

    @Override
    protected String storyPattern() {
        return PATH_TO_BE_INCLUDED;
    }
}