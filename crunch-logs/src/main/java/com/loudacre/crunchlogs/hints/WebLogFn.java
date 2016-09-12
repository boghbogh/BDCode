package com.loudacre.crunchlogs.hints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;

public class WebLogFn extends DoFn<String, String> {
	private static final long serialVersionUID = 1L;

	/** Regex to find all Knowledge Base articles */
	Pattern articlePattern = Pattern.compile("KBDOC-\\d+\\.html");

	@Override
	public void process(String line, Emitter<String> emitter) {
		// Create the Matcher object to see if the line passes the Regex
		Matcher articleMatcher = articlePattern.matcher(line);

		if (articleMatcher.find()) {
			// Pull out the Knowledge Base article name and emit
			String knowledgeBaseArticle = articleMatcher.group();

			// TODO: Emit knowledge base article
		}
	}
}
