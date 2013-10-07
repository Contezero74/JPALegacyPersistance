package ep.opensource.jpa.legacy.persistence.metadata;

/**
 * Defines inheritance strategy options.
 */
public enum InheritanceType {
    /**
     * A strategy in which fields that are specific to a subclass are mapped to a separate table than the fields that
     * are common to the parent class, and a join is performed to instantiate the subclass.
     */
    //JOINED, // not supported yet
    /**
     * A single table per class hierarchy. 
     */
    SINGLE_TABLE,
    /**
     * A table per concrete entity class.
     */ 
    TABLE_PER_CLASS;    
}
