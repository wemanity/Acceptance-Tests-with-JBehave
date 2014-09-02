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

    /**
     * Ajouter un nouveau todo.
     * 
     * @param label le libellé du todo
     * @return un objet {@link NewTodoDto}
     */
    NewTodoDto addNewTodo(String label);
}