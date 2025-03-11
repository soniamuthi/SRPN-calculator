import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class with methods to deal with tokens.
 * Includes methods to identify operands, operators, commands, and
 * ensure saturation of numbers.
 */

public class Token {

   /**
    * Checks if the provided string represents a valid operand.
    * This is a number, including negative numbers.
    */
   public static boolean isOperand(String op) {
      // Expression to check if the string is a number or a negative number
      Pattern pattern = Pattern.compile("^[\\d]|^-[\\d]");
      Matcher matcher = pattern.matcher(op);
      return matcher.find();
   }

   /**
    * Checks if the provided string represents a valid operator.
    */
   public static boolean isOperator(String op) {
      // Expression to check if the string is an operator
      Pattern pattern = Pattern.compile("^[+\\-*/^%]$");
      Matcher matcher = pattern.matcher(op);
      return matcher.matches();
   }

   /**
    * Checks if the provided string represents a valid command.
    */
   public static boolean isCommand(String op) {
      // Expression to check if the string is a command
      Pattern pattern = Pattern.compile("^[dr=]$");
      Matcher matcher = pattern.matcher(op);
      return matcher.find();
   }

   /**
    * Ensures that the result of an operation is within a predefined range.
    * If it is below or above that, adjust the result
    */
   public static long saturateOperand(long result) {
      // Check if the result exceeds the minimum or maximum bounds
      if (result < Constants.MIN_OPERAND_VALUE) {
          return Constants.MIN_OPERAND_VALUE;
      } else if (result > Constants.MAX_OPERAND_VALUE) {
          return Constants.MAX_OPERAND_VALUE; 
      }
  
      return result;
  } 
}
  