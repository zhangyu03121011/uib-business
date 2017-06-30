SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS delivery_corporation;




/* Create Tables */

-- 物流公司
CREATE TABLE delivery_corporation
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 排序
	orders int COMMENT '排序',
	-- 代码
	code varchar(255) COMMENT '代码',
	-- 名称
	name varchar(255) NOT NULL COMMENT '名称',
	-- 网址
	url varchar(255) COMMENT '网址',
	-- 创建日期
	create_date datetime COMMENT '创建日期',
	-- 修改日期
	modify_date datetime COMMENT '修改日期',
	PRIMARY KEY (id)
) COMMENT = '物流公司';



