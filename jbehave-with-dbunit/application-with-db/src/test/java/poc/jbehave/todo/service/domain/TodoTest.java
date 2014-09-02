/**
 * 
 */
package poc.jbehave.todo.service.domain;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test Cases for {@link Todo}.
 * 
 * @author Xavier Pigeon
 */
public class TodoTest {

    /**
     * Test method for
     * {@link poc.jbehave.todo.service.domain.Todo#equals(java.lang.Object)}.
     */
    @Test
    public void equals_should_be_symetric() throws Exception {
        // GIVEN
        Todo todo1 = new Todo(1L, "todo", true);
        Todo todo2 = new Todo(1L, "todo", true);

        // WHEN-THEN
        assertThat(todo1.equals(todo2)).isTrue();
        assertThat(todo2.equals(todo1)).isTrue();
    }

    /**
     * Test method for
     * {@link poc.jbehave.todo.service.domain.Todo#equals(java.lang.Object)}.
     */
    @Test
    public void equals_should_be_reflexive() throws Exception {
        // GIVEN
        Todo todo = new Todo(1L, "todo", true);

        // WHEN-THEN
        assertThat(todo.equals(todo)).isTrue();
    }

    /**
     * Test method for
     * {@link poc.jbehave.todo.service.domain.Todo#equals(java.lang.Object)}.
     */
    @Test
    public void equals_should_be_transitive() throws Exception {
        // GIVEN
        Todo todo1 = new Todo(1L, "todo", true);
        Todo todo2 = new Todo(1L, "todo", true);
        Todo todo3 = new Todo(1L, "todo", true);

        // WHEN
        assertThat(todo1.equals(todo2)).isTrue();
        assertThat(todo2.equals(todo3)).isTrue();

        // THEN
        assertThat(todo1.equals(todo3)).isTrue();
    }

    /**
     * Test method for {@link poc.jbehave.todo.service.domain.Todo#hashCode()}.
     */
    @Test
    public void hashcode_should_be_consistent_with_equals() throws Exception {
        // GIVEN
        Todo todo1 = new Todo(1L, "todo", true);
        Todo todo2 = new Todo(1L, "todo", true);

        // WHEN
        assertThat(todo1.equals(todo2)).isTrue();

        // THEN
        assertThat(todo1.hashCode()).isEqualTo(todo2.hashCode());
    }

    /**
     * Test method for
     * {@link poc.jbehave.todo.service.domain.Todo#equals(Object)}.
     */
    @Test
    public void should_not_be_equal_to_null() throws Exception {
        // GIVEN
        Todo todo = new Todo();

        // WHEN-THEN
        assertThat(todo.equals(null)).isFalse();
    }
}