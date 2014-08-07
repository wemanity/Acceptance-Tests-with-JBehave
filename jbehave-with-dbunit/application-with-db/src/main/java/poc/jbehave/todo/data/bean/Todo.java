/**
 * 
 */
package poc.jbehave.todo.data.bean;

/**
 * Todo.
 * 
 * @author Xavier Pigeon
 */
public class Todo {

    private long id;
    private String label;
    private boolean done;

    /**
     * @param id
     * @param label
     * @param done
     */
    public Todo(long id, String label, boolean done) {
        this.id = id;
        this.label = label;
        this.done = done;
    }

    /**
     * Getter for the field id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the field id.
     * 
     * @param id the id to set
     */
    public void setId(long id) {
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
    public boolean isDone() {
        return done;
    }

    /**
     * Setter for the field done.
     * 
     * @param done the done to set
     */
    public void setDone(boolean done) {
        this.done = done;
    }
}