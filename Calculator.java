import java.util.Arrays;

public class Calculator {

    public static String convertInfixToPostfix(String infix) {
        LinkedStack<Character> operatorStack = new LinkedStack<>();
        checkIntegrity(infix);
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
                case '(', '[', '{':
                    operatorStack.push(nextCharacter);
                    break;
                case ')', ']', '}':
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(' && operatorStack.peek() != '[' && operatorStack.peek() != '{') {
                        postfix.append(operatorStack.pop());
                    }
                    operatorStack.pop(); // Pop the '('
                    break;
                default:
                    postfix.append(nextCharacter);
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

    public static int evaluatePostfix(String postfix) {
        checkIntegrity(postfix);
        ResizableArrayStack<Integer> valueStack = new ResizableArrayStack<>();
        System.out.println("Postfix Evaluation Steps:");
        System.out.printf("%-25s %-25s %-25s%n", "Next Character", "Current Stack", "Evaluation Area");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < postfix.length(); i++) {
            char nextCharacter = postfix.charAt(i);

            switch (nextCharacter) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    if(valueStack.size() >= 2) {
                        int operandTwo = valueStack.pop();
                        int operandOne = valueStack.pop();
                        int result = performOperation(nextCharacter, operandOne, operandTwo);
                        valueStack.push(result);
                    } else {
                        throw new RuntimeException("Not enough operands for operation " + nextCharacter);
                    }
                    
                    break;
                default:
                    valueStack.push(nextCharacter - '0');
                    break; // Ignore unexpected characters
            }

            // Print the current state after processing the character
            System.out.printf("%-25s %-25s %-25s%n", nextCharacter, stackToString(valueStack), valueStack.isEmpty() ? " " : valueStack.peek());
        }

        return valueStack.peek(); // Return the final result
    }

    public static int evaluatePostfix(String postfix, int a, int b, int c, int d, int e) {
        checkIntegrity(postfix);
        ResizableArrayStack<Integer> valueStack = new ResizableArrayStack<>();
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
        ResizableArrayStack<Character> temp = new ResizableArrayStack<>();
        while(!stack.isEmpty()){
            char op = stack.pop();
            sb.append(op + " ");
            temp.push(op);
        }
        while(!temp.isEmpty()){
            stack.push(temp.pop());
        }
        return sb.toString().trim();
    }

    // Convert the value stack to a string for display
    private static String stackToString(ResizableArrayStack<Integer> stack) {
        StringBuilder sb = new StringBuilder();
        ResizableArrayStack<Integer> temp = new ResizableArrayStack<>();
        while(!stack.isEmpty()){
            int val = stack.pop();
            sb.append(val + " ");
            temp.push(val);
        }
        while(!temp.isEmpty()){
            stack.push(temp.pop());
        }
        return sb.toString().trim();
    }
    private static void checkIntegrity(String expression){
        StackInterface<String> bracket = new ResizableArrayStack<>();
        String[] infix = expression.split("");
        boolean isBalanced = true;
        int index = 0;
        int operand = 0;
        int operator = 0;

        while(isBalanced && index < infix.length){
            String next = infix[index];
            switch(next){
                case "{", "[", "(":
                    bracket.push(next);
                    break;
                case ")", "]", "}":
                    if(bracket.isEmpty()){
                        isBalanced = false;
                    } else {
                        isBalanced = isPaired(bracket.pop(), next);
                    }
                    break;
                case "+", "-", "/", "*", "^":
                    operator++;
                    break;
                case " ":
                    break;
                case "a","b","c","d","e":
                    operand++;
                    break;
                default:
                    try {
                        Double.parseDouble(next);
                        operand++;
                    } catch (Exception invalid) {
                        throw new RuntimeException("Expression contains an invalid item");
                    }
                    break;
            }
            index++;
        }
        if(!bracket.isEmpty()){
            isBalanced = false;
        }
        if(!isBalanced){
            throw new RuntimeException("Expression has bad brackets/parenthesis");
        }
        if(operand - operator != 1){
            throw new RuntimeException("Expression has too many operators/variables");
        }
    }
    private static boolean isPaired(String openBracket, String closeBracket)
    {
        return (openBracket.equals("(") && closeBracket.equals(")")) ||
                (openBracket.equals("[") && closeBracket.equals("]")) ||
                (openBracket.equals("{") && closeBracket.equals("}"));
    }
}
