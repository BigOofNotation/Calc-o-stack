import java.util.EmptyStackException;
/**
    A class of stacks whose entries are stored in a resizable array.
    @author Frank M. Carrano and Timothy M. Henry
    @version 5.0
*/
public final class ResizableArrayStack<T> implements StackInterface<T> {
   private T[] stack;    // Array of stack entries
   private int topIndex; // Index of top entry
   private boolean integrityOK = false;
   private static final int DEFAULT_CAPACITY = 50;
   private static final int MAX_CAPACITY = 10000;
 
   public ResizableArrayStack() {
       this(DEFAULT_CAPACITY);
   } // end default constructor
 
   public ResizableArrayStack(int initialCapacity) {
       integrityOK = false;
       checkCapacity(initialCapacity);
       
       // The cast is safe because the new array contains null entries
       @SuppressWarnings("unchecked")
       T[] tempStack = (T[]) new Object[initialCapacity];
       stack = tempStack;
       topIndex = -1;
       integrityOK = true;
   } // end constructor
 
   // Adds a new entry to the top of the stack.
   public void push(T newEntry) {
       checkIntegrity();
       if (topIndex >= stack.length - 1) { // Stack is full
           doubleCapacity();
       }
       stack[++topIndex] = newEntry;
   } // end push

   // Removes and returns the top entry of the stack.
   public T pop() {
       checkIntegrity();
       if (isEmpty()) {
           throw new EmptyStackException();
       } else {
           T top = stack[topIndex];
           stack[topIndex--] = null; // Avoid memory leak
           return top;
       }
   } // end pop

   // Retrieves the top entry of the stack.
   public T peek() {
       checkIntegrity();
       if (isEmpty()) {
           throw new EmptyStackException();
       } else {
           return stack[topIndex];
       }
   } // end peek

   // Checks if the stack is empty.
   public boolean isEmpty() {
       return topIndex < 0;
   } // end isEmpty

   // Clears the stack.
   public void clear() {
       checkIntegrity();
       
       // Remove references to allow garbage collection
       while (topIndex > -1) {
           stack[topIndex--] = null;
       }
   } // end clear

   // Doubles the capacity of the stack array.
   private void doubleCapacity() {
       int newLength = 2 * stack.length;
       checkCapacity(newLength);

       @SuppressWarnings("unchecked")
       T[] newStack = (T[]) new Object[newLength];
       
       // Copy existing entries into the new array
       System.arraycopy(stack, 0, newStack, 0, stack.length);
       stack = newStack;
   }

   // Ensures the capacity does not exceed the maximum limit.
   private void checkCapacity(int capacity) {
       if (capacity > MAX_CAPACITY) {
           throw new IllegalStateException("Attempt to create a stack whose capacity exceeds the allowed maximum of " + MAX_CAPACITY);
       }
   }

   // Checks if the stack is in a valid state.
   private void checkIntegrity() {
       if (!integrityOK) {
           throw new SecurityException("ResizableArrayStack object is corrupt.");
       }
   }
} // end ResizableArrayStack
