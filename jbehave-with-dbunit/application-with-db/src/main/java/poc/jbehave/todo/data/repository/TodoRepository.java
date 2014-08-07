/**
 * 
 */
package poc.jbehave.todo.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import poc.jbehave.todo.data.bean.Todo;

/**
 * Repository Todo.
 * 
 * @author Xavier Pigeon
 */
@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {}