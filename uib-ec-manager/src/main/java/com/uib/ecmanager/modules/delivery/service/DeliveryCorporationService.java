/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.delivery.dao.DeliveryCorporationDao;
import com.uib.ecmanager.modules.delivery.entity.DeliveryCorporation;

/**
 * 物流公司Service
 * @author gaven
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class DeliveryCorporationService extends CrudService<DeliveryCorporationDao, DeliveryCorporation> {

	public DeliveryCorporation get(String id) {
		return super.get(id);
	}
	
	public List<DeliveryCorporation> findList(DeliveryCorporation deliveryCorporation) {
		return super.findList(deliveryCorporation);
	}
	
	public Page<DeliveryCorporation> findPage(Page<DeliveryCorporation> page, DeliveryCorporation deliveryCorporation) {
		return super.findPage(page, deliveryCorporation);
	}
	
	@Transactional(readOnly = false)
	public void save(DeliveryCorporation deliveryCorporation) {
		super.save(deliveryCorporation);
	}
	
	@Transactional(readOnly = false)
	public void update(DeliveryCorporation deliveryCorporation) {
		super.update(deliveryCorporation);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeliveryCorporation deliveryCorporation) {
		super.delete(deliveryCorporation);
	}
	
}