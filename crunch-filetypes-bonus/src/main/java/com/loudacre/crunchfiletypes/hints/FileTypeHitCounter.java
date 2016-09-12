package com.loudacre.crunchfiletypes.hints;

import org.apache.crunch.PCollection;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.io.From;
import org.apache.crunch.io.To;
import org.apache.crunch.types.writable.Writables;
import org.apache.crunch.util.CrunchTool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import com.loudacre.crunchfiletypes.helpers.CounterHelper;
import com.loudacre.crunchfiletypes.solution.FileTypeHitFn;

public class FileTypeHitCounter extends CrunchTool {
	private static final long serialVersionUID = 1L;

	public PCollection<String> countWords(PCollection<String> lines) {
		return lines.parallelDo("output hit types", new FileTypeHitFn(), Writables.strings());
	}

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: FileTypeHitCounter <input dir> <output dir>\n");
			System.exit(-1);
		}

		// Create the list of counts for types of hits
		PCollection<String> lines = read(From.textFile(args[0]));
		PCollection<String> counts = countWords(lines);

		counts.write(To.textFile(args[1]));
		
		PipelineResult result = done();
		
		// TODO: Output the counter values
		
		return result.succeeded() ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(), new FileTypeHitCounter(), args);
		System.exit(exitCode);
	}
}
