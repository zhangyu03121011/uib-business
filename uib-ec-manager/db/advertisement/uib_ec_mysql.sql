SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS advertisement;
DROP TABLE IF EXISTS advertisement_position;




/* Create Tables */

-- 广告
CREATE TABLE advertisement
(
	-- 广告ID
	id varchar(64) NOT NULL COMMENT '广告ID',
	-- 标题
	title varchar(255) COMMENT '标题',
	-- 内容
	content longtext COMMENT '内容',
	-- 类型
	type int COMMENT '类型',
	-- 路径
	path varchar(255) COMMENT '路径',
	-- 广告位编号
	ad_position_id varchar(64) NOT NULL COMMENT '广告位编号',
	-- 排序
	orders int COMMENT '排序',
	-- 链接地址
	url varchar(255) COMMENT '链接地址',
	-- 起始日期
	begin_date datetime COMMENT '起始日期',
	-- 结束日期
	end_date datetime COMMENT '结束日期',
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
) COMMENT = '广告';


-- 广告位
CREATE TABLE advertisement_position
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 名称
	name varchar(255) COMMENT '名称',
	-- 高度
	height int COMMENT '高度',
	-- 宽度
	width int COMMENT '宽度',
	-- 描述
	description varchar(255) COMMENT '描述',
	-- 模板
	template longtext COMMENT '模板',
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
) COMMENT = '广告位';



/* Create Foreign Keys */

ALTER TABLE advertisement
	ADD FOREIGN KEY (ad_position_id)
	REFERENCES advertisement_position (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE advertisement ADD COLUMN `app_path` varchar(255) DEFAULT NULL COMMENT 'app展示图片路径';
ALTER TABLE advertisement ADD COLUMN `app_url` varchar(255) DEFAULT NULL COMMENT 'app图片链接地址';
ALTER TABLE advertisement ADD COLUMN `wx_path` varchar(255) DEFAULT NULL COMMENT '微信端展示图片路径';
ALTER TABLE advertisement ADD COLUMN `wx_url` varchar(255) DEFAULT NULL COMMENT '微信端展示图片链接地址';

