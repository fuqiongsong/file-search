package com.qiongsong.tools.filesearch.utils;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

/**
 * File 操作集合
 *
 * @author thierry.fu
 * @Date 2015年4月6日
 */
public class FileUtil {

	/**
	 *
	 * @param file
	 * @return
	 */
	public static String file2String(File file) {
		Tika tika = new Tika();
		String s = null;
		try {
			s = tika.parseToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
		return s;
	}
}
