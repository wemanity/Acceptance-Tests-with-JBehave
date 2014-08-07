/**
 * 
 */
package poc.jbehave.todo.service.business;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import poc.jbehave.todo.data.bean.Todo;
import poc.jbehave.todo.data.repository.TodoRepository;
import poc.jbehave.todo.service.api.AllTodosDto;

/**
 * Test Case for {@link TodoService}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;

    @Configuration
    @ComponentScan(basePackages = "poc.jbehave.todo.service")
    static class Config {

        @Bean
        TodoRepository todoRepository() {
            return EasyMock.createMock(TodoRepository.class);
        }
    }

    /**
     * Test method for
     * {@link poc.jbehave.todo.service.business.TodoService#getAllTodos()}.
     */
    @Test
    public void should_get_todos() {
        // GIVEN
        EasyMock.expect(todoRepository.findAll()) //
                .andReturn(Arrays.asList( //
                        new Todo(1, "todo 1", false), //
                        new Todo(2, "todo 2", true), //
                        new Todo(3, "todo 3", false))) //
                .once();
        EasyMock.replay(todoRepository);

        // WHEN
        AllTodosDto allTodosDto = todoService.getAllTodos();

        // THEN
        assertThat(allTodosDto).isNotNull();
        assertThat(allTodosDto.getTodos()).isNotEmpty();
        EasyMock.verify(todoRepository);
    }
}