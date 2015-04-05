package com.qiongsong.tools.filesearch.support;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.qiongsong.tools.filesearch.config.IndexConfig;
import com.qiongsong.tools.filesearch.config.IndexType;
import com.qiongsong.tools.filesearch.utils.FileUtil;

/**
 * Index Builder
 *
 * @author thierry.fu
 * @Date 2015年4月6日
 */
public class IndexBuilder {

	private final Logger log = Logger.getLogger(IndexBuilder.class);

	private IndexConfig indexConfig;

	private Directory directory;
	private IndexWriter indexWriter;
	private Analyzer analyzer;
	private IndexWriterConfig conf;

	/**
	 * 初始化Index 配置
	 */
	private void init() {
		try {
			directory = FSDirectory.open(new File(indexConfig.getIndexPath()));
			analyzer = new StandardAnalyzer(indexConfig.getLuceneVersion());
			conf = new IndexWriterConfig(indexConfig.getLuceneVersion(),
					analyzer);
			indexWriter = new IndexWriter(directory, conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IndexBuilder(IndexConfig indexConfig) {
		this.indexConfig = indexConfig;
		init();
	}

	private void add(File file) {
		Document doc = new Document();
		doc.add(new StringField(IndexType.PATH, file.getAbsolutePath(),
				Store.YES));
		doc.add(new StringField(IndexType.NAME, file.getName(), Store.YES));
		doc.add(new TextField(IndexType.CONTENT, FileUtil.file2String(file),
				Store.NO));
		doc.add(new LongField(IndexType.SIZE, file.getTotalSpace(), Store.YES));
		doc.add(new LongField(IndexType.MODIFIFYTIME, file.lastModified(),
				Store.YES));
		try {
			log.info("add a document" + file.getAbsolutePath());
			indexWriter.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void index(String filePath) {
		FileLoader fileLoader = new FileLoader(filePath);
		int fileSize = fileLoader.fileNums();
		for (int i = 0; i < fileSize; i++) {
			File file = new File(fileLoader.pop());
			add(file);
		}
	}

	public void index(File file) {
		add(file);
	}

	public void commit() {
		try {
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void delete() {
		try {
			indexWriter.deleteAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IndexWriter getIndexWriter() {
		return indexWriter;
	}

}
