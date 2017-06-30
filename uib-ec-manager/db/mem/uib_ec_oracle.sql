
/* Drop Tables */

DROP TABLE deposit CASCADE CONSTRAINTS;
DROP TABLE mem_merchant CASCADE CONSTRAINTS;
DROP TABLE mem_member CASCADE CONSTRAINTS;
DROP TABLE mem_rank CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE seq_oa_leave;
DROP SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT;




/* Create Sequences */

CREATE SEQUENCE seq_oa_leave INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 61 CACHE 20;



/* Create Tables */

-- 预存款
CREATE TABLE deposit
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 会员主键
	member_no varchar2(64) NOT NULL,
	-- 当前余额
	balance number,
	-- 收入金额
	credit number,
	-- 支出金额
	debit number,
	-- 操作员
	operator varchar2(32),
	-- 类型
	type varchar2(32),
	-- 订单
	order_no varchar2(32),
	-- 收款单
	payment_no varchar2(32),
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


-- 会员信息表
CREATE TABLE mem_member
(
	-- 主健
	id varchar2(64) NOT NULL,
	-- 姓名
	name varchar2(255) NOT NULL,
	-- 用户名
	username varchar2(32),
	-- 密码
	password varchar2(32) NOT NULL,
	-- 手机号
	phone varchar2(11) NOT NULL,
	-- 地址
	address varchar2(255),
	-- 消费金额
	amount number(19,2) DEFAULT 0 NOT NULL,
	-- 余额
	balance number(19,2) DEFAULT 0 NOT NULL,
	-- 出生日期
	birth timestamp,
	-- 邮箱地址
	email varchar2(255) NOT NULL,
	-- 性别
	gender char(1) NOT NULL,
	-- 锁定日期
	locked_date timestamp,
	-- 连续登录失改次数
	login_failure_count number(10,0) DEFAULT 0,
	-- 最后登录ip
	ogin_ip varchar2(255),
	-- 积分
	point varchar2(20),
	-- 注册ip
	register_ip varchar2(255),
	-- 安全密钥到期时间
	safe_key_expire timestamp,
	-- 安全秘钥
	safe_key_value varchar2(255),
	-- 邮编
	zip_code varchar2(255) NOT NULL,
	-- 地区
	area varchar2(255),
	-- 会员等级
	rank_id varchar2(64) NOT NULL,
	-- 身份证号
	id_card varchar2(18),
	-- 是否启用
	is_enabled char(1) DEFAULT '0' NOT NULL,
	-- 是否锁定
	is_locked char(1) DEFAULT '0' NOT NULL,
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


-- 卖家信息表
CREATE TABLE mem_merchant
(
	-- 主健
	ID varchar2(64) NOT NULL,
	-- 商户编号
	MERCHANT_NO varchar2(20) NOT NULL,
	-- 商户名称
	MERCHANT_NAME varchar2(20) NOT NULL,
	-- 入驻时间
	REGISTER_DATE timestamp,
	-- 有效期时间
	EFFECTIVE_DATE timestamp,
	-- 模板名称
	TEMPLATE_NUM varchar2(32),
	-- 主页地址
	MERCHANT_PAGE varchar2(32),
	-- 联系人姓名
	CONTACT_NAME varchar2(20),
	-- 联系人邮箱
	EMAIL varchar2(32),
	-- 联系人手机号
	PHONE varchar2(11),
	-- 引用member主健
	member_id varchar2(64) NOT NULL,
	EXT1 varchar2(32),
	EXT2 varchar2(32),
	EXT3 varchar2(32),
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
	PRIMARY KEY (ID)
);


-- 会员等级表
CREATE TABLE mem_rank
(
	-- 主健
	id varchar2(64) NOT NULL,
	-- 名称
	name varchar2(32),
	-- 消费金额
	amount number(19,2) DEFAULT 0,
	-- 是否默认(0:否,1:是)
	is_defult char(1) DEFAULT '0',
	-- 是否特殊(0:否,1:是)
	is_special char(1) DEFAULT '0',
	-- 优惠比例
	scale number(19,2),
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

ALTER TABLE deposit
	ADD FOREIGN KEY (member_no)
	REFERENCES mem_member (id)
;


ALTER TABLE mem_merchant
	ADD FOREIGN KEY (member_id)
	REFERENCES mem_member (id)
;


ALTER TABLE mem_member
	ADD FOREIGN KEY (rank_id)
	REFERENCES mem_rank (id)
;



/* Comments */

COMMENT ON TABLE deposit IS '预存款';
COMMENT ON COLUMN deposit.id IS '主键';
COMMENT ON COLUMN deposit.member_no IS '会员主键';
COMMENT ON COLUMN deposit.balance IS '当前余额';
COMMENT ON COLUMN deposit.credit IS '收入金额';
COMMENT ON COLUMN deposit.debit IS '支出金额';
COMMENT ON COLUMN deposit.operator IS '操作员';
COMMENT ON COLUMN deposit.type IS '类型';
COMMENT ON COLUMN deposit.order_no IS '订单';
COMMENT ON COLUMN deposit.payment_no IS '收款单';
COMMENT ON COLUMN deposit.create_by IS '创建者';
COMMENT ON COLUMN deposit.create_date IS '创建时间';
COMMENT ON COLUMN deposit.update_by IS '更新者';
COMMENT ON COLUMN deposit.update_date IS '更新时间';
COMMENT ON COLUMN deposit.remarks IS '备注信息';
COMMENT ON COLUMN deposit.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE mem_member IS '会员信息表';
COMMENT ON COLUMN mem_member.id IS '主健';
COMMENT ON COLUMN mem_member.name IS '姓名';
COMMENT ON COLUMN mem_member.username IS '用户名';
COMMENT ON COLUMN mem_member.password IS '密码';
COMMENT ON COLUMN mem_member.phone IS '手机号';
COMMENT ON COLUMN mem_member.address IS '地址';
COMMENT ON COLUMN mem_member.amount IS '消费金额';
COMMENT ON COLUMN mem_member.balance IS '余额';
COMMENT ON COLUMN mem_member.birth IS '出生日期';
COMMENT ON COLUMN mem_member.email IS '邮箱地址';
COMMENT ON COLUMN mem_member.gender IS '性别';
COMMENT ON COLUMN mem_member.locked_date IS '锁定日期';
COMMENT ON COLUMN mem_member.login_failure_count IS '连续登录失改次数';
COMMENT ON COLUMN mem_member.ogin_ip IS '最后登录ip';
COMMENT ON COLUMN mem_member.point IS '积分';
COMMENT ON COLUMN mem_member.register_ip IS '注册ip';
COMMENT ON COLUMN mem_member.safe_key_expire IS '安全密钥到期时间';
COMMENT ON COLUMN mem_member.safe_key_value IS '安全秘钥';
COMMENT ON COLUMN mem_member.zip_code IS '邮编';
COMMENT ON COLUMN mem_member.area IS '地区';
COMMENT ON COLUMN mem_member.rank_id IS '会员等级';
COMMENT ON COLUMN mem_member.id_card IS '身份证号';
COMMENT ON COLUMN mem_member.is_enabled IS '是否启用';
COMMENT ON COLUMN mem_member.is_locked IS '是否锁定';
COMMENT ON COLUMN mem_member.create_by IS '创建者';
COMMENT ON COLUMN mem_member.create_date IS '创建时间';
COMMENT ON COLUMN mem_member.update_by IS '更新者';
COMMENT ON COLUMN mem_member.update_date IS '更新时间';
COMMENT ON COLUMN mem_member.remarks IS '备注信息';
COMMENT ON COLUMN mem_member.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE mem_merchant IS '卖家信息表';
COMMENT ON COLUMN mem_merchant.ID IS '主健';
COMMENT ON COLUMN mem_merchant.MERCHANT_NO IS '商户编号';
COMMENT ON COLUMN mem_merchant.MERCHANT_NAME IS '商户名称';
COMMENT ON COLUMN mem_merchant.REGISTER_DATE IS '入驻时间';
COMMENT ON COLUMN mem_merchant.EFFECTIVE_DATE IS '有效期时间';
COMMENT ON COLUMN mem_merchant.TEMPLATE_NUM IS '模板名称';
COMMENT ON COLUMN mem_merchant.MERCHANT_PAGE IS '主页地址';
COMMENT ON COLUMN mem_merchant.CONTACT_NAME IS '联系人姓名';
COMMENT ON COLUMN mem_merchant.EMAIL IS '联系人邮箱';
COMMENT ON COLUMN mem_merchant.PHONE IS '联系人手机号';
COMMENT ON COLUMN mem_merchant.member_id IS '引用member主健';
COMMENT ON COLUMN mem_merchant.create_by IS '创建者';
COMMENT ON COLUMN mem_merchant.create_date IS '创建时间';
COMMENT ON COLUMN mem_merchant.update_by IS '更新者';
COMMENT ON COLUMN mem_merchant.update_date IS '更新时间';
COMMENT ON COLUMN mem_merchant.remarks IS '备注信息';
COMMENT ON COLUMN mem_merchant.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE mem_rank IS '会员等级表';
COMMENT ON COLUMN mem_rank.id IS '主健';
COMMENT ON COLUMN mem_rank.name IS '名称';
COMMENT ON COLUMN mem_rank.amount IS '消费金额';
COMMENT ON COLUMN mem_rank.is_defult IS '是否默认(0:否,1:是)';
COMMENT ON COLUMN mem_rank.is_special IS '是否特殊(0:否,1:是)';
COMMENT ON COLUMN mem_rank.scale IS '优惠比例';
COMMENT ON COLUMN mem_rank.create_by IS '创建者';
COMMENT ON COLUMN mem_rank.create_date IS '创建时间';
COMMENT ON COLUMN mem_rank.update_by IS '更新者';
COMMENT ON COLUMN mem_rank.update_date IS '更新时间';
COMMENT ON COLUMN mem_rank.remarks IS '备注信息';
COMMENT ON COLUMN mem_rank.del_flag IS '删除标记（0：正常；1：删除）';



