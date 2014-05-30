/**
 * 
 */
package poc.jbehave.calculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import poc.jbehave.calculator.util.StepsDefinition;

/**
 * <p>
 * Définition des étapes d'un scénario JBehave testant {@link Calculator} :
 * <ul>
 * <li>Les étapes suivent la pratique agile Given-When-Then, grâce aux
 * annotations du même nom.</li>
 * <li>La valeur de chaque annotation d'étape correspond à une phrase du
 * scénario.</li>
 * <li>Par défaut dans JBehave, les variables sont préfixées par '$'.</li>
 * <li>Ordre des variables : il y a 2 possibilités.
 * <ul>
 * <li>Soit les variables sont passées en paramètre dans le même ordre que dans
 * la phrase du scénario,</li>
 * <li>soit chaque paramètre est annoté par
 * {@link org.jbehave.core.annotations.Named}, afin d'indiquer quelle variable
 * il référence.</li>
 * </ul>
 * </li>
 * <li>Conversion de type : JBehave propose par défaut des convertisseurs
 * prédéfinis. Il est toutefois possible d'ajouter de nouveaux convertisseurs.</li>
 * </ul>
 * </p>
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class CalculatorSteps {

    private Calculator calculator;

    @BeforeScenario
    public void inializeScenario() {
        calculator = new Calculator();
    }

    @AfterScenario
    public void disposeScenario() {}

    @Given("a variable $variable with value $value")
    public void defineNamedVariableWithValue(String variable, int value) {
        calculator.defineVariable(variable, value);
    }

    @When("I add $value to $variable")
    public void addValueToVariable(@Named("variable") String variable, @Named("value") int value) {
        calculator.addToVariable(variable, value);
    }

    @Then("$variable should equal to $expected")
    public void assertVariableEqualTo(String variable, int expectedValue) {
        assertThat(calculator.getVariableValue(variable), equalTo(expectedValue));
    }
}
