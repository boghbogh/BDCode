package com.loudacre.helper;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * Utility class to make it easier to do NLP on text and parsing.
 */
public class KBHelper {
	SnowballStemmer stemmer = new englishStemmer();
	
	/**
	 * Checks to see if the line is the tags line
	 * 
	 * @param line
	 *            The line of HTML
	 * @return True if it is the tags line
	 */
	private boolean isTagLine(String line) {
		return line.contains("<h4>tags:");
	}

	/**
	 * Gets the tags if the line is a tag line
	 * 
	 * @param line
	 *            The line of HTML
	 * @return Null if the line is not a tag line. Otherwise a string with the HTML tags stripped.
	 */
	public String getTags(String line) {
		// Normalize and trim
		line = line.toLowerCase().trim();

		if (isTagLine(line)) {
			return line.replaceAll("<h4>tags: ", "").replaceAll("</h4>", "");
		} else {
			return null;
		}
	}
	
	/**
	 * Takes in a line of multiple sentences and breaks them in to individual
	 * words
	 * 
	 * @param line
	 *            The line of text
	 * @return A String array with all of the individual words
	 */
	public String[] getWords(String line) {
		return line.split("[^A-Za-z]+");
	}

	/**
	 * Gets the stem for a word
	 * 
	 * @param word
	 *            The word to stem
	 * @return The stem for the word
	 */
	public String getStem(String word) {
		stemmer.setCurrent(word);
		stemmer.stem();

		String stem = stemmer.getCurrent();

		return stem;
	}
}
