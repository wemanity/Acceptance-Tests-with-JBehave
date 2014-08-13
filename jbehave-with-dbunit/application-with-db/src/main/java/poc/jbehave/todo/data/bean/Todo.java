/**
 * 
 */
package poc.jbehave.todo.data.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;

/**
 * Todo.
 * 
 * @author Xavier Pigeon
 */
@Entity(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "label")
    private String label;
    @Column(name = "done")
    private boolean done;

    /**
     * Constructeur par défaut.
     */
    public Todo() {}

    /**
     * Constructeur.
     * 
     * @param id l'identifiant
     * @param label le libellé
     * @param done le statut de complétude
     */
    public Todo(Long id, String label, boolean done) {
        this.id = id;
        this.label = label;
        this.done = done;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(Todo.class) //
                .add("id", id) //
                .add("label", label) //
                .add("done", done) //
                .toString();
    }
}