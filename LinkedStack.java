import java.util.EmptyStackException;

// LinkedStack class implementing the stack
public final class LinkedStack<T> implements StackInterface<T> {
    private Node<T> topNode; // References the first node in the chain

    public LinkedStack() {
        topNode = null;
    } // end default constructor

    // Pushes a new entry onto the top of this stack.
    @Override
    public void push(T newEntry) {
        Node<T> newNode = new Node<>(newEntry, topNode);
        topNode = newNode;
    }

    // Removes and returns the top entry of this stack.
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T top = topNode.getData();
        topNode = topNode.getNextNode();
        return top;
    }

    // Retrieves the top entry of this stack.
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return topNode.getData();
    }

    // Detects whether this stack is empty.
    @Override
    public boolean isEmpty() {
        return topNode == null;
    }

    // Clears this stack.
    @Override
    public void clear() {
        topNode = null;
    }

    private class Node<T> {
        private T data; // Entry in stack
        private Node<T> next; // Link to next node

        private Node(T dataPortion) {
            this(dataPortion, null);
        } // end constructor

        private Node(T dataPortion, Node<T> linkPortion) {
            data = dataPortion;
            next = linkPortion;
        } // end constructor

        private T getData() {
            return data;
        } // end getData

        private Node<T> getNextNode() {
            return next;
        } // end getNextNode
    } // end Node

    // Method to convert infix to postfix
    public String convertToPostfix(String infix) {
        LinkedStack<Character> operatorStack = new LinkedStack<>();
        StringBuilder postfix = new StringBuilder();

        // Print table header
        System.out.printf("%-30s %-20s %-30s%n", "Next Character from Infix", "Postfix Form", "Operator Stack (Bottom to Top)");
        System.out.println("-----------------------------------------------------------------------");

        for (int i = 0; i < infix.length(); i++) {
            char nextCharacter = infix.charAt(i);
            switch (nextCharacter) {
                case ' ':
                    continue; // Ignore whitespace
                case '+':
                case '-':
                case '*':
                case '/':
                    while (!operatorStack.isEmpty() && precedence(nextCharacter) <= precedence(operatorStack.peek())) {
                        postfix.append(operatorStack.pop());
                    }
                    operatorStack.push(nextCharacter);
                    break;
                case '^':
                    operatorStack.push(nextCharacter);
                    break;
                case '(':
                    operatorStack.push(nextCharacter);
                    break;
                case ')':
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        postfix.append(operatorStack.pop());
                    }
                    operatorStack.pop(); // Pop the '('
                    break;
                default: // Assuming it's a variable
                    postfix.append(nextCharacter);
                    break;
            }

            // Print current state of the table after processing the character
            System.out.printf("%-30s %-20s %-30s%n", nextCharacter, postfix.toString(), stackToString(operatorStack));
        }

        // Pop all operators remaining in the stack
        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    // Helper method to determine precedence of operators
    private int precedence(char operator) {
        switch (operator) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return -1; // Unknown operator
        }
    }

    // Helper method to convert the stack to a string for printing
    private String stackToString(LinkedStack<Character> stack) {
        StringBuilder result = new StringBuilder();
        LinkedStack<Character> tempStack = new LinkedStack<>(); // Temporary stack for output
        
        while (!stack.isEmpty()) {
            char ch = stack.pop();
            result.append(ch).append(" ");
            tempStack.push(ch); // Store for restoring the original stack
        }

        // Restore the original stack
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }

        return result.toString().trim();
    }
}
