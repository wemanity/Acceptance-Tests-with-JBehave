/**
 * 
 */
package poc.jbehave.steps.harrypotter;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Then;

import poc.jbehave.plumbing.StepsDefinition;

/**
 * Définition des étapes inspirées de la saga Harry Potter.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class LivingCreatureSteps {

    @Then("$livingName is alive")
    @Alias("$livingName is still alive")
    public void checkIsAlive(String livingName) {}

    @Then("$livingName is dead")
    @Alias("$livingName is suddenly dead")
    public void checkIsDied(String livingName) {}
}