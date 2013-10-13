package ep.opensource.jpa.legacy.persistence.metadata;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * This helper is designed to support specific conversion from Java primitive types and classes to database values
 * (and vice-versa).
 */
@SuppressWarnings("unchecked")
public enum DataTypeConverter {
    /**
     * This helper represents all the classes that are not specialized.
     */
    UNKNOWN,
    
    /**
     * This helper represents all the Java primitive types.
     */
    BASIC,
    
    /**
     * This helper represents the java {@link java.util.Date} type. 
     */
    JAVA_DATE {
        /**
         * {@inheritDoc}
         */
        @Override
        <T, O> O from(final T value, final ColumnMetadata c) throws SQLException {
            assertNotNull(value, "value");
            assertNotNull(c, "c");
            
            Date date = null;
            if (!c.isTemporalType()) {
                date = (Date) value;
            } else {
                date = c.getTemporalType().from(rs, c.getColumnLabel(ColumnDecorator.TABLE));
            }
            
            return (T) date;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        <T, O> O to(final T value, final ColumnMetadata c) {
            assertNotNull(value, "value");
            assertNotNull(c, "c");
            
            Date date = (Date) value;
            
            return (O) new java.sql.Date(date.getTime());
        }
    },
    
    /**
     * This helper represents the java {@link java.util.Calendar} type. 
     */
    JAVA_CALENDAR {
        /**
         * {@inheritDoc}
         */
        @Override
        <T> T from(final ResultSet rs, final ColumnMetadata c) throws SQLException {
            assertNotNull(rs, "rs");
            assertNotNull(c, "c");
            
            Calendar date = null;
            if (!c.isTemporalType()) {
                date = (Calendar) rs.getObject(c.getColumnLabel(ColumnDecorator.TABLE), c.getOriginalType());
            } else {
                date = Calendar.getInstance();
                date.setTime((Date) c.getTemporalType().from(rs, c.getColumnLabel(ColumnDecorator.TABLE)));
            }
            
            return (T) date;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        <T, O> O to(final T value, final ColumnMetadata c) {
            assertNotNull(value, "value");
            assertNotNull(c, "c");
            
            Calendar date = (Calendar) value;
            
            return (O) new java.sql.Date(date.getTimeInMillis());
        }
    },
    
    /**
     * This helper represents the java {@link java.lang.Enum} type and derived classes. 
     */
    ENUM {
        /**
         * {@inheritDoc}
         */
        @Override
        <T> T from(final ResultSet rs, final ColumnMetadata c) throws SQLException {
            assertNotNull(rs, "rs");
            assertNotNull(c, "c");
            @SuppressWarnings("rawtypes")
            Class<? extends Enum> enumType = (Class<? extends Enum>) c.getOriginalType();
            
            if (!c.isEnumType()) {
                return null;
            }
            
            T result = (T) c.getEnumType().from(rs, c.getColumnLabel(ColumnDecorator.TABLE), enumType);
            
            return result;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        <T, O> O to(final T value, final ColumnMetadata c) {
            assertNotNull(value, "value");
            assertNotNull(c, "c");
            
            if (!c.isEnumType()) {
                return null;
            }
            
            return (O) c.getEnumType().to((Enum<?>)value);
        }
    };
    
    /**
     * This method converts the input attribute (retrieved via {@link ResultSet}) into the correct Java type.
     * 
     * @param rs the {@link ResultSet} instance storing the attribute to convert
     * @param c the {@link ColumnMetadata} instance representing the database column to use to extract the value to
     * convert from the {@link ResultSet} instance.
     * 
     * @return the converted Java type according to the the mata-information stored in the {@link ColumnMetadata} instance
     * 
     * @throws SQLException
     */
    <T, O> O from(final T value, final ColumnMetadata c) throws SQLException {
        assertNotNull(value, "value");
        assertNotNull(c, "c");
        
        return (O) value;
    }
    
    /**
     * This method converts the input generic value into the correct database type.
     * 
     * @param value the generic value to convert
     * @param c the {@link ColumnMetadata} instance storing the meta-information needed to convert from the Java value
     * into its database representation.
     * 
     * @return the converted database version of the input value
     */
    <T, O> O to(final T value, final ColumnMetadata c) {
        assertNotNull(value, "value");
        assertNotNull(c, "c");
        
        return (O) value;
    }
    
    /**
     * This factory method creates the correct {@link DataTypeConverter} instance starting from the input Java type.
     * 
     * @param type the Java type to use in order to create the {@link DataTypeConverter}
     * 
     * @return the correct {@link DataTypeConverter} instance starting from the input Java type
     */
    static DataTypeConverter create(final Class<?> type) {
        DataTypeConverter converter = null;
        if (null != type) {
            converter = CONVERTERS_MAP.get(type.toString());
        }
        
        if (null == converter) {
            converter = type.isPrimitive() ? BASIC : UNKNOWN;
        }
        
        return converter;
    }
    
    /**
     * This {@link Map} stores the specific conversion from the Java type to the {@link DataTypeConverter} instances.
     */
    private static final Map<String, DataTypeConverter> CONVERTERS_MAP = new HashMap<>();
    
    /**
     * This configuration setups the {@link DataTypeConverter#CONVERTERS_MAP} map.
     */
    static {
        CONVERTERS_MAP.put(Date.class.toString(), JAVA_DATE);
        CONVERTERS_MAP.put(Calendar.class.toString(), JAVA_CALENDAR);
    }
}