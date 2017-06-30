/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.method.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.method.dao.PaymentMethodDao;
import com.uib.ecmanager.modules.method.entity.PaymentMethod;

/**
 * 支付方式Service
 * @author limy
 * @version 2015-07-15
 */
@Service
@Transactional(readOnly = true)
public class PaymentMethodService extends CrudService<PaymentMethodDao, PaymentMethod> {

	public PaymentMethod get(String id) {
		return super.get(id);
	}
	
	public List<PaymentMethod> findList(PaymentMethod paymentMethod) {
		return super.findList(paymentMethod);
	}
	
	public Page<PaymentMethod> findPage(Page<PaymentMethod> page, PaymentMethod paymentMethod) {
		return super.findPage(page, paymentMethod);
	}
	
	@Transactional(readOnly = false)
	public void save(PaymentMethod paymentMethod) {
		super.save(paymentMethod);
	}
	
	@Transactional(readOnly = false)
	public void update(PaymentMethod paymentMethod) {
		super.update(paymentMethod);
	}
	
	@Transactional(readOnly = false)
	public void delete(PaymentMethod paymentMethod) {
		super.delete(paymentMethod);
	}
	
}