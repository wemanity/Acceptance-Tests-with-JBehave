/**
 * 
 */
package poc.jbehave;

import java.util.List;
import java.util.Locale;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
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

    private static final String MY_KEYWORDS = "mykeywords/my_keywords";
    private static final String PATH_TO_BE_INCLUDED = "**/*.histoire";

    @Override
    protected List<String> storyPaths() {
        return super.storyPaths();
    }

    @Override
    protected Keywords keywords() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Keywords keywords = new LocalizedKeywords(locale(), MY_KEYWORDS, classLoader);

        return keywords;
    }

    @Override
    protected Locale locale() {
        return Locale.FRENCH; // equivalent to: new Locale("fr")
    }

    @Override
    protected String storyPattern() {
        return PATH_TO_BE_INCLUDED;
    }
}
