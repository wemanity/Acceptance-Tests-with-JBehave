/**
 * 
 */
package poc.jbehave.todo.service.api;

import poc.jbehave.todo.service.domain.Todo;

/**
 * DTO pour récupérer les informations d'un todo après modification.
 * 
 * @author Xavier Pigeon
 */
public class UpdatedTodoDto {

    private Todo updatedTodo;

    /**
     * Constructeur.
     * 
     * @param updatedTodo le todo mis à jour
     */
    public UpdatedTodoDto(Todo updatedTodo) {
        super();
        this.updatedTodo = updatedTodo;
    }

    /**
     * Getter for the field updatedTodo.
     * 
     * @return the updatedTodo
     */
    public Todo getUpdatedTodo() {
        return updatedTodo;
    }

    /**
     * Setter for the field updatedTodo.
     * 
     * @param updatedTodo the updatedTodo to set
     */
    public void setUpdatedTodo(Todo updatedTodo) {
        this.updatedTodo = updatedTodo;
    }
}