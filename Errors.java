// /**
//  * Class for displaying errors in the SRPN calculator.
//  * This class provides static methods to display various errors that may occur during 
//  * the calculation process.
//  */


public final class Errors {

    private Errors() {
    }
 
    public static void displayEmptyStackError() {
       System.out.println("Stack empty.");
    }
 
    public static void displayStackOverflowError() {
       System.out.println("Stack overflow.");
    }
 
    public static void displayStackUnderflowError() {
       System.out.println("Stack underflow.");
    }
 
    public static void displayDivideByZeroError() {
       System.out.println("Divide by 0.");
    }

    public static void displayModuloByZeroError() {
      System.out.println("Floating point exception (core dumped)");
   }
 
    public static void displayNegativePowerError() {
       System.out.println("Negative power.");
    }
 
    public static void displayInvalidOperatorError(String token) {
       System.out.println("Unrecognised operator or operand \"" + token + "\".");
    }
 }