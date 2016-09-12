package com.loudacre.nlpmr.solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import com.loudacre.data.WordAndCountWritable;
import com.loudacre.nlpmr.solution.StemMapper;
import com.loudacre.nlpmr.solution.StemSumReducer;

public class StemMapReduceTest {

	MapDriver<LongWritable, Text, Text, WordAndCountWritable> mapDriver;
	ReduceDriver<Text, WordAndCountWritable, Text, Text> reduceDriver;
	MapReduceDriver<LongWritable, Text, Text, WordAndCountWritable, Text, Text> mapReduceDriver;

	/*
	 * Set up the test. This method will be called before every test.
	 */
	@Before
	public void setUp() {
		// Set up phase 1 tests
		// Set up the mapper test harness.
		StemMapper stemMapper = new StemMapper();
		mapDriver = new MapDriver<LongWritable, Text, Text, WordAndCountWritable>();
		mapDriver.setMapper(stemMapper);

		// Set up the reducer test harness.
		StemSumReducer stemReducer = new StemSumReducer();
		reduceDriver = new ReduceDriver<Text, WordAndCountWritable, Text, Text>();
		reduceDriver.setReducer(stemReducer);

		// Set up the mapper/reducer test harness.
		mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, WordAndCountWritable, Text, Text>();
		mapReduceDriver.setMapper(stemMapper);
		mapReduceDriver.setReducer(stemReducer);
	}

	/*
	 * Test the mapper.
	 */
	@Test
	public void testStemMapper() throws IOException {
		mapDriver.withInput(new LongWritable(1), new Text("<h4>Tags: cat, cat, dog</h4>"));

		mapDriver.withOutput(new Text("cat"), new WordAndCountWritable("cat", 1));
		mapDriver.withOutput(new Text("cat"), new WordAndCountWritable("cat", 1));
		mapDriver.withOutput(new Text("dog"), new WordAndCountWritable("dog", 1));

		mapDriver.runTest();
	}

	/*
	 * Test the reducer.
	 */
	@Test
	public void testStemReducer() throws IOException {

		List<WordAndCountWritable> values = new ArrayList<WordAndCountWritable>();
		values.add(new WordAndCountWritable("cat", 1));
		values.add(new WordAndCountWritable("cat", 1));

		reduceDriver.withInput(new Text("cat"), values);

		reduceDriver.withOutput(new Text("cat"), new Text("2 - cat,cat,"));

		reduceDriver.runTest();
	}

	/*
	 * Test the mapper and reducer working together.
	 */
	@Test
	public void testStemMapReduce() throws IOException {
		mapReduceDriver.withInput(new LongWritable(1), new Text("<h4>Tags: cat, cats, dog</h4>"));

		mapReduceDriver.addOutput(new Text("cat"), new Text("2 - cat,cats,"));
		mapReduceDriver.addOutput(new Text("dog"), new Text("1 - dog,"));

		mapReduceDriver.runTest();
	}
}
