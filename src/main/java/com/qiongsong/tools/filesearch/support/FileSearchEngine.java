package com.qiongsong.tools.filesearch.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.qiongsong.tools.filesearch.config.IndexConfig;
import com.qiongsong.tools.filesearch.config.IndexType;
import com.qiongsong.tools.filesearch.model.SearchFile;

/**
 *
 * @author thierry.fu
 * @Date 2015年4月6日
 */
public class FileSearchEngine {

	private static final Logger log = Logger.getLogger(FileSearchEngine.class);

	private IndexConfig indexConfig;

	private Directory directory;
	private IndexReader indexReader;
	private IndexSearcher indexSearcher;

	private void init() {
		try {
			directory = FSDirectory.open(new File(indexConfig.getIndexPath()));
			indexReader = DirectoryReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		indexSearcher = new IndexSearcher(indexReader);
	}

	public FileSearchEngine(IndexConfig indexConfig) {
		super();
		this.indexConfig = indexConfig;
		init();
	}

	public List<SearchFile> search(String value) {
		value = value.toLowerCase();
		Query q1 = new WildcardQuery(new Term(IndexType.NAME, value));
		Query q2 = new WildcardQuery(new Term(IndexType.CONTENT, value));
		BooleanQuery query = new BooleanQuery();
		query.add(q1, Occur.SHOULD);
		query.add(q2, Occur.SHOULD);
		List<SearchFile> list = new ArrayList<SearchFile>();
		try {
			TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
			ScoreDoc[] sds = topDocs.scoreDocs;
			log.info("find " + sds.length + " results");
			for (ScoreDoc s : sds) {
				Document doc = indexSearcher.doc(s.doc);
				String name = doc.get(IndexType.NAME);
				String path = doc.get(IndexType.PATH);
				String sizeStr = doc.get(IndexType.SIZE);
				String lastModifiedTimeStr = doc.get(IndexType.MODIFIFYTIME);
				long size = 0L;
				long lastModifiedTime = 0L;
				if (sizeStr != null) {
					size = Long.parseLong(sizeStr);
				}
				if (lastModifiedTimeStr != null) {
					lastModifiedTime = Long.parseLong(lastModifiedTimeStr);
				}
				SearchFile sf = new SearchFile(name, path, size,
						lastModifiedTime);
				list.add(sf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void close() {
		try {
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			indexReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
