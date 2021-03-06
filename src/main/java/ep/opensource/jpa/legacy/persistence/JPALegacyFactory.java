package ep.opensource.jpa.legacy.persistence;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ep.opensource.jpa.legacy.persistence.metadata.AllColumnsGeneratedKeysFlag;
import ep.opensource.jpa.legacy.persistence.metadata.ColumnDecorator;
import ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata;
import ep.opensource.jpa.legacy.persistence.metadata.EntityMetadata;
import ep.opensource.jpa.legacy.persistence.utility.Utility;

/**
 * This factory represents the main entry point to work JPA Legacy Persistence classes.
 * Using this class you can:
 * <ul>
 * 	<li>create {@link EntityMetadata} instance representing yout {@link Class} POJO;</li>
 * 	<li>map a {@link ResultSet} into a concrete object instance;</li>
 * 	<li>retrieve the parameters to use with JDBC queries;</li>
 * 	<li>generate the basic SQL queries for your POJO.</li>
 * </ul>
 */
public class JPALegacyFactory {
	/**
	 * This factory method creates an {@link EntityMetadata} instance based on the input {@link Class}.
	 * 
	 * @param clazz the input {@link Class} to use to create the {@link EntityMetadata} instance
	 * 
	 * @return an {@link EntityMetadata} instance based on the input {@link Class}
	 */
    public static EntityMetadata createEntityMetadata(final Class<?> clazz) {
        return EntityMetadata.createEntityMetadata(clazz);
    }
    
    /**
     * This method maps the input {@link ResultSet} into a concrete object instance using the information
     * stored in the {@link EntityMetadata} instance.
     * 
     * @param rs the {@link ResultSet} instance to map
     * @param entity the {@link EntityMetadata} instance representing the object to map in
     * 
     * @return a concrete object instance from the input {@link ResultSet}
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> T from(final ResultSet rs, final EntityMetadata entity) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        @SuppressWarnings("unchecked")
        Constructor<T> ctor = (Constructor<T>) entity.getTableMetadata().getOriginalClass().getDeclaredConstructor();
        
        boolean isCtorAccessible = ctor.isAccessible();
        ctor.setAccessible(true);
        T obj = ctor.newInstance();
        ctor.setAccessible(isCtorAccessible);
        
        for (ColumnMetadata columnMetadata : entity.getAllColumnsMetadata(AllColumnsGeneratedKeysFlag.WITH_GENERATED).values()) {
            try {
                columnMetadata.setFieldValue(obj, rs);
            } catch (SecurityException | IllegalArgumentException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return obj;
    }
    
    /**
     * This method returns an array of {@link Object}s retrieved from the input <code>obj</code> parameter and
     * its {@link EntityMetadata} representation. The set of values returned depend from the list of {@link AttributeType}s
     * used.
     * 
     * @param obj the instance of the {@link Object} of type T to use to retrieve the parameters
     * @param entity the {@link EntityMetadata} instance representing the {@link Object} of type T
     * @param types one or more {@link AttributeType} values representing the order of properties to retrieve
     * 
     * @return an array of {@link Object}s retrieved from the input <code>obj</code>
     */
    public static <T> Object[] getParamFrom(final T obj, final EntityMetadata entity, final AttributeType ... types) {
        List<Object> params = new ArrayList<>();
        
        for (AttributeType pt : types) {
            for (ColumnMetadata columnMetadata : entity.getAllColumnsMetadata(AllColumnsGeneratedKeysFlag.WITH_GENERATED).values()) {
                if (pt.isToRetrieve(columnMetadata)) {
                    try {
                        params.add(columnMetadata.getFieldValue(obj));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return params.toArray(new Object[params.size()]);
    }
    
    /**
     * This method returns the SQL query used to insert a DAO into the repository.
     * 
     * @return the insert SQL query 
     */
    public static String getInsertSql(final EntityMetadata entity) {
        final String tableName = entity.getTableMetadata().getTableLabel();
        
        List<String> insertableColumns = new ArrayList<>();
        List<String> insertableParams = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getAllColumnsMetadata(AllColumnsGeneratedKeysFlag.WITHOUT_GENERATED).values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.NONE);
            
            if ( columnMetadata.isInsertable() && columnMetadata.getTableLabel().equals(tableName) ) {                    
                insertableColumns.add(columnName);
                insertableParams.add(Utility.SQL_PARAM);
            }
        }
        
        return new StringBuilder(Utility.SQL_INSERT).append(entity.getTableMetadata().getTableLabel())
                                                    .append(Utility.SQL_OPEN_BREAK)
                                                    .append(Utility.join(insertableColumns, Utility.SQL_COMMA))
                                                    .append(Utility.SQL_CLOSE_BREAK)
                                                    .append(Utility.SQL_VALUES)
                                                    .append(Utility.SQL_OPEN_BREAK)
                                                    .append(Utility.join(insertableParams, Utility.SQL_COMMA))
                                                    .append(Utility.SQL_CLOSE_BREAK)
                                                    .toString();
    }
    
    /**
     * This method returns the SQL query used to update a DAO into the repository.
     * 
     * @return the update SQL query 
     */
    public static String getUpdateSql(final EntityMetadata entity) {
        final String tableName = entity.getTableMetadata().getTableLabel();
        
        List<String> updatableParams = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getOtherColumnsMetadata().values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.TABLE);
            
            if ( columnMetadata.isUpdatable() && columnMetadata.getTableLabel().equals(tableName) ) {                    
                updatableParams.add(columnName + Utility.SQL_EQUAL_PARAM);
            }
        }

        List<String> keyParam = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getKeyColumnsMetadata().values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.TABLE);
            
            keyParam.add(columnName + Utility.SQL_EQUAL_PARAM);
        }    
       
        return new StringBuilder(Utility.SQL_UPDATE).append(entity.getTableMetadata().getTableLabel())
                                                                  .append(Utility.SQL_SET)
                                                                  .append(Utility.join(updatableParams, Utility.SQL_COMMA))
                                                                  .append(Utility.SQL_WHERE)
                                                                  .append(Utility.join(keyParam, Utility.SQL_COMMA))
                                                                  .toString();
    }
    
    /**
     * This method returns the SQL query used to retrieve all the items.
     * 
     * @return the select SQL
     */
    public static String getSelectSql(final EntityMetadata entity) {        
        List<String> selectableParam = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getAllColumnsMetadata(AllColumnsGeneratedKeysFlag.WITH_GENERATED).values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.TABLE);
            
            if ( !columnMetadata.isTransient()) {                    
                selectableParam.add(columnName);
            }
        }
        
        return new StringBuilder(Utility.SQL_SELECT).append(Utility.join(selectableParam, Utility.SQL_COMMA))
                                                    .append(Utility.SQL_FROM)
                                                    .append(entity.generateSQLSelectTableDefinition())
                                                    .toString();
    }
    
    /**
     * This method returns the SQL query used to retrieve the item with a specific key
     * 
     * @return the select SQL
     */
    public static String getSelectByIdSql(final EntityMetadata entity) {        
        List<String> keyParam = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getKeyColumnsMetadata().values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.TABLE);
            
            keyParam.add(columnName + Utility.SQL_EQUAL_PARAM);
        }    
        
        return new StringBuilder(getSelectSql(entity)).append(Utility.SQL_WHERE)
                                                      .append(Utility.join(keyParam, Utility.SQL_COMMA))
                                                      .toString();
    }
    
    /**
     * This method returns the column-label of the POJO represented by the {@link EntityMetadata} instance that
     * represents the <code>propertyName</code> parameter.
     *  
     * @param propertyName the name of the property associated to the column-label to retrieve
     * @param entity the {@link EntityMetadata} information storing the POJO information
     * 
     * @return the column-label associated to the <code>propertyName</code> parameter
     */
    public static String getColumnLabelFromPropertyName(final String propertyName, final EntityMetadata entity) {
        return getColumnLabelFromPropertyName(propertyName, entity, ColumnDecorator.NONE);
    }
    
    /**
     * This method returns the column-label of the POJO represented by the {@link EntityMetadata} instance that
     * represents the <code>propertyName</code> parameter.
     *  
     * @param propertyName the name of the property associated to the column-label to retrieve
     * @param entity the {@link EntityMetadata} information storing the POJO information
     * @param columnDecorator the {@link ColumnDecorator} instance to use to return the column-label
     * 
     * @return the column-label associated to the <code>propertyName</code> parameter
     */
    public static String getColumnLabelFromPropertyName(final String propertyName, final EntityMetadata entity, final ColumnDecorator columnDecorator) {
        assertNotNull(propertyName, "propertyName");
        assertNotNull(entity, "entity");
        assertNotNull(columnDecorator, "columnDecorator");
        
        ColumnMetadata c = entity.getAllColumnsMetadata(AllColumnsGeneratedKeysFlag.WITH_GENERATED).get(propertyName);
        return (null != c) ? c.getColumnLabel(columnDecorator) : null;
    }
    
    public static String generateGenericQuery(final String query, final EntityMetadata entity) {
        assertNotNull(query, "query");
        assertNotNull(entity, "entity");
        
        Matcher m = COLUMN_PATTERN.matcher(query);
        
        String finalQuery = query;
        
        while (m.find()) {
            String queryParamName = m.group(0);
            String propertyName = m.group(1);
            finalQuery = finalQuery.replaceAll(queryParamName, getColumnLabelFromPropertyName(propertyName, entity, ColumnDecorator.TABLE));
        }
        
        return finalQuery;
    }
    
    private static final Pattern COLUMN_PATTERN = Pattern.compile("\\$(.+?)\\$");
}
