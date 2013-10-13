package ep.opensource.jpa.legacy.persistence.exception;

/**
 * Thrown by the persistence provider when a problem occurs.
 */
public class PersistenceException extends RuntimeException {
    /**
     * Constructs a new {@link PersistenceException} exception with null as its detail message.
     */
    public PersistenceException() {
        super();
    }
    
    /**
     * Constructs a new {@link PersistenceException} exception with the specified detail message.
     * 
     * @param message the detail message
     */
    public PersistenceException(final String message) {
        super(message);
    }
    
    /**
     * Constructs a new {@link PersistenceException} exception with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause
     */
    public PersistenceException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new {@link PersistenceException} exception with the specified cause.
     *  
     * @param cause the cause
     */
    public PersistenceException(final Throwable cause) {
        super(cause);
    }
    
    /**
     * This constant value is used to identify this serializable class.
     */
    private static final long serialVersionUID = 194046360972285738L;
}
