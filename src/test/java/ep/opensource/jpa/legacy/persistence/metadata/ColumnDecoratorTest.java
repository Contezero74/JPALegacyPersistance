package ep.opensource.jpa.legacy.persistence.metadata;

import static org.junit.Assert.*;

import org.junit.Test;

import ep.opensource.jpa.legacy.persistence.utility.Utility;

/**
 * This test verifies that the {@link ColumnDecorator} instances work correctly.
 */
public class ColumnDecoratorTest {

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnDecorator#decorate(java.lang.String, java.lang.String)}
	 * for the value {@link ColumnDecorator#NONE}.
	 */
	@Test
	public final void testDecorateForNONE() {
		assertEquals("The result is not the expected one", COLUMN_LABEL, ColumnDecorator.NONE.decorate(COLUMN_LABEL, null));
		assertEquals("The result is not the expected one", COLUMN_LABEL, ColumnDecorator.NONE.decorate(COLUMN_LABEL, ""));
		assertEquals("The result is not the expected one", COLUMN_LABEL, ColumnDecorator.NONE.decorate(COLUMN_LABEL, TABLE_LABEL));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnDecorator#decorate(java.lang.String, java.lang.String)}
	 * for the value {@link ColumnDecorator#NONE} in case of <code>null</code> column-label.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testDecorateForNONEWithNullColumnLabel() {
		assertEquals("The result is not the expected one", COLUMN_LABEL, ColumnDecorator.NONE.decorate(null, TABLE_LABEL));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnDecorator#decorate(java.lang.String, java.lang.String)}
	 * for the value {@link ColumnDecorator#TABLE}.
	 */
	@Test
	public final void testDecorateForTABLE() {
		assertEquals("The result is not the expected one", COLUMN_LABEL, ColumnDecorator.TABLE.decorate(COLUMN_LABEL, null));
		assertEquals("The result is not the expected one", COLUMN_LABEL, ColumnDecorator.TABLE.decorate(COLUMN_LABEL, ""));
		assertEquals("The result is not the expected one", TABLE_COLUMN_LABEL, ColumnDecorator.TABLE.decorate(COLUMN_LABEL, TABLE_LABEL));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnDecorator#decorate(java.lang.String, java.lang.String)}
	 * for the value {@link ColumnDecorator#TABLE} in case of <code>null</code> column-label.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testDecorateForTABLEWithNullColumnLabel() {
		assertEquals("The result is not the expected one", COLUMN_LABEL, ColumnDecorator.TABLE.decorate(null, TABLE_LABEL));
	}

	/**
	 * This constant represents the column-label used in the test.
	 */
	private static final String COLUMN_LABEL = "Column";
	
	/**
	 * This constant represents the table-label used in the test.
	 */
	private static final String TABLE_LABEL = "Table";
	
	/**
	 * This constant represents the pair table-label and column-label: the result for the {@link ColumnDecorator#TABLE}
	 * scenario.
	 */
	private static final String TABLE_COLUMN_LABEL = TABLE_LABEL + Utility.SQL_SEPARATOR + COLUMN_LABEL;
}
