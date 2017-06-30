
/* Drop Tables */

DROP TABLE coupon_code CASCADE CONSTRAINTS;
DROP TABLE coupon CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE seq_oa_leave;
DROP SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT;




/* Create Sequences */

CREATE SEQUENCE seq_oa_leave INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 61 CACHE 20;



/* Create Tables */

-- 优惠劵
CREATE TABLE coupon
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 使用起始日期
	begin_date timestamp,
	-- 使用结束日期
	end_date timestamp,
	-- 介绍
	introduction clob,
	-- 是否启用
	is_enabled char,
	-- 是否允许积分兑换
	is_exchange char,
	-- 最大商品价格
	maximum_price number,
	-- 最大商品数量
	maximum_quantity number,
	-- 最小商品价格
	minimum_price number,
	-- 最小商品数量
	minimum_quantity number(10,0),
	-- 名称
	name varchar2(32),
	-- 积分兑换数
	point number(19,0),
	-- 前缀
	prefix varchar2(255),
	-- 价格运算表达式
	price_expression varchar2(255),
	-- 创建者
	create_by varchar2(64) NOT NULL,
	-- 创建时间
	create_date timestamp NOT NULL,
	-- 更新者
	update_by varchar2(64) NOT NULL,
	-- 更新时间
	update_date timestamp NOT NULL,
	-- 备注信息
	remarks nvarchar2(255),
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE coupon_code
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 优惠券主键
	coupon_id varchar2(64) NOT NULL,
	-- 号码
	code varchar2(100),
	-- 是否已使用
	is_used char,
	-- 使用日期
	used_date timestamp,
	-- 优惠卷
	coupon_no varchar2(32),
	-- 会员
	member_no varchar2(32),
	-- 创建时间
	create_date timestamp NOT NULL,
	-- 更新时间
	update_date timestamp NOT NULL,
	-- 备注信息
	remarks nvarchar2(255),
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (id)
);



/* Create Foreign Keys */

ALTER TABLE coupon_code
	ADD FOREIGN KEY (coupon_id)
	REFERENCES coupon (id)
;



/* Comments */

COMMENT ON TABLE coupon IS '优惠劵';
COMMENT ON COLUMN coupon.id IS '主键';
COMMENT ON COLUMN coupon.begin_date IS '使用起始日期';
COMMENT ON COLUMN coupon.end_date IS '使用结束日期';
COMMENT ON COLUMN coupon.introduction IS '介绍';
COMMENT ON COLUMN coupon.is_enabled IS '是否启用';
COMMENT ON COLUMN coupon.is_exchange IS '是否允许积分兑换';
COMMENT ON COLUMN coupon.maximum_price IS '最大商品价格';
COMMENT ON COLUMN coupon.maximum_quantity IS '最大商品数量';
COMMENT ON COLUMN coupon.minimum_price IS '最小商品价格';
COMMENT ON COLUMN coupon.minimum_quantity IS '最小商品数量';
COMMENT ON COLUMN coupon.name IS '名称';
COMMENT ON COLUMN coupon.point IS '积分兑换数';
COMMENT ON COLUMN coupon.prefix IS '前缀';
COMMENT ON COLUMN coupon.price_expression IS '价格运算表达式';
COMMENT ON COLUMN coupon.create_by IS '创建者';
COMMENT ON COLUMN coupon.create_date IS '创建时间';
COMMENT ON COLUMN coupon.update_by IS '更新者';
COMMENT ON COLUMN coupon.update_date IS '更新时间';
COMMENT ON COLUMN coupon.remarks IS '备注信息';
COMMENT ON COLUMN coupon.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON COLUMN coupon_code.id IS '主键';
COMMENT ON COLUMN coupon_code.coupon_id IS '优惠券主键';
COMMENT ON COLUMN coupon_code.code IS '号码';
COMMENT ON COLUMN coupon_code.is_used IS '是否已使用';
COMMENT ON COLUMN coupon_code.used_date IS '使用日期';
COMMENT ON COLUMN coupon_code.coupon_no IS '优惠卷';
COMMENT ON COLUMN coupon_code.member_no IS '会员';
COMMENT ON COLUMN coupon_code.create_by IS '创建者';
COMMENT ON COLUMN coupon_code.create_date IS '创建时间';
COMMENT ON COLUMN coupon_code.update_by IS '更新者';
COMMENT ON COLUMN coupon_code.update_date IS '更新时间';
COMMENT ON COLUMN coupon_code.remarks IS '备注信息';
COMMENT ON COLUMN coupon_code.del_flag IS '删除标记（0：正常；1：删除）';



