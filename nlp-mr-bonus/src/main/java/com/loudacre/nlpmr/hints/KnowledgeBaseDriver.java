package com.loudacre.nlpmr.hints;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class KnowledgeBaseDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: KnowledgeBaseDriver <input dir> <output dir>\n");
			return -1;
		}

		Job job = Job.getInstance(getConf());
		// TODO: Set the Driver class
		job.setJarByClass(Class.class);
		// TODO: Set the Job Name
		job.setJobName("TODO");

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		// TODO: Set the classes with the correct ones
		job.setMapperClass(Mapper.class);
		job.setReducerClass(Reducer.class);

		// TODO: Set the Map Output classes
		job.setMapOutputKeyClass(Class.class);
		job.setMapOutputValueClass(Class.class);

		// TODO: Set the Output classes
		job.setOutputKeyClass(Class.class);
		job.setOutputValueClass(Class.class);

		boolean success = job.waitForCompletion(true);

		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(),
				new KnowledgeBaseDriver(), args);
		System.exit(exitCode);
	}
}
