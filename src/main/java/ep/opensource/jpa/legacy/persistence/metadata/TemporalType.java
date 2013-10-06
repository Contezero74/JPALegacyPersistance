package ep.opensource.jpa.legacy.persistence.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;

import ep.opensource.jpa.legacy.persistence.utility.Utility;

/**
 * Type used to indicate a specific mapping of {@link java.util.Date} or {@link java.util.Calendar}.
 */
public enum TemporalType {
    /**
     * Map as {@link java.sql.Date}.
     */
    DATE {
        /**
         * {@inheritDoc}
         */
    	@SuppressWarnings("unchecked")
        @Override
        <T> T from(final ResultSet rs, final String columnLabel) throws SQLException {
        	Utility.assertNotNull(rs, "rs");
        	Utility.assertNotNull(columnLabel, "columnLabel");
        	
            return (T) rs.getDate(columnLabel);
        }
    },
    /**
     * Map as {@link java.sql.Time}.
     */
    TIME  {
        /**
         * {@inheritDoc}
         */
    	@SuppressWarnings("unchecked")
		@Override
        <T> T from(final ResultSet rs, final String columnLabel) throws SQLException {
        	Utility.assertNotNull(rs, "rs");
        	Utility.assertNotNull(columnLabel, "columnLabel");
        	
            return (T) rs.getTime(columnLabel);
        }
    },
    /**
     * Map as {@link java.sql.Timestamp}.
     */
    TIMESTAMP {
        /**
         * {@inheritDoc}
         */
    	@SuppressWarnings("unchecked")
        @Override
        <T> T from(final ResultSet rs, final String columnLabel) throws SQLException {
        	Utility.assertNotNull(rs, "rs");
        	Utility.assertNotNull(columnLabel, "columnLabel");
        	
            return (T) rs.getTimestamp(columnLabel);
        }
    };
    
    /**
     * This method converts the column specified by the <code>columnLabel</code> {@link String} of the input
     * {@link ResultSet} in the correct <code>java.sql</code> date/time value, if possible. Otherwise it throws an
     * exception.
     * 
     * @param rs the {@link ResultSet} instance containing the column to convert
     * @param columnLabel the {@link String} representing the name of the column to convert
     * 
     * @return the correct <code>java.sql</code> date/time value
     * 
     * @throws SQLException
     */
    abstract <T> T from(final ResultSet rs, final String columnLabel) throws SQLException;
}
