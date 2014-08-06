/**
 * 
 */
package poc.jbehave.todo.service.api;

import java.util.List;

import poc.jbehave.todo.service.domain.Todo;

/**
 * DTO pour la récupération de tous les todos.
 * 
 * @author Xavier Pigeon
 */
public class AllTodosDto {

    private List<Todo> todos;

    /**
     * Getter for the field todos.
     * 
     * @return the todos
     */
    public List<Todo> getTodos() {
        return todos;
    }

    /**
     * Setter for the field todos.
     * 
     * @param todos the todos to set
     */
    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}