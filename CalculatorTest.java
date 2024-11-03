public class CalculatorTest {
    public static void main(String[] args) {
        // Define variable values for a, b, c, d, and e
        int a = 2; // Given values
        int b = 3;
        int c = 4;
        int d = 5;
        int e = 6;

        // Postfix expression: "a b * c a - / d e * +"
        String postfixExpression = "ab*c a-/de*+";

        // Replace variables with values
        String evaluatedPostfix = replaceVariables(postfixExpression, a, b, c, d, e);

        // Evaluate the postfix expression
        int result = ResizableArrayStack.evaluatePostfix(evaluatedPostfix);

        // Display result
        System.out.println("Postfix Expression: " + postfixExpression);
        System.out.println("With values substituted: " + evaluatedPostfix);
        System.out.println("Evaluation Result: " + result); // Should yield 33
    }

    // Method to replace variables in the postfix expression with their values
    private static String replaceVariables(String expression, int a, int b, int c, int d, int e) {
        return expression
                .replace("a", Integer.toString(a))
                .replace("b", Integer.toString(b))
                .replace("c", Integer.toString(c))
                .replace("d", Integer.toString(d))
                .replace("e", Integer.toString(e));
    }
}
