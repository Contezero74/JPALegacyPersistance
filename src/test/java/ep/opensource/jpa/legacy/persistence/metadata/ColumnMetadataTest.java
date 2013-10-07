/**
 * 
 */
package ep.opensource.jpa.legacy.persistence.metadata;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import ep.opensource.jpa.legacy.persistence.annotation.Basic;
import ep.opensource.jpa.legacy.persistence.annotation.Column;
import ep.opensource.jpa.legacy.persistence.annotation.Enumerated;
import ep.opensource.jpa.legacy.persistence.annotation.Id;
import ep.opensource.jpa.legacy.persistence.annotation.Transient;


/**
 * This test verifies that the {@link ColumnMetadata} instances work correctly.
 */
public class ColumnMetadataTest {

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getPropertyName()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testGetPropertyName() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property name has to match", ID_NAME, cmId.getPropertyName());
        
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property name has to match", TRANSIENT_NAME, cmTransient.getPropertyName());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getColumnLabel(ep.opensource.jpa.legacy.persistence.metadata.ColumnDecorator)}.
     */
    @Test
    public final void testGetColumnLabel() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getTableLabel()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testGetTableLabel() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The table label has to match", PRIMARY_TABLE_LABEL, cmId.getTableLabel());
        
        ColumnMetadata cmEnum2 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM2_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The table label has to match", SECONDARY_TABLE_LABEL, cmEnum2.getTableLabel());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getOriginalType()}.
     */
    @Test
    public final void testGetOriginalType() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isKey()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsKey() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + ID_NAME + " should be key", cmId.isKey());
        
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + TRANSIENT_NAME + " should be not key", cmTransient.isKey());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isGenerated()}.
     */
    @Test
    public final void testIsGenerated() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isNullable()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsNullable() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + ID_NAME + " should be not nullable", cmId.isNullable());
        
        ColumnMetadata cmOptional = new ColumnMetadata(TestClassAllPublicProperties.class.getField(OPTIONAL_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + OPTIONAL_NAME + " should be nullable", cmOptional.isNullable());
        
        ColumnMetadata cmNotOptional = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_OPTIONAL_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + NOT_OPTIONAL_NAME + " should be not nullable", cmNotOptional.isNullable());
        
        ColumnMetadata cmNullable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NULLABLE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + NULLABLE_NAME + " should be not nullable", cmNullable.isNullable());
        
        ColumnMetadata cmNotNullable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_NULLABLE_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + NOT_NULLABLE_NAME + " should be not nullable", cmNotNullable.isNullable());
        
        ColumnMetadata cmOptionalNullable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(OPTIONAL_NULLABLE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + OPTIONAL_NULLABLE_NAME + " should be not nullable", cmOptionalNullable.isNullable());
        
        ColumnMetadata cmOptionalNotNullable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(OPTIONAL_NOT_NULLABLE_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + OPTIONAL_NOT_NULLABLE_NAME + " should be not nullable", cmOptionalNotNullable.isNullable());
        
        ColumnMetadata cmNotOptionalNullable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_OPTIONAL_NULLABLE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + NOT_OPTIONAL_NULLABLE_NAME + " should be not nullable", cmNotOptionalNullable.isNullable());
        
        ColumnMetadata cmNotOptionalNotNullable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_OPTIONAL_NOT_NULLABLE_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + NOT_OPTIONAL_NOT_NULLABLE_NAME + " should be not nullable", cmNotOptionalNotNullable.isNullable());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isInsertable()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsInsertable() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + ID_NAME + " should be insertable", cmId.isInsertable());
        
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + TRANSIENT_NAME + " should be not insertable", cmTransient.isInsertable());
        
        ColumnMetadata cmInsertableUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(INSERTABLE_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + INSERTABLE_UPDATABLE_NAME + " should be insertable", cmInsertableUpdatable.isInsertable());
        
        ColumnMetadata cmInsertableNotUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(INSERTABLE_NOT_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + INSERTABLE_NOT_UPDATABLE_NAME + " should be insertable", cmInsertableNotUpdatable.isInsertable());
        
        ColumnMetadata cmNotInsertableUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_INSERTABLE_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + NOT_INSERTABLE_UPDATABLE_NAME + " should be not insertable", cmNotInsertableUpdatable.isInsertable());
        
        ColumnMetadata cmNotInsertableNotUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_INSERTABLE_NOT_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + NOT_INSERTABLE_NOT_UPDATABLE_NAME + " should be not insertable", cmNotInsertableNotUpdatable.isInsertable());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isUpdatable()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsUpdatable() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + ID_NAME + " should be updatable", cmId.isUpdatable());
        
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + TRANSIENT_NAME + " should be not updatable", cmTransient.isUpdatable());
        
        ColumnMetadata cmInsertableUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(INSERTABLE_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + INSERTABLE_UPDATABLE_NAME + " should be updatable", cmInsertableUpdatable.isUpdatable());
        
        ColumnMetadata cmInsertableNotUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(INSERTABLE_NOT_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + INSERTABLE_NOT_UPDATABLE_NAME + " should be updatable", cmInsertableNotUpdatable.isUpdatable());
        
        ColumnMetadata cmNotInsertableUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_INSERTABLE_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + NOT_INSERTABLE_UPDATABLE_NAME + " should be not updatable", cmNotInsertableUpdatable.isUpdatable());
        
        ColumnMetadata cmNotInsertableNotUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NOT_INSERTABLE_NOT_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + NOT_INSERTABLE_NOT_UPDATABLE_NAME + " should be not updatable", cmNotInsertableNotUpdatable.isUpdatable());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isTransient()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsTransient() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + TRANSIENT_NAME + " should be transient", cmTransient.isTransient());
        
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + ID_NAME + " should be not transient", cmId.isTransient());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isTemporalType()}.
     */
    @Test
    public final void testIsTemporalType() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getTemporalType()}.
     */
    @Test
    public final void testGetTemporalType() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isEnumType()}.
     */
    @Test
    public final void testIsEnumType() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getEnumType()}.
     */
    @Test
    public final void testGetEnumType() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getConverter()}.
     */
    @Test
    public final void testGetConverter() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getFieldValue(java.lang.Object)}.
     */
    @Test
    public final void testGetFieldValue() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#setFieldValue(java.lang.Object, java.lang.Object)}.
     */
    @Test
    public final void testSetFieldValue() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#ColumnMetadata(java.lang.reflect.Field, java.lang.String)}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testColumnMetadata() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cm = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertNotNull("A ColumnMetadata instance has exist now", cm);
    }
    
    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#ColumnMetadata(java.lang.reflect.Field, java.lang.String)}
     * with a <code>null</code> {@link Field} argument.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testColumnMetadataWithNullField() throws NoSuchFieldException, SecurityException {
        @SuppressWarnings("unused")
        ColumnMetadata cm = new ColumnMetadata(null, PRIMARY_TABLE_LABEL);
    }
    
    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#ColumnMetadata(java.lang.reflect.Field, java.lang.String)}
     * with a <code>null</code> table-label argument.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testColumnMetadataWithNullTableLabel() throws NoSuchFieldException, SecurityException {
        @SuppressWarnings("unused")
        ColumnMetadata cm = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), null);
    }
    
    /**
     * This enumeration is the one used during the tests.
     */
    private enum TestEnum {
        ONE, TWO, THREE
    }

    private class TestClassAllPublicProperties {
        @Transient public String transientProperty;
        
        @Id public int id;
        
        @Basic(optional=true) public long isOptional;
        
        @Basic(optional=false) public long isNotOptional;
        
        @Column(nullable=true) public long isNullable;
        
        @Column(nullable=false) public long isNotNullable;
        
        @Basic(optional=true) @Column(nullable=true) public long isOptionalAndNullable;
        
        @Basic(optional=true) @Column(nullable=false) public long isOptionalButNotNullable;
        
        @Basic(optional=false) @Column(nullable=true) public long isNotOptionalButNullable;
        
        @Basic(optional=false) @Column(nullable=false) public long isNotOptionalAndNotNullable;
        
        @Column(insertable = true, updatable=true) public String insertableAndUpdatable;
        
        @Column(insertable = true, updatable=false) public String insertableButNotUpdatable;
        
        @Column(insertable = false, updatable=true) public String notInsertableButUpdatable;
        
        @Column(insertable = false, updatable=false) public String notInsertableAndNotUpdatable;
        
        public TestEnum enum1;
        
        @Enumerated @Column(table=SECONDARY_TABLE_LABEL) public TestEnum enum2;
    }
    
    private static final String PRIMARY_TABLE_LABEL = "primary_table";
    private static final String SECONDARY_TABLE_LABEL = "secondary_table";
    
    private static final String ID_NAME = "id";
    
    private static final String TRANSIENT_NAME = "transientProperty";
    
    private static final String OPTIONAL_NAME = "isOptional";
    private static final String NOT_OPTIONAL_NAME = "isNotOptional";
    private static final String NULLABLE_NAME = "isNullable";
    private static final String NOT_NULLABLE_NAME = "isNotNullable";
    private static final String OPTIONAL_NULLABLE_NAME = "isOptionalAndNullable";
    private static final String OPTIONAL_NOT_NULLABLE_NAME = "isOptionalButNotNullable";
    private static final String NOT_OPTIONAL_NULLABLE_NAME = "isNotOptionalButNullable";
    private static final String NOT_OPTIONAL_NOT_NULLABLE_NAME = "isNotOptionalAndNotNullable";
    
    private static final String INSERTABLE_UPDATABLE_NAME = "insertableAndUpdatable";
    private static final String INSERTABLE_NOT_UPDATABLE_NAME = "insertableButNotUpdatable";
    private static final String NOT_INSERTABLE_UPDATABLE_NAME = "notInsertableButUpdatable";
    private static final String NOT_INSERTABLE_NOT_UPDATABLE_NAME = "notInsertableAndNotUpdatable";
    
    private static final String ENUM2_NAME = "enum2";    
}
