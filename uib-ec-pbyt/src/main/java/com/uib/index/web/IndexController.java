package com.uib.index.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/*import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.SimpleFSDirectory;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easypay.core.web.TreeUtils;
import com.uib.ad.entity.Advertisement;
import com.uib.ad.entity.AdvertisementPosition;
import com.uib.ad.service.AdvertisementService;
import com.uib.base.BaseController;
import com.uib.cms.entity.Article;
import com.uib.cms.service.ArticleService;
import com.uib.navigation.entity.Navigation;
import com.uib.navigation.service.NavigationService;
import com.uib.product.entity.ProductCategory;
import com.uib.product.service.ProductCategoryService;

/**
 * 会员信息管理
 * @author kevin
 */
@Controller
@RequestMapping
public class IndexController extends BaseController {

	//private static final String STARTTAG="<font color='red'>";
	//private static final String ENDTAG="</font>";
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Value("/index")
	private String indexView;

	@Value("/makeup")
	private String makeupView;
	
	@Value("${upload.image.path}")
	private String UPLOAD_IMAGE_PATH;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private NavigationService navigationService;

	//@Autowired
	// 这里我写了required = false,需要时再引入，不写的话会报错，大家有更好解决方案请留言哈
	//private Analyzer myAnalyzer;
	//@Autowired
	//private IndexWriter indexWriter;
	
	//@Autowired
	//private SimpleFSDirectory luceneDirectory;

	//public static final FieldType TYPE_NOT_STORED = new FieldType();

	//public static final FieldType TYPE_STORED = new FieldType();

	
	/*public IndexSearcher getIndexSearcher() throws IOException{
		DirectoryReader indexReader = DirectoryReader.open(luceneDirectory);
		return new IndexSearcher(indexReader);	
	}*/
	
	

	/**
	 * 登陆后首页
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public String toIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// 获取一级菜单
			List<ProductCategory> parentCategoryList = productCategoryService.getCategoryByMeridAndParentId("0", null);
			List<TreeUtils> categoryList = geProductCategoryToTreeUtils(parentCategoryList);
			HttpSession session = request.getSession();
			// 首页banner
			AdvertisementPosition adPosition_banner = advertisementService
					.getAdvertisementPositionByName("index_banner");
			if (null != adPosition_banner) {
				List<Advertisement> banner_adList = advertisementService.getByAdPositionId(adPosition_banner.getId());
				for (Advertisement ad : banner_adList) {
					ad.setAdvertisementPosition(adPosition_banner);
				}
				request.setAttribute("banner_adList", banner_adList);
			}
			Map<String, String> map = new HashMap<String, String>();
			// 一楼广告
			map.put("index_one_banner", "one_bannerList");
			map.put("index_one_centre_big", "one_centre_big_List");
			map.put("index_one_centre_small", "one_centre_small_List");
			map.put("index_one_right_big", "one_right_big_List");
			map.put("index_one_right_small", "one_right_small_List");
			map.put("index_one_brand", "one_brand_List");
			
			// 二楼广告
			map.put("index_two_left_big", "two_left_big_List");
			map.put("index_two_left_small", "two_left_small_List");
			map.put("index_two_centre_big", "two_centre_big_List");
			map.put("index_two_centre_small", "two_centre_small_List");
			map.put("index_two_right_banner", "two_right_banner_List");
			map.put("index_two_brand", "two_brand_List");

			// 个护化妆广告
			map.put("makeup_banner", "makeup_banner_List");

			loadAdvertisement(request, map);

			// 获取公告文章分类
			List<Article> articleList = articleService.getCmsArticleByCategoryId("d82e8a6256f54dd8b91e07cabbb6f04e");
			if (null != articleList) {
				request.setAttribute("articleList", articleList);
			}

			session.setAttribute("categoryList", categoryList);
			session.setAttribute("UPLOAD_IMAGE_PATH", UPLOAD_IMAGE_PATH);
			List<Navigation> navigationList = navigationService.getByPosition(Navigation.Position.middle.getIndex());
			session.setAttribute("navigationList", navigationList);
		} catch (Exception ex) {
			logger.error("toIndex 方法出错", ex);
			return "redirect:loginPage";
		}

		return indexView;
	}

	/**
	 * 个护化妆首页
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping("/makeup")
	public String makeup(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			// 个护化妆 一楼广告
			map.put("makeup_one_left", "makeup_one_left_List");
			map.put("makeup_one_centre_banner", "makeup_one_centre_List");
			map.put("makeup_one_right_big", "makeup_one_right_big_List");
			map.put("makeup_one_right_small", "omakeup_one_right_small_List");

			// 个护化妆二楼广告
			map.put("makeup_two_left", "makeup_two_left_List");
			map.put("makeup_two_centre_big", "makeup_two_centre_big_List");
			map.put("makeup_two_centre_small", "makeup_two_centre_small_List");
			map.put("makeup_two_right_banner", "makeup_two_right_banner_List");

			loadAdvertisement(request, map);
		} catch (Exception ex) {
			logger.error("个护化妆首页 出错", ex);
			ex.printStackTrace();
		}
		return makeupView;
	}

	private void loadAdvertisement(HttpServletRequest request, Map<String, String> map) throws Exception {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			//System.out.println("entry.getKey() ===" + entry.getKey());
			AdvertisementPosition adPosition = advertisementService.getAdvertisementPositionByName(entry.getKey());
			if (null != adPosition) {
				List<Advertisement> advertisementList = advertisementService.getByAdPositionId(adPosition.getId());
				for (Advertisement ad : advertisementList) {
					ad.setAdvertisementPosition(adPosition);
				}
				request.getSession().setAttribute(entry.getValue(), advertisementList);
			}
		}

	}

	/**
	 * 将菜单封装成树
	 * 
	 * @Title getMenuToTreeUtils
	 * @author WANGHUAN
	 * @param
	 * @return String
	 * @throws Exception
	 * @throws
	 * @date 2014年11月05日
	 * @time 上午9:35:57
	 * @Description
	 */
	private List<TreeUtils> geProductCategoryToTreeUtils(List<ProductCategory> productCategoryList) throws Exception {
		List<TreeUtils> treeUtilsList = new ArrayList<TreeUtils>();
		if (null != productCategoryList) {
			for (ProductCategory productCategory : productCategoryList) {
				TreeUtils treeUtils = new TreeUtils();
				treeUtils.setId(productCategory.getId());
				treeUtils.setText(productCategory.getName());
				List<ProductCategory> childrenProductCategory = productCategoryService.getCategoryByMeridAndParentId(
						productCategory.getId(), null);
				if (null != childrenProductCategory) {
					treeUtils.setChildren(geProductCategoryToTreeUtils(childrenProductCategory));
				}
				// treeUtils.setState("open");
				treeUtilsList.add(treeUtils);
			}
		}
		return treeUtilsList;
	}
	
	
	
	
	
	
	/*@RequestMapping("/testCreate")
	public void testCreate(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try {
			create("大中华");
		} catch (Exception ex) {
			logger.error("个护化妆首页 出错", ex);
			ex.printStackTrace();
		}
	}
	
	@RequestMapping("/search")
	public void search(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try {
			 int start = 0;
			 int howMany = 10000;
			 IndexSearcher searcher=getIndexSearcher();
			 QueryParser parser=new MultiFieldQueryParser(Version.LUCENE_44, new String[]{"name"}, myAnalyzer);
			
			 Query query= new WildcardQuery(new Term("name","*"));//parser.parse("*");
	         
			//使用：search(Query query , Collector results)
			 int hm = start+howMany ;
			 TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
			 System.out.println("total hits :"+res.getTotalHits());
			 searcher.search(query, res);
			 //注意点2:这里可以控制分页。
			 TopDocs tds = res.topDocs(start, howMany);
			
		        ScoreDoc[] sd=tds.scoreDocs;
		         
		        SimpleHTMLFormatter simpleHtmlFormatter=new SimpleHTMLFormatter(STARTTAG,ENDTAG);
		         
		        Highlighter highlighter=new Highlighter(simpleHtmlFormatter,new QueryScorer(query));
		         
		        // 这个一般等于你要返回的，高亮的数据长度      
	            // 如果太小，则只有数据的开始部分被解析并高亮，且返回的数据也少      
	            // 太大，有时太浪费了。      
	            highlighter.setTextFragmenter(new SimpleFragmenter(Integer.MAX_VALUE));
		        Document doc = null;
		         
		       // TokenStream tokenStream=null;
		        for(int i=0;i<sd.length;i++){
		             
		            int docId=sd[i].doc;
		            doc=searcher.doc(docId);
		            String name = doc.get("name");
		            
		        //    tokenStream=myAnalyzer.tokenStream("name", new StringReader(name));
		        //    name=highlighter.getBestFragment(tokenStream, name);
		            System.out.println("name ===" + name);
		        }
		       
		         
		} catch (Exception ex) {
			logger.error("个护化妆首页 出错", ex);
			ex.printStackTrace();
		}
	}
	

	public void create(String string) throws Exception {
		long begin = System.currentTimeMillis();
		for (int m = 604; m < 605; m++) {
			for (int i = m * 10000; i < (m + 1) * 10000; i++) {
				Document doc = new Document();
				// doc.add(new Field("id", String.valueOf(i), Store.YES,
				// Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new TextField("name", "索引" + string+ i, Field.Store.YES));

				indexWriter.addDocument(doc);
			}
			System.out.println(m);
		}
		indexWriter.commit();
		System.out.println("create cost:" + (System.currentTimeMillis() - begin) / 1000 + "s");
	}*/
}
