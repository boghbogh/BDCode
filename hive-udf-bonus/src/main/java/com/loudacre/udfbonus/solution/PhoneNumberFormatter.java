package com.loudacre.udfbonus.solution;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(name = "phonenumberformatter", value = "_FUNC_(phonenumber) - formats a phone number to \"(XXX) XXX-XXXX\" and removes any existing formatting." +
		"_FUNC_(phonenumber, format) - formats a phone number to the format specified. Use a capital letter X to indicate where a number should go.",
		extended = "Example:\n"
		+ " > SELECT _FUNC_(phonenumber) FROM src;\n"
		+ " > SELECT _FUNC_(phonenumber, \"(XXX) XXX-XXXX\") FROM src;")
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
				builder.append(phoneNumber.charAt(phoneNumberIndex));
				phoneNumberIndex++;
			} else {
				// The current character should not be replaced
				builder.append(currentChar);
			}
		}

		// Set the new value in the Text object
		formattedPhoneNumber.set(builder.toString());
		
		return formattedPhoneNumber;
	}
}
