import java.util.EmptyStackException;

/**
 * An interface for a generic stack data structure.
 *
 * @param <T> the type of elements in this stack
 */
public interface StackInterface<T> {
    /**
     * Adds a new entry to the top of the stack.
     *
     * @param newEntry an object to be added to the stack
     */
    void push(T newEntry);
    
    /**
     * Removes and returns the stack's top entry.
     *
     * @return the object at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    T pop();
    
    /**
     * Retrieves the stack's top entry without changing the stack.
     *
     * @return the object at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    T peek();
    
    /**
     * Detects whether the stack is empty.
     *
     * @return true if the stack is empty
     */
    boolean isEmpty();
    
    /**
     * Removes all entries from the stack.
     */
    void clear();
}
