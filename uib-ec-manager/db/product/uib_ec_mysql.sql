SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS product_image_ref;
DROP TABLE IF EXISTS product_property_ref;
DROP TABLE IF EXISTS product_specification_ref;
DROP TABLE IF EXISTS gift_item;
DROP TABLE IF EXISTS product_review;
DROP TABLE IF EXISTS product_parameter_ref;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS product_parameter;
DROP TABLE IF EXISTS parameter_group;
DROP TABLE IF EXISTS product_property;
DROP TABLE IF EXISTS property_group;
DROP TABLE IF EXISTS product_specification;
DROP TABLE IF EXISTS specification_group;
DROP TABLE IF EXISTS product_category;




/* Create Tables */

-- 品牌
CREATE TABLE brand
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 排序
	orders int COMMENT '排序',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 品牌介绍
	introduce varchar(255) COMMENT '品牌介绍',
	-- 品牌url
	url varchar(255) COMMENT '品牌url',
	-- 品牌logo
	logo varchar(255) COMMENT '品牌logo',
	-- 商户编号
	merchant_no varchar(32) COMMENT '商户编号',
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
) COMMENT = '品牌';


-- 赠品项
CREATE TABLE gift_item
(
	-- 主键
	id varchar(64) NOT NULL COMMENT '主键',
	-- 数量
	quantity int COMMENT '数量',
	-- 外键，商品ID
	product_id varchar(64) NOT NULL COMMENT '外键，商品ID',
	-- 促销Id
	promotion_id varchar(64) COMMENT '促销Id',
	-- 创建日期
	create_date datetime COMMENT '创建日期',
	-- 修改日期
	modify_date datetime COMMENT '修改日期',
	PRIMARY KEY (id)
) COMMENT = '赠品项';


-- 商品参数组
CREATE TABLE parameter_group
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 分组编号
	group_no varchar(32) NOT NULL COMMENT '分组编号',
	-- 排序
	orders int COMMENT '排序',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 绑定分类
	product_category_no varchar(32) COMMENT '绑定分类',
	-- 商户号
	merchant_no varchar(64) COMMENT '商户号',
	-- 商品分类ID
	product_category_id varchar(64) NOT NULL COMMENT '商品分类ID',
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
	UNIQUE (group_no)
) COMMENT = '商品参数组';


-- 商品表
CREATE TABLE product
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 商品编号
	product_no varchar(32) COMMENT '商品编号',
	-- 已分配库存
	allocated_stock int COMMENT '已分配库存',
	-- 成本价
	cost decimal COMMENT '成本价',
	-- 全称
	full_name varchar(32) COMMENT '全称',
	-- 点击数
	hits int COMMENT '点击数',
	-- 展示图片
	image varchar(32) COMMENT '展示图片',
	-- 介绍
	introduction varchar(32) COMMENT '介绍',
	-- 是否为赠品 1:是 0:否
	is_gift varchar(1) COMMENT '是否为赠品 1:是 0:否',
	-- 是否列出 1:是 0:否
	is_list varchar(1) COMMENT '是否列出 1:是 0:否',
	-- 是否上架 1:是 0:否
	is_marketable varchar(1) COMMENT '是否上架 1:是 0:否',
	-- 是否置顶 1:是 0:否
	is_top varchar(1) COMMENT '是否置顶 1:是 0:否',
	-- 是否会员价 1:是 0:否
	is_member_price varchar(1) COMMENT '是否会员价 1:是 0:否',
	-- 热销 1:是 0:否
	hot_sell varchar(1) COMMENT '热销 1:是 0:否',
	-- 最新 1:是 0:否
	newest varchar(1) COMMENT '最新 1:是 0:否',
	-- 活动促销 1:是 0:否
	promotion varchar(1) COMMENT '活动促销 1:是 0:否',
	-- 搜索关键词
	keyword varchar(32) COMMENT '搜索关键词',
	-- 市场价
	market_price decimal COMMENT '市场价',
	-- 备注
	memo varchar(32) COMMENT '备注',
	-- 月点击数
	month_hits int COMMENT '月点击数',
	-- 月点击数更新日期
	month_hits_date datetime COMMENT '月点击数更新日期',
	-- 月销售量
	month_sales int COMMENT '月销售量',
	-- 月销售量更新日期
	month_sales_date datetime COMMENT '月销售量更新日期',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 赠送积分
	point int COMMENT '赠送积分',
	-- 销售价格
	price decimal COMMENT '销售价格',
	-- 销量
	sales int COMMENT '销量',
	-- 评分
	score decimal COMMENT '评分',
	-- 评分数
	score_count varchar(32) COMMENT '评分数',
	-- 页面描述
	seo_description varchar(32) COMMENT '页面描述',
	-- 页面关键词
	seo_keywords varchar(32) COMMENT '页面关键词',
	-- 页面标题
	seo_title varchar(32) COMMENT '页面标题',
	-- 库存
	stock int COMMENT '库存',
	-- 库存备注
	stock_memo varchar(32) COMMENT '库存备注',
	-- 总评分
	total_score int COMMENT '总评分',
	-- 单位
	unit varchar(32) COMMENT '单位',
	-- 周点击数
	week_hits int COMMENT '周点击数',
	-- 周点击数更新日期
	week_hits_date datetime COMMENT '周点击数更新日期',
	-- 周销售量
	week_sales int COMMENT '周销售量',
	-- 周销售量更新日期
	week_sales_date datetime COMMENT '周销售量更新日期',
	-- 重量
	weight decimal COMMENT '重量',
	-- 货品
	goods int COMMENT '货品',
	-- 商品分类编号
	product_category_no varchar(32) COMMENT '商品分类编号',
	-- 是否自营
	proprietary varchar(32) COMMENT '是否自营',
	-- 商户号
	mechant_no varchar(32) COMMENT '商户号',
	-- 商品分类主键ID
	product_category_id varchar(64) NOT NULL COMMENT '商品分类主键ID',
	-- 品牌主键ID
	brand_id varchar(64) NOT NULL COMMENT '品牌主键ID',
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
) COMMENT = '商品表';


-- 商品分类
CREATE TABLE product_category
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 商品分类编号
	category_no varchar(32) NOT NULL COMMENT '商品分类编号',
	-- 排序
	orders int COMMENT '排序',
	-- 层级
	grade int COMMENT '层级',
	-- 名称
	name varchar(32) COMMENT '名称',
	-- 页面描述
	seo_description varchar(32) COMMENT '页面描述',
	-- 页面关健字
	seo_keywords  varchar(32) COMMENT '页面关健字',
	-- 页面标题
	seo_title varchar(32) COMMENT '页面标题',
	-- 商户号
	merchant_no varchar(64) COMMENT '商户号',
	-- 上级分类编号
	parent_id varchar(64) COMMENT '上级分类编号',
	-- 所以父级节点ID
	parent_ids varchar(2000) COMMENT '所以父级节点ID',
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
	UNIQUE (category_no)
) COMMENT = '商品分类';


-- 商品图片关联表
CREATE TABLE product_image_ref
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 商品编号
	product_no varchar(32) COMMENT '商品编号',
	-- 图片文件路径
	file_path varchar(32) COMMENT '图片文件路径',
	-- 标题
	title varchar(32) COMMENT '标题',
	-- 原图片
	source varchar(32) COMMENT '原图片',
	-- 大图片
	large varchar(32) COMMENT '大图片',
	-- 中图片
	medium varchar(32) COMMENT '中图片',
	-- 缩略图
	thumbnail varchar(32) COMMENT '缩略图',
	-- 排序
	orders int COMMENT '排序',
	-- 商品主键ID
	product_id varchar(64) NOT NULL COMMENT '商品主键ID',
	PRIMARY KEY (id)
) COMMENT = '商品图片关联表';


-- 商品参数表
CREATE TABLE product_parameter
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 商品参数编号
	parameter_no varchar(32) NOT NULL COMMENT '商品参数编号',
	-- 排序
	orders int COMMENT '排序',
	-- 商品参数名称
	name varchar(32) COMMENT '商品参数名称',
	-- 所属商品参数组编号
	group_no varchar(32) COMMENT '所属商品参数组编号',
	-- 商户号
	merchant_no varchar(64) COMMENT '商户号',
	-- 商品参数组主键Id
	parameter_group_id varchar(64) NOT NULL COMMENT '商品参数组主键Id',
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
	UNIQUE (parameter_no)
) COMMENT = '商品参数表';


-- 商品参数关联表
CREATE TABLE product_parameter_ref
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 参数主键ID
	parameter_id varchar(64) NOT NULL COMMENT '参数主键ID',
	-- 商品主键ID
	product_id varchar(64) NOT NULL COMMENT '商品主键ID',
	-- 参数值
	parameter_value varchar(32) COMMENT '参数值',
	PRIMARY KEY (id)
) COMMENT = '商品参数关联表';


-- 商品属性表
CREATE TABLE product_property
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 属性编号
	property_no varchar(32) COMMENT '属性编号',
	-- 排序
	orders int COMMENT '排序',
	-- 属性名称
	name varchar(32) COMMENT '属性名称',
	-- 属性组编号，字段来自property_group表
	group_no varchar(32) COMMENT '属性组编号，字段来自property_group表',
	-- 商户号
	merchant_no varchar(32) COMMENT '商户号',
	-- 商品属性组ID
	group_id varchar(64) NOT NULL COMMENT '商品属性组ID',
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
) COMMENT = '商品属性表';


-- 商品与属性关联表
CREATE TABLE product_property_ref
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 商品属性主键ID
	property_id varchar(64) NOT NULL COMMENT '商品属性主键ID',
	-- 商品主键ID
	product_id varchar(64) NOT NULL COMMENT '商品主键ID',
	PRIMARY KEY (id)
) COMMENT = '商品与属性关联表';


-- 商品评论
CREATE TABLE product_review
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 评论内容
	content varchar(5000) COMMENT '评论内容',
	-- 评论的ip地址
	ip varchar(32) COMMENT '评论的ip地址',
	-- 是否显示 1:是 0:否
	is_show varchar(1) COMMENT '是否显示 1:是 0:否',
	-- 评分
	score int COMMENT '评分',
	-- 会员帐号
	use_name varchar(32) COMMENT '会员帐号',
	-- 商品编号
	product_no varchar(32) COMMENT '商品编号',
	-- 商品主键ID
	product_id varchar(64) NOT NULL COMMENT '商品主键ID',
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
) COMMENT = '商品评论';


-- 商品规格
CREATE TABLE product_specification
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 商品规格编号
	specification_no varchar(32) COMMENT '商品规格编号',
	-- 排序
	orders int COMMENT '排序',
	-- 规格名称
	name varchar(32) COMMENT '规格名称',
	-- 规格文件路径
	file_path varchar(255) COMMENT '规格文件路径',
	-- 商品规格组编号
	specification_group_no varchar(32) COMMENT '商品规格组编号',
	-- 商户号
	merchant_no varchar(32) COMMENT '商户号',
	-- 规格组主键ID
	specification_group_id varchar(64) NOT NULL COMMENT '规格组主键ID',
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
) COMMENT = '商品规格';


-- 商品与规格关联表
CREATE TABLE product_specification_ref
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 规格主键ID
	specification_id varchar(64) NOT NULL COMMENT '规格主键ID',
	-- 商品主键ID
	product_id varchar(64) NOT NULL COMMENT '商品主键ID',
	PRIMARY KEY (id)
) COMMENT = '商品与规格关联表';


-- 商品属性组
CREATE TABLE property_group
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 属性组编号
	group_no varchar(32) COMMENT '属性组编号',
	-- 排序
	orders int COMMENT '排序',
	-- 商品属性组名称
	name varchar(32) COMMENT '商品属性组名称',
	-- 绑定分类
	product_category_no varchar(32) COMMENT '绑定分类',
	-- 商户号
	merchant_no varchar(64) COMMENT '商户号',
	-- 商品分类主键ID
	product_category_id varchar(64) NOT NULL COMMENT '商品分类主键ID',
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
) COMMENT = '商品属性组';


-- 商品规格组
CREATE TABLE specification_group
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 规格组编号
	group_no varchar(32) COMMENT '规格组编号',
	-- 排序
	orders int COMMENT '排序',
	-- 规格组名称
	name varchar(32) COMMENT '规格组名称',
	-- 分类编号
	product_category_no varchar(32) COMMENT '分类编号',
	-- 备注
	remark varchar(255) COMMENT '备注',
	-- 商户号
	merchant_no varchar(32) COMMENT '商户号',
	-- 分类主键ID
	product_category_id varchar(64) NOT NULL COMMENT '分类主键ID',
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
) COMMENT = '商品规格组';

-- 商品规格组关联表
CREATE TABLE product_specification_group_ref
(
	-- 主键ID
	id varchar(64) NOT NULL COMMENT '主键ID',
	-- 参数主键ID
	specification_group_id varchar(64) NOT NULL COMMENT '规格组主键ID',
	-- 商品主键ID
	product_id varchar(64) NOT NULL COMMENT '商品主键ID',
	PRIMARY KEY (id)
) COMMENT = '商品规格组关联表';

/* Create Foreign Keys */

ALTER TABLE product
	ADD FOREIGN KEY (brand_id)
	REFERENCES brand (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_parameter
	ADD FOREIGN KEY (parameter_group_id)
	REFERENCES parameter_group (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_image_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_property_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_specification_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE gift_item
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_review
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_parameter_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE property_group
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE parameter_group
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE specification_group
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_parameter_ref
	ADD FOREIGN KEY (parameter_id)
	REFERENCES product_parameter (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_property_ref
	ADD FOREIGN KEY (property_id)
	REFERENCES product_property (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_specification_ref
	ADD FOREIGN KEY (specification_id)
	REFERENCES product_specification (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_property
	ADD FOREIGN KEY (group_id)
	REFERENCES property_group (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE product_specification
	ADD FOREIGN KEY (specification_group_id)
	REFERENCES specification_group (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE product_category MODIFY COLUMN `category_no` varchar(64) DEFAULT NULL COMMENT '商品分类编号';

ALTER TABLE parameter_group MODIFY COLUMN `group_no` varchar(64) COMMENT '分组编号';

ALTER TABLE product_parameter MODIFY COLUMN `parameter_no` varchar(64) COMMENT '商品参数编号';

ALTER TABLE specification_group ADD content_type int(11) COMMENT '规格类型'; 

ALTER TABLE product DROP FOREIGN KEY product_ibfk_1;
ALTER TABLE product ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE product_parameter DROP FOREIGN KEY product_parameter_ibfk_1;
ALTER TABLE product_parameter ADD CONSTRAINT `product_parameter_ibfk_1` FOREIGN KEY (`parameter_group_id`) REFERENCES `parameter_group` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
