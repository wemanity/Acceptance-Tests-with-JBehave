/**
 * 
 */
package poc.jbehave.todo.service.business;

import org.springframework.stereotype.Service;

import poc.jbehave.todo.service.api.AllTodosDto;
import poc.jbehave.todo.service.api.ITodoService;

/**
 * Service métier pour les todos.
 * 
 * @author Xavier Pigeon
 */
@Service
public class TodoService implements ITodoService {

    /**
     * {@inheritDoc}
     */
    @Override
    public AllTodosDto getAllTodos() {
        throw new RuntimeException("Not yet implemented!");
    }
}