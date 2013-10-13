package ep.opensource.jpa.legacy.persistence.metadata;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;
import static ep.opensource.jpa.legacy.persistence.utility.Utility.SQL_JOIN;
import static ep.opensource.jpa.legacy.persistence.utility.Utility.SQL_ON;
import static ep.opensource.jpa.legacy.persistence.utility.Utility.SQL_AND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class JoinTable {
	/**
	 * This method returns the table-label associated to the secondary table used for the join with the
	 * primary table.
	 * 
	 * @return the table-label associated to the secondary table used for the join
	 */
    public String getTableLabel() {
        return tableLabel;
    }
    
    /**
     * This method returns an unmodifiable {@link List} of {@link ForeignKey} instances representing
     * the relations between the primary table and the secondary one.
     * 
     * @return a {@link List} of {@link ForeignKey} instances representing the primary-secondary tables relations 
     */
    public List<ForeignKey> getForeignKeys() {
        return Collections.unmodifiableList(foreignKeys);
    }
    
    /**
     * This method returns a {@link String} representing the full SQL JOIN statement of the current relation.
     * 
     * @return a {@link String} representing the full SQL JOIN statement of the current relation
     */
    public String generateSQLJoinQueryPart() {
        StringBuilder b = new StringBuilder(SQL_JOIN).append(tableLabel)
                                                             .append(SQL_ON);
        
        boolean isFirst = true; 
        for (ForeignKey fk : foreignKeys) {
            if (!isFirst) {
                b.append(SQL_AND);
            } else {
                isFirst = false;
            }
            
            b.append(fk.generateSQLJoinCriteria());
        }
        
        return b.toString();
    }
    
    /**
     * This constructor initializes the {@link JoinTable} instance to represent a relation between the primary
     * table and the secondary table represented by the tableLabel argument via the specified {@link ForeignKey} {@link List}.
     * 
     * @param tableLabel the label of the secondary table
     * @param foreignKeys the {@link List} of {@link ForeignKey} instance representing the primary-secondary tables relations
     */
    JoinTable(final String tableLabel, final List<ForeignKey> foreignKeys) {
        assertNotNull(tableLabel, "tableName");
        assertNotNull(foreignKeys, "foreignKeys");
        
        this.tableLabel = tableLabel;
        this.foreignKeys.addAll(foreignKeys);
    }
    
    /**
     * This property represents the label of the secondary table. 
     */
    private String tableLabel = null;
    
    /**
     * This property represents the {@link List} of {@link ForeignKey} instance representing the primary-secondary tables relations
     */
    private List<ForeignKey> foreignKeys = new ArrayList<>();
}
