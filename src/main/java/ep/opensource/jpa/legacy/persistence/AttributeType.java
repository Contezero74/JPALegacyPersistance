package ep.opensource.jpa.legacy.persistence;

import ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata;

/**
 * This enumeration represent the type of database attribute supported:
 * <ul>
 * 	<li>{@link AttributeType#KEYS}: representing the primary-key attributes;</li>
 * 	<li>{@link AttributeType#OTHERS}: representing the non-primary-key attributes.</li>
 * </ul>
 */
public enum AttributeType {
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
    
    /**
     * This method returns <code>true</code> if the attribute is to retrieve according to the current
     * value of the {@link AttributeType} enumeration, <code>false</code> otherwise.
     * 
     * @param columnMetadata the {@link ColumnMetadata} instance to check
     * 
     * @return <code>true</code> if the attribute is to retrieve, <code>false</code> otherwise
     */
    public abstract boolean isToRetrieve(final ColumnMetadata columnMetadata);
}