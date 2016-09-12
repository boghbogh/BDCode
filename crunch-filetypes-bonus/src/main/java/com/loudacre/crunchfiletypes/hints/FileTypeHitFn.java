package com.loudacre.crunchfiletypes.hints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;

public class FileTypeHitFn extends DoFn<String, String> {
	private static final long serialVersionUID = 1L;

	/** Regex to find all extensions */
	Pattern extensionPattern = Pattern.compile("GET /.+\\.(\\w+) ");

	@Override
	public void process(String line, Emitter<String> emitter) {
		// Create the Matcher object to see if the line passes the Regex
		Matcher extensionMatcher = extensionPattern.matcher(line);

		if (extensionMatcher.find()) {
			// Pull out the extension and emit
			String extension = extensionMatcher.group(1);

			// TODO: Increment the group name and extension
		}
	}
}
