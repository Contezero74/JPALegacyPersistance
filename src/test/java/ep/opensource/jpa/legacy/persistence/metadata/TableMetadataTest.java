package ep.opensource.jpa.legacy.persistence.metadata;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import ep.opensource.jpa.legacy.persistence.utility.Utility;

/**
 * This test verifies that the {@link TableMetadata} instances work correctly.
 */
public class TableMetadataTest {
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TableMetadata#TableMetadata(java.lang.Class)}.
	 */
	@Test
	public final void testTableMetadata() {
		TableMetadata tm = new TableMetadata(TestClassAllPublicProperties.class);
		assertNotNull("An instance of the TableMetadata should exist now", tm);
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TableMetadata#getOriginalClass()}.
	 */
	@Test
	public final void testGetOriginalClass() {
		TableMetadata tm = new TableMetadata(TestClassAllPublicProperties.class);
		assertEquals("The retrieved class has to be the correct one", TestClassAllPublicProperties.class, tm.getOriginalClass());
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TableMetadata#getTableLabel()}.
	 */
	@Test
	public final void testGetTableLabel() {
		TableMetadata tm = new TableMetadata(TestClassAllPublicProperties.class);
		assertEquals("The retrieved table label has to be the correct one", TestClassAllPublicProperties.TABLE_LABEL, tm.getTableLabel());
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TableMetadata#getSchemaName()}.
	 */
	@Test
	public final void testGetSchemaName() {
		TableMetadata tm = new TableMetadata(TestClassAllPublicProperties.class);
		assertEquals("The retrieved schema name has to be the correct one", TestClassAllPublicProperties.SCHEMA_NAME, tm.getSchemaName());
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TableMetadata#getFullyQuilifiedTableLabel()}.
	 */
	@Test
	public final void testGetFullyQuilifiedTableLabel() {
		final String FULLY_QUALIFIED_TABLE_LABEL = TestClassAllPublicProperties.SCHEMA_NAME + Utility.SQL_SEPARATOR + TestClassAllPublicProperties.TABLE_LABEL;
		
		TableMetadata tm = new TableMetadata(TestClassAllPublicProperties.class);
		assertEquals("The retrieved fully qualified table label has to be the correct one", FULLY_QUALIFIED_TABLE_LABEL, tm.getFullyQuilifiedTableLabel());
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TableMetadata#executePostLoadCallback(java.lang.Object)}.
	 * 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public final void testExecutePostLoadCallback() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		TestClassAllPublicProperties obj = new TestClassAllPublicProperties();
		obj.transientProperty = "StartValue";
		
		TableMetadata tm = new TableMetadata(TestClassAllPublicProperties.class);
		tm.executePostLoadCallback(obj);
		assertEquals("The transient property value should be changed", "EndValue", obj.transientProperty);
	}
}
