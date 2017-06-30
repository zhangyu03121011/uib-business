
/* Drop Tables */

DROP TABLE advertisement CASCADE CONSTRAINTS;
DROP TABLE advertisement_position CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE seq_oa_leave;
DROP SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT;




/* Create Sequences */

CREATE SEQUENCE seq_oa_leave INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 61 CACHE 20;



/* Create Tables */

-- 广告
CREATE TABLE advertisement
(
	-- 广告ID
	id varchar2(64) NOT NULL,
	-- 标题
	title varchar2(255),
	-- 内容
	content clob,
	-- 类型
	type number(10,0),
	-- 路径
	path varchar2(255),
	-- 排序
	orders number(10,0),
	-- 链接地址
	url varchar2(255),
	-- 起始日期
	begin_date date,
	-- 结束日期
	end_date date,
	-- 广告位编号
	ad_position_id varchar2(64) NOT NULL,
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


-- 广告位
CREATE TABLE advertisement_position
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 名称
	name varchar2(255),
	-- 描述
	description varchar2(255),
	-- 高度
	height number(10,0),
	-- 宽度
	width number(10,0),
	-- 模板
	template clob,
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

ALTER TABLE advertisement
	ADD FOREIGN KEY (ad_position_id)
	REFERENCES advertisement_position (id)
;



/* Comments */

COMMENT ON TABLE advertisement IS '广告';
COMMENT ON COLUMN advertisement.id IS '广告ID';
COMMENT ON COLUMN advertisement.title IS '标题';
COMMENT ON COLUMN advertisement.content IS '内容';
COMMENT ON COLUMN advertisement.type IS '类型';
COMMENT ON COLUMN advertisement.path IS '路径';
COMMENT ON COLUMN advertisement.orders IS '排序';
COMMENT ON COLUMN advertisement.url IS '链接地址';
COMMENT ON COLUMN advertisement.begin_date IS '起始日期';
COMMENT ON COLUMN advertisement.end_date IS '结束日期';
COMMENT ON COLUMN advertisement.ad_position_id IS '广告位编号';
COMMENT ON COLUMN advertisement.create_by IS '创建者';
COMMENT ON COLUMN advertisement.create_date IS '创建时间';
COMMENT ON COLUMN advertisement.update_by IS '更新者';
COMMENT ON COLUMN advertisement.update_date IS '更新时间';
COMMENT ON COLUMN advertisement.remarks IS '备注信息';
COMMENT ON COLUMN advertisement.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE advertisement_position IS '广告位';
COMMENT ON COLUMN advertisement_position.id IS '主键';
COMMENT ON COLUMN advertisement_position.name IS '名称';
COMMENT ON COLUMN advertisement_position.description IS '描述';
COMMENT ON COLUMN advertisement_position.height IS '高度';
COMMENT ON COLUMN advertisement_position.width IS '宽度';
COMMENT ON COLUMN advertisement_position.template IS '模板';
COMMENT ON COLUMN advertisement_position.create_by IS '创建者';
COMMENT ON COLUMN advertisement_position.create_date IS '创建时间';
COMMENT ON COLUMN advertisement_position.update_by IS '更新者';
COMMENT ON COLUMN advertisement_position.update_date IS '更新时间';
COMMENT ON COLUMN advertisement_position.remarks IS '备注信息';
COMMENT ON COLUMN advertisement_position.del_flag IS '删除标记（0：正常；1：删除）';



