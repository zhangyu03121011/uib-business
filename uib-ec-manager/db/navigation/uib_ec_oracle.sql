
/* Drop Tables */

DROP TABLE navigation CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE seq_oa_leave;
DROP SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT;




/* Create Sequences */

CREATE SEQUENCE seq_oa_leave INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 61 CACHE 20;



/* Create Tables */

-- 导航
CREATE TABLE navigation
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 名称
	name varchar2(255),
	-- 链接地址
	url varchar2(255),
	-- 位置
	position number(10,0),
	-- 所属标签
	tag number(10,0),
	-- 是否新窗口打开
	is_blank_target char,
	-- 排序
	orders number(10,0),
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

COMMENT ON TABLE navigation IS '导航';
COMMENT ON COLUMN navigation.id IS '主键';
COMMENT ON COLUMN navigation.name IS '名称';
COMMENT ON COLUMN navigation.url IS '链接地址';
COMMENT ON COLUMN navigation.position IS '位置';
COMMENT ON COLUMN navigation.tag IS '所属标签';
COMMENT ON COLUMN navigation.is_blank_target IS '是否新窗口打开';
COMMENT ON COLUMN navigation.orders IS '排序';
COMMENT ON COLUMN navigation.create_by IS '创建者';
COMMENT ON COLUMN navigation.create_date IS '创建时间';
COMMENT ON COLUMN navigation.update_by IS '更新者';
COMMENT ON COLUMN navigation.update_date IS '更新时间';
COMMENT ON COLUMN navigation.remarks IS '备注信息';
COMMENT ON COLUMN navigation.del_flag IS '删除标记（0：正常；1：删除）';



