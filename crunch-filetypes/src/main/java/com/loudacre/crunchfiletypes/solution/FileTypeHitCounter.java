package com.loudacre.crunchfiletypes.solution;

import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.io.From;
import org.apache.crunch.io.To;
import org.apache.crunch.types.writable.Writables;
import org.apache.crunch.util.CrunchTool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class FileTypeHitCounter extends CrunchTool {
	private static final long serialVersionUID = 1L;

	public PTable<String, Long> countWords(PCollection<String> lines) {
		return lines.parallelDo("output hit types", new FileTypeHitFn(),
				Writables.strings()).count();
	}

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: FileTypeHitCounter <input dir> <output dir>\n");
			System.exit(-1);
		}

		// Create the list of counts for types of hits
		PCollection<String> lines = read(From.textFile(args[0]));
		PTable<String, Long> counts = countWords(lines);

		counts.write(To.textFile(args[1]));

		PipelineResult result = done();
		return result.succeeded() ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(), new FileTypeHitCounter(), args);
		System.exit(exitCode);
	}
}
