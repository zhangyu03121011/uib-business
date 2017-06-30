/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.mem.entity.UserComplaint;

/**
 * 会员投诉DAO接口
 * @author luogc
 * @version 2016-01-12
 */
@MyBatisDao
public interface UserComplaintDao extends CrudDao<UserComplaint> {

}