SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS navigation;




/* Create Tables */

-- 导航
CREATE TABLE navigation
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 名称
	name varchar(255) COMMENT '名称',
	-- 链接地址
	url varchar(255) COMMENT '链接地址',
	-- 位置
	position int COMMENT '位置',
	-- 所属标签
	tag int COMMENT '所属标签',
	-- 是否新窗口打开
	is_blank_target bit(1) COMMENT '是否新窗口打开',
	-- 排序
	orders int COMMENT '排序',
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
) COMMENT = '导航';



