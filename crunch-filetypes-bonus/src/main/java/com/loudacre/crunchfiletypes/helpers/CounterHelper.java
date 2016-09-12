package com.loudacre.crunchfiletypes.helpers;

import java.util.Map;
import java.util.Set;

import org.apache.crunch.PipelineResult;
import org.apache.crunch.PipelineResult.StageResult;

/**
 * Helper class to make it easier to output counter results
 */
public class CounterHelper {
	/** The group name for the file types counter */
	public static final String COUNTER_GROUP_NAME = "filetypecounts";
	
	/**
	 * Outputs all counter results for the filetypecounts group
	 * 
	 * @param result
	 *            The pipeline that was run
	 */
	public static void outputCounterValues(PipelineResult result) {
		for (StageResult stageResult : result.getStageResults()) {
			Map<String, Set<String>> counters = stageResult.getCounterNames();

			for (String counter : counters.get(COUNTER_GROUP_NAME)) {
				long value = stageResult.getCounterValue(COUNTER_GROUP_NAME, counter);
				System.out.println("The value for " + counter + " was " + value);
			}
		}
	}
}
