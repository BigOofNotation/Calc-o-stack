class LinkedStackDemo
{
    public static void main(String[] args)
    {
        LinkedStack<Character> stack = new LinkedStack<>();
        String infixExpression = "a / b * (c + (d - e))"; // Updated infix expression
        String postfixExpression = stack.convertToPostfix(infixExpression);
        System.out.println("Final Postfix Expression: " + postfixExpression);
    }
}
