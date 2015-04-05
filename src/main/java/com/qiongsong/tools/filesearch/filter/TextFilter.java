package com.qiongsong.tools.filesearch.filter;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author thierry.fu
 * @Date 2015年4月5日
 */
public class TextFilter implements FileFilter {

	public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
			return true;
		}
		if (pathname.isHidden()) {
			return false;
		}
		String name = pathname.getName();
		if (name.equalsIgnoreCase("txt")) {
			return true;
		}
		return false;
	}

}
