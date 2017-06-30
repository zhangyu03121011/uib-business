package com.uib.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.SpecificationGroupDao;
import com.uib.product.entity.ProductSpecification;
import com.uib.product.entity.SpecificationGroup;
import com.uib.product.service.ProductSpecificationService;
import com.uib.product.service.SpecificationGroupService;

@Service
public class SpecificationGroupServiceImpl implements SpecificationGroupService{
//	private Logger logger = LoggerFactory.getLogger(SpecificationGroupServiceImpl.class);
	@Autowired
	private SpecificationGroupDao specificationGroupDao;
	@Autowired
	private ProductSpecificationService productSpecificationService;

	@Override
	public List<SpecificationGroup> querySpecificationGroupsByCategoryId(
			String categoryId) throws Exception {
		return specificationGroupDao.querySpecificationGroupsByCategoryId(categoryId);
	}
	
	/**
	 * 根据商品id查询规格组集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<SpecificationGroup> querySpecificationGroupsByProductId(
			String productId) throws Exception{
		// 下面查询需要优化
		List<ProductSpecification> specifications = productSpecificationService
				.querySpecificationsByProductId(productId);
		List<SpecificationGroup> groups = specificationGroupDao
				.querySpecificationGroupsByProductId(productId);
		for (ProductSpecification specification : specifications) {
			String groupId = specification.getSpecificationGroup().getId();
			for (SpecificationGroup specificationGroup : groups) {
				if(specificationGroup.getId().equals(groupId)){
					specificationGroup.getProductSpecificationList().add(specification);
					break;
				}
			}
		}
		return groups;
	}

}
