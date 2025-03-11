import java.util.*;

/**
 * Class for processing and cleaning input from the console.
 * This class is responsible for cleaning the input to ensure it is properly formatted
 * and ready for further parsing. 
 */

public class alterInput {

    /**
     * Method to alter the console input.
     */
    public static String alterConsoleInput(String input) {

        // Handle null input
        if (input == null) {
            return ""; 
        }

        // Trim input
        String alteredInput = input.trim(); 

        // Remove comments
        alteredInput = removeComments(alteredInput);

        // Remove extra spaces
        alteredInput = removeMultipleSpaces(alteredInput);

        // Deal with merged characters
        // If it is empty, a command or a number by itself we don't need to unmerge
        if (!alteredInput.isEmpty() && !Token.isCommand(alteredInput) && !Token.isOperator(alteredInput)) {
            alteredInput = unmergeInput(alteredInput);
        }

        return alteredInput;
    }

    /**
     * Method to remove comments.
     */
    private static String removeComments(String input) {
        return input.replaceAll(Constants.COMMENT_REGEX, "");
    }

    /**
     * Method to remove extra spaces.
     */
    private static String removeMultipleSpaces(String input) {
        return input.replaceAll(Constants.MULTIPLE_SPACES_REGEX, Constants.SPACE);
    }

    /**
     * Method to break down merged expressions into separate operands and operators.
     * It handles expressions like "2+2*3" or "4-3 4+4" within a single input line.
     */
    private static String unmergeInput(String input) {
        // Initialize a StringBuilder to build the processed result
        StringBuilder result = new StringBuilder();

        // Split the input into individual tokens based on spaces
        String[] tokens = input.split(Constants.SPACE);
        boolean isExpressionStarted = false;
        int tokenCount = 0;

        for (String token : tokens) {
            tokenCount++;
            // If the token is a standalone number, operator or command (e.g., "2", "+", or "3"), append it directly
            // if (isNumberOrSingleOperator(token, tokenCount, tokens.length, input)) {
            if (isValidStandaloneToken(token, tokenCount, tokens.length, input)) {
                result.append(token).append(Constants.SPACE);
            } 
            // If it is an expression (e.g., 2+2*3), process it
            else {
                result.append(processTokenForExpression(token, isExpressionStarted)).append(Constants.SPACE);
                isExpressionStarted = true;
            }
        }

        return result.toString().trim();
    }

    /**
     * Method to check if the token is not an expression (e.g., "1+2").
     * Check if it is a standalone number, operator or command.
     */
    private static boolean isValidStandaloneToken(String token, int tokenCount, int totalTokens, String input) {
        return (Token.isOperator(token) || 
               ((Token.isOperand(token) || (Token.isCommand(token))) && tokenCount == totalTokens && input.length() == 1));
    }

    /**
     * Method to process a token that is an expression (e.g., "1+2").
     */
    private static String processTokenForExpression(String token, boolean isExpressionStarted) {

        if (!isExpressionStarted && ! Token.isOperator(token)){
            // Explicitly set expression started to true if token is non-numeric
            isExpressionStarted = true;
        }

        // If it is an expression, actual process it
        String processedToken = isExpressionStarted ? processExpression(token) : token;

        return processedToken;
    }

    /**
     * Processes an expression by separating numbers and operators/commands,
     * handling negative numbers, and sorting operators/commands by precedence.
     */
    private static String processExpression(String expression) {
        // StringBuilder to hold numbers and operators separately
        StringBuilder numbers = new StringBuilder();
        StringBuilder operators = new StringBuilder();

        // StringBuilder to build the current number as we iterate through the expression
        StringBuilder currentNumber = new StringBuilder();

        // If the first character of the expression is a '-', handle it as part of the number
        if (expression.charAt(0) == '-') {
            currentNumber.append('-');
            // Remove the negative sign for further processing
            expression = expression.substring(1); 
        }

        // Iterate over the characters of the expression
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the character is a digit, append it to the current number being built
            if (Character.isDigit(c)) {
                currentNumber.append(c);
            }

            // If the character is an operator or command add it to operators
            else if (isOperatorOrCommand(c)) {
                // If we have a number already built, append it to the numbers list
                appendIfNotEmpty(numbers, currentNumber);
                // Append the operator to the operators list with a space for separation
                operators.append(c).append(Constants.SPACE);
                // Reset the current number as we now expect the next number
                currentNumber.setLength(0);
            } 
            // If the character is not recognized throw an error message a
            else {
                Errors.displayInvalidOperatorError(c + "");
                // Append the previously formed number
                // e.g., if we have "123*" and we reach this else because of "*"
                // "123" is still a valid number
                appendIfNotEmpty(numbers, currentNumber);
            }
        }

        // If there's a leftover number at the end of the expression, append it
        appendIfNotEmpty(numbers, currentNumber);

        // Sort operators/commands based on their precedence
        String sortedOperators = sortByPrecedence(operators.toString());

        // Return the result: numbers followed by sorted operators/commands
        return numbers.toString() + sortedOperators;
    }

    /**
     * Appends the content of the current number to the numbers StringBuilder if it is not empty.
     * Then, clears the current number to prepare for the next one.
     */
    private static void appendIfNotEmpty(StringBuilder numbers, StringBuilder currentNumber) {
         // Check if the current number has any digits in it
        if (currentNumber.length() > 0) {
            // Append the current number to the numbers StringBuilder 
            numbers.append(currentNumber.toString()).append(Constants.SPACE);
            // Clear the current number for the next number
            currentNumber.setLength(0);
        }
    }

    /**
     * Sorts operators/commands based on their precedence, with higher-precedence operators coming first.
     * It removes spaces from the input string, creates a list of operators, and sorts them
     */
    private static String sortByPrecedence(String operators) {
        // Remove spaces from the input operators string
        operators = operators.replaceAll(Constants.SPACE, "");
    
        // Create a list to hold each individual operator
        List<Character> operatorList = new ArrayList<>();
    
        // Add each character to the list
        for (char c : operators.toCharArray()) {
            operatorList.add(c);
        }
    
        // Sort the operators based on their precedence
        operatorList.sort((a, b) -> Integer.compare(Constants.PRECEDENCE.get(b), Constants.PRECEDENCE.get(a)));
    
        // Build the sorted operators string, appending each operator followed by a space
        StringBuilder sortedOperators = new StringBuilder();
        for (char operator : operatorList) {
            sortedOperators.append(operator).append(Constants.SPACE);
        }
    
        // Return the final sorted string of operators, removing the trailing space
        return sortedOperators.toString().trim();
    }
    
    /**
     * Method to check if a char c is an operator or command
     */
    private static boolean isOperatorOrCommand(char c) {
        return Constants.PRECEDENCE.containsKey(c);
    }

}
