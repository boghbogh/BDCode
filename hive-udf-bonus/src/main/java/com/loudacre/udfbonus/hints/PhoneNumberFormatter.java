package com.loudacre.udfbonus.hints;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

// TODO: Fill out description
@Description(name = "TODO", value = "TODO")
public class PhoneNumberFormatter extends UDF {
	/** The left side of the string formatting */
	private static final String LEFT = "(";

	/** The middle of the string formatting */
	private static final String MIDDLE = ") ";
	
	/** The middle of the string formatting */
	private static final String RIGHT = "-";
	
	/** The text object that is returned */
	private Text formattedPhoneNumber = new Text();
	
	private static final char NUMBER_CHAR = 'X';
	
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
	
	public Text evaluate(Text phoneNumberText, Text phoneNumberFormatText) {
		String phoneNumber = phoneNumberText.toString();
		String phoneNumberFormat = phoneNumberFormatText.toString();

		// Remove all non-numeric characters
		phoneNumber = phoneNumber.replaceAll("\\D", "");

		// Format the phone number
		StringBuilder builder = new StringBuilder(phoneNumberFormat.length());
		
		int phoneNumberIndex = 0;
		
		for (int i = 0; i < phoneNumberFormat.length(); i++) {
			char currentChar = phoneNumberFormat.charAt(i);
			
			if (currentChar == NUMBER_CHAR && phoneNumberIndex < phoneNumber.length()) {
				// The current character should be replaced with a number, replace with the phone number
				// TODO: Append the phone number character at phoneNumberIndex index
				builder.append("TODO");
				phoneNumberIndex++;
			} else {
				// The current character should not be replaced
				// TODO: Append the current character
				builder.append("TODO");
			}
		}

		// Set the new value in the Text object
		formattedPhoneNumber.set(builder.toString());
		
		return formattedPhoneNumber;
	}
}
