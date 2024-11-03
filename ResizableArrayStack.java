import java.util.EmptyStackException;

public final class ResizableArrayStack<T> implements StackInterface<T> {
    private T[] stack;    // Array of stack entries
    private int topIndex; // Index of top entry
    private boolean integrityOK = false;
    private static final int DEFAULT_CAPACITY = 50;
    private static final int MAX_CAPACITY = 10000;
  
    public ResizableArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    public ResizableArrayStack(int initialCapacity) {
        integrityOK = false;
        checkCapacity(initialCapacity);
        
        @SuppressWarnings("unchecked")
        T[] tempStack = (T[]) new Object[initialCapacity];
        stack = tempStack;
        topIndex = -1;
        integrityOK = true;
    }

    // Adds a new entry to the top of the stack.
    public void push(T newEntry) {
        checkIntegrity();
        if (topIndex >= stack.length - 1) {
            doubleCapacity();
        }
        stack[++topIndex] = newEntry;
    }

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
    }

    // Retrieves the top entry of the stack.
    public T peek() {
        checkIntegrity();
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack[topIndex];
        }
    }

    // Checks if the stack is empty.
    public boolean isEmpty() {
        return topIndex < 0;
    }

    // Clears the stack.
    public void clear() {
        checkIntegrity();
        while (topIndex > -1) {
            stack[topIndex--] = null;
        }
    }

    private void doubleCapacity() {
        int newLength = 2 * stack.length;
        checkCapacity(newLength);

        @SuppressWarnings("unchecked")
        T[] newStack = (T[]) new Object[newLength];
        System.arraycopy(stack, 0, newStack, 0, stack.length);
        stack = newStack;
    }

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Attempt to create a stack whose capacity exceeds the allowed maximum of " + MAX_CAPACITY);
        }
    }

    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("ResizableArrayStack object is corrupt.");
        }
    }

    // Method to evaluate a postfix expression
    public static int evaluatePostfix(String postfix) {
        ResizableArrayStack<Integer> valueStack = new ResizableArrayStack<>();
        
        for (int i = 0; i < postfix.length(); i++) {
            char nextCharacter = postfix.charAt(i);

            // Skip whitespace
            if (Character.isWhitespace(nextCharacter)) {
                continue;
            }

            if (Character.isDigit(nextCharacter)) {
                // Convert character to integer and push to stack
                valueStack.push(Character.getNumericValue(nextCharacter));
            } else {
                // Assume it's an operator (+, -, *, /, ^)
                int operandTwo = valueStack.pop();
                int operandOne = valueStack.pop();
                int result = 0;

                switch (nextCharacter) {
                    case '+':
                        result = operandOne + operandTwo;
                        break;
                    case '-':
                        result = operandOne - operandTwo;
                        break;
                    case '*':
                        result = operandOne * operandTwo;
                        break;
                    case '/':
                        result = operandOne / operandTwo;
                        break;
                    case '^':
                        result = (int) Math.pow(operandOne, operandTwo);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected character: " + nextCharacter);
                }
                valueStack.push(result);
            }
        }
        return valueStack.peek(); // The result of the postfix expression
    }
}
