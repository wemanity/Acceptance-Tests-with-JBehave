/**
 * 
 */
package poc.jbehave.todo.service.domain;

import com.google.common.base.Objects;

/**
 * Un élément Todo.
 * 
 * @author Xavier Pigeon
 */
public class Todo {

    private Long id;
    private String label;
    private Boolean done;

    /**
     * Constructeur.
     */
    public Todo() {}

    /**
     * Constructeur.
     * 
     * @param id
     * @param label
     */
    public Todo(Long id, String label) {
        this.id = id;
        this.label = label;
        done = false;
    }

    /**
     * Constructeur.
     * 
     * @param id
     * @param label
     */
    public Todo(Long id, String label, Boolean done) {
        this(id, label);
        this.done = done;
    }

    /**
     * Constructeur.
     * 
     * @param todo
     */
    public Todo(poc.jbehave.todo.data.bean.Todo todo) {
        this(todo.getId(), todo.getLabel(), todo.isDone());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        boolean areEqual = false;

        if (obj == null) {
            areEqual = false;
        } else if (getClass() != obj.getClass()) {
            areEqual = false;
        } else {
            final Todo other = (Todo) obj;

            areEqual = Objects.equal(this.id, other.id) //
                    && Objects.equal(this.label, other.label) //
                    && Objects.equal(this.done, other.done);
        }

        return areEqual;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id, label, done);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(Todo.class) //
                .addValue(id) //
                .addValue(label) //
                .addValue(done) //
                .toString();
    }

    /**
     * Getter for the field id.
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the field id.
     * 
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the field label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter for the field label.
     * 
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for the field done.
     * 
     * @return the done
     */
    public Boolean getDone() {
        return done;
    }

    /**
     * Setter for the field done.
     * 
     * @param done the done to set
     */
    public void setDone(Boolean done) {
        this.done = done;
    }
}