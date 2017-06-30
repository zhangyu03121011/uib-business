SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS order_returns_ref;
DROP TABLE IF EXISTS order_shipping_ref;
DROP TABLE IF EXISTS order_table_refunds;
DROP TABLE IF EXISTS order_table_payment;
DROP TABLE IF EXISTS order_table_item;
DROP TABLE IF EXISTS order_table;
DROP TABLE IF EXISTS order_table_log;
DROP TABLE IF EXISTS order_table_returns_item;
DROP TABLE IF EXISTS order_table_returns;
DROP TABLE IF EXISTS order_table_shipping_item;
DROP TABLE IF EXISTS order_table_shipping;




/* Create Tables */

-- 订单退货关联表
CREATE TABLE order_returns_ref
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 退货主键ID
	return_id varchar(64) NOT NULL COMMENT '退货主键ID',
	-- 订单主键ID
	order_tabel_id varchar(64) NOT NULL COMMENT '订单主键ID',
	PRIMARY KEY (id)
) COMMENT = '订单退货关联表';


-- 订单发货关联表
CREATE TABLE order_shipping_ref
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 发货主键ID
	shipping_id varchar(64) NOT NULL COMMENT '发货主键ID',
	-- 订单主键ID
	order_tabel_id varchar(64) NOT NULL COMMENT '订单主键ID',
	PRIMARY KEY (id)
) COMMENT = '订单发货关联表';


-- 订单表
CREATE TABLE order_table
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 订单编号
	order_no varchar(32) NOT NULL COMMENT '订单编号',
	-- 地址
	address varchar(32) COMMENT '地址',
	-- 已付金额
	amount_paid decimal COMMENT '已付金额',
	-- 订单金额
	order_amount decimal COMMENT '订单金额',
	-- 地区名称
	area_name varchar(32) COMMENT '地区名称',
	-- 收货人
	consignee varchar(32) COMMENT '收货人',
	-- 优惠劵折扣
	coupon_discount decimal COMMENT '优惠劵折扣',
	-- 到期时间
	expire datetime COMMENT '到期时间',
	-- 支付手续费
	fee decimal COMMENT '支付手续费',
	-- 发票抬头
	invoice_title varchar(32) COMMENT '发票抬头',
	-- 是否分配库存
	is_allocated_stock varchar(32) COMMENT '是否分配库存',
	-- 是否开据发票
	is_invoice varchar(32) COMMENT '是否开据发票',
	-- 锁定到期时间
	lock_expire datetime COMMENT '锁定到期时间',
	-- 附言
	memo varchar(32) COMMENT '附言',
	-- 调整金额
	offset_amount decimal COMMENT '调整金额',
	-- 订单状态
	order_status varchar(32) COMMENT '订单状态',
	-- 支付方式名称
	payment_method_name varchar(32) COMMENT '支付方式名称',
	-- 支付状态
	payment_status varchar(32) COMMENT '支付状态',
	-- 电话
	phone varchar(32) COMMENT '电话',
	-- 赠送积分
	point varchar(32) COMMENT '赠送积分',
	-- 促销
	promotion varchar(32) COMMENT '促销',
	-- 促销折扣
	promotion_discount decimal COMMENT '促销折扣',
	-- 配送方式名称
	shipping_method_name varchar(32) COMMENT '配送方式名称',
	-- 配送状态
	shipping_status varchar(32) COMMENT '配送状态',
	-- 税金
	tax varchar(32) COMMENT '税金',
	-- 邮编
	zip_code varchar(32) COMMENT '邮编',
	-- 地区
	area varchar(32) COMMENT '地区',
	-- 优惠码
	coupon_code varchar(32) COMMENT '优惠码',
	-- 支付方式
	payment_method varchar(32) COMMENT '支付方式',
	-- 配送方式
	shipping_method varchar(32) COMMENT '配送方式',
	-- 用户名
	user_name varchar(32) COMMENT '用户名',
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
	PRIMARY KEY (id),
	UNIQUE (order_no)
) COMMENT = '订单表';


-- 订单项表
CREATE TABLE order_table_item
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 订单表ID
	order_table_id varchar(64) NOT NULL COMMENT '订单表ID',
	-- 商品全称
	full_name varchar(32) COMMENT '商品全称',
	-- 是否为赠品
	is_gift varchar(32) COMMENT '是否为赠品',
	-- 商品名称
	name varchar(32) COMMENT '商品名称',
	-- 商品价格
	price decimal COMMENT '商品价格',
	-- 商品数量
	quantity int COMMENT '商品数量',
	-- 退货量
	return_quantity int COMMENT '退货量',
	-- 发货量
	shipped_quantity int COMMENT '发货量',
	-- 商品编号
	goods_no varchar(32) COMMENT '商品编号',
	-- 缩略图
	thumbnail varchar(32) COMMENT '缩略图',
	-- 商品重量
	weight int COMMENT '商品重量',
	-- 订单编号
	order_no varchar(32) COMMENT '订单编号',
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
) COMMENT = '订单项表';


-- 订单日志
CREATE TABLE order_table_log
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 内容
	content varchar(255) COMMENT '内容',
	-- 操作员
	operator varchar(32) COMMENT '操作员',
	-- 类型
	type varchar(32) COMMENT '类型',
	-- 订单编号
	order_no varchar(32) COMMENT '订单编号',
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
) COMMENT = '订单日志';


-- 收款单
CREATE TABLE order_table_payment
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 订单表ID
	order_table_id varchar(64) NOT NULL COMMENT '订单表ID',
	-- 收款单编号
	payment_no varchar(32) COMMENT '收款单编号',
	-- 订单编号
	order_no varchar(32) COMMENT '订单编号',
	-- 方式
	method varchar(32) COMMENT '方式',
	-- 收款账号
	account varchar(32) COMMENT '收款账号',
	-- 付款金额
	amount decimal COMMENT '付款金额',
	-- 收款银行
	bank varchar(32) COMMENT '收款银行',
	-- 到期时间
	expire datetime COMMENT '到期时间',
	-- 支付手续费
	fee decimal COMMENT '支付手续费',
	-- 操作员
	operator varchar(32) COMMENT '操作员',
	-- 付款人
	payer varchar(32) COMMENT '付款人',
	-- 付款日期
	payment_date datetime COMMENT '付款日期',
	-- 付款方式
	payment_method varchar(32) COMMENT '付款方式',
	-- 支付插件编号
	payment_plugin_id varchar(32) COMMENT '支付插件编号',
	-- 状态
	status int COMMENT '状态',
	-- 用户名
	user_name varchar(32) COMMENT '用户名',
	-- 类型
	type varchar(32) COMMENT '类型',
	-- 会员
	member varchar(32) COMMENT '会员',
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
) COMMENT = '收款单';


-- 退款单
CREATE TABLE order_table_refunds
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 订单表ID
	order_table_id varchar(64) NOT NULL COMMENT '订单表ID',
	-- 退款单编号
	refund_no varchar(32) COMMENT '退款单编号',
	-- 退款账号
	account varchar(32) COMMENT '退款账号',
	-- 退款金额
	amount decimal COMMENT '退款金额',
	-- 退款银行
	bank varchar(32) COMMENT '退款银行',
	-- 方式
	method varchar(32) COMMENT '方式',
	-- 操作员
	operator varchar(32) COMMENT '操作员',
	-- 收款人
	payee varchar(32) COMMENT '收款人',
	-- 支付方式
	payment_method varchar(32) COMMENT '支付方式',
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
) COMMENT = '退款单';


-- 退货单
CREATE TABLE order_table_returns
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 退货单编号
	return_no varchar(32) COMMENT '退货单编号',
	-- 地址
	address varchar(32) COMMENT '地址',
	-- 地区
	area varchar(32) COMMENT '地区',
	-- 物流公司
	delivery_corp varchar(32) COMMENT '物流公司',
	-- 物流费用
	freight decimal COMMENT '物流费用',
	-- 操作员
	operator varchar(32) COMMENT '操作员',
	-- 电话
	phone varchar(32) COMMENT '电话',
	-- 发货人
	shipper varchar(32) COMMENT '发货人',
	-- 配送方式
	shipping_method varchar(32) COMMENT '配送方式',
	-- 运单号
	tracking_no varchar(32) COMMENT '运单号',
	-- 邮编
	zip_code varchar(32) COMMENT '邮编',
	-- 订单编号
	order_no varchar(32) COMMENT '订单编号',
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
) COMMENT = '退货单';


-- 退货项
CREATE TABLE order_table_returns_item
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 退货单ID
	order_table_returns_id varchar(64) NOT NULL COMMENT '退货单ID',
	-- 商品名称
	name varchar(32) COMMENT '商品名称',
	-- 数量
	quantity int COMMENT '数量',
	-- 退货单编号
	return_no varchar(32) COMMENT '退货单编号',
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
) COMMENT = '退货项';


-- 发货单
CREATE TABLE order_table_shipping
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 发货单编号
	shipping_no varchar(32) COMMENT '发货单编号',
	-- 地址
	address varchar(32) COMMENT '地址',
	-- 地区
	area varchar(32) COMMENT '地区',
	-- 收货人
	consignee varchar(32) COMMENT '收货人',
	-- 物流公司
	delivery_corp varchar(32) COMMENT '物流公司',
	-- 物流公司代码
	delivery_corp_code varchar(32) COMMENT '物流公司代码',
	-- 物流公司网址
	delivery_corp_url varchar(32) COMMENT '物流公司网址',
	-- 物流费用
	freight decimal COMMENT '物流费用',
	-- 操作员
	operator varchar(32) COMMENT '操作员',
	-- 电话
	phone varchar(32) COMMENT '电话',
	-- 配送方式
	shipping_method varchar(32) COMMENT '配送方式',
	-- 运单号
	tracking_no varchar(32) COMMENT '运单号',
	-- 邮编
	zip_code varchar(32) COMMENT '邮编',
	-- 订单编号
	order_no varchar(32) COMMENT '订单编号',
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
) COMMENT = '发货单';


-- 发货项
CREATE TABLE order_table_shipping_item
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 发货单ID
	order_table_shipping_id varchar(64) NOT NULL COMMENT '发货单ID',
	-- 商品名称
	name varchar(32) COMMENT '商品名称',
	-- 商品数量
	quantity int COMMENT '商品数量',
	-- 发货单编号
	shipping_no varchar(32) COMMENT '发货单编号',
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
) COMMENT = '发货项';



/* Create Foreign Keys */

ALTER TABLE order_table_refunds
	ADD FOREIGN KEY (order_table_id)
	REFERENCES order_table (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE order_table_payment
	ADD FOREIGN KEY (order_table_id)
	REFERENCES order_table (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE order_table_item
	ADD FOREIGN KEY (order_table_id)
	REFERENCES order_table (order_no)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE order_returns_ref
	ADD FOREIGN KEY (return_id)
	REFERENCES order_table (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE order_shipping_ref
	ADD FOREIGN KEY (shipping_id)
	REFERENCES order_table (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE order_table_returns_item
	ADD FOREIGN KEY (order_table_returns_id)
	REFERENCES order_table_returns (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE order_returns_ref
	ADD FOREIGN KEY (order_tabel_id)
	REFERENCES order_table_returns (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE order_table_shipping_item
	ADD FOREIGN KEY (order_table_shipping_id)
	REFERENCES order_table_shipping (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE order_shipping_ref
	ADD FOREIGN KEY (order_tabel_id)
	REFERENCES order_table_shipping (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



