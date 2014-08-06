/**
 * 
 */
package poc.jbehave.todo.test.step;

import static org.fest.assertions.Assertions.assertThat;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;

import poc.jbehave.plumbing.StepsDefinition;
import poc.jbehave.todo.service.api.AllTodosDto;
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
    private AllTodosDto allTodosDto;

    @Given("some todos available")
    public void someTodosAvailable() {
        // TODO
    }

    @When("I list all my todos")
    public void iListAllMyTodos() {
        allTodosDto = todoService.getAllTodos();
    }

    @Then("I get $number todos")
    public void iGetTodos(Integer number) {
        assertThat(allTodosDto.getTodos()).hasSize(number);
    }
}