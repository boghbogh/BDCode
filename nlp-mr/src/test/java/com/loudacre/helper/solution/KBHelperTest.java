package com.loudacre.helper.solution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.loudacre.helper.KBHelper;

/**
 * Unit test for the KBHelper class.
 */
public class KBHelperTest {

	private KBHelper kbHelper;

	/*
	 * Set up the test. This method will be called before every test.
	 */
	@Before
	public void setUp() {
		kbHelper = new KBHelper();
	}

	/*
	 * Test a line that is not a tag
	 */
	@Test
	public void testKBHelperNotTag() {
		String line = "not a tag";

		assertNull("Line should be null", kbHelper.getTags(line));
	}

	/*
	 * Test a line that is a tag
	 */
	@Test
	public void testKBHelperTag() {
		String line = "<h4>Tags: changing, contact, the</h4>";

		assertEquals("Line did not match", "changing, contact, the", kbHelper.getTags(line));
	}
}
