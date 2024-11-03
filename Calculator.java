public class Calculator {

    public static String convertInfixToPostfix(String infix) {
        LinkedStack<Character> operatorStack = new LinkedStack<>();
        StringBuilder postfix = new StringBuilder();
        System.out.println("Infix to Postfix Conversion Steps:");
        System.out.printf("%-25s %-25s %-25s%n", "Next Character", "Postfix", "Operator Stack (bottom to top)");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < infix.length(); i++) {
            char nextCharacter = infix.charAt(i);

            switch (nextCharacter) {
                case ' ':
                    continue; // Ignore whitespace
                case 'a': case 'b': case 'c': case 'd': case 'e': // Assuming variables are single letters
                    postfix.append(nextCharacter);
                    break;
                case '^':
                    operatorStack.push(nextCharacter);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    while (!operatorStack.isEmpty() && precedence(nextCharacter) <= precedence(operatorStack.peek())) {
                        postfix.append(operatorStack.pop());
                    }
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
                default:
                    break; // Ignore unexpected characters
            }

            // Print the current state after processing the character
            System.out.printf("%-25s %-25s %-25s%n", nextCharacter, postfix.toString(), operatorStackToString(operatorStack));
        }

        // Pop all remaining operators in the stack
        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    public static int evaluatePostfix(String postfix, int a, int b, int c, int d, int e) {
        ResizeableArrayStack<Integer> valueStack = new ResizeableArrayStack<>();
        System.out.println("Postfix Evaluation Steps:");
        System.out.printf("%-25s %-25s %-25s%n", "Next Character", "Current Stack", "Evaluation Area");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < postfix.length(); i++) {
            char nextCharacter = postfix.charAt(i);

            switch (nextCharacter) {
                case 'a':
                    valueStack.push(a);
                    break;
                case 'b':
                    valueStack.push(b);
                    break;
                case 'c':
                    valueStack.push(c);
                    break;
                case 'd':
                    valueStack.push(d);
                    break;
                case 'e':
                    valueStack.push(e);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    int operandTwo = valueStack.pop();
                    int operandOne = valueStack.pop();
                    int result = performOperation(nextCharacter, operandOne, operandTwo);
                    valueStack.push(result);
                    break;
                default:
                    break; // Ignore unexpected characters
            }

            // Print the current state after processing the character
            System.out.printf("%-25s %-25s %-25s%n", nextCharacter, stackToString(valueStack), valueStack.peek());
        }

        return valueStack.peek(); // Return the final result
    }

    // Helper method to get the precedence of operators
    private static int precedence(char operator) {
        switch (operator) {
            case '^': return 4;
            case '*':
            case '/': return 3;
            case '+':
            case '-': return 2;
            default: return 0; // Not an operator
        }
    }

    // Helper method to perform arithmetic operations
    private static int performOperation(char operator, int operandOne, int operandTwo) {
        switch (operator) {
            case '+': return operandOne + operandTwo;
            case '-': return operandOne - operandTwo;
            case '*': return operandOne * operandTwo;
            case '/': return operandOne / operandTwo;
            case '^': return (int) Math.pow(operandOne, operandTwo);
            default: throw new UnsupportedOperationException("Invalid operator: " + operator);
        }
    }

    // Convert the operator stack to a string for display
    private static String operatorStackToString(LinkedStack<Character> stack) {
        StringBuilder sb = new StringBuilder();
        for (Character op : stack) {
            sb.append(op).append(" ");
        }
        return sb.toString().trim();
    }

    // Convert the value stack to a string for display
    private static String stackToString(ResizeableArrayStack<Integer> stack) {
        StringBuilder sb = new StringBuilder();
        for (Integer val : stack) {
            sb.append(val).append(" ");
        }
        return sb.toString().trim();
    }
}
