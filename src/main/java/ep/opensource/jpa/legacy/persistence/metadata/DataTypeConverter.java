package ep.opensource.jpa.legacy.persistence.metadata;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ep.ColumnMetadata;


@SuppressWarnings("unchecked")
public enum DataTypeConverter {
    UNKNOWN,
    BASIC,
    JAVA_DATE {
        @Override
        public <T> T from(final ResultSet rs, final ColumnMetadata c) throws SQLException {
            assertNotNull(rs, "rs");
            assertNotNull(c, "c");
            
            java.util.Date date = null;
            if (!c.isTemporalType()) {
                date = (Date) rs.getObject(c.getColumnLabel(ColumnDecorator.TABLE), c.getOriginalType());
            } else {
                date = c.getTemporalType().from(rs, c.getColumnLabel(ColumnDecorator.TABLE));
            }
            
            assertNullability(date, c.isNullable(), c.getPropertyName());
            
            return (T) date;
        }
        
        @Override
        public <T, O> O to(final T value, final ColumnMetadata c) {
            assertNotNull(value, "value");
            assertNotNull(c, "c");
            
            java.util.Date date = (java.util.Date) value;
            
            return (O) new java.sql.Date(date.getTime());
        }
    },
    JAVA_CALENDAR {
        @Override
        public <T> T from(final ResultSet rs, final ColumnMetadata c) throws SQLException {
            assertNotNull(rs, "rs");
            assertNotNull(c, "c");
            
            Calendar date = null;
            if (!c.isTemporalType()) {
                date = (Calendar) rs.getObject(c.getColumnLabel(ColumnDecorator.TABLE), c.getOriginalType());
            } else {
                date = Calendar.getInstance();
                date.setTime((Date) c.getTemporalType().from(rs, c.getColumnLabel(ColumnDecorator.TABLE)));
            }
            
            assertNullability(date, c.isNullable(), c.getPropertyName());
            
            return (T) date;
        }
        
        @Override
        public <T, O> O to(final T value, final ColumnMetadata c) {
            assertNotNull(value, "value");
            assertNotNull(c, "c");
            
            Calendar date = (Calendar) value;
            
            return (O) new java.sql.Date(date.getTimeInMillis());
        }
    },
    ENUM {
        @Override
        public <T> T from(final ResultSet rs, final ColumnMetadata c) throws SQLException {
            assertNotNull(rs, "rs");
            assertNotNull(c, "c");
            @SuppressWarnings("rawtypes")
            Class<? extends Enum> enumType = (Class<? extends Enum>) c.getOriginalType();
            
            if (!c.isEnumType()) {
                return null;
            }
            
            T result = (T) c.getEnumType().from(rs, c.getColumnLabel(ColumnDecorator.TABLE), enumType);
                
            assertNullability(result, c.isNullable(), c.getPropertyName());
            
            return result;
        }
        
        @Override
        public <T, O> O to(final T value, final ColumnMetadata c) {
            assertNotNull(value, "value");
            assertNotNull(c, "c");
            
            if (!c.isEnumType()) {
                return null;
            }
            
            return (O) c.getEnumType().to((Enum<?>)value);
        }
    };
    
    public <T> T from(final ResultSet rs, final ColumnMetadata c) throws SQLException {
        assertNotNull(rs, "rs");
        assertNotNull(c, "c");
        
        T obj = (T) rs.getObject(c.getColumnLabel(ColumnDecorator.TABLE), c.getOriginalType());
        
        assertNullability(obj, c.isNullable(), c.getPropertyName());
        
        return obj;
    }
    
    public <T, O> O to(final T value, final ColumnMetadata c) {
        assertNotNull(value, "value");
        assertNotNull(c, "c");
        
        return (O) value;
    }
    
    public static DataTypeConverter from(final Class<?> type) {
        DataTypeConverter converter = null;
        if (null != type) {
            converter = CONVERTERS_MAP.get(type.toString());
        }
        
        if (null == converter) {
            converter = type.isPrimitive() ? BASIC : UNKNOWN;
        }
        
        return converter;
    }
    
    private static final Map<String, DataTypeConverter> CONVERTERS_MAP = new HashMap<>();
    
    static {
        CONVERTERS_MAP.put(java.util.Date.class.toString(), JAVA_DATE);
        CONVERTERS_MAP.put(java.util.Calendar.class.toString(), JAVA_CALENDAR);
    }
      
    /**
     * This utility method verifies if the <code>value</code> related to the property <code>propertyName</code>
     * is null or not according to the argument <code>isNullable</code>.
     * 
     * @param value the value to check
     * @param isNullable true if the value can be <code>null</code>, false otherwise
     * @param propertyName the property name associated to the value input
     * 
     * @throws IllegalArgumentException
     */
    static <T> void assertNullability(final T value, final boolean isNullable, final String propertyName) throws IllegalArgumentException {
        if (!isNullable && null == value) {
            throw new IllegalArgumentException(NOT_NULLABLE_PROPERTY + ((null != propertyName) ? propertyName : "unknown"));
        }
    }
    
    /**
     * This constant {@link String} represents the error message to use then a property is <code>null</code>
     * but it shouldn't be. 
     */
    private static final String NOT_NULLABLE_PROPERTY = "the property cannot be null: ";
}