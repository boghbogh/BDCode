package com.loudacre.udf.hints;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

// TODO: Fill out description
@Description(name = "TODO", value = "TODO")
public class PhoneNumberFormatter extends UDF {
	/** The left side of the string formatting */
	// TODO: Add the character for the left of the phone format
	private static final String LEFT = "TODO";

	/** The middle of the string formatting */
	// TODO: Add the characters for the middle of the phone format
	private static final String MIDDLE = "TODO";
	
	/** The middle of the string formatting */
	// TODO: Add the character for the right of the phone format
	private static final String RIGHT = "TODO";
	
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
