package com.qiongsong.tools;

import junit.framework.Assert;

import org.junit.Test;

import com.qiongsong.tools.filesearch.support.FileLoader;

public class FileLoaderTest {

	@Test
	public void test01() {
		FileLoader fileUtil = new FileLoader("C:\\dd2015-04-04\\");
		fileUtil.reload();
		int num = fileUtil.fileNums();
		Assert.assertEquals(0, num);
	}

	@Test
	public void test02() {
		FileLoader fileUtil = new FileLoader("C:\\2015-04-04\\");
		fileUtil.reload();
		int num = fileUtil.fileNums();
		Assert.assertEquals(261, num);
	}
}
