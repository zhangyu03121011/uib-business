
/* Drop Tables */

DROP TABLE payment_Method CASCADE CONSTRAINTS;
DROP TABLE Shipping_Method CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE seq_oa_leave;
DROP SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT;




/* Create Sequences */

CREATE SEQUENCE seq_oa_leave INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 61 CACHE 20;



/* Create Tables */

-- 支付方式
CREATE TABLE payment_Method
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 方式
	method varchar2(32),
	-- 超时时间
	timeout varchar2(32),
	-- 图标
	icon varchar2(32),
	-- 介绍
	description varchar2(255),
	-- 内容
	content varchar2(255),
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


-- 配送方式
CREATE TABLE Shipping_Method
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 名称
	name varchar2(32),
	-- 首重量
	firstWeight varchar2(32),
	-- 续重量
	continueWeight varchar2(32),
	-- 首重价格
	firstPrice number,
	-- 续重价格
	continuePrice number,
	-- 图标
	icon varchar2(32),
	-- 介绍
	description varchar2(255),
	-- 默认物流公司
	defaultDeliveryCorp varchar2(32),
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



/* Comments */

COMMENT ON TABLE payment_Method IS '支付方式';
COMMENT ON COLUMN payment_Method.id IS '主键';
COMMENT ON COLUMN payment_Method.method IS '方式';
COMMENT ON COLUMN payment_Method.timeout IS '超时时间';
COMMENT ON COLUMN payment_Method.icon IS '图标';
COMMENT ON COLUMN payment_Method.description IS '介绍';
COMMENT ON COLUMN payment_Method.content IS '内容';
COMMENT ON COLUMN payment_Method.create_by IS '创建者';
COMMENT ON COLUMN payment_Method.create_date IS '创建时间';
COMMENT ON COLUMN payment_Method.update_by IS '更新者';
COMMENT ON COLUMN payment_Method.update_date IS '更新时间';
COMMENT ON COLUMN payment_Method.remarks IS '备注信息';
COMMENT ON COLUMN payment_Method.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE Shipping_Method IS '配送方式';
COMMENT ON COLUMN Shipping_Method.id IS '主键';
COMMENT ON COLUMN Shipping_Method.name IS '名称';
COMMENT ON COLUMN Shipping_Method.firstWeight IS '首重量';
COMMENT ON COLUMN Shipping_Method.continueWeight IS '续重量';
COMMENT ON COLUMN Shipping_Method.firstPrice IS '首重价格';
COMMENT ON COLUMN Shipping_Method.continuePrice IS '续重价格';
COMMENT ON COLUMN Shipping_Method.icon IS '图标';
COMMENT ON COLUMN Shipping_Method.description IS '介绍';
COMMENT ON COLUMN Shipping_Method.defaultDeliveryCorp IS '默认物流公司';
COMMENT ON COLUMN Shipping_Method.create_by IS '创建者';
COMMENT ON COLUMN Shipping_Method.create_date IS '创建时间';
COMMENT ON COLUMN Shipping_Method.update_by IS '更新者';
COMMENT ON COLUMN Shipping_Method.update_date IS '更新时间';
COMMENT ON COLUMN Shipping_Method.remarks IS '备注信息';
COMMENT ON COLUMN Shipping_Method.del_flag IS '删除标记（0：正常；1：删除）';



