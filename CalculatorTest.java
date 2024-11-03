public class CalculatorTest
{
    public static void main(String[] args) {
        // Define the infix expression
        String infixExpression = "a*b/(c-a)+d*e";

        // Define variable values
        int a = 2;
        int b = 3;
        int c = 4;
        int d = 5;
        int e = 6;

        // Test the conversion of infix to postfix
        String postfixResult = Calculator.convertInfixToPostfix(infixExpression);

        // Test the evaluation of the postfix expression
        int evaluationResult = Calculator.evaluatePostfix(postfixResult, a, b, c, d, e);

        // Print the results
        System.out.println("\nFinal Results:");
        System.out.println("Infix Expression: " + infixExpression);
        System.out.println("Postfix Expression: " + postfixResult);
        System.out.println("Evaluation Result: " + evaluationResult);
    }
}
