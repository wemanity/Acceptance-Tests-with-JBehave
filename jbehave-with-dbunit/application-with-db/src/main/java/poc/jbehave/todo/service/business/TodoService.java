/**
 * 
 */
package poc.jbehave.todo.service.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.jbehave.todo.data.repository.TodoRepository;
import poc.jbehave.todo.service.api.AllTodosDto;
import poc.jbehave.todo.service.api.ITodoService;
import poc.jbehave.todo.service.domain.Todo;

import com.google.common.collect.Lists;

/**
 * Service métier pour les todos.
 * 
 * @author Xavier Pigeon
 */
@Service
public class TodoService implements ITodoService {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public AllTodosDto getAllTodos() {
        List<Todo> todos = Lists.newArrayList(todoRepository.findAll()) //
                .stream() //
                .map(todo -> new Todo(todo)) //
                .collect(Collectors.toList());

        return new AllTodosDto(todos);
    }
}