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
        System.out.println("Postfix Expression: " + postfixResult + "\n");

        // Test the evaluation of the postfix expression
        int evaluationResult = Calculator.evaluatePostfix(postfixResult, a, b, c, d, e);
        System.out.println("Postfix Expression: " + evaluationResult + "\n");

        // Print the results
        System.out.println("\nFinal Results:");
        System.out.println("Infix Expression: " + infixExpression);
        System.out.println("Postfix Expression: " + postfixResult);
        System.out.println("Evaluation Result: " + evaluationResult);

        //test cases
        System.out.println("\n\nTest Cases\n");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("testing precedence: 5+4-3*2^1");
        String test1 = "5+4-3*2^1";
        System.out.println("Postfix should be 54+321^*- and result should be 3\n");
        String test1post = Calculator.convertInfixToPostfix(test1);
        System.out.println("Postfix Expression: " + test1post + "\n");
        int test1val = Calculator.evaluatePostfix(test1post);
        System.out.println("Evaluation Result: " + test1val);

        System.out.println("\n\ntesting parentheses: 1+{2-[8/(2^3)]}");
        String test2 = "1+{2-[8/(2^3)]}";
        System.out.println("Postfix should be 12823^/-+ and result should be 2\n");
        String test2post = Calculator.convertInfixToPostfix(test2);
        System.out.println("Postfix Expression: " + test2post + "\n");
        int test2val = Calculator.evaluatePostfix(test2post);
        System.out.println("Evaluation Result: " + test2val);

        System.out.println("\n\ntesting parenthesis error: (1+1)}");
        String test3 = "(1+1)}";
        System.out.println("(1+1)} throws the exception: ");
        try {
            Calculator.convertInfixToPostfix(test3);
        } catch (RuntimeException invalid) {
            System.out.println("Error: " + invalid.getMessage());
        }

        System.out.println("\ntesting for invalid items: (x+3)");
        String test4 = "(x+3)";
        System.out.println("(x+3) throws the exception: ");
        try {
            Calculator.convertInfixToPostfix(test4);
        } catch (RuntimeException invalid) {
            System.out.println("Error: " + invalid.getMessage());
        }

        System.out.println("\ntesting for excessive operators: (3++2-1)");
        String test5 = "(3++2-1)";
        System.out.println("(3++2-1) throws the exception: ");
        try {
            Calculator.convertInfixToPostfix(test5);
        } catch (RuntimeException invalid) {
            System.out.println("Error: " + invalid.getMessage());
        }

        System.out.println("\ntesting with random input: 2;3 @@9 8i");
        String test6 = "2;3 @@9 8i";
        System.out.println("2;3 @@9 8i throws the exception: ");
        try {
            Calculator.convertInfixToPostfix(test6);
        } catch (RuntimeException invalid) {
            System.out.println("Error: " + invalid.getMessage());
        }

    }
}
