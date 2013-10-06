package ep.opensource.jpa.legacy.persistence.utility;

import java.util.Collection;

/**
 * This utility class expose a set of static method used in all the project.
 * For every method present in this class there is a better implementation, but the idea is to maintain this
 * implementation as more as possible auto-included.
 */
public class Utility {
	/**
	 * This static method returns a {@link String} representing the the concatenation of the input
	 * {@link Collection}.
	 * 
	 * @param collection the {@link Collection} to concatenated into a {@link String}
	 * 
	 * @return a {@link String} representing the the concatenation of the input {@link Collection}
	 */
    public static String join(final Collection<?> collection) {
        return join(collection, "");
    }
    
    /**
	 * This static method returns a {@link String} representing the the concatenation of the input
	 * {@link Collection} using the separator provided.
	 * 
	 * @param collection the {@link Collection} to concatenated into a {@link String}
	 * @param separator the {@link String} representing the separator used to concatenate the items
	 * of the input {@link Collection}
	 * 
	 * @return a {@link String} representing the the concatenation of the input {@link Collection} using the separator provided
	 */
    public static <T> String join(final Collection<T> collection, final String separator) {
        assertNotNull(collection, "collection");
        assertNotNull(separator, "separator");
        
        StringBuilder b = new StringBuilder();
        
        boolean isFirst = true;
        for (T obj : collection) {
            if (!isFirst) {
                b.append(separator);
            } else {
                isFirst = false;
            }
            
            b.append(obj.toString());
        }
        
        return b.toString();
    }
    
    /**
     * This method verifies if the input argument is <code>null</code> or not.
     * If it's <code>null</code> an {@link IllegalArgumentException} is thrown.
     * 
     * @param value the value to check
     * @param param the name of the attribute storing the value to check
     * 
     * @throws IllegalArgumentException
     */
    public static <T> void assertNotNull(final T value, final String param) throws IllegalArgumentException {
        if (null == value) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MSG + ((null != param) ? param : "unknown"));
        }
    }
    
    public static final String SQL_SELECT = "SELECT ";
    public static final String SQL_UPDATE = "UPDATE ";
    public static final String SQL_INSERT = "INSERT INTO ";
    public static final String SQL_VALUES = " VALUES ";
    public static final String SQL_DELETE = "DELETE ";
    public static final String SQL_FROM = " FROM ";
    public static final String SQL_JOIN = " JOIN ";
    public static final String SQL_ON = " ON ";
    public static final String SQL_SET = " SET ";
    public static final String SQL_WHERE = " WHERE ";
    public static final String SQL_AND = " AND ";
    public static final String SQL_COMMA = ", ";
    public static final String SQL_SEPARATOR = ".";
    public static final String SQL_EQUAL_PARAM = "=?";
    public static final String SQL_PARAM = "?";
    public static final String SQL_EQUAL = "=";
    public static final String SQL_OPEN_BREAK = "(";
    public static final String SQL_CLOSE_BREAK = ")";
    
    /***
     * This constant {@link String} represents the error message to use then an {@link IllegalArgumentException}
     * is thrown because the assert on <code>null</code> value check fails. 
     */
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MSG = "the paramter cannot be null: ";
}
