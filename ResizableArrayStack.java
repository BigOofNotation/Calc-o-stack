import java.util.EmptyStackException;

public final class ResizableArrayStack<T> implements StackInterface<T> {
    private T[] stack;
    private int topIndex;
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

    public void push(T newEntry) {
        checkIntegrity();
        ensureCapacity();
        stack[++topIndex] = newEntry;
    }

    public T pop() {
        checkIntegrity();
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            T top = stack[topIndex];
            stack[topIndex--] = null;
            return top;
        }
    }

    public T peek() {
        checkIntegrity();
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack[topIndex];
        }
    }

    public boolean isEmpty() {
        return topIndex < 0;
    }

    public void clear() {
        checkIntegrity();
        while (topIndex > -1) {
            stack[topIndex--] = null;
        }
    }

    private void ensureCapacity() {
        if (topIndex >= stack.length - 1) { // If array is full, double its size
            int newLength = 2 * stack.length;
            checkCapacity(newLength);
            stack = java.util.Arrays.copyOf(stack, newLength);
        }
    }

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Attempt to create a stack whose capacity exceeds allowed maximum.");
        }
    }

    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("ResizableArrayStack object is corrupt.");
        }
    }

    // Method to evaluate a postfix expression with detailed step-by-step output
    public static int evaluatePostfix(String expression) {
        ResizableArrayStack<Integer> valueStack = new ResizableArrayStack<>();

        System.out.printf("%-20s %-20s %-20s%n", "Next character scanned", "Current stack", "Evaluation area");
        System.out.println("---------------------------------------------------------------");

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch)) {
                // Push operand onto the stack
                int operand = Character.getNumericValue(ch);
                valueStack.push(operand);
                System.out.printf("%-20s %-20s %-20s%n", ch, valueStack, "Push " + operand);
            } else if (isOperator(ch)) {
                // Pop two operands and apply operator
                int operandTwo = valueStack.pop();
                int operandOne = valueStack.pop();
                int result = applyOperator(operandOne, operandTwo, ch);
                valueStack.push(result);
                System.out.printf("%-20s %-20s %-20s%n", ch, valueStack, operandOne + " " + ch + " " + operandTwo + " = " + result);
            }
        }
        // Final result at the top of the stack
        return valueStack.peek();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    private static int applyOperator(int operandOne, int operandTwo, char operator) {
        return switch (operator) {
            case '+' -> operandOne + operandTwo;
            case '-' -> operandOne - operandTwo;
            case '*' -> operandOne * operandTwo;
            case '/' -> operandOne / operandTwo;
            case '^' -> (int) Math.pow(operandOne, operandTwo);
            default -> 0;
        };
    }

    // Helper method for stack toString to show current elements
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i <= topIndex; i++) {
            sb.append(stack[i]);
            if (i < topIndex) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
