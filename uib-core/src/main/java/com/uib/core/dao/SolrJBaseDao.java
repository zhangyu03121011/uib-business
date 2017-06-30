package com.uib.core.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;




/**
 * 
 * solrJ baseDao工具类
 * @author kevin
 *
 */
@SuppressWarnings("unchecked")
public class SolrJBaseDao<T> {
	
	private Class<T> entityClass;
	private HttpSolrClient httpSolr;
	
	
	
	public SolrJBaseDao(){
		this.entityClass = ReflectionUtils.getParameterizedTypeByParent(getClass());
	}
	
	/**
	 * 单个修改索引
	 * @param solrAddUr
	 * @param object
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public  void saveOrUpdateIndex(String solrAddUrl,Class<T> clas) throws IOException, SolrServerException{
		HttpSolrClient httpSolr = new HttpSolrClient(solrAddUrl);
		httpSolr.addBean(clas);
		httpSolr.commit();
		httpSolr.close();
	}
	
	/**
	 * 根据条件分页查询查询索引返回对象
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public  List<T> getPageIndex(String queryUrl,String queryFilter,Integer start,Integer rows) throws SolrServerException, IOException{
		httpSolr = new HttpSolrClient(queryUrl);
		SolrQuery params = new SolrQuery();
		params.setQuery(queryFilter);
		params.setStart(start);
		params.setRows(rows);
		QueryResponse response = httpSolr.query(params);
		//SolrDocumentList sdocList = 	response.getResults();
		return response.getBeans(entityClass);
	}
	
	
	
	/**
	 * 根据条件查询查询索引总记录数
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public  Long getPageIndexCount(String queryUrl,String queryFilter) throws SolrServerException, IOException{
		 httpSolr = new HttpSolrClient(queryUrl);
		SolrQuery params = new SolrQuery();
		params.setQuery(queryFilter);
		QueryResponse response = httpSolr.query(params);
		//int size = response.getResults().size();
		long total =	response.getResults().getNumFound();
		//SolrDocumentList sdocList = 	response.getResults();
		return total;
	}
	
	/**
	 * 根据条件获取单个查询对象
	 * @param queryUrl
	 * @param queryFilter
	 * @return
	 * @throws Exception
	 */
	public T getObjectIndex(String queryUrl, String queryFilter) throws Exception {
		 httpSolr = new HttpSolrClient(queryUrl);
		SolrQuery params = new SolrQuery();
		params.setQuery(queryFilter);
		QueryResponse response = httpSolr.query(params);
		List<T> listT = response.getBeans(entityClass);
		return listT.get(0);
	}
	
	
	 public T toBean( SolrDocument record ){
         T o = null;
         Field[] fields =   entityClass.getDeclaredFields();
         for(Field field:fields){
             Object value = record.get(field.getName());
             try {
                BeanUtils.setProperty(o, field.getName(), value);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         }
        return o;
    }
     
    public  List<T> toBeanList(SolrDocumentList records){
        List<T>  list = new ArrayList<T>();
        for(SolrDocument record : records){
            list.add(toBean(record));
        }
        return list;
    }
 
}

