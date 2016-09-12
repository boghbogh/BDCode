package com.loudacre.udf.solution;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(name = "phonenumberformatter", value = "_FUNC_(phonenumber) - formats a phone number to \"(XXX) XXX-XXXX\" and removes any existing formatting.", extended = "Example:\n"
		+ " > SELECT _FUNC_(phonenumber) FROM src;")
public class PhoneNumberFormatter extends UDF {
	/** The left side of the string formatting */
	private static final String LEFT = "(";

	/** The middle of the string formatting */
	private static final String MIDDLE = ") ";
	
	/** The middle of the string formatting */
	private static final String RIGHT = "-";
	
	/** The text object that is returned */
	private Text formattedPhoneNumber = new Text();
	
	public Text evaluate(Text phoneNumberText) {
		String phoneNumber = phoneNumberText.toString();

		// Remove all non-numeric characters
		phoneNumber = phoneNumber.replaceAll("\\D", "");

		// Format the phone number
		StringBuilder builder = new StringBuilder(10);
		builder.append(LEFT);
		builder.append(phoneNumber.substring(0, 3));
		builder.append(MIDDLE);
		builder.append(phoneNumber.substring(3, 6));
		builder.append(RIGHT);
		builder.append(phoneNumber.substring(6, 10));

		// Set the new value in the Text object
		formattedPhoneNumber.set(builder.toString());
		
		return formattedPhoneNumber;
	}
}
