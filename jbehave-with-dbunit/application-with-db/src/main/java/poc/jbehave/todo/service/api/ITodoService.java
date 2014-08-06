package poc.jbehave.todo.service.api;

import java.util.List;

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
    List<Todo> getAllTodos();
}