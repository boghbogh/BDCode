package com.loudacre.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * Writable for word counts
 * 
 */
public class WordAndCountWritable implements Writable {
	/** The word for the count */
	private String word;
	/** The count for the word */
	private int count;

	/**
	 * Constructor
	 * 
	 * @param word
	 *            The word for the count
	 * @param count
	 *            The count for the word
	 */
	public WordAndCountWritable(String word, int count) {
		this.word = word;
		this.count = count;
	}

	/** Empty constructor for serialization */
	public WordAndCountWritable() {

	}

	@Override
	public void readFields(DataInput input) throws IOException {
		word = input.readUTF();
		count = input.readInt();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(word);
		output.writeInt(count);
	}

	/**
	 * Gets the word for the count
	 * 
	 * @return The word for the count
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Gets the count for the word
	 * 
	 * @return The count for the word
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Deep clones the object
	 */
	public WordAndCountWritable clone() {
		return new WordAndCountWritable(word, count);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WordAndCountWritable) {
			WordAndCountWritable other = (WordAndCountWritable) obj;
			
			if (other.count == count && other.word.equals(word)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		// Hashcode overridden because MRUnit needs it
		return word.hashCode();
	}

	@Override
	public String toString() {
		return word + ":" + count;
	}
}
