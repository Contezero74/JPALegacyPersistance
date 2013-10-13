package ep.opensource.jpa.legacy.persistence.metadata;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ep.opensource.jpa.legacy.persistence.annotation.Entity;
import ep.opensource.jpa.legacy.persistence.annotation.PostLoad;
import ep.opensource.jpa.legacy.persistence.annotation.Table;

/**
 * This class represents the meta-information of the database table associated to the the entity class. 
 */
public class TableMetadata {
	/**
	 * This method returns the original {@link Class} instance used to generate the current {@link TableMetadata}
	 * instance.
	 * 
	 * @return the original {@link Class} instance used to generate the {@link TableMetadata} information
	 */
    public Class<?> getOriginalClass() {
        return clazz;
    }
    
    /**
     * This method returns the entity-label associated to the original class.
     * 
     * @return the entity-label associated to the original class
     */
    public String getEntityLabel() {
        return (null != entityLabel && !entityLabel.isEmpty()) ? entityLabel : getTableLabel();
    }
    
    /**
     * This method returns the table-label associated to the original class.
     * 
     * @return the table-label associated to the original class
     */
    public String getTableLabel() {
        return (null != tableLabel && !tableLabel.isEmpty()) ? tableLabel : className;
    }
    
    /**
     * This method returns the name of the schema associated to the original class.
     * 
     * @return the name of the schema associated to the original class
     */
    public String getSchemaName() {
        return (null != schemaName) ? schemaName : "";
    }
    
    /**
     * This method returns the fully qualified table-label associated to the current instance of the
     * {@link TableMetadata} class.
     * 
     * @return  the fully qualified table-label associated to the current instance of the {@link TableMetadata} class
     */
    public String getFullyQuilifiedTableLabel() {
        String schema = getSchemaName();
        String table = getTableLabel();
        
        StringBuilder b = new StringBuilder();
        if (!schema.isEmpty()) {
            b.append(schema).append(".");
        }
        b.append(table);
        
        return b.toString();
    }
    
    /**
     * This method invokes the method marked with the annotation {@link PostLoad} of the {@link Class} associated
     * to the current {@link TableMetadata} instance.
     *  
     * @param obj the instance of the {@link Class} associated to the current {@link TableMetadata} instance
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public <T> void executePostLoadCallback(final T obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertNotNull(obj, "obj");
        
        if (null != postLoadMethodCallback) {
            boolean isAccessible = postLoadMethodCallback.isAccessible();
            
            postLoadMethodCallback.setAccessible(true);
            postLoadMethodCallback.invoke(obj);
            postLoadMethodCallback.setAccessible(isAccessible);
        }
    }
    
	/**
     * This constructor creates a new instance of the {@link TableMetadata} class according to the input arguments.
     * 
	 * @param clazz the {@link Class} instance to use to create the {@link TableMetadata} instance
	 */
    TableMetadata(final Class<?> clazz) {
        assertNotNull(clazz, "clazz");
        
        this.clazz = clazz;
        
        className = clazz.getSimpleName();
        
        setEntityClassAttribute(clazz.getAnnotation(Entity.class));
        setTableClassAttribute(clazz.getAnnotation(Table.class));
        
        setPostLoadMethodAttributes(clazz.getDeclaredMethods());
    }
    
    /**
     * This method sets the meta-information related to the {@link Entity} annotation.
     * 
     * @param a the {@link Entity} annotation related to the database entity table
     */
    private void setEntityClassAttribute(final Entity a) {
    	entityLabel = (null != a) ? a.name() : entityLabel;
    }
    
    /**
     * This method sets the meta-information related to the {@link Table} annotation.
     * 
     * @param a the {@link Table} annotation related to the database entity table
     */
    private void setTableClassAttribute(final Table a) {
        if (null != a) {
            tableLabel = a.name();
            schemaName = a.schema();
        }
    }
    
    /**
     * This method sets the method associated to the annotation {@link PostLoad} to invoke.
     * 
     * @param methods an array of {@link Method} instance retrieved from the {@link Class} instance
     * used to generate the current {@link TableMetadata} instance.
     */
    private void setPostLoadMethodAttributes(final Method[] methods) {
        if (null != methods) {
            for (Method method : methods) {
                PostLoad postLoadAnnotation = method.getAnnotation(PostLoad.class);
                if (null != postLoadAnnotation) {
                    postLoadMethodCallback = method;
                    
                    break;
                }
            }
        }
    }
    
    /**
     * This property represents the original {@link Class} instance used to create the current {@link TableMetadata}
     * instance.
     */
    private Class<?> clazz = null;
    
    /**
     * This property represents the name of the {@link Class} instance used to create the current {@link TableMetadata}
     * instance.
     */
    private String className = null;
    
    /**
     * This property represents the entity-label associate to the current {@link TableMetadata} instance.
     */
    private String entityLabel = null;
    
    /**
     * This property represents the table-label associate to the current {@link TableMetadata} instance.
     */
    private String tableLabel = null;
    
    /**
     * This property represents the database schema associate to the current {@link TableMetadata} instance.
     */
    private String schemaName = null;
    
    /**
     * This property represents the method associated to the annotation {@link PostLoad} to invoke.
     */
    private Method postLoadMethodCallback = null;
}