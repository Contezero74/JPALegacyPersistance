package ep.opensource.jpa.legacy.persistence;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;

import com.lumata.e4o.database.support.test.pojo.metadata.EntityMetadata;

import ep.opensource.jpa.legacy.persistence.utility.Utility;


public class PersistentPojo<T> {
    public PersistentPojo(final Class<T> baseClass) {
        Utility.assertNotNull(baseClass, "baseClass");
        
        entity = PersistentPojoFactory.createEntityMetadata(baseClass);
    }
    
    public String getSelectSQL() {
        return PersistentPojoFactory.getSelectSql(entity);
    }
    
    public String getSelectSQLById() {
        return PersistentPojoFactory.getSelectByIdSql(entity);
    }
    
    public String getInsertSQL() {
        return PersistentPojoFactory.getInsertSql(entity);
    }
       
    public String getUpdateSQL() {
        return PersistentPojoFactory.getUpdateSql(entity);
    }
        
    public String getDeleteSQL() {
        return "";
    }
    
    public String getCountSQL() {
        return "";
    }
    
    protected EntityMetadata getEntity() {
        return entity;
    }
        
    public static <T extends PersistentPojo<T>> T from(final ResultSet rs, final T entity) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        return PersistentPojoFactory.from(rs, entity.getEntity());
    }
    
    private EntityMetadata entity;
}
