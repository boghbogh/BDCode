package com.loudacre.nlpmr.hints;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.loudacre.data.WordAndCountWritable;

/**
 * Sums all of the words
 */
public class StemSumReducer extends Reducer<Text, WordAndCountWritable, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<WordAndCountWritable> values, Context context) throws IOException,
			InterruptedException {
		// The number of times the word appeared
		int wordCount = 0;
		
		StringBuilder output = new StringBuilder();
		output.append(" - ");
		
		// Go through all words to find the total
		for (WordAndCountWritable value : values) {
			wordCount += value.getCount();
			
			output.append(value.getWord());
			output.append(",");
		}
		
		// TODO: Emit the stem, the total count, and the list of words for the stem
	}
}
