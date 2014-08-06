/**
 * 
 */
package poc.jbehave.todo.service.domain;

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
     * @param id
     * @param label
     */
    public Todo(Long id, String label) {
        this.id = id;
        this.label = label;
        done = false;
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