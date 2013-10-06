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

import com.lumata.e4o.database.support.test.pojo.metadata.ColumnMetadata;
import com.lumata.e4o.database.support.test.pojo.metadata.EntityMetadata;
import com.lumata.e4o.database.support.test.pojo.metadata.ColumnMetadata.ColumnDecorator;
import com.lumata.e4o.database.support.test.pojo.metadata.EntityMetadata.AllColumnsGeneratedKeysFlag;

import ep.opensource.jpa.legacy.persistence.utility.Utility;

public class PersistentPojoFactory {
    public enum ParamsType {
        KEYS {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isToRetrieve(final ColumnMetadata columnMetadata) {
                return columnMetadata.isKey();
            }
        },
        OTHERS {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isToRetrieve(final ColumnMetadata columnMetadata) {
                return !columnMetadata.isKey();
            }
        };
        
        public abstract boolean isToRetrieve(final ColumnMetadata columnMetadata);
        
    }
    public static EntityMetadata createEntityMetadata(final Class<?> clazz) {
        return EntityMetadata.createEntityMetadata(clazz);
    }
    
    public static <T> T from(final ResultSet rs, final EntityMetadata entity) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        @SuppressWarnings("unchecked")
        Constructor<T> ctor = (Constructor<T>) entity.getTableMetadata().getOriginalClass().getDeclaredConstructor();
        
        boolean isCtorAccessible = ctor.isAccessible();
        ctor.setAccessible(true);
        T obj = ctor.newInstance();
        ctor.setAccessible(isCtorAccessible);
        
        for (ColumnMetadata columnMetadata : entity.getAllColumnsMetadata(AllColumnsGeneratedKeysFlag.WITH_GENERATED).values()) {
            try {
                columnMetadata.setFieldValue(obj, columnMetadata.getConverter().from(rs, columnMetadata));
            } catch (SecurityException | IllegalArgumentException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return obj;
    }
    
    public static <T> Object[] getParamFrom(final T obj, final EntityMetadata entity, final ParamsType ... types) {
        List<Object> params = new ArrayList<>();
        
        for (ParamsType pt : types) {
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
        final String tableName = entity.getTableMetadata().getTableName();
        
        List<String> insertableColumns = new ArrayList<>();
        List<String> insertableParams = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getAllColumnsMetadata(AllColumnsGeneratedKeysFlag.WITHOUT_GENERATED).values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.NONE);
            
            if ( columnMetadata.isInsertable() && columnMetadata.getTableName().equals(tableName) ) {                    
                insertableColumns.add(columnName);
                insertableParams.add(Utility.SQL_PARAM);
            }
        }
        
        return new StringBuilder(Utility.SQL_INSERT).append(entity.getTableMetadata().getTableName())
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
        final String tableName = entity.getTableMetadata().getTableName();
        
        List<String> updatableParams = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getOtherColumnsMetadata().values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.TABLE);
            
            if ( columnMetadata.isUpdatable() && columnMetadata.getTableName().equals(tableName) ) {                    
                updatableParams.add(columnName + Utility.SQL_EQUAL_PARAM);
            }
        }

        List<String> keyParam = new ArrayList<>();
        for (ColumnMetadata columnMetadata : entity.getKeyColumnsMetadata().values()) {
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.TABLE);
            
            keyParam.add(columnName + Utility.SQL_EQUAL_PARAM);
        }    
       
        return new StringBuilder(Utility.SQL_UPDATE).append(entity.getTableMetadata().getTableName())
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
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.ALIAS);
            
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
            final String columnName = columnMetadata.getColumnLabel(ColumnDecorator.ALIAS);
            
            keyParam.add(columnName + Utility.SQL_EQUAL_PARAM);
        }    
        
        return new StringBuilder(getSelectSql(entity)).append(Utility.SQL_WHERE)
                                                      .append(Utility.join(keyParam, Utility.SQL_COMMA))
                                                      .toString();
    }
    
    public static String getColumnNameFromPropertyName(final String propertyName, final EntityMetadata entity) {
        return getColumnNameFromPropertyName(propertyName, entity, ColumnDecorator.NONE);
    }
    
    public static String getColumnNameFromPropertyName(final String propertyName, final EntityMetadata entity, final ColumnDecorator columnDecorator) {
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
            finalQuery = finalQuery.replaceAll(queryParamName, getColumnNameFromPropertyName(propertyName, entity, ColumnDecorator.TABLE));
        }
        
        return finalQuery;
    }
    
    private static final Pattern COLUMN_PATTERN = Pattern.compile("\\$(.+?)\\$");
}
