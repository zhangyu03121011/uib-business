/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.product.dao.ProductSpecificationGroupRefDao;
import com.uib.ecmanager.modules.product.entity.ProductSpecificationGroupRef;

/**
 * 商品规格组关联Service
 * @author gaven
 * @version 2015-11-17
 */
@Service
@Transactional(readOnly = true)
public class ProductSpecificationGroupRefService extends CrudService<ProductSpecificationGroupRefDao, ProductSpecificationGroupRef> {
	@Autowired
	private ProductSpecificationGroupRefDao productSpecificationGroupRefDao;

	public ProductSpecificationGroupRef get(String id) {
		return super.get(id);
	}
	
	public List<ProductSpecificationGroupRef> findList(ProductSpecificationGroupRef productSpecificationGroupRef) {
		return super.findList(productSpecificationGroupRef);
	}
	
	public Page<ProductSpecificationGroupRef> findPage(Page<ProductSpecificationGroupRef> page, ProductSpecificationGroupRef productSpecificationGroupRef) {
		return super.findPage(page, productSpecificationGroupRef);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductSpecificationGroupRef productSpecificationGroupRef) {
		super.save(productSpecificationGroupRef);
	}
	
	@Transactional(readOnly = false)
	public void update(ProductSpecificationGroupRef productSpecificationGroupRef) {
		super.update(productSpecificationGroupRef);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductSpecificationGroupRef productSpecificationGroupRef) {
		super.delete(productSpecificationGroupRef);
	}
	
	@Transactional(readOnly = false)
	public void batchUpdateProductSpecificationGroupRefs(List<String> groupIds,List<String> productIds){
		productSpecificationGroupRefDao.batchDeleteByProductIds(productIds);
		productSpecificationGroupRefDao.batchSave(groupIds, productIds);
	}
	
	@Transactional(readOnly = false)
	public void batchDeleteByGoodsId(String goodsId){
		productSpecificationGroupRefDao.batchDeleteByGoodsId(goodsId);
	}
	
}