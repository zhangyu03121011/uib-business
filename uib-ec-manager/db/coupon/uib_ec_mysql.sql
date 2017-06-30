SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS coupon_code;
DROP TABLE IF EXISTS coupon;




/* Create Tables */

-- 优惠劵
CREATE TABLE coupon
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 使用起始日期
	begin_date datetime COMMENT '使用起始日期',
	-- 使用结束日期
	end_date datetime COMMENT '使用结束日期',
	-- 介绍
	introduction longtext COMMENT '介绍',
	-- 是否启用
	is_enabled bit(1) COMMENT '是否启用',
	-- 是否允许积分兑换
	is_exchange bit(1) COMMENT '是否允许积分兑换',
	-- 最大商品价格
	maximum_price decimal COMMENT '最大商品价格',
	-- 最大商品数量
	maximum_quantity decimal COMMENT '最大商品数量',
	-- 最小商品价格
	minimum_price decimal COMMENT '最小商品价格',
	-- 最小商品数量
	minimum_quantity int COMMENT '最小商品数量',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 积分兑换数
	point bigint(32) COMMENT '积分兑换数',
	-- 前缀
	prefix varchar(255) COMMENT '前缀',
	-- 价格运算表达式
	price_expression varchar(255) COMMENT '价格运算表达式',
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
) COMMENT = '优惠劵';


CREATE TABLE coupon_code
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 优惠券主键
	coupon_id varchar(64) NOT NULL COMMENT '优惠券主键',
	-- 号码
	code varchar(100) COMMENT '号码',
	-- 是否已使用
	is_used bit(1) COMMENT '是否已使用',
	-- 使用日期
	used_date datetime COMMENT '使用日期',
	-- 优惠卷
	coupon_no varchar(32) COMMENT '优惠卷',
	-- 会员
	member_no varchar(32) COMMENT '会员',
	-- 创建时间
	create_date datetime NOT NULL COMMENT '创建时间',
	-- 更新时间
	update_date datetime NOT NULL COMMENT '更新时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
);



/* Create Foreign Keys */

ALTER TABLE coupon_code
	ADD FOREIGN KEY (member_no)
	REFERENCES mem_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE coupon_code
	ADD FOREIGN KEY (coupon_id)
	REFERENCES coupon (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


