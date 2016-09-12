package com.loudacre.nlpmr.stubs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class KnowledgeBaseDriver extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {
		if (arg0.length!=2)
		{
			System.out.printf("Usage: KnowledgeBaseDriver <input dir> <output dir>\n");
			return -1;
		}
		
		Job job=Job.getInstance(getConf());
		job.setJarByClass(KnowledgeBaseDriver.class);
		job.setJobName("KB Tag Counter");

		FileInputFormat.setInputPaths(job, new Path(arg0[0]));
		FileOutputFormat.setOutputPath(job, new Path(arg0[1]));

		job.setMapperClass(StemMapper.class);
		job.setReducerClass(StemSumReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		boolean success = job.waitForCompletion(true);

		return success ? 0 : 1;
		
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(), new KnowledgeBaseDriver(), args);
		System.exit(exitCode);
	}

}
