SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS payment_Method;
DROP TABLE IF EXISTS Shipping_Method;




/* Create Tables */

-- 支付方式
CREATE TABLE payment_Method
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 订单表
	order_no varchar(32) COMMENT '订单表',
	-- 方式
	method varchar(32) COMMENT '方式',
	-- 超时时间
	timeout varchar(32) COMMENT '超时时间',
	-- 图标
	icon varchar(32) COMMENT '图标',
	-- 介绍
	description varchar(255) COMMENT '介绍',
	-- 内容
	content varchar(255) COMMENT '内容',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '支付方式';


-- 配送方式
CREATE TABLE Shipping_Method
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 首重量
	firstWeight varchar(32) COMMENT '首重量',
	-- 续重量
	continueWeight varchar(32) COMMENT '续重量',
	-- 首重价格
	firstPrice decimal COMMENT '首重价格',
	-- 续重价格
	continuePrice decimal COMMENT '续重价格',
	-- 图标
	icon varchar(32) COMMENT '图标',
	-- 介绍
	description varchar(255) COMMENT '介绍',
	-- 默认物流公司
	defaultDeliveryCorp varchar(32) COMMENT '默认物流公司',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '配送方式';



