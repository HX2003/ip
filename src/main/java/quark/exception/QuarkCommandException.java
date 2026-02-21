package quark.exception;

/**
 * Signals a parsing or execution error of a command
 */
public class QuarkCommandException extends Exception {
    /**
     * Constructs a new command exception with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public QuarkCommandException(String message) {
        super(message);
    }
}
