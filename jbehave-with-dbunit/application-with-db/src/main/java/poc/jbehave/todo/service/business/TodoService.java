/**
 * 
 */
package poc.jbehave.todo.service.business;

import java.util.List;

import org.springframework.stereotype.Service;

import poc.jbehave.todo.service.api.ITodoService;
import poc.jbehave.todo.service.domain.Todo;

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
    public List<Todo> getAllTodos() {
        throw new RuntimeException("Not yet implemented!");
    }
}