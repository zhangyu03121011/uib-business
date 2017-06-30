
/* Drop Tables */

DROP TABLE order_returns_ref CASCADE CONSTRAINTS;
DROP TABLE order_shipping_ref CASCADE CONSTRAINTS;
DROP TABLE order_table_refunds CASCADE CONSTRAINTS;
DROP TABLE order_table_payment CASCADE CONSTRAINTS;
DROP TABLE order_table_item CASCADE CONSTRAINTS;
DROP TABLE order_table CASCADE CONSTRAINTS;
DROP TABLE order_table_log CASCADE CONSTRAINTS;
DROP TABLE order_table_returns_item CASCADE CONSTRAINTS;
DROP TABLE order_table_returns CASCADE CONSTRAINTS;
DROP TABLE order_table_shipping_item CASCADE CONSTRAINTS;
DROP TABLE order_table_shipping CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE seq_oa_leave;
DROP SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT;




/* Create Sequences */

CREATE SEQUENCE seq_oa_leave INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 61 CACHE 20;



/* Create Tables */

-- 订单退货关联表
CREATE TABLE order_returns_ref
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 退货主键ID
	return_id varchar2(64) NOT NULL,
	-- 订单主键ID
	order_tabel_id varchar2(64) NOT NULL,
	PRIMARY KEY (id)
);


-- 订单发货关联表
CREATE TABLE order_shipping_ref
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 发货主键ID
	shipping_id varchar2(64) NOT NULL,
	-- 订单主键ID
	order_tabel_id varchar2(64) NOT NULL,
	PRIMARY KEY (id)
);


-- 订单表
CREATE TABLE order_table
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 订单编号
	order_no varchar2(32) NOT NULL UNIQUE,
	-- 地址
	address varchar2(32),
	-- 已付金额
	amount_paid number,
	-- 订单金额
	order_amount number,
	-- 地区名称
	area_name varchar2(32),
	-- 收货人
	consignee varchar2(32),
	-- 优惠劵折扣
	coupon_discount number,
	-- 到期时间
	expire timestamp,
	-- 支付手续费
	fee number,
	-- 发票抬头
	invoice_title varchar2(32),
	-- 是否分配库存
	is_allocated_stock varchar2(32),
	-- 是否开据发票
	is_invoice varchar2(32),
	-- 锁定到期时间
	lock_expire timestamp,
	-- 附言
	memo varchar2(32),
	-- 调整金额
	offset_amount number,
	-- 订单状态
	order_status varchar2(32),
	-- 支付方式名称
	payment_method_name varchar2(32),
	-- 支付状态
	payment_status varchar2(32),
	-- 电话
	phone varchar2(32),
	-- 赠送积分
	point varchar2(32),
	-- 促销
	promotion varchar2(32),
	-- 促销折扣
	promotion_discount number,
	-- 配送方式名称
	shipping_method_name varchar2(32),
	-- 配送状态
	shipping_status varchar2(32),
	-- 税金
	tax varchar2(32),
	-- 邮编
	zip_code varchar2(32),
	-- 地区
	area varchar2(32),
	-- 优惠码
	coupon_code varchar2(32),
	-- 支付方式
	payment_method varchar2(32),
	-- 配送方式
	shipping_method varchar2(32),
	-- 用户名
	user_name varchar2(32),
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


-- 订单项表
CREATE TABLE order_table_item
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 订单表ID
	order_table_id varchar2(64) NOT NULL,
	-- 商品全称
	full_name varchar2(32),
	-- 是否为赠品
	is_gift varchar2(32),
	-- 商品名称
	name varchar2(32),
	-- 商品价格
	price number,
	-- 商品数量
	quantity number(10,0),
	-- 退货量
	return_quantity number(10,0),
	-- 发货量
	shipped_quantity number(10,0),
	-- 商品编号
	goods_no varchar2(32),
	-- 缩略图
	thumbnail varchar2(32),
	-- 商品重量
	weight number(10,0),
	-- 订单编号
	order_no varchar2(32),
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


-- 订单日志
CREATE TABLE order_table_log
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 内容
	content varchar2(255),
	-- 操作员
	operator varchar2(32),
	-- 类型
	type varchar2(32),
	-- 订单编号
	order_no varchar2(32),
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


-- 收款单
CREATE TABLE order_table_payment
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 订单表ID
	order_table_id varchar2(64) NOT NULL,
	-- 收款单编号
	payment_no varchar2(32),
	-- 订单编号
	order_no varchar2(32),
	-- 方式
	method varchar2(32),
	-- 收款账号
	account varchar2(32),
	-- 付款金额
	amount number,
	-- 收款银行
	bank varchar2(32),
	-- 到期时间
	expire timestamp,
	-- 支付手续费
	fee number,
	-- 操作员
	operator varchar2(32),
	-- 付款人
	payer varchar2(32),
	-- 付款日期
	payment_date timestamp,
	-- 付款方式
	payment_method varchar2(32),
	-- 支付插件编号
	payment_plugin_id varchar2(32),
	-- 状态
	status number(10,0),
	-- 用户名
	user_name varchar2(32),
	-- 类型
	type varchar2(32),
	-- 会员
	member varchar2(32),
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


-- 退款单
CREATE TABLE order_table_refunds
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 订单表ID
	order_table_id varchar2(64) NOT NULL,
	-- 退款单编号
	refund_no varchar2(32),
	-- 退款账号
	account varchar2(32),
	-- 退款金额
	amount number,
	-- 退款银行
	bank varchar2(32),
	-- 方式
	method varchar2(32),
	-- 操作员
	operator varchar2(32),
	-- 收款人
	payee varchar2(32),
	-- 支付方式
	payment_method varchar2(32),
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


-- 退货单
CREATE TABLE order_table_returns
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 退货单编号
	return_no varchar2(32),
	-- 地址
	address varchar2(32),
	-- 地区
	area varchar2(32),
	-- 物流公司
	delivery_corp varchar2(32),
	-- 物流费用
	freight number,
	-- 操作员
	operator varchar2(32),
	-- 电话
	phone varchar2(32),
	-- 发货人
	shipper varchar2(32),
	-- 配送方式
	shipping_method varchar2(32),
	-- 运单号
	tracking_no varchar2(32),
	-- 邮编
	zip_code varchar2(32),
	-- 订单编号
	order_no varchar2(32),
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


-- 退货项
CREATE TABLE order_table_returns_item
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 退货单ID
	order_table_returns_id varchar2(64) NOT NULL,
	-- 商品名称
	name varchar2(32),
	-- 数量
	quantity number(10,0),
	-- 退货单编号
	return_no varchar2(32),
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


-- 发货单
CREATE TABLE order_table_shipping
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 发货单编号
	shipping_no varchar2(32),
	-- 地址
	address varchar2(32),
	-- 地区
	area varchar2(32),
	-- 收货人
	consignee varchar2(32),
	-- 物流公司
	delivery_corp varchar2(32),
	-- 物流公司代码
	delivery_corp_code varchar2(32),
	-- 物流公司网址
	delivery_corp_url varchar2(32),
	-- 物流费用
	freight number,
	-- 操作员
	operator varchar2(32),
	-- 电话
	phone varchar2(32),
	-- 配送方式
	shipping_method varchar2(32),
	-- 运单号
	tracking_no varchar2(32),
	-- 邮编
	zip_code varchar2(32),
	-- 订单编号
	order_no varchar2(32),
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


-- 发货项
CREATE TABLE order_table_shipping_item
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 发货单ID
	order_table_shipping_id varchar2(64) NOT NULL,
	-- 商品名称
	name varchar2(32),
	-- 商品数量
	quantity number(10,0),
	-- 发货单编号
	shipping_no varchar2(32),
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



/* Create Foreign Keys */

ALTER TABLE order_table_refunds
	ADD FOREIGN KEY (order_table_id)
	REFERENCES order_table (id)
;


ALTER TABLE order_table_payment
	ADD FOREIGN KEY (order_table_id)
	REFERENCES order_table (id)
;


ALTER TABLE order_table_item
	ADD FOREIGN KEY (order_table_id)
	REFERENCES order_table (id)
;


ALTER TABLE order_returns_ref
	ADD FOREIGN KEY (return_id)
	REFERENCES order_table (id)
;


ALTER TABLE order_shipping_ref
	ADD FOREIGN KEY (shipping_id)
	REFERENCES order_table (id)
;


ALTER TABLE order_table_returns_item
	ADD FOREIGN KEY (order_table_returns_id)
	REFERENCES order_table_returns (id)
;


ALTER TABLE order_returns_ref
	ADD FOREIGN KEY (order_tabel_id)
	REFERENCES order_table_returns (id)
;


ALTER TABLE order_table_shipping_item
	ADD FOREIGN KEY (order_table_shipping_id)
	REFERENCES order_table_shipping (id)
;


ALTER TABLE order_shipping_ref
	ADD FOREIGN KEY (order_tabel_id)
	REFERENCES order_table_shipping (id)
;



/* Comments */

COMMENT ON TABLE order_returns_ref IS '订单退货关联表';
COMMENT ON COLUMN order_returns_ref.id IS '主键ID';
COMMENT ON COLUMN order_returns_ref.return_id IS '退货主键ID';
COMMENT ON COLUMN order_returns_ref.order_tabel_id IS '订单主键ID';
COMMENT ON TABLE order_shipping_ref IS '订单发货关联表';
COMMENT ON COLUMN order_shipping_ref.id IS '主键ID';
COMMENT ON COLUMN order_shipping_ref.shipping_id IS '发货主键ID';
COMMENT ON COLUMN order_shipping_ref.order_tabel_id IS '订单主键ID';
COMMENT ON TABLE order_table IS '订单表';
COMMENT ON COLUMN order_table.id IS '主键';
COMMENT ON COLUMN order_table.order_no IS '订单编号';
COMMENT ON COLUMN order_table.address IS '地址';
COMMENT ON COLUMN order_table.amount_paid IS '已付金额';
COMMENT ON COLUMN order_table.area_name IS '地区名称';
COMMENT ON COLUMN order_table.consignee IS '收货人';
COMMENT ON COLUMN order_table.coupon_discount IS '优惠劵折扣';
COMMENT ON COLUMN order_table.expire IS '到期时间';
COMMENT ON COLUMN order_table.fee IS '支付手续费';
COMMENT ON COLUMN order_table.invoice_title IS '发票抬头';
COMMENT ON COLUMN order_table.is_allocated_stock IS '是否分配库存';
COMMENT ON COLUMN order_table.is_invoice IS '是否开据发票';
COMMENT ON COLUMN order_table.lock_expire IS '锁定到期时间';
COMMENT ON COLUMN order_table.memo IS '附言';
COMMENT ON COLUMN order_table.offset_amount IS '调整金额';
COMMENT ON COLUMN order_table.order_status IS '订单状态';
COMMENT ON COLUMN order_table.payment_method_name IS '支付方式名称';
COMMENT ON COLUMN order_table.payment_status IS '支付状态';
COMMENT ON COLUMN order_table.phone IS '电话';
COMMENT ON COLUMN order_table.point IS '赠送积分';
COMMENT ON COLUMN order_table.promotion IS '促销';
COMMENT ON COLUMN order_table.promotion_discount IS '促销折扣';
COMMENT ON COLUMN order_table.shipping_method_name IS '配送方式名称';
COMMENT ON COLUMN order_table.shipping_status IS '配送状态';
COMMENT ON COLUMN order_table.tax IS '税金';
COMMENT ON COLUMN order_table.zip_code IS '邮编';
COMMENT ON COLUMN order_table.area IS '地区';
COMMENT ON COLUMN order_table.coupon_code IS '优惠码';
COMMENT ON COLUMN order_table.payment_method IS '支付方式';
COMMENT ON COLUMN order_table.shipping_method IS '配送方式';
COMMENT ON COLUMN order_table.user_name IS '用户名';
COMMENT ON COLUMN order_table.create_by IS '创建者';
COMMENT ON COLUMN order_table.create_date IS '创建时间';
COMMENT ON COLUMN order_table.update_by IS '更新者';
COMMENT ON COLUMN order_table.update_date IS '更新时间';
COMMENT ON COLUMN order_table.remarks IS '备注信息';
COMMENT ON COLUMN order_table.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_item IS '订单项表';
COMMENT ON COLUMN order_table_item.id IS '主键';
COMMENT ON COLUMN order_table_item.order_table_id IS '订单表ID';
COMMENT ON COLUMN order_table_item.full_name IS '商品全称';
COMMENT ON COLUMN order_table_item.is_gift IS '是否为赠品';
COMMENT ON COLUMN order_table_item.name IS '商品名称';
COMMENT ON COLUMN order_table_item.price IS '商品价格';
COMMENT ON COLUMN order_table_item.quantity IS '商品数量';
COMMENT ON COLUMN order_table_item.return_quantity IS '退货量';
COMMENT ON COLUMN order_table_item.shipped_quantity IS '发货量';
COMMENT ON COLUMN order_table_item.goods_no IS '商品编号';
COMMENT ON COLUMN order_table_item.thumbnail IS '缩略图';
COMMENT ON COLUMN order_table_item.weight IS '商品重量';
COMMENT ON COLUMN order_table_item.order_no IS '订单编号';
COMMENT ON COLUMN order_table_item.create_by IS '创建者';
COMMENT ON COLUMN order_table_item.create_date IS '创建时间';
COMMENT ON COLUMN order_table_item.update_by IS '更新者';
COMMENT ON COLUMN order_table_item.update_date IS '更新时间';
COMMENT ON COLUMN order_table_item.remarks IS '备注信息';
COMMENT ON COLUMN order_table_item.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_log IS '订单日志';
COMMENT ON COLUMN order_table_log.id IS '主键';
COMMENT ON COLUMN order_table_log.content IS '内容';
COMMENT ON COLUMN order_table_log.operator IS '操作员';
COMMENT ON COLUMN order_table_log.type IS '类型';
COMMENT ON COLUMN order_table_log.order_no IS '订单编号';
COMMENT ON COLUMN order_table_log.create_by IS '创建者';
COMMENT ON COLUMN order_table_log.create_date IS '创建时间';
COMMENT ON COLUMN order_table_log.update_by IS '更新者';
COMMENT ON COLUMN order_table_log.update_date IS '更新时间';
COMMENT ON COLUMN order_table_log.remarks IS '备注信息';
COMMENT ON COLUMN order_table_log.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_payment IS '收款单';
COMMENT ON COLUMN order_table_payment.id IS '主键';
COMMENT ON COLUMN order_table_payment.order_table_id IS '订单表ID';
COMMENT ON COLUMN order_table_payment.payment_no IS '收款单编号';
COMMENT ON COLUMN order_table_payment.order_no IS '订单编号';
COMMENT ON COLUMN order_table_payment.method IS '方式';
COMMENT ON COLUMN order_table_payment.account IS '收款账号';
COMMENT ON COLUMN order_table_payment.amount IS '付款金额';
COMMENT ON COLUMN order_table_payment.bank IS '收款银行';
COMMENT ON COLUMN order_table_payment.expire IS '到期时间';
COMMENT ON COLUMN order_table_payment.fee IS '支付手续费';
COMMENT ON COLUMN order_table_payment.operator IS '操作员';
COMMENT ON COLUMN order_table_payment.payer IS '付款人';
COMMENT ON COLUMN order_table_payment.payment_date IS '付款日期';
COMMENT ON COLUMN order_table_payment.payment_method IS '付款方式';
COMMENT ON COLUMN order_table_payment.payment_plugin_id IS '支付插件编号';
COMMENT ON COLUMN order_table_payment.status IS '状态';
COMMENT ON COLUMN order_table_payment.user_name IS '用户名';
COMMENT ON COLUMN order_table_payment.type IS '类型';
COMMENT ON COLUMN order_table_payment.member IS '会员';
COMMENT ON COLUMN order_table_payment.create_by IS '创建者';
COMMENT ON COLUMN order_table_payment.create_date IS '创建时间';
COMMENT ON COLUMN order_table_payment.update_by IS '更新者';
COMMENT ON COLUMN order_table_payment.update_date IS '更新时间';
COMMENT ON COLUMN order_table_payment.remarks IS '备注信息';
COMMENT ON COLUMN order_table_payment.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_refunds IS '退款单';
COMMENT ON COLUMN order_table_refunds.id IS '主键';
COMMENT ON COLUMN order_table_refunds.order_table_id IS '订单表ID';
COMMENT ON COLUMN order_table_refunds.refund_no IS '退款单编号';
COMMENT ON COLUMN order_table_refunds.account IS '退款账号';
COMMENT ON COLUMN order_table_refunds.amount IS '退款金额';
COMMENT ON COLUMN order_table_refunds.bank IS '退款银行';
COMMENT ON COLUMN order_table_refunds.method IS '方式';
COMMENT ON COLUMN order_table_refunds.operator IS '操作员';
COMMENT ON COLUMN order_table_refunds.payee IS '收款人';
COMMENT ON COLUMN order_table_refunds.payment_method IS '支付方式';
COMMENT ON COLUMN order_table_refunds.create_by IS '创建者';
COMMENT ON COLUMN order_table_refunds.create_date IS '创建时间';
COMMENT ON COLUMN order_table_refunds.update_by IS '更新者';
COMMENT ON COLUMN order_table_refunds.update_date IS '更新时间';
COMMENT ON COLUMN order_table_refunds.remarks IS '备注信息';
COMMENT ON COLUMN order_table_refunds.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_returns IS '退货单';
COMMENT ON COLUMN order_table_returns.id IS '主键';
COMMENT ON COLUMN order_table_returns.return_no IS '退货单编号';
COMMENT ON COLUMN order_table_returns.address IS '地址';
COMMENT ON COLUMN order_table_returns.area IS '地区';
COMMENT ON COLUMN order_table_returns.delivery_corp IS '物流公司';
COMMENT ON COLUMN order_table_returns.freight IS '物流费用';
COMMENT ON COLUMN order_table_returns.operator IS '操作员';
COMMENT ON COLUMN order_table_returns.phone IS '电话';
COMMENT ON COLUMN order_table_returns.shipper IS '发货人';
COMMENT ON COLUMN order_table_returns.shipping_method IS '配送方式';
COMMENT ON COLUMN order_table_returns.tracking_no IS '运单号';
COMMENT ON COLUMN order_table_returns.zip_code IS '邮编';
COMMENT ON COLUMN order_table_returns.order_no IS '订单编号';
COMMENT ON COLUMN order_table_returns.create_by IS '创建者';
COMMENT ON COLUMN order_table_returns.create_date IS '创建时间';
COMMENT ON COLUMN order_table_returns.update_by IS '更新者';
COMMENT ON COLUMN order_table_returns.update_date IS '更新时间';
COMMENT ON COLUMN order_table_returns.remarks IS '备注信息';
COMMENT ON COLUMN order_table_returns.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_returns_item IS '退货项';
COMMENT ON COLUMN order_table_returns_item.id IS '主键';
COMMENT ON COLUMN order_table_returns_item.order_table_returns_id IS '退货单ID';
COMMENT ON COLUMN order_table_returns_item.name IS '商品名称';
COMMENT ON COLUMN order_table_returns_item.quantity IS '数量';
COMMENT ON COLUMN order_table_returns_item.return_no IS '退货单编号';
COMMENT ON COLUMN order_table_returns_item.create_by IS '创建者';
COMMENT ON COLUMN order_table_returns_item.create_date IS '创建时间';
COMMENT ON COLUMN order_table_returns_item.update_by IS '更新者';
COMMENT ON COLUMN order_table_returns_item.update_date IS '更新时间';
COMMENT ON COLUMN order_table_returns_item.remarks IS '备注信息';
COMMENT ON COLUMN order_table_returns_item.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_shipping IS '发货单';
COMMENT ON COLUMN order_table_shipping.id IS '主键';
COMMENT ON COLUMN order_table_shipping.shipping_no IS '发货单编号';
COMMENT ON COLUMN order_table_shipping.address IS '地址';
COMMENT ON COLUMN order_table_shipping.area IS '地区';
COMMENT ON COLUMN order_table_shipping.consignee IS '收货人';
COMMENT ON COLUMN order_table_shipping.delivery_corp IS '物流公司';
COMMENT ON COLUMN order_table_shipping.delivery_corp_code IS '物流公司代码';
COMMENT ON COLUMN order_table_shipping.delivery_corp_url IS '物流公司网址';
COMMENT ON COLUMN order_table_shipping.freight IS '物流费用';
COMMENT ON COLUMN order_table_shipping.operator IS '操作员';
COMMENT ON COLUMN order_table_shipping.phone IS '电话';
COMMENT ON COLUMN order_table_shipping.shipping_method IS '配送方式';
COMMENT ON COLUMN order_table_shipping.tracking_no IS '运单号';
COMMENT ON COLUMN order_table_shipping.zip_code IS '邮编';
COMMENT ON COLUMN order_table_shipping.order_no IS '订单编号';
COMMENT ON COLUMN order_table_shipping.create_by IS '创建者';
COMMENT ON COLUMN order_table_shipping.create_date IS '创建时间';
COMMENT ON COLUMN order_table_shipping.update_by IS '更新者';
COMMENT ON COLUMN order_table_shipping.update_date IS '更新时间';
COMMENT ON COLUMN order_table_shipping.remarks IS '备注信息';
COMMENT ON COLUMN order_table_shipping.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE order_table_shipping_item IS '发货项';
COMMENT ON COLUMN order_table_shipping_item.id IS '主键';
COMMENT ON COLUMN order_table_shipping_item.order_table_shipping_id IS '发货单ID';
COMMENT ON COLUMN order_table_shipping_item.name IS '商品名称';
COMMENT ON COLUMN order_table_shipping_item.quantity IS '商品数量';
COMMENT ON COLUMN order_table_shipping_item.shipping_no IS '发货单编号';
COMMENT ON COLUMN order_table_shipping_item.create_by IS '创建者';
COMMENT ON COLUMN order_table_shipping_item.create_date IS '创建时间';
COMMENT ON COLUMN order_table_shipping_item.update_by IS '更新者';
COMMENT ON COLUMN order_table_shipping_item.update_date IS '更新时间';
COMMENT ON COLUMN order_table_shipping_item.remarks IS '备注信息';
COMMENT ON COLUMN order_table_shipping_item.del_flag IS '删除标记（0：正常；1：删除）';



