package com.qiongsong.tools.filesearch.config;

import org.apache.lucene.util.Version;

/**
 * Index 配置
 *
 * @author thierry.fu
 * @Date 2015年4月6日
 */
public class IndexConfig {

	private Version luceneVersion = Version.LUCENE_47;

	private String indexPath;

	public Version getLuceneVersion() {
		return luceneVersion;
	}

	public void setLuceneVersion(Version luceneVersion) {
		this.luceneVersion = luceneVersion;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

}
