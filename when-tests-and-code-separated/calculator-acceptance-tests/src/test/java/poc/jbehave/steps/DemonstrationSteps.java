/**
 * 
 */
package poc.jbehave.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poc.jbehave.calculator.plumbing.StepsDefinition;

/**
 * <p>
 * Cette classe de définition d'étapes sert à montrer toutes les étapes
 * proposées par JBehave, ainsi que leur ordre d'exécution.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class DemonstrationSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemonstrationSteps.class);

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

    @Given("my context")
    public void givenMyContext() {
        LOGGER.info("<<<<<< Given >>>>>>");
    }

    @When("somes events happen")
    public void whenSomeEvents() {
        LOGGER.info("<<<<<< When >>>>>>");
    }

    @Then("I get expected results")
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