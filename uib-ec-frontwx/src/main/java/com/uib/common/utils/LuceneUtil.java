package com.uib.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.uib.product.entity.Product;

//@Component
public class LuceneUtil {
	
	private Logger logger = LoggerFactory.getLogger(LuceneUtil.class);


	@Autowired
	// 这里我写了required = false,需要时再引入，不写的话会报错，大家有更好解决方案请留言哈
	private Analyzer myAnalyzer;
	@Autowired
	private IndexWriter indexWriter;

	@Autowired
	private SimpleFSDirectory luceneDirectory;

	public void create(List<Product> productList) throws Exception {
		long begin = System.currentTimeMillis();
		
		
		for (Product product: productList){
			Document doc = new Document();
			doc.add(new TextField("id", product.getId(), Field.Store.YES));
			doc.add(new TextField("name", product.getName(), Field.Store.YES));
			doc.add(new TextField("image", product.getImage(), Field.Store.YES));
			doc.add(new TextField("fullName", product.getFullName(), Field.Store.YES));
			doc.add(new TextField("price", String.valueOf(product.getPrice()) , Field.Store.YES));
			doc.add(new TextField("productCategoryId", product.getProductCategoryId() , Field.Store.YES));
			indexWriter.addDocument(doc);
			
		}

		indexWriter.commit();
		System.out.println("create cost:" + (System.currentTimeMillis() - begin) / 1000 + "s");
	}

	public IndexSearcher getIndexSearcher() throws IOException {
		DirectoryReader indexReader = DirectoryReader.open(luceneDirectory);
		return new IndexSearcher(indexReader);
	}

	/**
	 * searchAll
	 */
	public List<String> searchAll(String queryFieldName) {
		List<String> idList = null;
		try {
		//	QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_44, new String[] { queryFieldName }, myAnalyzer);
			Query query = new WildcardQuery(new Term(queryFieldName, "*"));// parser.parse("*");
			idList = searchAndShowResult(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return idList;
	}
	
	
	public List<Product> searchByProductName(String productName){
		List<Product> productList = null;
		try {
			QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_44, new String[] { "fullName" }, myAnalyzer);
			 Query query=parser.parse(productName);
			productList = searchProduct(query);
		} catch (Exception ex) {
			logger.error("根据商品名称检索 searchByProductName:", productName);
		}
		return productList;
	}
	
	public List<Product> searchProduct(Query query) throws IOException {
		List<Product> productList = new ArrayList<Product>();

		IndexSearcher searcher = getIndexSearcher();
		TopScoreDocCollector res = TopScoreDocCollector.create(1000000, false);
		searcher.search(query, res);
		// 注意点2:这里可以控制分页。
		TopDocs tds = res.topDocs(0, 100000);

		ScoreDoc[] sd = tds.scoreDocs;

		if (null != sd && sd.length > 0) {
			// TokenStream tokenStream=null;
			for (int i = 0; i < sd.length; i++) {

				int docId = sd[i].doc;
				// 根据编号拿到Document数据
				Document doc = searcher.doc(docId);
				String id = doc.get("id");
				String productCategoryId =	doc.get("productCategoryId");
				String name = doc.get("name");
				String image = doc.get("image");
				String fullName = doc.get("fullName");
				String price = doc.get("price");
						
				
				Product product = new Product();
				product.setId(id);
				product.setProductCategoryId(productCategoryId);
				product.setName(name);
				product.setImage(image);
				product.setFullName(fullName);
				if (!StringUtils.isEmpty(price)){
					product.setPrice(Double.parseDouble(price) );
				}
				productList.add(product);
				
				// tokenStream=myAnalyzer.tokenStream("name", new
				// StringReader(name));
				// name=highlighter.getBestFragment(tokenStream, name);
			}
		}

		return productList;
	}
	

	public List<String> searchAndShowResult(Query query) throws IOException {
		List<String> list = new ArrayList<String>();

		IndexSearcher searcher = getIndexSearcher();
		TopScoreDocCollector res = TopScoreDocCollector.create(1000000, false);
		searcher.search(query, res);
		// 注意点2:这里可以控制分页。
		TopDocs tds = res.topDocs(0, 100000);

		ScoreDoc[] sd = tds.scoreDocs;

		if (null != sd && sd.length > 0) {
			// TokenStream tokenStream=null;
			for (int i = 0; i < sd.length; i++) {

				int docId = sd[i].doc;
				// 根据编号拿到Document数据
				Document doc = searcher.doc(docId);
				String id = doc.get("id");
				list.add(id);
				// tokenStream=myAnalyzer.tokenStream("name", new
				// StringReader(name));
				// name=highlighter.getBestFragment(tokenStream, name);
			}
		}

		return list;
	}

}
