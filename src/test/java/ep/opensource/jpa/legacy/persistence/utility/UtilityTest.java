package ep.opensource.jpa.legacy.persistence.utility;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * This class tests the functionalities provided by the {@link Utility} class.
 */
public class UtilityTest {

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.utility.Utility#join(java.util.Collection)}.
	 */
	@Test
	public final void testJoinCollection() {
		assertEquals("The Utility.join(Collection) result doesn't match the expected one", LOI_JOIN_RESULT, Utility.join(LOI));
		assertEquals("The Utility.join(Collection) result doesn't match the expected one", LOS_JOIN_RESULT, Utility.join(LOS));
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.utility.Utility#join(java.util.Collection, java.lang.String)}.
	 */
	@Test
	public final void testJoinCollectionOfTString() {
		assertEquals("The Utility.join(Collection, String) result doesn't match the expected one", LOI_JOIN_COMMA_RESULT, Utility.join(LOI, COMMA));
		assertEquals("The Utility.join(Collection, String) result doesn't match the expected one", LOS_JOIN_COMMA_RESULT, Utility.join(LOS, COMMA));
	}

	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.utility.Utility#assertNotNull(java.lang.Object, java.lang.String)}
	 * with a <code>null</code> input.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testAssertNotNullWithNullArg() {
		Utility.assertNotNull(null, "NullAttribute");
		fail("An IllegalArgumentException is expected");
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.utility.Utility#assertNotNull(java.lang.Object, java.lang.String)}
	 * with a <code>null</code> input.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testAssertNotNullWithNullArgAndNullArgumentName() {
		Utility.assertNotNull(null, null);
		fail("An IllegalArgumentException is expected");
	}
	
	/**
	 * Test method for {@link ep.opensource.jpa.legacy.persistence.utility.Utility#assertNotNull(java.lang.Object, java.lang.String)}
	 * without a <code>null</code> input.
	 */
	@Test
	public final void testAssertNotNullWithoutNullArg() {
		Utility.assertNotNull(new Integer(0), "NotNullAttribute");
	}
	
	/**
	 * This constant is the separator used in the tests of the {@link Utility#join(java.util.Collection, String)} method.
	 */
	private static final String COMMA = ", ";

	/**
	 * This constant {@link List} represents a list of {@link Integer} used to test the {@link Utility#join(java.util.Collection)} and
	 * {@link Utility#join(java.util.Collection, String))} methods.
	 */
	private static final List<Integer> LOI = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7 ,8, 9));
	
	/**
	 * This constant {@link List} represents a list of {@link String} used to test the {@link Utility#join(java.util.Collection)} and
	 * {@link Utility#join(java.util.Collection, String))} methods.
	 */
	private static final List<String> LOS = Collections.unmodifiableList(Arrays.asList("one", "two", "three", "four"));
	
	/**
	 * This constant {@link String} represents the result of the {@link Utility#join(java.util.Collection)} call over
	 * {@link UtilityTest#LOI} {@link List}.
	 */
	private static final String LOI_JOIN_RESULT = "0123456789";
	
	/**
	 * This constant {@link String} represents the result of the {@link Utility#join(java.util.Collection)} call over
	 * {@link UtilityTest#LOS} {@link List}.
	 */
	private static final String LOS_JOIN_RESULT = "onetwothreefour";
	
	/**
	 * This constant {@link String} represents the result of the {@link Utility#join(java.util.Collection, String)} call over
	 * {@link UtilityTest#LOI} {@link List}.
	 */
	private static final String LOI_JOIN_COMMA_RESULT = "0, 1, 2, 3, 4, 5, 6, 7, 8, 9";
	
	/**
	 * This constant {@link String} represents the result of the {@link Utility#join(java.util.Collection, String)} call over
	 * {@link UtilityTest#LOS} {@link List}.
	 */
	private static final String LOS_JOIN_COMMA_RESULT = "one, two, three, four";
}
