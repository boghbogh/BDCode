package com.loudacre.nlpmr.solution;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.loudacre.data.WordAndCountWritable;
import com.loudacre.helper.KBHelper;

/**
 * Takes in a line and breaks it in to words.
 */
public class StemMapper extends Mapper<LongWritable, Text, Text, WordAndCountWritable> {
	Logger logger = Logger.getLogger(StemMapper.class);

	/** The KBHelper object for tags and NLP */
	KBHelper kbHelper = new KBHelper();

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = kbHelper.getTags(value.toString());

		// See if it is the tags line
		if (line == null) {
			// Line is not the tags, skip it
			return;
		}

		// Convert the line to words
		String[] words = kbHelper.getWords(line);

		for (String word : words) {
			// Get the stem for the word
			String stem = kbHelper.getStem(word);

			// Emit the stem, word and count
			context.write(new Text(stem), new WordAndCountWritable(word, 1));
		}
	}
}
