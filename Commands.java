import java.util.Stack;

/**
 * This class contains various methods that handle the commands
 * ("r", "d", "=") for the SRPN calculator.
 */

public class Commands {

   /**
    * Get the next random number from a predefined list.
    */
   public static String getNextRandom(int currentRandomIndex) {
      long number = Constants.RANDOM_NUMBERS[currentRandomIndex];
      return Long.toString(number);
   }

   /**
    * Handle "r" command.
    */
   public static void handleRandom(Stack<String> stack, int currentRandomIndex) {
      // Get the next random number
      String number = getNextRandom(currentRandomIndex);

      // Check if stack is full
      if (stack.size() >= Constants.STACK_LIMIT) {
         Errors.displayStackOverflowError();
      } 
      // Push random number to the stack
      else {
         stack.push(number);
      }
   }

   /**
    * Handle "d" command (prints the content of the stack).
    */
   public static void printStack(Stack<String> stack) {
      // Check if stack is empty
      if (stack.isEmpty()) {
         System.out.println(Constants.MIN_OPERAND_VALUE);
      } 
      // Print each element in the stack
      else {
         for (String item : stack) {
            System.out.println(item); 
            }
      }
   }

   /**
    * Handle "=" command.
    */
   public static void printResult(Stack<String> stack) {
      // Check if stack is empty
      if (stack.isEmpty()) {
         Errors.displayEmptyStackError();
      } 
      // Print the element on top of the stack
      else {
         System.out.println((String)stack.peek());
      }
   }
}
   