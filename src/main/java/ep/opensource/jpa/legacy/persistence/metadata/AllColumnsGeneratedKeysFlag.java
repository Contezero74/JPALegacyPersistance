package ep.opensource.jpa.legacy.persistence.metadata;

/**
 * This enumeration represents a selector that permits to decide if we want to consider in the primary key
 * columns the the primary keys marked as {@link ep.opensource.jpa.legacy.persistence.annotation.GeneratedValue} or not.
 */
public enum AllColumnsGeneratedKeysFlag {
    WITH_GENERATED {
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isIncluded(final ColumnMetadata c) {
            return c.isGenerated();
        }
    },
    WITHOUT_GENERATED {
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isIncluded(final ColumnMetadata c) {
            return !c.isGenerated();
        }
    };
    
    /**
     * This method return <code>true</code> if the particular type of primary key has to be considered,
     * <code>false</code> otherwise.
     *  
     * @param c the {@link ColumnMetadata} instance to consider
     * 
     * @return <code>true</code> if the particular type of primary key has to be considered, <code>false</code> otherwise
     */
    public abstract boolean isIncluded(final ColumnMetadata c);
}
