/**
 * 
 */
package ep.opensource.jpa.legacy.persistence.metadata;

import static org.junit.Assert.*;
import static ep.opensource.jpa.legacy.persistence.utility.Utility.SQL_SEPARATOR;

import java.lang.reflect.Field;

import org.junit.Test;



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
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testGetColumnLabel() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The column label has to match", ID_NAME, cmId.getColumnLabel(ColumnDecorator.NONE));
        assertEquals("The column label has to match", PRIMARY_TABLE_LABEL + SQL_SEPARATOR + ID_NAME, cmId.getColumnLabel(ColumnDecorator.TABLE));
        
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The column label has to match", TRANSIENT_NAME, cmTransient.getColumnLabel(ColumnDecorator.NONE));
        assertEquals("The column label has to match", PRIMARY_TABLE_LABEL + SQL_SEPARATOR + TRANSIENT_NAME, cmTransient.getColumnLabel(ColumnDecorator.TABLE));
        
        ColumnMetadata cmEnum1 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM1_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The column label has to match", COLUMN_ENUM1_LABEL, cmEnum1.getColumnLabel(ColumnDecorator.NONE));
        assertEquals("The column label has to match", PRIMARY_TABLE_LABEL + SQL_SEPARATOR + COLUMN_ENUM1_LABEL, cmEnum1.getColumnLabel(ColumnDecorator.TABLE));
        
        ColumnMetadata cmEnum2 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM2_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The column label has to match", ENUM2_NAME, cmEnum2.getColumnLabel(ColumnDecorator.NONE));
        assertEquals("The column label has to match", SECONDARY_TABLE_LABEL + SQL_SEPARATOR + ENUM2_NAME, cmEnum2.getColumnLabel(ColumnDecorator.TABLE));
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
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testGetOriginalType() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The class has to match", int.class, cmId.getOriginalType());
        
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The class has to match", String.class, cmTransient.getOriginalType());
        
        ColumnMetadata cmEnum2 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM2_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The class has to match", TestEnum.class, cmEnum2.getOriginalType());
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
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsGenerated() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property key " + ID_NAME + " should be not generated", cmId.isGenerated());
        
        ColumnMetadata cmSecondaryId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(SECONDARY_ID_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property key " + SECONDARY_ID_NAME + " should be generated", cmSecondaryId.isGenerated());
        
        ColumnMetadata cmTransient = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TRANSIENT_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + TRANSIENT_NAME + " should be not generated", cmTransient.isGenerated());
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
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsTemporalType() throws NoSuchFieldException, SecurityException {
    	ColumnMetadata cmDate = new ColumnMetadata(TestClassAllPublicProperties.class.getField(DATE_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + DATE_NAME + " should be temporal", cmDate.isTemporalType());
        
        ColumnMetadata cmTime = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TIME_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + TIME_NAME + " should be temporal", cmTime.isTemporalType());
        
        ColumnMetadata cmCalendar = new ColumnMetadata(TestClassAllPublicProperties.class.getField(CALENDAR_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + CALENDAR_NAME + " should be temporal", cmCalendar.isTemporalType());
        
        ColumnMetadata cmNoTemporalCalendar = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NO_TEMPORAL_CALENDAR_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + NO_TEMPORAL_CALENDAR_NAME + " should not be temporal", cmNoTemporalCalendar.isTemporalType());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getTemporalType()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testGetTemporalType() throws NoSuchFieldException, SecurityException {
    	ColumnMetadata cmDate = new ColumnMetadata(TestClassAllPublicProperties.class.getField(DATE_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + DATE_NAME + " should have the correct temporal", TemporalType.DATE, cmDate.getTemporalType());
        
        ColumnMetadata cmTime = new ColumnMetadata(TestClassAllPublicProperties.class.getField(TIME_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + TIME_NAME + " should have the correct temporal", TemporalType.TIME, cmTime.getTemporalType());
        
        ColumnMetadata cmCalendar = new ColumnMetadata(TestClassAllPublicProperties.class.getField(CALENDAR_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + CALENDAR_NAME + " should have the correct temporal", TemporalType.TIMESTAMP, cmCalendar.getTemporalType());
        
        ColumnMetadata cmNoTemporalCalendar = new ColumnMetadata(TestClassAllPublicProperties.class.getField(NO_TEMPORAL_CALENDAR_NAME), PRIMARY_TABLE_LABEL);
        assertNull("The property " + NO_TEMPORAL_CALENDAR_NAME + " should have the correct temporal", cmNoTemporalCalendar.getTemporalType());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#isEnumType()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testIsEnumType() throws NoSuchFieldException, SecurityException {
    	ColumnMetadata cmEnum1 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM1_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + ENUM1_NAME + ".isEnumType() has to match", cmEnum1.isEnumType());
        
        ColumnMetadata cmEnum2 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM2_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + ENUM2_NAME + ".isEnumType() has to match", cmEnum2.isEnumType());
        
        ColumnMetadata cmEnum3 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM3_NAME), PRIMARY_TABLE_LABEL);
        assertTrue("The property " + ENUM3_NAME + ".isEnumType() has to match", cmEnum3.isEnumType());
        
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertFalse("The property " + ID_NAME + ".isEnumType() has to match", cmId.isEnumType());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getEnumType()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testGetEnumType() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmEnum1 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM1_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + ENUM1_NAME + ".getEnumType() has to match", null, cmEnum1.getEnumType());
        
        ColumnMetadata cmEnum2 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM2_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + ENUM2_NAME + ".getEnumType() has to match", EnumType.ORDINAL, cmEnum2.getEnumType());
        
        ColumnMetadata cmEnum3 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM3_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + ENUM3_NAME + ".getEnumType() has to match", EnumType.STRING, cmEnum3.getEnumType());
        
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + ID_NAME + ".getEnumType() has to match", null, cmId.getEnumType());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getConverter()}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    @Test
    public final void testGetConverter() throws NoSuchFieldException, SecurityException {
        ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + ID_NAME + " has to return the correct Coverter", DataTypeConverter.BASIC.getClass().getName(), cmId.getConverter().getClass().getName());
        
        ColumnMetadata cmSecondaryId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(SECONDARY_ID_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + SECONDARY_ID_NAME + " has to return the correct Coverter", DataTypeConverter.UNKNOWN.getClass().getName(), cmSecondaryId.getConverter().getClass().getName());
        
        ColumnMetadata cmEnum1 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM1_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The property " + ENUM1_NAME + " has to return the correct Coverter", DataTypeConverter.ENUM.getClass().getName(), cmEnum1.getConverter().getClass().getName());
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#getFieldValue(java.lang.Object)}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @Test
    public final void testGetFieldValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	final int ID_VALUE = 10;
    	final Integer SECONDARY_ID_VALUE = new Integer(100);
    	final String STRING_VALUE = "Test";
    	
    	TestClassAllPublicProperties obj = new TestClassAllPublicProperties();
    	obj.id = ID_VALUE;
    	obj.secondaryId = SECONDARY_ID_VALUE;
    	obj.insertableAndUpdatable = STRING_VALUE;
    	obj.enum1 = TestEnum.ONE;
    	obj.enum2 = TestEnum.THREE;
    	obj.enum3 = TestEnum.TWO;
    	
    	
    	ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
    	assertEquals("The value of the property " + ID_NAME + " should match", ID_VALUE, cmId.getFieldValue(obj));
    	
    	ColumnMetadata cmSecondaryId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(SECONDARY_ID_NAME), PRIMARY_TABLE_LABEL);
    	assertEquals("The value of the property " + SECONDARY_ID_NAME + " should match", SECONDARY_ID_VALUE, cmSecondaryId.getFieldValue(obj));
    	
    	ColumnMetadata cmInsertableUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(INSERTABLE_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
    	assertEquals("The value of the property " + INSERTABLE_UPDATABLE_NAME + " should match", STRING_VALUE, cmInsertableUpdatable.getFieldValue(obj));
    	
    	ColumnMetadata cmEnum1 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM1_NAME), PRIMARY_TABLE_LABEL);
    	assertEquals("The value of the property " + ENUM1_NAME + " should match", TestEnum.ONE, cmEnum1.getFieldValue(obj));
        
        ColumnMetadata cmEnum2 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM2_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The value of the property " + ENUM2_NAME + " should match", TestEnum.THREE, cmEnum2.getFieldValue(obj));
        
        ColumnMetadata cmEnum3 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM3_NAME), PRIMARY_TABLE_LABEL);
        assertEquals("The value of the property " + ENUM3_NAME + " should match", TestEnum.TWO, cmEnum3.getFieldValue(obj));
    }

    /**
     * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadata#setFieldValue(java.lang.Object, java.lang.Object)}.
     * 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @Test
    public final void testSetFieldValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	final int ID_VALUE = 10;
    	final Integer SECONDARY_ID_VALUE = new Integer(100);
    	final String STRING_VALUE = "Test";
    	
    	TestClassAllPublicProperties obj = new TestClassAllPublicProperties();
    	
    	ColumnMetadata cmId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ID_NAME), PRIMARY_TABLE_LABEL);
    	cmId.setFieldValue(obj, ID_VALUE);
    	assertEquals("The value of the property " + ID_NAME + " should match", ID_VALUE, obj.id);
    	
    	ColumnMetadata cmSecondaryId = new ColumnMetadata(TestClassAllPublicProperties.class.getField(SECONDARY_ID_NAME), PRIMARY_TABLE_LABEL);
    	cmSecondaryId.setFieldValue(obj, SECONDARY_ID_VALUE);
    	assertEquals("The value of the property " + SECONDARY_ID_NAME + " should match", SECONDARY_ID_VALUE, obj.secondaryId);
    	
    	ColumnMetadata cmInsertableUpdatable = new ColumnMetadata(TestClassAllPublicProperties.class.getField(INSERTABLE_UPDATABLE_NAME), PRIMARY_TABLE_LABEL);
    	cmInsertableUpdatable.setFieldValue(obj, STRING_VALUE);
    	assertEquals("The value of the property " + INSERTABLE_UPDATABLE_NAME + " should match", STRING_VALUE, obj.insertableAndUpdatable);
    	
    	ColumnMetadata cmEnum1 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM1_NAME), PRIMARY_TABLE_LABEL);
    	cmEnum1.setFieldValue(obj, TestEnum.ONE);
    	assertEquals("The value of the property " + ENUM1_NAME + " should match", TestEnum.ONE, obj.enum1);
        
        ColumnMetadata cmEnum2 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM2_NAME), PRIMARY_TABLE_LABEL);
        cmEnum2.setFieldValue(obj, TestEnum.THREE);
        assertEquals("The value of the property " + ENUM2_NAME + " should match", TestEnum.THREE, cmEnum2.getFieldValue(obj));
        
        ColumnMetadata cmEnum3 = new ColumnMetadata(TestClassAllPublicProperties.class.getField(ENUM3_NAME), PRIMARY_TABLE_LABEL);
        cmEnum3.setFieldValue(obj, TestEnum.TWO);
        assertEquals("The value of the property " + ENUM3_NAME + " should match", TestEnum.TWO, cmEnum3.getFieldValue(obj));
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
    enum TestEnum {
        ONE, TWO, THREE
    }

    private static final String PRIMARY_TABLE_LABEL = "primary_table";
    static final String SECONDARY_TABLE_LABEL = "secondary_table";
    
    static final String COLUMN_ENUM1_LABEL = "enumeration_one";
    static final String COLUMN_ENUM3_LABEL = "enumeration_three";
    
    private static final String ID_NAME = "id";
    private static final String SECONDARY_ID_NAME = "secondaryId";
    
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
    
    private static final String DATE_NAME = "date";
    private static final String TIME_NAME = "time";
    private static final String CALENDAR_NAME = "calendar";
    private static final String NO_TEMPORAL_CALENDAR_NAME = "noTemporalCalendar";
    
    private static final String ENUM1_NAME = "enum1";
    private static final String ENUM2_NAME = "enum2";
    private static final String ENUM3_NAME = "enum3";
}

