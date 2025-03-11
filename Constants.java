import java.util.HashMap;
import java.util.Map;

// /**
//  * Class for constants used across the SRPN calculator.
//  */

public final class Constants {
    // Constants related to the "r" command.
    public static final long[] RANDOM_NUMBERS = new long[]{
        1804289383L, 846930886L, 1681692777L, 1714636915L, 1957747793L, 424238335L, 
        719885386L, 1649760492L, 596516649L, 1189641421L, 1025202362L, 1350490027L, 
        783368690L, 1102520059L, 2044897763L, 1967513926L, 1365180540L, 1540383426L, 
        304089172L, 1303455736L, 35005211L, 521595368L
    };

    // Minimum and maximum values for operand saturation
    public static final long MIN_OPERAND_VALUE = -2147483648L;
    public static final long MAX_OPERAND_VALUE = 2147483647L;
    
    // Constants related to the stack size limits.
    public static final int STACK_LIMIT = 23;
    public static final int MIN_STACK_SIZE = 2;

    public static final String SPACE = " ";
    public static final String COMMENT_REGEX = "#[^#]*#";
    public static final String MULTIPLE_SPACES_REGEX = " +";

    // Operator precedence map
    static final Map<Character, Integer> PRECEDENCE = new HashMap<>();

    static {
        // Set operator precedence values
        PRECEDENCE.put('+', 1);
        PRECEDENCE.put('-', 1);
        PRECEDENCE.put('*', 2);
        PRECEDENCE.put('/', 2);
        PRECEDENCE.put('%', 2);
        PRECEDENCE.put('^', 3);
        PRECEDENCE.put('r', 1);
        PRECEDENCE.put('d', 1);
        PRECEDENCE.put('=', 5);
    }
}
