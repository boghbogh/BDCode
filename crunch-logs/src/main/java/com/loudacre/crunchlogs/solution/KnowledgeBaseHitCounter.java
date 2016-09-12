package com.loudacre.crunchlogs.solution;

import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.io.From;
import org.apache.crunch.io.To;
import org.apache.crunch.types.writable.Writables;
import org.apache.crunch.util.CrunchTool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class KnowledgeBaseHitCounter extends CrunchTool {
	private static final long serialVersionUID = 1L;

	/**
	 * Counts the number of hits for Knowledge Base articles
	 * 
	 * @param lines
	 *            The PCollection with the hit lines
	 * @return A PTable with the Knowledge Base article and the number of hits it had
	 */
	public PTable<String, Long> countHits(PCollection<String> lines) {
		return lines.parallelDo("output most hit knowledge base articles", new WebLogFn(), Writables.strings()).count();
	}

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: KnowledgeBaseHitCounter <input dir> <output dir>\n");
			System.exit(-1);
		}

		int maximumResults = Integer.parseInt(getConf().get("maximumresults", "20"));

		// Create the list of counts for Knowledge Base articles
		PCollection<String> lines = read(From.textFile(args[0]));
		PTable<String, Long> counts = countHits(lines);

		// Get the top N articles and write them out
		PTable<String, Long> topArticles = counts.top(maximumResults);
		topArticles.write(To.textFile(args[1]));

		PipelineResult result = done();
		return result.succeeded() ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(), new KnowledgeBaseHitCounter(), args);
		System.exit(exitCode);
	}
}
