package poc.jbehave.todo.service.api;

import poc.jbehave.todo.service.domain.Todo;

/**
 * API pour la manipulation des todos.
 * 
 * @author Xavier Pigeon
 */
public interface ITodoService {

    /**
     * Lister tous les todos.
     * 
     * @return une liste de {@link Todo}
     */
    AllTodosDto getAllTodos();
}