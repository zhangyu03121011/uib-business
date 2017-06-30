package com.uib.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.uib.core.exception.GenericException;

/**
 * 根据spring Mybatis 再做一层封装
 * 
 * @Title: MyBatisDao Company: uib Copyright: Copyright(C) 2014
 * @Version 1.0
 * @author wanghuan
 * @date: 2014-07-06
 * @time: 上午09:18:44 Description: 根据spring Mybatis 再做一层封装
 */

@SuppressWarnings("unchecked")
public class MyBatisDao<T> {

	@Autowired
	private SqlSession sqlSession;

	private String sqlMapNameSpace;

	private Class<T> entityClass;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public MyBatisDao() {
		this.entityClass = ReflectionUtils.getParameterizedTypeByParent(getClass());
		this.sqlMapNameSpace = this.entityClass.getName();
	}

	/**
	 * 根据传入方法名称, 返回单个值对象
	 * 
	 * @Title: findObjectValue
	 * @author wanghuan
	 * @param 配置文件中的方法名称
	 * @retrun Object 如: count(*) 值
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午09:19:15 Description:
	 */
	public Object getObjectValue(final String statementId) throws GenericException {
		return sqlSession.selectOne(fullStmtName(statementId));
	}

	/**
	 * 根据传入方法名称,参数, 返回单个值对象
	 * 
	 * @Title: findObjectValue
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @param parameterObject
	 *            参数
	 * @retrun Object
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午09:23:36 Description:
	 */
	public Object getObjectValue(final String statementId, final Object parameterObject) throws GenericException {
		return sqlSession.selectOne(fullStmtName(statementId), parameterObject);
	}

	public Map<String, Object> getMap(final String statementId, final Object parameterObject) throws GenericException {
		return sqlSession.selectOne(fullStmtName(statementId), parameterObject);
	}

	public List<T> getObjectList(final String statementId, final Object parameterObject) throws GenericException {
		return sqlSession.selectList(fullStmtName(statementId), parameterObject);
	}
	
	public List<T> getObjectList(final String statementId) throws GenericException {
		return sqlSession.selectList(fullStmtName(statementId));
	}

	public List<Map<String, Object>> getList(final String statementId, final Object parameterObject) throws GenericException {
		return sqlSession.selectList(fullStmtName(statementId), parameterObject);
	}
	
	public List<Object> getObjects(final String statementId, final Object parameterObject) throws GenericException{
		return sqlSession.selectList(fullStmtName(statementId),parameterObject);
	}
	
	/**
	 * 查询object数据
	 * @param statementId
	 * @return
	 * @throws GenericException
	 */
	public List<Object> getObjects(final String statementId) throws GenericException{
		return sqlSession.selectList(fullStmtName(statementId));
	}

	/**
	 * 根据传入方法名称,返回Unique对象类型
	 * 
	 * @Title: findUnique
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @retrun T 对象类型
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午09:30:22 Description:
	 */
	public T getUnique(final String statementId) throws GenericException {
		return sqlSession.selectOne(fullStmtName(statementId));
	}

	/**
	 * 根据传入方法名称,以及参数,返回Unique对象类型
	 * 
	 * @Title: findUnique
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @param parameterObject
	 *            参数
	 * @retrun T 对象类型
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午09:53:28 Description:
	 */
	public T getUnique(final String statementId, final Object parameterObject) throws GenericException {
		return sqlSession.selectOne(fullStmtName(statementId), parameterObject);
	}

	/**
	 * 根据方法名称,返回List<T> 集合类型
	 * 
	 * @Title: find
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @retrun List<T>
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午09:58:46 Description:
	 */
	public List<T> get(final String statementId) throws GenericException {
		return sqlSession.selectList(fullStmtName(statementId));
	}

	/**
	 * 根据方法名称,参数 获取List<T>集合类型
	 * 
	 * @Title: find
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @param parameterObject
	 *            参数
	 * @retrun List<T>
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午10:09:57 Description:
	 */
	public List<T> get(final String statementId, final Object parameterObject) throws GenericException {
		return sqlSession.selectList(fullStmtName(statementId), parameterObject);
	}

	/**
	 * 查询分页
	 * 
	 * @Title: findPage
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @param start
	 *            分页开始位置
	 * @param limit
	 *            每页记录数
	 * @retrun List<T>
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午10:12:21 Description:
	 */
	public List<T> getPage(String statementId, int pageNum, int pageSize) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		long beginNum = 0l;
		long endNum = 0l;
		endNum = pageSize * pageNum;
		beginNum = endNum - pageSize;
		map.put("begin", beginNum);
		map.put("end", endNum);
		return sqlSession.selectList(fullStmtName(statementId), map);
	}

	public List<T> getPage(final String statementId, final Map<String, Object> paramsMap) throws GenericException {
		int beginNum = 0;
		int endNum = 0;
		int pageNum = ((Integer) paramsMap.get("currentPage")).intValue();
		int pageSize = ((Integer) paramsMap.get("pageSize")).intValue();
		// endNum = (pageSize-1) * pageNum;
		// endNum = pageSize * pageNum;
		// beginNum = endNum - pageSize;
		beginNum = (pageNum - 1) * pageSize;
		endNum = pageSize;
		return sqlSession.selectList(fullStmtName(statementId), paramsMap, new RowBounds(beginNum, endNum));
	}

	public List<T> getMySqlPage(final String statementId, final Map<String, Object> paramsMap) throws GenericException {
		// int beginNum = 0;
		int endNum = 0;
		String strCurrpage = paramsMap.get("currentPage").toString();
		String strpageSize = paramsMap.get("pageSize").toString();
		int pageNum = Integer.parseInt(strCurrpage) == 0 ? 1 : Integer.parseInt(strCurrpage);
		int pageSize = Integer.parseInt(strpageSize);
		// endNum = (pageSize-1) * pageNum;
		endNum = pageSize + pageNum;
		// beginNum = endNum - pageSize;
		paramsMap.put("begin", pageNum);
		paramsMap.put("end", endNum);
		return sqlSession.selectList(fullStmtName(statementId), paramsMap);
	}
	public List<Map<String, Object>> getMapPage(final String statementId, final Map<String, Object> paramsMap) throws GenericException {
		/*// int beginNum = 0;
		int endNum = 0;
		String strCurrpage = paramsMap.get("currentPage").toString();
		String strpageSize = paramsMap.get("pageSize").toString();
		int pageNum = Integer.parseInt(strCurrpage) == 0 ? 1 : Integer.parseInt(strCurrpage);
		int pageSize = Integer.parseInt(strpageSize);
		// endNum = (pageSize-1) * pageNum;
		endNum = pageSize + pageNum;
		// beginNum = endNum - pageSize;
		paramsMap.put("begin", pageNum);
		paramsMap.put("end", endNum);*/
		
		return sqlSession.selectList(fullStmtName(statementId), paramsMap);
	}
	/**
	 * 根据方法名称,执行修改
	 * 
	 * @Title: update
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @retrun void
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午10:15:49 Description:
	 */
	public void update(final String statementId) throws GenericException {
		sqlSession.update(fullStmtName(statementId));
	}

	/**
	 * 根据方法名称以及参数,执行修改
	 * 
	 * @Title: update
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @param parameterObject
	 *            参数
	 * @retrun void
	 * @throws @date:
	 *             2012-12-6
	 * @time: 上午10:17:03 Description:
	 */
	public void update(final String statementId, final Object parameterObject) throws GenericException {
		sqlSession.update(fullStmtName(statementId), parameterObject);
	}

	/**
	 * 根据方法名称 执行增加操作
	 * 
	 * @Title: insert
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @retrun void
	 * @throws @date:
	 *             2012-12-6
	 * @time: 上午10:20:16 Description:
	 */
	public void save(final String statementId) throws GenericException {
		sqlSession.insert(fullStmtName(statementId));
	}

	/**
	 * 根据方法名称以及参数,执行增加操作
	 * 
	 * @Title: insert
	 * @author wanghuan
	 * @param statementId
	 *            方法名称
	 * @param parameterObject
	 *            参数
	 * @retrun void
	 * @throws SQLException
	 * @date: 2012-12-6
	 * @time: 上午10:21:00 Description:
	 */
	public void save(final String statementId, final Object parameterObject) throws GenericException {
		sqlSession.insert(fullStmtName(statementId), parameterObject);
	}

	/**
	 * 根据statementId 执行删除操作
	 * 
	 * @Title: delete
	 * @author wanghuan
	 * @param
	 * @retrun void
	 * @throws @date:
	 *             2012-12-6
	 * @time: 上午10:29:48 Description:
	 */
	public void remove(final String statementId) throws GenericException {
		sqlSession.delete(fullStmtName(statementId));
	}

	/**
	 * 根据statementId 执行删除操作, 参数
	 * 
	 * @Title: delete
	 * @author wanghuan
	 * @param statementId
	 * @param parameterObject
	 * @throws GenericException
	 * @throws @date:
	 *             2012-12-7
	 * @time: 下午02:58:47 Description:
	 */
	public void remove(final String statementId, final Object parameterObject) throws GenericException {
		sqlSession.delete(fullStmtName(statementId), parameterObject);
	}

	private String getSqlMapNameSpace() {
		return sqlMapNameSpace;
	}

	private String fullStmtName(String statementId) {
		return getSqlMapNameSpace() + "." + statementId;
	}

}
