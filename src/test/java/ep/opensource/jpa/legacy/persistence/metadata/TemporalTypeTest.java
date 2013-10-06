package ep.opensource.jpa.legacy.persistence.metadata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This test verifies that the {@link TemporalType} instances work correctly.
 */
@RunWith(MockitoJUnitRunner.class) 
public class TemporalTypeTest {
	/**
	 * This method setups the {@link ResultSet} mocked-up instance for the tests.
	 * 
	 * @throws SQLException
	 */
	@Before
	public void setUp() throws SQLException {
		when(resultSet.getDate(COLUMN_LABEL)).thenReturn(NOW_SQL_DATE);
		when(resultSet.getTime(COLUMN_LABEL)).thenReturn(NOW_SQL_TIME);
		when(resultSet.getTimestamp(COLUMN_LABEL)).thenReturn(NOW_SQL_TIMESTAMP);
		
		when(resultSet.getDate(COLUMN_NULL_LABEL)).thenReturn(null);
		when(resultSet.getTime(COLUMN_NULL_LABEL)).thenReturn(null);
		when(resultSet.getTimestamp(COLUMN_NULL_LABEL)).thenReturn(null);
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TemporalType#from(java.sql.ResultSet, String)}
	 * for the value {@link TemporalType#DATE}.
	 * 
	 * @throws SQLException 
	 */
	@Test
	public final void testFromForDATE() throws SQLException {
		assertEquals("The result is not the expected one", NOW_SQL_TIME, TemporalType.DATE.from(resultSet, COLUMN_LABEL));
		assertEquals("The result is not the expected one", null, TemporalType.DATE.from(resultSet, COLUMN_NULL_LABEL));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TemporalType#from(java.sql.ResultSet, String)}
	 * for the value {@link TemporalType#TIME}.
	 * 
	 * @throws SQLException 
	 */
	@Test
	public final void testFromForTIME() throws SQLException {
		assertEquals("The result is not the expected one", NOW_SQL_TIME, TemporalType.TIME.from(resultSet, COLUMN_LABEL));
		assertEquals("The result is not the expected one", null, TemporalType.TIME.from(resultSet, COLUMN_NULL_LABEL));
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.metadata.TemporalType#from(java.sql.ResultSet, String)}
	 * for the value {@link TemporalType#TIMESTAMP}.
	 * @throws SQLException 
	 */
	@Test
	public final void testFromForTIMESTAMP() throws SQLException {
		assertEquals("The result is not the expected one", NOW_SQL_TIME, TemporalType.TIMESTAMP.from(resultSet, COLUMN_LABEL));
		assertEquals("The result is not the expected one", null, TemporalType.TIMESTAMP.from(resultSet, COLUMN_NULL_LABEL));
	}
	
	/**
	 * This property represents the {@link ResultSet} mocked-up instance used during the test.
	 */
	@Mock
	private static ResultSet resultSet;
	
	/**
	 * This property is the date and time at the time of the test. It used to setup the <code>java.sql.*</code>
	 * mocked-up results. 
	 */
	private static final Calendar NOW = GregorianCalendar.getInstance();
	
	/**
	 * This property represents the {@link java.sql.Date} instance used during the test.
	 * It's based on {@link TemporalTypeTest#NOW}.
	 */
	private static final java.sql.Date NOW_SQL_DATE = new java.sql.Date(NOW.getTimeInMillis());
	
	/**
	 * This property represents the {@link java.sql.Time} instance used during the test.
	 * It's based on {@link TemporalTypeTest#NOW}.
	 */
	private static final java.sql.Time NOW_SQL_TIME = new java.sql.Time(NOW.getTimeInMillis());
	
	/**
	 * This property represents the {@link java.sql.Timestamp} instance used during the test. 
	 * It's based on {@link TemporalTypeTest#NOW}.
	 */
	private static final java.sql.Timestamp NOW_SQL_TIMESTAMP = new java.sql.Timestamp(NOW.getTimeInMillis());
	
	/**
	 * This {@link String} represents the column-label used to retrieve the <code>java.sql.*</code>
	 * mocked-up instances.
	 */
	private static final String COLUMN_LABEL = "test";
	
	/**
	 * This {@link String} represents the column-label used to retrieve the <code>null</code> value from the
	 * {@link ResultSet} mocked-up instance.
	 */
	private static final String COLUMN_NULL_LABEL = "null";
}
