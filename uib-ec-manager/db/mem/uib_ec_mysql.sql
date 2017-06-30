SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS deposit;
DROP TABLE IF EXISTS mem_merchant;
DROP TABLE IF EXISTS mem_member;
DROP TABLE IF EXISTS mem_rank;




/* Create Tables */

-- 预存款
CREATE TABLE deposit
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 会员主键
	member_no varchar(64) NOT NULL COMMENT '会员主键',
	-- 当前余额
	balance decimal COMMENT '当前余额',
	-- 收入金额
	credit decimal COMMENT '收入金额',
	-- 支出金额
	debit decimal COMMENT '支出金额',
	-- 操作员
	operator varchar(32) COMMENT '操作员',
	-- 类型
	type varchar(32) COMMENT '类型',
	-- 订单
	order_no varchar(32) COMMENT '订单',
	-- 收款单
	payment_no varchar(32) COMMENT '收款单',
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
) COMMENT = '预存款';


-- 会员信息表
CREATE TABLE mem_member
(
	-- 主健
	id varchar(64) NOT NULL COMMENT '主健',
	-- 姓名
	name varchar(255) NOT NULL COMMENT '姓名',
	-- 用户名
	username varchar(32) COMMENT '用户名',
	-- 密码
	password varchar(32) NOT NULL COMMENT '密码',
	-- 手机号
	phone varchar(11) NOT NULL COMMENT '手机号',
	-- 地址
	address varchar(255) COMMENT '地址',
	-- 消费金额
	amount decimal(19,2) DEFAULT 0 NOT NULL COMMENT '消费金额',
	-- 余额
	balance decimal(19,2) DEFAULT 0 NOT NULL COMMENT '余额',
	-- 出生日期
	birth datetime COMMENT '出生日期',
	-- 邮箱地址
	email varchar(255) NOT NULL COMMENT '邮箱地址',
	-- 性别
	gender char(1) NOT NULL COMMENT '性别',
	-- 锁定日期
	locked_date datetime COMMENT '锁定日期',
	-- 连续登录失改次数
	login_failure_count int(11) DEFAULT 0 COMMENT '连续登录失改次数',
	-- 最后登录ip
	ogin_ip varchar(255) COMMENT '最后登录ip',
	-- 积分
	point varchar(20) COMMENT '积分',
	-- 注册ip
	register_ip varchar(255) COMMENT '注册ip',
	-- 安全密钥到期时间
	safe_key_expire datetime COMMENT '安全密钥到期时间',
	-- 安全秘钥
	safe_key_value varchar(255) COMMENT '安全秘钥',
	-- 邮编
	zip_code varchar(255) NOT NULL COMMENT '邮编',
	-- 地区
	area varchar(255) COMMENT '地区',
	-- 会员等级
	rank_id varchar(64) NOT NULL COMMENT '会员等级',
	-- 身份证号
	id_card varchar(18) COMMENT '身份证号',
	-- 是否启用
	is_enabled char(1) DEFAULT '0' NOT NULL COMMENT '是否启用',
	-- 是否锁定
	is_locked char(1) DEFAULT '0' NOT NULL COMMENT '是否锁定',
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
) COMMENT = '会员信息表';


-- 卖家信息表
CREATE TABLE mem_merchant
(
	-- 主健
	ID varchar(64) NOT NULL COMMENT '主健',
	-- 商户编号
	MERCHANT_NO varchar(20) NOT NULL COMMENT '商户编号',
	-- 商户名称
	MERCHANT_NAME varchar(20) NOT NULL COMMENT '商户名称',
	-- 入驻时间
	REGISTER_DATE timestamp COMMENT '入驻时间',
	-- 有效期时间
	EFFECTIVE_DATE timestamp COMMENT '有效期时间',
	-- 模板名称
	TEMPLATE_NUM varchar(32) COMMENT '模板名称',
	-- 主页地址
	MERCHANT_PAGE varchar(32) COMMENT '主页地址',
	-- 联系人姓名
	CONTACT_NAME varchar(20) COMMENT '联系人姓名',
	-- 联系人邮箱
	EMAIL varchar(32) COMMENT '联系人邮箱',
	-- 联系人手机号
	PHONE varchar(11) COMMENT '联系人手机号',
	-- 引用member主健
	member_id varchar(64) NOT NULL COMMENT '引用member主健',
	EXT1 varchar(32),
	EXT2 varchar(32),
	EXT3 varchar(32),
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
	PRIMARY KEY (ID)
) COMMENT = '卖家信息表';


-- 会员等级表
CREATE TABLE mem_rank
(
	-- 主健
	id varchar(64) NOT NULL COMMENT '主健',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 消费金额
	amount decimal(19,2) DEFAULT 0 COMMENT '消费金额',
	-- 是否默认(0:否,1:是)
	is_defult char(1) DEFAULT '0' COMMENT '是否默认(0:否,1:是)',
	-- 是否特殊(0:否,1:是)
	is_special char(1) DEFAULT '0' COMMENT '是否特殊(0:否,1:是)',
	-- 优惠比例
	scale decimal(19,2) COMMENT '优惠比例',
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
) COMMENT = '会员等级表';



/* Create Foreign Keys */

ALTER TABLE deposit
	ADD FOREIGN KEY (member_no)
	REFERENCES mem_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE deposit
	ADD FOREIGN KEY (order_no)
	REFERENCES mem_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
ALTER TABLE deposit
	ADD FOREIGN KEY (payment_no)
	REFERENCES mem_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE mem_merchant
	ADD FOREIGN KEY (member_id)
	REFERENCES mem_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mem_member
	ADD FOREIGN KEY (rank_id)
	REFERENCES mem_rank (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

-- 收货地址
CREATE TABLE mem_receiver
(
	-- 主健
	id varchar(64) NOT NULL COMMENT '主健',
	-- 地址
	address varchar(2000) COMMENT '地址',
	-- 区域名称
	area_name varchar(2000) COMMENT '区域名称',
	-- 联系人
	consignee varchar(200) COMMENT '联系人',
	-- 是否默认
	is_default varchar(1) DEFAULT '0' COMMENT '是否默认',
	-- 手机号
	phone varchar(13) COMMENT '手机号',
	-- 邮编
	zip_code varchar(8) COMMENT '邮编',
	-- 地区
	area varchar(30) COMMENT '地区',
	-- 会员编号
	member_id varchar(64) COMMENT '会员编号',
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
) COMMENT = '收货地址';



