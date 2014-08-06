/**
 * 
 */
package poc.jbehave.todo.test.step;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import poc.jbehave.plumbing.StepsDefinition;

/**
 * Définition des étapes JBehave.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class TodoSteps {

    @Given("some todos available")
    public void someTodosAvailable() {
        throw new RuntimeException("Not yet implemented!");
    }

    @When("I list all my todos")
    public void iListAllMyTodos() {
        throw new RuntimeException("Not yet implemented!");
    }

    @Then("I get $number todos")
    public void iGetTodos(Integer number) {
        throw new RuntimeException("Not yet implemented!");
    }
}