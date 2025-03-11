import java.util.Stack;

/**
 * Class for the SRPN calculator.
 */

public class SRPN {

    // Index used to go through the array of random numbers
    private static int currentRandomIndex = 0;

    // Declare the Stack for operands
    private Stack<String> stack;

    // Constructor to instantiate the Stack.
    public SRPN() {
        stack = new Stack<>();
    }

    /**
     * Execute a command based on the provided string.
     * This method handles commands such as 'r' (random number), 'd' (print stack), and '=' (print top result).
     */
    private void executeCommand(String command) {
        switch (command) {
            case "r":
                // Handle the "r" command
                Commands.handleRandom(stack, currentRandomIndex);
                // Update the random index after the command is processed
                currentRandomIndex = (currentRandomIndex + 1) % Constants.RANDOM_NUMBERS.length;
                break;
            case "d":
                // Handle the "d" command
                Commands.printStack(stack);
                break;
            case "=":
                // Handle the "=" command
                Commands.printResult(stack);
                break;
            default:
                break;
        }
    }

    /**
     * Take two operands from the stack and perform the operation with the provided operator.
     */
    private String performOperation(String operator) {
        // Check if there are enough operands in the stack
        if (stack.size() < Constants.MIN_STACK_SIZE) {
            Errors.displayStackUnderflowError();
            return null;
        }
    
        // Pop the two operands from the stack and ensure they are saturated
        long b = Token.saturateOperand(Long.parseLong(stack.pop()));
        long a = Token.saturateOperand(Long.parseLong(stack.pop()));

        // Apply the operation
        long result = applyOperation(a, b, operator);
        
        // Return the result and ensure it is saturated
        return Long.toString(Token.saturateOperand(result));
    }

    /**
     * Apply the specified arithmetic operation to two operands.
     */
    private long applyOperation(long a, long b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    Errors.displayDivideByZeroError();
                    // Push the first operand back in case of division by zero
                    stack.push(String.valueOf(a));
                    return Long.parseLong(String.valueOf(b));
                }
                return a / b;
            case "%":
                if (b == 0) {
                    Errors.displayModuloByZeroError();
                    System.exit(1);
                }
                return a % b;
            case "^":
                if (b < 0) {
                    Errors.displayNegativePowerError();
                    stack.push(String.valueOf(a));
                    return Long.parseLong(String.valueOf(b));
                }
                return (long) Math.pow(a, b);
            default:
                return 0;
        }
    }
    
    /**
     * Parses the given console input string by cleaning and preprocessing it.
     * 
     * This is needed because:
     * The input might contain unwanted characters but could still pass as an expression/operand/operator/command.
     * A one liner input can contain multiple expressions that need to be handled in the correct order.
     * E.g., "3a", "1 2 + # And so i s t h i s #", "3+4 2-4"
     */
    public static String[] parseConsoleInput(String consoleInput) {
        // Clean and preprocess the console input
        String cleanedInput = alterInput.alterConsoleInput(consoleInput.toLowerCase());
        return cleanedInput.split("\\s+");
    }

    /**
     * Evaluates an expression given as an array of tokens, which may include operands, operators, commands or subexpressions (multi-part tokens).
     * The method processes each token sequentially, breaking down multi-part tokens (e.g., "-4 1 +") and performing operations accordingly.
     * Operands are pushed onto a stack, operators trigger arithmetic operations,
     * and commands are executed as they are encountered.
     */
    public Long evaluateExpression(String[] tokens) {
        for (String token : tokens) {
            // If the token contains multiple expressions (e.g., "-4 1 +"), split it into subTokens for individual processing
            if (token.contains(" ")) {
                String[] subTokens = token.split(" ");
                for (String subToken : subTokens) {
                    // If the subToken is an operand, push it onto the stack
                    if (Token.isOperand(subToken)) {
                        // Check if stack is full
                        if (stack.size() >= Constants.STACK_LIMIT) {
                            Errors.displayStackOverflowError();
                        }
                        else {
                        // Is stack is not full, push
                            stack.push(subToken);
                        }
                    }
                    // If the subToken is an operator, perform the arithmetic operation
                    else if (Token.isOperator(subToken)) {
                        String result = performOperation(subToken);
                        // Check if result is not null
                        if (result != null){
                            // Check if stack is full
                            if (stack.size() >= Constants.STACK_LIMIT) {
                                Errors.displayStackOverflowError();
                            }
                            else {
                            // Is stack is not full, push
                                stack.push(result);
                            }
                        }
                    }
                    // If the subToken is a command, execute it
                    else if (Token.isCommand(subToken)) {
                        executeCommand(subToken);
                    }
                }
            } else {
                // If the token is just a single element (operand, operator, command), handle it normally
                // If the token is an operand, push it onto the stack
                if (Token.isOperand(token)) {
                    // Check if stack is full
                    if (stack.size() >= Constants.STACK_LIMIT) {
                        Errors.displayStackOverflowError();
                    }
                    else {
                    // Is stack is not full, push
                        stack.push(token);
                    }
                } 
                // If the token is an operator, perform the arithmetic operation
                else if (Token.isOperator(token)) {
                    String result = performOperation(token);
                    // Check if result is not null
                    if (result != null){
                        // Check if stack is full
                        if (stack.size() >= Constants.STACK_LIMIT) {
                            Errors.displayStackOverflowError();
                        }
                        else {
                        // Is stack is not full, push
                            stack.push(result);
                        }
                    }
                } 
                // If the token is a command, execute it
                else if (Token.isCommand(token)) {
                    executeCommand(token);
                }
            }
        }
    
        // After processing all tokens, return the result if the stack is not empty
        if (stack.isEmpty()) {
            return null;
        }
        return Long.parseLong(stack.peek());
    }

    /**
     * Processes the console input by parsing it and forwarding the tokens for evaluation.
     * Returns the final result of the evaluated expression.
     */
    public Long processCommand(String consoleInput) {
        // Do nothing if the input string is empty
        if (consoleInput.equals("")) {
            return null;
        }
        // Parse the string from the console into tokens
        String[] tokens = parseConsoleInput(consoleInput);

        // Evaluate the expression
        return evaluateExpression(tokens);
    }
}





    




