package com.loudacre.nlpmr.stubs;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class StemSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
			InterruptedException {
		// The number of times the word appeared
		int wordCount = 0;
		// Go through all words to find the total
		for (IntWritable value : values) {
			wordCount += value.get();
		}
		// Emit the stem and the total count
		context.write(key, new IntWritable(wordCount));
	}
}
