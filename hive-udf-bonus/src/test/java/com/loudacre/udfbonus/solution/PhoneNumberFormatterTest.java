package com.loudacre.udfbonus.solution;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;

import com.loudacre.udfbonus.solution.PhoneNumberFormatter;
 
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
	
	@Test
	public void testPhoneNumberFormatted() {
		Text phoneNumber = dateDiff.evaluate(new Text("1234567890"), new Text("(XXX) XXX-XXXX"));
		assertEquals("Phone number did not match", "(123) 456-7890", phoneNumber.toString());
	}
	
	@Test
	public void testExtraFormattedPhoneNumber() {
		Text phoneNumber = dateDiff.evaluate(new Text("123-456-7890"), new Text("(XXX) XXX-XXXX XXXXXX"));
		assertEquals("Phone number did not match", "(123) 456-7890 XXXXXX", phoneNumber.toString());
	}
}
