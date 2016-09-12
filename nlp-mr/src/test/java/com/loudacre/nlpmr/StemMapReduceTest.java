package com.loudacre.nlpmr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import com.loudacre.nlpmr.solution.StemMapper;
import com.loudacre.nlpmr.solution.StemSumReducer;

/**
 * This class illustrates how you can test a Mapper and Reducer 
 * (either in isolation or in conjunction with one another) using 
 * the <a href="http://mrunit.apache.org">Apache MRUnit</a> library.
 */
public class StemMapReduceTest {

	// used to test the mapper by itself
	private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;

	// used to test the reducer by itself
	private ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;

	// used to test the mapper and reducer together
	private MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

	/*
	 * This method will be called before every test, so we will 
	 * instantiate and configure the test-related code here.
	 */
	@Before
	public void setUp() {
		// Set up the harness for the mapper test
		mapDriver = new MapDriver<LongWritable, Text, Text, IntWritable>();
		mapDriver.setMapper(new StemMapper());

		// Set up the harness for the reducer test
		reduceDriver = new ReduceDriver<Text, IntWritable, Text, IntWritable>();
		reduceDriver.setReducer(new StemSumReducer());

		// Set up the harness used to test the entire job
		mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable>();
		mapReduceDriver.setMapper(new StemMapper());
		mapReduceDriver.setReducer(new StemSumReducer());
	}

	/*
	 * Verify that the mapper works as expected.
	 */
	@Test
	public void testStemMapper() throws IOException {
		mapDriver.withInput(new LongWritable(1), new Text("<h4>Tags: cat, cat, dog</h4>"));

		mapDriver.withOutput(new Text("cat"), new IntWritable(1));
		mapDriver.withOutput(new Text("cat"), new IntWritable(1));
		mapDriver.withOutput(new Text("dog"), new IntWritable(1));

		mapDriver.runTest();
	}

	/*
	 * Verify that the reducer works as expected
	 */
	@Test
	public void testStemReducer() throws IOException {
		List<IntWritable> values = new ArrayList<IntWritable>();
		values.add(new IntWritable(1));
		values.add(new IntWritable(1));

		reduceDriver.withInput(new Text("cat"), values);

		reduceDriver.withOutput(new Text("cat"), new IntWritable(2));

		reduceDriver.runTest();
	}

	/*
	 * Verify that the mapper and reducer work together as expected.
	 */
	@Test
	public void testStemMapReduce() throws IOException {
		mapReduceDriver.withInput(new LongWritable(1), new Text("<h4>Tags: cat, cat, dog</h4>"));

		mapReduceDriver.addOutput(new Text("cat"), new IntWritable(2));
		mapReduceDriver.addOutput(new Text("dog"), new IntWritable(1));

		mapReduceDriver.runTest();
	}
}
