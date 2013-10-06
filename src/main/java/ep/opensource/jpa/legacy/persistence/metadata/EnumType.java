package ep.opensource.jpa.legacy.persistence.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;

import ep.opensource.jpa.legacy.persistence.utility.Utility;

/**
 * Defines mapping for enumerated types. The constants of this enumerated type specify how a persistent property or
 * field of an enumerated type should be persisted.
 */
@SuppressWarnings("rawtypes")
public enum EnumType {
    /**
     * Persist enumerated type property or field as an {@link Integer}.
     */
    ORDINAL {
        /**
         * {@inheritDoc}
         */
        @Override
        Enum from(final ResultSet rs, final String columnLabel, final Class<? extends Enum> enumType) throws SQLException {
        	Utility.assertNotNull(rs, "rs");
        	Utility.assertNotNull(columnLabel, "columnLabel");
        	Utility.assertNotNull(enumType, "enumType");
        	
        	Integer enumOrdinal = rs.getObject(columnLabel, int.class);
        	
            return (null != enumOrdinal) ? (Enum) enumType.getEnumConstants()[enumOrdinal] : null;
        }
        
        /**
         * This method transforms the input value into its numerical representation.
         * 
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        <T, E extends Enum> T to(final E value) {
        	Utility.assertNotNull(value, "value");
        	
            return (T) new Integer(value.ordinal());
        }
    },
    /**
     * Persist enumerated type property or field as a String.
     */
    STRING {
        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        Enum from(final ResultSet rs, final String columnLabel, final Class<? extends Enum> enumType) throws SQLException {
        	Utility.assertNotNull(rs, "rs");
        	Utility.assertNotNull(columnLabel, "columnLabel");
        	Utility.assertNotNull(enumType, "enumType");
        	
        	String enumLabel = rs.getObject(columnLabel, String.class);
        	
            return (null != enumLabel) ? (Enum) Enum.valueOf(enumType, enumLabel) : null;
        }
        
       /**
        * This method transforms the input value into its {@link String} representation.
        * 
        * {@inheritDoc}
        */
       @SuppressWarnings("unchecked")
       @Override
       <T, E extends Enum> T to(final E value) {
    	   Utility.assertNotNull(value, "value");
    	   
           return (T) value.name();
       }
    };
    
    /**
     * This method converts the column specified by the <code>columnLabel</code> {@link String} of the input
     * {@link ResultSet} in the correct {@link Enum} value, if possible. Otherwise it throws an exception.
     * 
     * @param rs the {@link ResultSet} instance containing the column to convert
     * @param columnLabel the {@link String} representing the name of the column to convert
     * @param enumType the goal {@link Enum} class to convert to
     * 
     * @return the {@link Enum} value converted from the {@link ResultSet} column
     * 
     * @throws SQLException
     */
    abstract Enum from(final ResultSet rs, final String columnLabel, final Class<? extends Enum> enumType) throws SQLException;
    
    /**
     * This method converts the input enumeration value instance into the correct mapped value.
     * 
     * @param value the enumeration value instance to convert
     * 
     * @return the correct mapped value.
     */
    abstract <T, E extends Enum> T to(final E value);
}
