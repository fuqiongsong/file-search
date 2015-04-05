package com.qiongsong.tools.filesearch.support;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * 通过路径获取完整文件目录下的所有文件
 *
 * @author thierry.fu
 * @Date 2015年4月5日
 */
public class FileLoader {

	private static final Logger log = Logger.getLogger(FileLoader.class);

	private String fileName;

	private FileFilter defaultFilter;

	public FileLoader(String fileName) {
		this.fileName = fileName;
		defaultFilter = new DefaultFilter();
		reload();
	}

	private LinkedList<String> fileList = new LinkedList<String>();

	/**
	 *
	 * @param filename
	 */
	public synchronized void push(String filename) {
		log.debug("file:" + filename + " loaded.");
		fileList.push(filename);
	}

	/**
	 *
	 * @return
	 */
	public synchronized String pop() {
		return fileList.pop();
	}

	/**
	 *
	 * @return
	 */
	public synchronized int fileNums() {
		return fileList.size();
	}

	/*
	 *
	 */
	public void reload() {
		reload(null);
	}

	/**
	 *
	 * @param filter
	 */
	public void reload(FileFilter filter) {

		fileList.clear();

		File file = new File(fileName);

		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			push(file.getAbsolutePath());
			return;
		}
		if (null != filter) {
			defaultFilter = filter;
		}
		LinkedList<File> list = new LinkedList<File>();
		File[] files = file.listFiles(defaultFilter);

		/**
		 * 第一次过滤
		 */
		for (File f : files) {
			if (f.isHidden()) {
				continue;
			}
			if (f.isDirectory()) {
				list.add(f);
			} else {
				push(f.getAbsolutePath());
			}
		}

		/**
		 * 遍历操作
		 */
		while (!list.isEmpty()) {
			File one = list.removeFirst();
			File[] tmps = one.listFiles(defaultFilter);
			for (File f : tmps) {
				if (f.isHidden()) {
					continue;
				}
				if (f.isDirectory()) {
					list.add(f);
				} else {
					push(f.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 过滤文件接收HTMl以及TXT
	 *
	 * @author thierry.fu
	 * @Date 2015年4月5日
	 */
	static class DefaultFilter implements FileFilter {

		public boolean accept(File pathname) {
			return true;
		}
	}
}
