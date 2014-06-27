/**
 * 
 */
package poc.jbehave.steps.harrypotter;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;

import poc.jbehave.plumbing.StepsDefinition;

/**
 * Définition des étapes inspirées de la saga Harry Potter.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class DarkMagicSteps {

    @Given("$personName has $quantity horcruxes")
    @Alias("$personName has $quantity horcrux")
    public void associateHorcrux(String personName, int quantity) {}
}
