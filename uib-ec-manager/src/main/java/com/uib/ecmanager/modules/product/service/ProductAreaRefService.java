/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.product.entity.ProductAreaRef;
import com.uib.ecmanager.modules.product.entity.ProductPriceRef;
import com.uib.ecmanager.modules.product.dao.ProductAreaRefDao;
import com.uib.ecmanager.modules.product.dao.ProductPriceRefDao;

/**
 * 商品地区关联表Service
 * @author limy
 * @version 2016-08-23
 */
@Service
@Transactional(readOnly = true)
public class ProductAreaRefService extends CrudService<ProductAreaRefDao, ProductAreaRef> {
	
	public ProductAreaRef get(String id) {
		return super.get(id);
	}
	
	public List<ProductAreaRef> findList(ProductAreaRef productAreaRef) {
		return super.findList(productAreaRef);
	}
	
	public Page<ProductAreaRef> findPage(Page<ProductAreaRef> page, ProductAreaRef productAreaRef) {
		return super.findPage(page, productAreaRef);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductAreaRef productAreaRef) {
		super.save(productAreaRef);
	}
	/**
	 * 批量插入
	 * @param productPriceRef
	 */
	@Transactional(readOnly = false)
	public void insertList(List<ProductAreaRef> productAreaRefList) {		
		for (ProductAreaRef productAreaRef : productAreaRefList) {
			if (productAreaRef.getIsNewRecord()){
				dao.insert(productAreaRef);
			}
		}
	}
	
	/**
	 * mybatis批量插入
	 * @param productPriceRef
	 */
	@Transactional(readOnly = false)
	public void insertProductAreaRef(List<ProductAreaRef> parList){
		dao.insertProductAreaRef(parList);
	}
	
	@Transactional(readOnly = false)
	public void update(ProductAreaRef productAreaRef) {
		super.update(productAreaRef);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductAreaRef productAreaRef) {
		super.delete(productAreaRef);
	}
	@Transactional(readOnly = false)
	public List<ProductAreaRef> findProductAreaRefList(String productId){
		return dao.findProductAreaRefList(productId);
	};
	
	@Transactional(readOnly = false)
	public void deleteByProductId(String productId){
		dao.deleteByProductId(productId);
	}
}