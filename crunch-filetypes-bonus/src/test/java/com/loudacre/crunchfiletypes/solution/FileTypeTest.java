package com.loudacre.crunchfiletypes.solution;

import static org.junit.Assert.assertEquals;

import org.apache.crunch.PCollection;
import org.apache.crunch.impl.mem.MemPipeline;
import org.apache.crunch.types.writable.Writables;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.loudacre.crunchfiletypes.helpers.CounterHelper;
import com.loudacre.crunchfiletypes.solution.FileTypeHitFn;

public class FileTypeTest {
	@Before
	public void setUp() throws Exception {
		MemPipeline.clearCounters();
	}

	@Test
	public void testBadCountWords() {
		PCollection<String> inputStrings = MemPipeline
				.collectionOf("165.32.101.206 - 8 [15/Sep/2013:23:59:50 +0100] GET /WILLNOTFIND HTTP/1.0\" 200 17446 \"http://www.loudacre.com\"  \"Loudacre CSR Browser\"");
		PCollection<String> fileTypeStrings = inputStrings.parallelDo(new FileTypeHitFn(), Writables.strings());
		
		assertEquals("File type still emitted", ImmutableList.of(), Lists.newArrayList(fileTypeStrings.materialize()));
		assertEquals("Increment did not match", 0L,
				MemPipeline.getCounters().findCounter(CounterHelper.COUNTER_GROUP_NAME, "html").getValue());
	}

	@Test
	public void testIncrements() {
		PCollection<String> inputStrings = MemPipeline
				.collectionOf("165.32.101.206 - 8 [15/Sep/2013:23:59:50 +0100] GET /index.html HTTP/1.0\" 200 17446 \"http://www.loudacre.com\"  \"Loudacre CSR Browser\"");
		PCollection<String> fileTypeStrings = inputStrings.parallelDo(new FileTypeHitFn(), Writables.strings());
		
		assertEquals("File type still emitted", ImmutableList.of(), Lists.newArrayList(fileTypeStrings.materialize()));
		assertEquals("Increment did not match", 1L,
				MemPipeline.getCounters().findCounter(CounterHelper.COUNTER_GROUP_NAME, "html").getValue());
	}
}
