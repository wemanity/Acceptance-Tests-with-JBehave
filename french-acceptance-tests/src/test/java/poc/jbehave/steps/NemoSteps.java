/**
 * 
 */
package poc.jbehave.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poc.jbehave.plumbing.StepsDefinition;

/**
 * Définition des étapes.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class NemoSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(NemoSteps.class);

    private int scenarioNumber = 0;

    @BeforeStories
    public void beforeAllStories() {
        LOGGER.info("<<<<<< BeforeStories >>>>>>");
    }

    @BeforeStory
    public void beforeMyStory() {
        LOGGER.info("<<<<<< BeforeStory >>>>>>");
    }

    @BeforeScenario
    public void beforeMyScenario() {
        LOGGER.info("<<<<<< BeforeScenario >>>>>>");

        LOGGER.debug("Number of scenarii = {}", ++scenarioNumber);
    }

    @Given("mon contexte")
    @Alias("mon contexte est connu")
    public void givenMyContext() {
        LOGGER.info("<<<<<< Given >>>>>>");
    }

    @When("certains événements surviennent")
    public void whenSomeEvents() {
        LOGGER.info("<<<<<< When >>>>>>");
    }

    @Then("j'obtiens les résultats attendus")
    public void thenExpectedResults() {
        LOGGER.info("<<<<<< Then >>>>>>");
    }

    @AfterScenario
    public void afterMyScenario() {
        LOGGER.info("<<<<<< AfterScenario >>>>>>");
    }

    @AfterStory
    public void afterMyStory() {
        LOGGER.info("<<<<<< AfterStory >>>>>>");
    }

    @AfterStories
    public void afterAllStories() {
        LOGGER.info("<<<<<< AfterStories >>>>>>");
    }
}
