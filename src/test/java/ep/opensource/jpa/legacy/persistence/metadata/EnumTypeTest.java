package ep.opensource.jpa.legacy.persistence.metadata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This test verifies that the {@link EnumType} instances work correctly.
 */
@RunWith(MockitoJUnitRunner.class) 
public class EnumTypeTest {

	/**
	 * This method setups the {@link ResultSet} mocked-up instance for the tests.
	 * 
	 * @throws SQLException
	 */
	@Before
	public void setUp() throws SQLException {
		when(resultSet.getObject(COLUMN_ONE_LABEL, int.class)).thenReturn(ORDINA_FOR_ONE);
		when(resultSet.getObject(COLUMN_TWO_LABEL, int.class)).thenReturn(ORDINA_FOR_TWO);
		when(resultSet.getObject(COLUMN_THREE_LABEL, int.class)).thenReturn(ORDINA_FOR_THREE);
		
		when(resultSet.getObject(COLUMN_ONE_LABEL, String.class)).thenReturn(TestEnum.ONE.name());
		when(resultSet.getObject(COLUMN_TWO_LABEL, String.class)).thenReturn(TestEnum.TWO.name());
		when(resultSet.getObject(COLUMN_THREE_LABEL, String.class)).thenReturn(TestEnum.THREE.name());
		
		when(resultSet.getDate(COLUMN_NULL_LABEL)).thenReturn(null);
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#ORDINAL} scenario.
	 * 
	 * @throws SQLException 
	 */
	@Test
	public final void testFromForOrdinal() throws SQLException {
		assertEquals("The result is not the expected one", TestEnum.ONE, EnumType.ORDINAL.from(resultSet, COLUMN_ONE_LABEL, TestEnum.class));
		assertEquals("The result is not the expected one", TestEnum.TWO, EnumType.ORDINAL.from(resultSet, COLUMN_TWO_LABEL, TestEnum.class));
		assertEquals("The result is not the expected one", TestEnum.THREE, EnumType.ORDINAL.from(resultSet, COLUMN_THREE_LABEL, TestEnum.class));
		assertEquals("The result is not the expected one", null, EnumType.ORDINAL.from(resultSet, COLUMN_NULL_LABEL, TestEnum.class));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#STRING} scenario.
	 * 
	 * @throws SQLException 
	 */
	@Test
	public final void testFromForString() throws SQLException {
		assertEquals("The result is not the expected one", TestEnum.ONE, EnumType.STRING.from(resultSet, COLUMN_ONE_LABEL, TestEnum.class));
		assertEquals("The result is not the expected one", TestEnum.TWO, EnumType.STRING.from(resultSet, COLUMN_TWO_LABEL, TestEnum.class));
		assertEquals("The result is not the expected one", TestEnum.THREE, EnumType.STRING.from(resultSet, COLUMN_THREE_LABEL, TestEnum.class));
		assertEquals("The result is not the expected one", null, EnumType.STRING.from(resultSet, COLUMN_NULL_LABEL, TestEnum.class));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#ORDINAL} scenario to verify if input argument check.
	 * 
	 * @throws SQLException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testFromForOrdinalWithNullResulSet() throws SQLException {
		EnumType.ORDINAL.from(null, COLUMN_ONE_LABEL, TestEnum.class);
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#ORDINAL} scenario to verify if input argument check.
	 * 
	 * @throws SQLException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testFromForOrdinalWithNullColumnLabel() throws SQLException {
		EnumType.ORDINAL.from(resultSet, null, TestEnum.class);
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#ORDINAL} scenario to verify if input argument check.
	 * 
	 * @throws SQLException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testFromForOrdinalWithNullEnumType() throws SQLException {
		EnumType.ORDINAL.from(resultSet, COLUMN_ONE_LABEL, null);
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#STRING} scenario to verify if input argument check.
	 * 
	 * @throws SQLException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testFromForStringWithNullResulSet() throws SQLException {
		EnumType.STRING.from(null, COLUMN_ONE_LABEL, TestEnum.class);
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#STRING} scenario to verify if input argument check.
	 * 
	 * @throws SQLException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testFromForStringWithNullColumnLabel() throws SQLException {
		EnumType.STRING.from(resultSet, null, TestEnum.class);
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#from(java.sql.ResultSet, java.lang.String, java.lang.Class)}
	 * for the {@link EnumType#STRING} scenario to verify if input argument check.
	 * 
	 * @throws SQLException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testFromForStringWithNullEnumType() throws SQLException {
		EnumType.STRING.from(resultSet, COLUMN_ONE_LABEL, null);
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#to(java.lang.Enum)}
	 * for the {@link EnumType#ORDINAL} scenario.
	 */
	@Test
	public final void testToForOrdinal() {
		assertEquals("The result is not the expected one", ORDINA_FOR_ONE, EnumType.ORDINAL.to(TestEnum.ONE));
		assertEquals("The result is not the expected one", ORDINA_FOR_TWO, EnumType.ORDINAL.to(TestEnum.TWO));
		assertEquals("The result is not the expected one", ORDINA_FOR_THREE, EnumType.ORDINAL.to(TestEnum.THREE));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#to(java.lang.Enum)}
	 * for the {@link EnumType#STRING} scenario.
	 */
	@Test
	public final void testToForString() {
		assertEquals("The result is not the expected one", TestEnum.ONE.name(), EnumType.STRING.to(TestEnum.ONE));
		assertEquals("The result is not the expected one", TestEnum.TWO.name(), EnumType.STRING.to(TestEnum.TWO));
		assertEquals("The result is not the expected one", TestEnum.THREE.name(), EnumType.STRING.to(TestEnum.THREE));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#to(java.lang.Enum)}
	 * for the {@link EnumType#ORDINAL} scenario to verify if input argument check.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testToForOrdinalWithNullEnumType() throws SQLException {
		EnumType.ORDINAL.to(null);
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.EnumType#to(java.lang.Enum)}
	 * for the {@link EnumType#STRING} scenario to verify if input argument check.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testToForStringWithNullEnumType() throws SQLException {
		EnumType.STRING.to(null);
	}

	
	/**
	 * This enumration is the one used during the tests.
	 */
	private enum TestEnum {
		ONE, TWO, THREE
	}
	
	/**
	 * This property represents the {@link ResultSet} mocked-up instance used during the test.
	 */
	@Mock
	private static ResultSet resultSet;
	
	/**
	 * This {@link String} represents the column-label used to retrieve the first element in the {@link TestEnum}
	 * enumeration.
	 */
	private static final String COLUMN_ONE_LABEL = "test_one";
	
	/**
	 * This {@link String} represents the column-label used to retrieve the second element in the {@link TestEnum}
	 * enumeration.
	 */
	private static final String COLUMN_TWO_LABEL = "test_two";
	
	
	/**
	 * This {@link String} represents the column-label used to retrieve the third element in the {@link TestEnum}
	 * enumeration.
	 */
	private static final String COLUMN_THREE_LABEL = "test_three";
	
	/**
	 * This constant represent the ordinal position of {@link TestEnum#ONE} in the enumration {@link TestEnum}.
	 */
	private static final Integer ORDINA_FOR_ONE = 0;
	
	/**
	 * This constant represent the ordinal position of {@link TestEnum#TWO} in the enumration {@link TestEnum}.
	 */
	private static final Integer ORDINA_FOR_TWO = 1;
	
	/**
	 * This constant represent the ordinal position of {@link TestEnum#THREE} in the enumration {@link TestEnum}.
	 */
	private static final Integer ORDINA_FOR_THREE = 2;
	
	/**
	 * This {@link String} represents the column-label used to retrieve the <code>null</code> value from the
	 * {@link ResultSet} mocked-up instance.
	 */
	private static final String COLUMN_NULL_LABEL = "null";
	
}
