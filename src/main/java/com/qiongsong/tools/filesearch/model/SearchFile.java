package com.qiongsong.tools.filesearch.model;

/**
 *
 * @author thierry.fu
 * @Date 2015年4月5日
 */
public class SearchFile {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getEditTime() {
		return editTime;
	}

	public void setEditTime(long editTime) {
		this.editTime = editTime;
	}

	public SearchFile(String name, String path, long size, long editTime) {
		super();
		this.name = name;
		this.path = path;
		this.size = size;
		this.editTime = editTime;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", path=" + path + ", size=" + size
				+ ", editTime=" + editTime + "]";
	}

	private String name;

	private String path;

	private long size;

	private long editTime;
}
