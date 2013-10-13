package ep.opensource.jpa.legacy.persistence.metadata;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ep.opensource.jpa.legacy.persistence.annotation.Entity;
import ep.opensource.jpa.legacy.persistence.annotation.PrimaryKeyJoinColumn;
import ep.opensource.jpa.legacy.persistence.annotation.SecondaryTable;
import ep.opensource.jpa.legacy.persistence.annotation.Table;
import ep.opensource.jpa.legacy.persistence.annotation.Transient;
import ep.opensource.jpa.legacy.persistence.exception.PersistenceException;

/**
 * This class represents the meta-information associated to an entity class.
 */
public class EntityMetadata {
	/**
	 * This factory method creates an instance of the {@link EntityMetadata} class representing
	 * the input {@link Class}.
	 * 
	 * @param clazz the {@link Class} to use to create the {@link EntityMetadata} instance
	 * 
	 * @return an instance of the {@link EntityMetadata} class based on the input {@link Class}
	 */
    public static EntityMetadata createEntityMetadata(final Class<?> clazz) {
        assertNotNull(clazz, "clazz");
        
        if (null == clazz.getAnnotation(Entity.class)) {
            throw new PersistenceException("The class " + clazz.getName() + " is not annotated as @Entity");
        }
        
        return createRealEntityMetadata(clazz);   
    }
    
    /**
     * This method returns the {@link TableMetadata} information associated to the current instance of
     * the {@link EntityMetadata} class.
     * 
     * @return the {@link TableMetadata} information associated to the current instance of the {@link EntityMetadata}
     * class
     */
    public TableMetadata getTableMetadata() {
        return tableInfo;
    }
    
    /**
     * This method returns a unmodifiable {@link Map} representing the {@link ColumnMetadata} instances
     * associated with the primary keys of the class used to create the current {@link EntityMetadata} instance.
     * 
     * @return a {@link Map} representing the {@link ColumnMetadata} primary-keys-related instances of the current
     * {@link EntityMetadata}
     */
    public Map<String, ColumnMetadata> getKeyColumnsMetadata() {
        return Collections.unmodifiableMap(keyColumnsInfo);
    }
    
    /**
     * This method returns a unmodifiable {@link Map} representing the {@link ColumnMetadata} instances
     * associated with the columns that are not primary keys of the class used to create the current
     * {@link EntityMetadata} instance.
     * 
     * @return a {@link Map} representing the {@link ColumnMetadata} non-primary-keys-related instances of the current
     * {@link EntityMetadata}
     */
    public Map<String, ColumnMetadata> getOtherColumnsMetadata() {
        return Collections.unmodifiableMap(otherColumnsInfo);
    }
    
    /**
     * This method returns all the {@link ColumnMetadata} (primary-key-related and not) instances related to
     * the current {@link EntityMetadata} instance according to the {@link AllColumnsGeneratedKeysFlag}
     * passed.
     *  
     * @param flag the {@link AllColumnsGeneratedKeysFlag} enumeration value used to determinate what primary-key
     * properties to return
     *  
     * @return all the {@link ColumnMetadata} (primary-key-related and not) instances related to the current
     * {@link EntityMetadata} instance
     */
    public Map<String, ColumnMetadata> getAllColumnsMetadata(final AllColumnsGeneratedKeysFlag flag) {
    	assertNotNull(flag, "flag");
    	
        Map<String, ColumnMetadata> result = new LinkedHashMap<>();
        
        for (Entry<String, ColumnMetadata> item : allColumnsInfoSummary.entrySet()) {
            ColumnMetadata columnMetadata = item.getValue();
            
            boolean toInsert = !columnMetadata.isKey() || (columnMetadata.isKey() && flag.isIncluded(columnMetadata));
            if (toInsert) {
                result.put(item.getKey(), columnMetadata);
            }            
        }
        
        return Collections.unmodifiableMap(result);
    }
    
    /**
     * This method returns a {@link String} representing a SQL SELECT FROM statement part.
     *  
     * @return a {@link String} representing a SQL SELECT FROM statement part
     */
    public String generateSQLSelectTableDefinition() {
        StringBuilder b = new StringBuilder(tableInfo.getFullyQuilifiedTableLabel());
        
        for (JoinTable jt : joinTables) {
            b.append(jt.generateSQLJoinQueryPart());
        }
        
        return b.toString();
    }
    
    /**
     * This method creates the {@link EntityMetadata} instance according to the {@link Class} argument passed.
     * 
     * @param clazz the {@link Class} instance used as base to create the {@link EntityMetadata} instance
     * 
     * @return the {@link EntityMetadata} instance based on the {@link Class} argument
     */
    private static EntityMetadata createRealEntityMetadata(final Class<?> clazz) {
        Map<String, ColumnMetadata> keyColumnsInfo = new LinkedHashMap<>();
        Map<String, ColumnMetadata> otherColumnsInfo = new LinkedHashMap<>();
        List<JoinTable> joinTables = new ArrayList<>();
        
        TableMetadata tableInfo = new TableMetadata(clazz);
        fillColumnsMetadata(clazz, tableInfo.getTableLabel(), keyColumnsInfo, otherColumnsInfo);
        fillJoinTables(clazz, keyColumnsInfo.keySet().toArray(new String[keyColumnsInfo.keySet().size()]), tableInfo.getTableLabel(), joinTables);
        
        return new EntityMetadata(tableInfo, joinTables, keyColumnsInfo, otherColumnsInfo);
    }
    
    /**
     * This method fills the {@link ColumnMetadata} arguments analyzing the {@link Class} instance.
     * 
     * @param clazz the {@link Class} instance used as base to fill the {@link ColumnMetadata} instances
     * @param defaultTableLabel the label to use as default table label filling the {@link ColumnMetadata} instances
     * @param keyColumnsInfo the {@link Map} representing the {@link ColumnMetadata} instances related to the primary-key columns
     * @param otherColumnsInfo the {@link Map} representing the {@link ColumnMetadata} instances related to the non-primary-key columns
     */
    private static void fillColumnsMetadata(final Class<?> clazz,
                                            final String defaultTableLabel,
                                            final Map<String, ColumnMetadata> keyColumnsInfo,
                                            final Map<String, ColumnMetadata> otherColumnsInfo) {
        assertNotNull(clazz, "clazz");
        assertNotNull(defaultTableLabel, "defaultTable");
        assertNotNull(keyColumnsInfo, "keyColumnsInfo");
        assertNotNull(otherColumnsInfo, "otherColumnsInfo");
        
        for (Field f : clazz.getDeclaredFields()) {
            if (!f.getName().equals(SERIAL_VERSION_ID_CONST)) {
                ColumnMetadata c = new ColumnMetadata(f, defaultTableLabel);
                
                if (!c.isTransient()){
                    if (c.isKey()) {
                        keyColumnsInfo.put(c.getPropertyName(), c);
                    } else {
                        otherColumnsInfo.put(c.getPropertyName(), c);
                    }
                    
                }
            }
        }
    }
    
    /**
     * This method fills the information related to the secondary table definition (if present).
     * 
     * @param clazz the {@link Class} instance used as base to fill the secondary tables information
     * @param keyColumnLabels a {@link String} array representing the label of the primary-key columns
     * @param tableLabel the label associated to the database table
     * @param joinTables a {@link List} storing the information of all the secondary tables that will be joined with
     * the primary one
     */
    private static void fillJoinTables(final Class<?> clazz,
                                       final String[] keyColumnLabels,
                                       final String tableLabel,
                                       final List<JoinTable> joinTables) {
        assertNotNull(clazz, "clazz");
        assertNotNull(keyColumnLabels, "keyColumnLabels");
        assertNotNull(tableLabel, "tableOrAlias");
        assertNotNull(joinTables, "joinTables");
        
        SecondaryTable secondaryTable = clazz.getAnnotation(SecondaryTable.class);
        if (null != secondaryTable) {
            String secondaryTableName = secondaryTable.name();
            PrimaryKeyJoinColumn[] pkJoinColumns = secondaryTable.pkJoinColumns();            
            
            int i = 0;
            List<ForeignKey> foreignKeys = new ArrayList<>();
            for (PrimaryKeyJoinColumn pkjc : pkJoinColumns) {
                String primaryKey = pkjc.name().isEmpty() ? keyColumnLabels[i] : pkjc.name();
                String fullPrimaryTableKey = tableLabel + "." + primaryKey;
                String fullSecondaryTableKey = secondaryTableName + "." + (pkjc.referencedColumnName().isEmpty() ? primaryKey : pkjc.referencedColumnName());
                
                foreignKeys.add(new ForeignKey(fullPrimaryTableKey, fullSecondaryTableKey));
                
                ++i;
            }
            
            joinTables.add( new JoinTable(secondaryTableName, foreignKeys));
        }
    }
    
    /**
     * This constructor creates the {@link EntityMetadata} instance starting from the input arguments.
     * 
     * @param tableInfo the {@link TableMetadata} instance used to initialize the new {@link EntityMetadata} instance
     * @param joinTables the List of {@link JoinTable} instances used to initialize the new {@link EntityMetadata} instance
     * @param keyColumnsInfo the {@link ColumnMetadata} {@link Map} representing the primary-key columns used to initialize the new {@link EntityMetadata} instance
     * @param otherColumnsInfo the {@link ColumnMetadata} {@link Map} representing the non-primary-key columns used to initialize the new {@link EntityMetadata} instance
     */
    private EntityMetadata(final TableMetadata tableInfo,
            final List<JoinTable> joinTables,
            final Map<String, ColumnMetadata> keyColumnsInfo,
            final Map<String, ColumnMetadata> otherColumnsInfo) {
		assertNotNull(tableInfo, "tableInfo");
		assertNotNull(joinTables, "joinTables");
		assertNotNull(keyColumnsInfo, "keyColumnsInfo");
		assertNotNull(otherColumnsInfo, "otherColumnsInfo");

		this.tableInfo = tableInfo;
		this.joinTables.addAll(joinTables);
		this.keyColumnsInfo = keyColumnsInfo;
		this.otherColumnsInfo = otherColumnsInfo;

		this.allColumnsInfoSummary = new LinkedHashMap<>();
		this.allColumnsInfoSummary.putAll(keyColumnsInfo);
		this.allColumnsInfoSummary.putAll(otherColumnsInfo);
	}
    
    /**
     * This property represents the {@link TableMetadata} instance associated to the current {@link EntityMetadata} one.
     */
    private TableMetadata tableInfo;
    
    /**
     * This property represents the {@link List} of {@link JoinTable} instances associated to the current {@link EntityMetadata} one.
     */
    private List<JoinTable> joinTables = new ArrayList<>();
    
    /**
     * This property represents the {@link Map} of {@link ColumnMetadata} (related to the primary-key columns) instances
     * associated to the current {@link EntityMetadata} one.
     */
    private Map<String, ColumnMetadata> keyColumnsInfo;
    
    /**
     * This property represents the {@link Map} of {@link ColumnMetadata} (related to the non-primary-key columns)
     * instances associated to the current {@link EntityMetadata} one.
     */
    private Map<String, ColumnMetadata> otherColumnsInfo;
    
    /**
     * This property represents the {@link Map} of {@link ColumnMetadata} (related to the primary-key and
     * non-primary-key columns) instances associated to the current {@link EntityMetadata} one.
     */
    private Map<String, ColumnMetadata> allColumnsInfoSummary;
    
    /**
     * This constant represents the JAVA statement used to identify the version of {@link java.io.Serializable}
     * class. This property is {@link Transient} by default.
     */
    private static final String SERIAL_VERSION_ID_CONST = "serialVersionUID";
}
