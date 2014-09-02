/**
 * 
 */
package poc.jbehave.todo.service.api;

import poc.jbehave.todo.service.domain.Todo;

/**
 * DTO pour la création d'un nouveau todo.
 * 
 * @author Xavier Pigeon
 */
public class NewTodoDto {

    private Todo newTodo;

    /**
     * Constructeur.
     * 
     * @param newTodo le todo nouvellement créé
     */
    public NewTodoDto(Todo newTodo) {
        setNewTodo(newTodo);
    }

    /**
     * Getter for the field newTodo.
     * 
     * @return the newTodo
     */
    public Todo getNewTodo() {
        return newTodo;
    }

    /**
     * Setter for the field newTodo.
     * 
     * @param newTodo the newTodo to set
     */
    public void setNewTodo(Todo newTodo) {
        this.newTodo = newTodo;
    }
}