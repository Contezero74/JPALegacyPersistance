package ep.opensource.jpa.legacy.persistence.metadata;

import static ep.opensource.jpa.legacy.persistence.utility.Utility.assertNotNull;
import static ep.opensource.jpa.legacy.persistence.utility.Utility.SQL_EQUAL;

/**
 * This class represents the relation between a primary table and its secondary table.
 */
class ForeignKey {
    /**
     * This method returns the fully qualified label primary-key of the primary table.
     * 
     * @return the fully qualified label primary-key of the primary table.
     */
    public String getFullyPrimaryTableKey() {
        return fullyPrimaryTableKey;
    }
    
    /**
     * This method returns the fully qualified label primary-key of the secondary table.
     * 
     * @return the fully qualified label primary-key of the secondary table.
     */
    public String getFullySecondaryTableKey() {
        return fullySecondaryTableKey;
    }
    
    /**
     * This method returns a {@link String} representing the SQL JOIN condition statement of the current relation.
     * 
     * @return a {@link String} representing the SQL JOIN condition statement of the current relation
     */
    public String generateSQLJoinCriteria() {
        return new StringBuilder(fullyPrimaryTableKey).append(SQL_EQUAL).append(fullySecondaryTableKey).toString();
    }
    
    /**
     * This constructor initializes the primary-secondary table relation.
     * 
     * @param fullyPrimaryTableKey the fully qualified label primary-key of the primary table
     * @param fullySecondaryTableKey the fully qualified label primary-key of the secondary table
     */
    ForeignKey(final String fullyPrimaryTableKey, final String fullySecondaryTableKey) {
        assertNotNull(fullyPrimaryTableKey, "fullyPrimaryTableKey");
        assertNotNull(fullySecondaryTableKey, "fullSecondaryTableKey");
        
        this.fullyPrimaryTableKey = fullyPrimaryTableKey;
        this.fullySecondaryTableKey = fullySecondaryTableKey;
    }
    
    /**
     * This property represents the fully qualified label primary-key of the primary table.
     */
    private String fullyPrimaryTableKey = null;
    
    /**
     * This property represents the fully qualified label primary-key of the secondary table.
     */
    private String fullySecondaryTableKey = null;
}
