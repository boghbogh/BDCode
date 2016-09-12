package com.loudacre.crunchlogs.solution;

import static org.junit.Assert.assertEquals;

import org.apache.crunch.PCollection;
import org.apache.crunch.impl.mem.MemPipeline;
import org.apache.crunch.types.writable.Writables;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class KnowledgeBaseTest {
	@Before
	public void setUp() throws Exception {
		MemPipeline.clearCounters();
	}
	
	@Test
	public void testCountKnowledgeBase() {
		PCollection<String> inputStrings = MemPipeline
				.collectionOf("165.32.101.206 - 8 [15/Sep/2013:23:59:50 +0100] GET /KBDOC-00001.html HTTP/1.0\" 200 17446 \"http://www.loudacre.com\"  \"Loudacre CSR Browser\"");
		PCollection<String> fileTypeStrings = inputStrings.parallelDo(new WebLogFn(), Writables.strings());

		assertEquals("Knowledge Base article did not match", ImmutableList.of("KBDOC-00001.html"),
				Lists.newArrayList(fileTypeStrings.materialize()));
	}

	@Test
	public void testNonKnowledgeBase() {
		PCollection<String> inputStrings = MemPipeline
				.collectionOf("165.32.101.206 - 8 [15/Sep/2013:23:59:50 +0100] GET /ANOTHERDOC.html HTTP/1.0\" 200 17446 \"http://www.loudacre.com\"  \"Loudacre CSR Browser\"");
		PCollection<String> fileTypeStrings = inputStrings.parallelDo(new WebLogFn(), Writables.strings());

		assertEquals("Should not have emitted a non-Knowledge Base article", ImmutableList.of(),
				Lists.newArrayList(fileTypeStrings.materialize()));
	}
}
