package ep.opensource.jpa.legacy.persistence.metadata;

import ep.ColumnMetadata;
import ep.opensource.jpa.legacy.persistence.utility.Utility;

/**
 * This enumeration represent the possible ways to represent a column table in relationship
 * with its table.
 */
public enum ColumnDecorator {
	/**
	 * This enumeration represents the only column-label.
	 */
    NONE {
    	/**
    	 * This method returns only the column-label without the related table-table.
    	 * 
    	 * {@inheritDoc}
    	 */
        @Override
        String decorate(final String columnLabel, final String tableLabel) {
        	Utility.assertNotNull(columnLabel, "columnLabel");
        	
            return columnLabel;
        }
    },
    /**
	 * This enumeration represents the only column-label as table-label.column-label format.
	 */
    TABLE {
    	/**
    	 * This method returns only the column-label with the related table-table as table-label.column-label.
    	 * 
    	 * {@inheritDoc}
    	 */
        @Override
        String decorate(final String columnLabel, final String tableLabel) {
        	Utility.assertNotNull(columnLabel, "columnLabel");
        	
        	return (tableLabel != null && !tableLabel.isEmpty()) ? tableLabel + Utility.SQL_SEPARATOR + columnLabel : columnLabel;
        }
    };
    
    /**
     * This abstract method returns the <table-label, column-label> pair according to chosen
     * decorator.
     * 
     * @param columnName the column name to return
     * @param c the {@link ColumnMetadata} instance related to the column name.
     * 
     * @return the specified representation
     */
    abstract String decorate(final String columnName, final String tableLabel);
}