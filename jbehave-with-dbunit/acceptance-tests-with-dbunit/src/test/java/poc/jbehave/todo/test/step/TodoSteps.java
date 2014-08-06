/**
 * 
 */
package poc.jbehave.todo.test.step;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;

import poc.jbehave.plumbing.StepsDefinition;
import poc.jbehave.todo.service.api.ITodoService;

/**
 * Définition des étapes JBehave.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class TodoSteps {

    @Autowired
    private ITodoService todoService;

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