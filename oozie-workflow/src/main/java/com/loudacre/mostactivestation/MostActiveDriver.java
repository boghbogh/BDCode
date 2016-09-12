package com.loudacre.mostactivestation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.loudacre.data.BaseStation;

public class MostActiveDriver extends Configured implements Tool {
	Logger logger = Logger.getLogger(MostActiveDriver.class);
	
	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.printf("Usage: MostActiveDriver <input dir> <output dir> <basestation>\n");
			return -1;
		}

		// Define the parameters
		getConf().setIfUnset("basestationlist", args[2]);
		getConf().setIfUnset("maxstations", "5");
		getConf().setIfUnset("timewindow", "60");
		
		// Verify the parameters
		Integer.parseInt(getConf().get("maxstations"));
		Integer.parseInt(getConf().get("timewindow"));
		
		// Create the job
		Job job = Job.getInstance(getConf());
		job.setJarByClass(MostActiveDriver.class);
		job.setJobName("Most Active Station By Time");

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(DistanceMapper.class);
		job.setReducerClass(ActivityByStationReducer.class);

		// Use TSV as the input
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		// Use TSV as the output
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(BaseStation.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(), new MostActiveDriver(), args);
		System.exit(exitCode);
	}
}
