package ep.opensource.jpa.legacy.persistence.metadata;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import ep.opensource.jpa.legacy.persistence.annotation.Basic;
import ep.opensource.jpa.legacy.persistence.annotation.Column;
import ep.opensource.jpa.legacy.persistence.annotation.Enumerated;
import ep.opensource.jpa.legacy.persistence.annotation.GeneratedValue;
import ep.opensource.jpa.legacy.persistence.annotation.Id;
import ep.opensource.jpa.legacy.persistence.annotation.Temporal;
import ep.opensource.jpa.legacy.persistence.annotation.Transient;

/**
 * This class represents the meta-information associated to a database column.
 */
public class ColumnMetadata {
    /**
     * This method returns the Java class property name related to the database entity column.
     * 
     * @return the Java class property name
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * This method returns the column-label associated to the database entity column according to the input
     * {@link ColumnDecorator} instance.
     * 
     * @param columnDecorator the {@link ColumnDecorator} to use to return the column-label
     * 
     * @return the column-label associated to the database entity column
     */
    public String getColumnLabel(final ColumnDecorator columnDecorator) {
        assertNotNull(columnDecorator, "columnDecorator");
        
        return columnDecorator.decorate(((null != columnLabel) ? columnLabel : propertyName), this.getTableLabel());
    }
    
    /**
     * This method returns the table-label associated to the database entity.
     * 
     * @return the table-label associated to the database entity
     */
    public String getTableLabel() {
        return tableLabel;
    }
    
    /**
     * This method returns the {@link Class} type of the original Java type associated with the database entity column.
     * 
     * @return the {@link Class} type of the original Java type
     */
    public Class<?> getOriginalType() {
        return originalType;
    }
    
    /**
     * This method returns true if the database entity column is part of the entity primary key. Otherwise it returns
     * false.
     * 
     * @return true if the database entity column is part of the entity primary key, otherwise false
     */
    public boolean isKey() {
        return isKey;
    }
    
    /**
     * This method returns true if the database entity column is auto-generated (e.g., it's an auto-increment attribute).
     * Otherwise it returns false.
     * 
     * @return true if the database entity column is auto-generated, otherwise false
     */
    public boolean isGenerated() {
        return isGenerated;
    }
    
    /**
     * This method returns true if the database entity column can be <code>null</code>. Otherwise it returns false.
     * 
     * @return true if the database entity column can be <code>null</code>, otherwise false
     */
    public boolean isNullable() {
        return !isKey && isNullable;
    }
    
    /**
     * This method returns true if the database entity column can be used in an <code>INSERT</code> SQL statement.
     * Otherwise it returns false.
     * 
     * @return true if the database entity column can be used in an <code>INSERT</code> SQL statement, otherwise false
     */
    public boolean isInsertable() {
        return isInsertable;
    }

    /**
     * This method returns true if the database entity column can be used in an <code>UPDATE</code> SQL statement.
     * Otherwise it returns false.
     * 
     * @return true if the database entity column can be used in an <code>UPDATE</code> SQL statement, otherwise false
     */
    public boolean isUpdatable() {
        return isUpdatable;
    }
    
    /**
     * This method returns true if the entity property is not mapped on the database entity. Otherwise it
     * returns false.
     * 
     * @return true if the database entity column is not mapped on the database entity, otherwise false
     */
    public boolean isTransient() {
        return isTransient;
    }
    
    /**
     * This method returns true if the database entity column is represented as {@link TemporalType}. Otherwise it
     * returns false.
     * 
     * @return true if the database entity column is represented as {@link TemporalType}, otherwise false
     */
    public boolean isTemporalType() {
        return null != temporalType;
    }
    
    /**
     * This method returns the {@link TemporalType} if the column has been annotated as {@link TemporalType}.
     * Otherwise it returns <code>null</code>.
     * 
     * @return the {@link TemporalType} if the column has been annotated as {@link TemporalType}, otherwise <code>null</code>
     */
    public TemporalType getTemporalType() {
        return temporalType;
    }
    
    /**
     * This method returns true if the database entity column is represented as {@link java.lang.Enum} type.
     * Otherwise it returns false.
     * 
     * @return true if the database entity column is represented as {@link java.lang.Enum} type, otherwise false
     */
    public boolean isEnumType() {
        return isEnumerated;
    }
    
    /**
     * This method returns the {@link EnumType} if the column is an {@link java.lang.Enum} instance.
     * Otherwise it returns <code>null</code>.
     * 
     * @return the {@link EnumType} if the column is an {@link java.lang.Enum} instance, otherwise <code>null</code>
     */
    public EnumType getEnumType() {
        return enumType;
    }
    
    /**
     * This method returns the {@link DataTypeConverter} instance associated to the database entity column.
     * 
     * @return the {@link DataTypeConverter} instance associated to the database entity column
     */
    public DataTypeConverter getConverter() {
        return converter;
    }
    
    /**
     * This method retrieves the property value of the input instance of the Java entity according to the information
     * stored in the current instance of the {@link ColumnMetadata}.
     * 
     * @param obj the instance of the Java entity that store the property to retrieve
     * 
     * @return the property value of the input instance of the Java entity
     * 
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public <T, O> O getFieldValue(final T obj) throws IllegalArgumentException, IllegalAccessException {
        assertNotNull(obj, "obj");
        
        O value;
        
        boolean isAccessible = property.isAccessible();
        property.setAccessible(true);
        value = (O) property.get(obj);
        property.setAccessible(isAccessible);
        
        return value;
    }
    
    /**
     * This method sets the the property value of the input instance of the Java entity to the value passed as input.
     * All the conversion are performed according to the information stored in the current instance of the
     * {@link ColumnMetadata}.
     * 
     * @param obj the instance of the Java entity to update with the new value
     * @param value the value to store into the Java entity
     * 
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public <O, V> void setFieldValue(final O obj, final V value) throws IllegalArgumentException, IllegalAccessException {
        assertNotNull(obj, "obj");
        
        boolean isAccessible = property.isAccessible();
        property.setAccessible(true);
        property.set(obj, value);
        property.setAccessible(isAccessible);
    }
    
    /**
     * This method sets the the property value using the input {@link ResultSet} as source.     * 
     * 
     * @param obj the instance of the Java entity to update with the new value
     * @param rs the {@link ResultSet} instance storing the value to set
     * 
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SQLException 
     */
    public <O, T> void setFieldValue(final O obj, final ResultSet rs) throws IllegalArgumentException, IllegalAccessException, SQLException {
        assertNotNull(obj, "obj");
        assertNotNull(rs, "rs");
        
        @SuppressWarnings("unchecked")
		T value = (T) rs.getObject(getColumnLabel(ColumnDecorator.TABLE), getOriginalType());
        
        assertNullability(obj, isNullable(), getPropertyName());
        
        setFieldValue(obj, getConverter().from(value, this));
    }
    
    /**
     * This constructor creates a new instance of the {@link ColumnMetadata} class according to the input arguments.
     * 
     * @param property the Java type property to map on the current {@link ColumnMetadata} instance
     * @param defaultTableLabel the default table-label to use
     */
    ColumnMetadata(final Field property, final String defaultTableLabel) {
        assertNotNull(property, "property");
        assertNotNull(defaultTableLabel, "defaultTableLabel");
        
        this.property = property;
        
        this.propertyName = property.getName();
        this.columnLabel = propertyName;
        this.tableLabel = defaultTableLabel;
        this.originalType = property.getType();
        this.converter = DataTypeConverter.create(originalType);

        setBasicAttributes(property.getAnnotation(Basic.class));
        setColumnAttributes(property.getAnnotation(Column.class));
        setIdAttributes(property.getAnnotation(Id.class));
        setGeneratedValueAttributes(property.getAnnotation(GeneratedValue.class));
        
        isEnum = property.getType().isEnum();
        if (isEnum) {
            this.converter = DataTypeConverter.ENUM;
            setEnumeratedAttributes(property.getAnnotation(Enumerated.class));
        }
        
        setTransientAttributes(property.getAnnotation(Transient.class));
        setTemporalAttributes(property.getAnnotation(Temporal.class));
    }
    
    /**
     * This method sets the meta-information related to the {@link Basic} annotation.
     * 
     * @param a the {@link Basic} annotation related to the database entity column
     */
    private void setBasicAttributes(final Basic a) {
        isNullable = (null != a) ? a.optional() : isNullable;
    }
    
    /**
     * This method sets the meta-information related to the {@link Column} annotation.
     * 
     * @param a the {@link Column} annotation related to the database entity column
     */
    private void setColumnAttributes(final Column a) {
        if (null != a) {
            columnLabel = a.name().isEmpty() ? columnLabel : a.name();
            isInsertable = a.insertable();
            isUpdatable = a.updatable();
            isNullable = a.nullable();
            tableLabel = a.table().isEmpty() ? tableLabel : a.table();
        }
    }
    
    /**
     * This method sets the meta-information related to the {@link Temporal} annotation.
     * 
     * @param a the {@link Temporal} annotation related to the database entity column
     */
    private void setTemporalAttributes(final Temporal a) {
        temporalType = (null != a) ? a.value() : null;
    }
    
    /**
     * This method sets the meta-information related to the {@link Id} annotation.
     * 
     * @param a the {@link Id} annotation related to the database entity column
     */
    private void setIdAttributes(final Id a) {
        isKey = (null != a);
    }
    
    /**
     * This method sets the meta-information related to the {@link GeneratedValue} annotation.
     * 
     * @param a the {@link GeneratedValue} annotation related to the database entity column
     */
    private void setGeneratedValueAttributes(final GeneratedValue a) {
        isGenerated = (null != a);
    }
    
    /**
     * This method sets the meta-information related to the {@link Enumerated} annotation.
     * 
     * @param a the {@link Enumerated} annotation related to the database entity column
     */
    private void setEnumeratedAttributes(final Enumerated a) {
        isEnumerated = (null != a);
        if (isEnumerated) {
            enumType = a.value();
        }
    }
    
    /**
     * This method sets the meta-information related to the {@link Transient} annotation.
     * 
     * @param a the {@link Transient} annotation related to the database entity column
     */
    private void setTransientAttributes(final Transient a) {
        isTransient = (null != a);
    }
    
    /**
     * This property stores the {@link Field} instance of the original database entity property.
     */
    private Field property = null;
    
    /**
     * This property stores the name of the property instance of the original database entity property.
     */
    private String propertyName = null;
    
    /**
     * This property stores the {@link Class} instance representing the type of the property instance of the original
     * database entity property.
     */
    private Class<?> originalType = null;
    
    /**
     * This property stores the column-label of the property instance of the original database entity property.
     */
    private String columnLabel = null;
    
    /**
     * This property stores the table-label of the property instance of the original database entity property.
     */
    private String tableLabel = null;
    
    /**
     * If this property is true the database entity column can be used in the <code>INSERT</code> SQL statements,
     * otherwise not.
     */
    private boolean isInsertable = true;
    
    /**
     * If this property is true the database entity column can be used in the <code>UPDATE</code> SQL statements,
     * otherwise not.
     */
    private boolean isUpdatable = true;
    
    /**
     * If this property is true the database entity column can be <code>null</code>, otherwise not.
     * 
     * Note: this property can be set using both {@link Basic} and {@link Column} annotations. The {@link Column}
     * annotation has the priority over the {@link Basic} one.
     */
    private boolean isNullable = true;
    
    /**
     * If this property is true the database entity column is part of the primary key, otherwise not.
     */
    private boolean isKey = false;
    
    /**
     * If this property is true the database entity column is auto-generated (e.g., auto-increment).
     */
    private boolean isGenerated = false;
    
    /**
     * If this property is true the database entity column is an {@link java.lang.Enum} instance, otherwise not.
     */
    private boolean isEnum = false;
    
    /**
     * If this property is true the database entity column is an {@link java.lang.Enum} instance and the property has
     * been annotated as {@link Enumerated}, otherwise not.
     */
    private boolean isEnumerated = false;
    
    /**
     * If this property is true the entity property is not mapped on the database entity.
     */
    private boolean isTransient = false;
    
    /**
     * This property stores the {@link TemporalType} instance related to the current database entity column if it is
     * annotated as {@link Temporal}. Otherwise it is <code>null</code>. 
     */
    private TemporalType temporalType = null;
    
    /**
     * This property stores the {@link EnumType} instance related to the current database entity column if it is
     * annotated as {@link Enumerated}. Otherwise it is <code>null</code>. 
     */
    private EnumType enumType = null;
    
    /**
     * This property stores the {@link DataTypeConverter} instance related to the current database entity column. 
     */
    private DataTypeConverter converter = null;
    
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
    private static <T> void assertNullability(final T value, final boolean isNullable, final String propertyName) throws IllegalArgumentException {
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