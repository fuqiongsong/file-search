package com.qiongsong.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qiongsong.tools.filesearch.config.IndexConfig;
import com.qiongsong.tools.filesearch.support.IndexBuilder;

public class IndexBuilerTest {

	IndexBuilder builer;

	@Before
	public void before() {
		IndexConfig config = new IndexConfig();
		config.setIndexPath("C://index2");
		builer = new IndexBuilder(config);
	}

	@Test
	public void test01() {

		builer.index("C://2015-04-04");
	}

	@After
	public void after() {
		builer.close();
	}

}
