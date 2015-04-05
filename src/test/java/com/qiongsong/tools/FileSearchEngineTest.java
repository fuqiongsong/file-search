package com.qiongsong.tools;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qiongsong.tools.filesearch.config.IndexConfig;
import com.qiongsong.tools.filesearch.model.SearchFile;
import com.qiongsong.tools.filesearch.support.FileSearchEngine;

public class FileSearchEngineTest {
	FileSearchEngine engine;

	@Before
	public void before() {
		IndexConfig config = new IndexConfig();
		config.setIndexPath("C://index2");
		engine = new FileSearchEngine(config);
	}

	@Test
	public void test01() {
		List<SearchFile> files = engine.search("cns3013");
		for (SearchFile searchFile : files) {
			System.out.println(searchFile.getName()
					+ new Date(searchFile.getEditTime()));
		}
	}

	@After
	public void after() {
		engine.close();
	}
}
