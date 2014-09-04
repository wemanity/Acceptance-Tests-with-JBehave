/**
 * 
 */
package poc.jbehave.steps.harrypotter;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import poc.jbehave.plumbing.StepsDefinition;

/**
 * Définition des étapes inspirées de la saga Harry Potter.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class CastSpellSteps {

    /**
     * Define the target of any further action to be the specified entity.
     * 
     * Target is then set as current one and is available through several
     * aliases: target, a target, the target...
     * 
     * The entity can be a wizard, a person or anything else such as an elephant
     * or a door... The entity is retrieved from the current context using the
     * given name, and thus one must ensure it is defined first unless it is a
     * well known entity. If it is a well known entity, the entity is created
     * (if needed) and put in the context.
     * 
     * @param entityName
     *            the name of the entity to define as target for the next
     *            actions.
     */
    @Given("a target named $targetName")
    public void defineTarget(String targetName) {}

    @Given("a wizard named $wizardName")
    public void defineWizard(String wizardName) {}

    @Given("a target named $targetName blessed with $spellName")
    public void defineBlessedTarget(String targetName, String spellName) {}

    @Given("$entityName that counterspells $spellFilterName")
    public void activateSpellShield(String entityName, String filterName) {}

    @When("the wizard cast the spell $spellName")
    public void cast(String spellName) {}

    @Then("the target is dead")
    public void targetIsDead() {}
}
