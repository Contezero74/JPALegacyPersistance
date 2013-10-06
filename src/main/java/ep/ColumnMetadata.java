package ep;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;

import java.lang.reflect.Field;

import ep.opensource.jpa.legacy.persistence.annotation.Basic;
import ep.opensource.jpa.legacy.persistence.annotation.Column;
import ep.opensource.jpa.legacy.persistence.annotation.Enumerated;
import ep.opensource.jpa.legacy.persistence.annotation.GeneratedValue;
import ep.opensource.jpa.legacy.persistence.annotation.Id;
import ep.opensource.jpa.legacy.persistence.annotation.Temporal;
import ep.opensource.jpa.legacy.persistence.annotation.Transient;
import ep.opensource.jpa.legacy.persistence.metadata.ColumnDecorator;
import ep.opensource.jpa.legacy.persistence.metadata.DataTypeConverter;
import ep.opensource.jpa.legacy.persistence.metadata.EnumType;
import ep.opensource.jpa.legacy.persistence.metadata.TemporalType;

public class ColumnMetadata {
    public ColumnMetadata(final Field field, final String defaultTable, final String defaultAlias) {
        assertNotNull(field, "field");
        assertNotNull(defaultTable, "defaultTable");
        assertNotNull(defaultAlias, "defaultAlias");
        
        this.field = field;
        
        this.fieldName = field.getName();
        this.columnLabel = fieldName;
        this.tableName = defaultTable;
        this.tableAlias = defaultAlias;
        this.originalType = field.getType();
        this.converter = DataTypeConverter.from(originalType);

        setBasicAttributes(field.getAnnotation(Basic.class));
        setColumnAttributes(field.getAnnotation(Column.class));
        setIdAttributes(field.getAnnotation(Id.class));
        setGeneratedValueAttributes(field.getAnnotation(GeneratedValue.class));
        
        isEnum = field.getType().isEnum();
        if (isEnum) {
            setEnumeratedAttributes(field.getAnnotation(Enumerated.class));
            this.converter = DataTypeConverter.ENUM;
        }
        
        setTransientAttributes(field.getAnnotation(Transient.class));
        setTemporalAttributes(field.getAnnotation(Temporal.class));
    }
    
    public String getPropertyName() {
        return fieldName;
    }
    
    public String getColumnLabel(final ColumnDecorator columnDecorator) {
        return columnDecorator.decorate(((null != columnLabel) ? columnLabel : fieldName), this);
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public String getTableAliasName() {
        return tableAlias;
    }
    
    public Class<?> getOriginalType() {
        return originalType;
    }
    
    public boolean isKey() {
        return isKey;
    }
    
    public boolean isGenerated() {
        return isGenerated;
    }
    
    public boolean isNullable() {
        return isNullable || isOptional;
    }
    
    public boolean isInsertable() {
        return isInsertable;
    }
    
    public boolean isUpdatable() {
        return isUpdatable;
    }
    
    public boolean isTransient() {
        return isTransient;
    }
    
    public boolean isTemporalType() {
        return null != temporalType;
    }
    
    public TemporalType getTemporalType() {
        return temporalType;
    }
    
    public boolean isEnumType() {
        return isEnumerated;
    }
    
    public EnumType getEnumType() {
        return enumType;
    }
    
    public DataTypeConverter getConverter() {
        return converter;
    }
    
    @SuppressWarnings("unchecked")
    public <T, O> O getFieldValue(final T obj) throws IllegalArgumentException, IllegalAccessException {
        O value;
        
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        value = (O) converter.to(field.get(obj), this);
        field.setAccessible(isAccessible);
        
        return value;
    }
    
    public <T, I> void setFieldValue(final T obj, final I value) throws IllegalArgumentException, IllegalAccessException {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(obj, value);
        field.setAccessible(isAccessible);
    }
    
    private void setBasicAttributes(final Basic b) {
        isOptional = (null != b) ? b.optional() : isOptional;
    }
    
    private void setColumnAttributes(final Column c) {
        if (null != c) {
            columnLabel = c.name();
            isInsertable = c.insertable();
            isUpdatable = c.updatable();
            isNullable = c.nullable();
            if (!c.table().isEmpty()) {
                tableName = c.table();
                tableAlias = "";
            }
        }
    }
    
    private void setTemporalAttributes(final Temporal t) {
        temporalType = (null != t) ? t.value() : null;
    }
    
    private void setIdAttributes(final Id i) {
        isKey = (null != i);
    }
    
    private void setGeneratedValueAttributes(final GeneratedValue g) {
        isGenerated = (null != g);
    }
    
    private void setEnumeratedAttributes(final Enumerated e) {
        isEnumerated = (null != e);
        if (isEnumerated) {
            enumType = e.value();
        }
    }
    
    private void setTransientAttributes(final Transient t) {
        isTransient = (null != t);
    }
    
    private Field field = null;
    
    private String fieldName = null;
    String columnLabel = null;
    private Class<?> originalType = null;
    public String tableName = null;
    public String tableAlias = null;
    private boolean isInsertable = true;
    private boolean isUpdatable = true;
    private boolean isNullable = true;
    private boolean isOptional = true;
    private boolean isKey = false;
    private boolean isGenerated = false;
    private boolean isEnumerated = false;
    private boolean isEnum = false;
    private boolean isTransient = false;
    private TemporalType temporalType = null;
    private EnumType enumType = null;
    private DataTypeConverter converter = null;
}