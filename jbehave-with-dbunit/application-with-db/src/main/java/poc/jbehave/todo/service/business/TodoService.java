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
import poc.jbehave.todo.service.api.NewTodoDto;
import poc.jbehave.todo.service.api.UpdatedTodoDto;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public NewTodoDto addNewTodo(String label) {
        Todo newTodo = new Todo( //
                todoRepository.save(new poc.jbehave.todo.data.bean.Todo(label)));
        return new NewTodoDto(newTodo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOldTodo(Long number) {
        todoRepository.delete(number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdatedTodoDto updateLabel(Long number, String label) {
        poc.jbehave.todo.data.bean.Todo todoToBeUpdated = todoRepository.findOne(number);
        todoToBeUpdated.setLabel(label);
        poc.jbehave.todo.data.bean.Todo updatedTodo = todoRepository.save(todoToBeUpdated);

        return new UpdatedTodoDto(new Todo(updatedTodo));
    }

    @Override
    public UpdatedTodoDto achieveTodo(Long number) {
        poc.jbehave.todo.data.bean.Todo todoToBeUpdated = todoRepository.findOne(number);
        todoToBeUpdated.setDone(true);
        poc.jbehave.todo.data.bean.Todo updatedTodo = todoRepository.save(todoToBeUpdated);

        return new UpdatedTodoDto(new Todo(updatedTodo));
    }
}