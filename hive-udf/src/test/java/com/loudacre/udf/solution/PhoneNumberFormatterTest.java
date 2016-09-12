package com.loudacre.udf.solution;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;
 
public class PhoneNumberFormatterTest {
	PhoneNumberFormatter dateDiff;
	
	@Before
	public void init() {
		dateDiff = new PhoneNumberFormatter();
	}
	
	@Test
	public void testPhoneNumber() {
		Text phoneNumber = dateDiff.evaluate(new Text("1234567890"));		
		assertEquals("Phone number did not match", "(123) 456-7890", phoneNumber.toString());
	}
	
	@Test
	public void testFormattedPhoneNumber() {
		Text phoneNumber = dateDiff.evaluate(new Text("123-456-7890"));		
		assertEquals("Phone number did not match", "(123) 456-7890", phoneNumber.toString());
	}
}
