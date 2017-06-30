
/* Drop Tables */

DROP TABLE product_image_ref CASCADE CONSTRAINTS;
DROP TABLE product_property_ref CASCADE CONSTRAINTS;
DROP TABLE product_specification_ref CASCADE CONSTRAINTS;
DROP TABLE gift_item CASCADE CONSTRAINTS;
DROP TABLE product_review CASCADE CONSTRAINTS;
DROP TABLE product_parameter_ref CASCADE CONSTRAINTS;
DROP TABLE product CASCADE CONSTRAINTS;
DROP TABLE brand CASCADE CONSTRAINTS;
DROP TABLE product_parameter CASCADE CONSTRAINTS;
DROP TABLE parameter_group CASCADE CONSTRAINTS;
DROP TABLE product_property CASCADE CONSTRAINTS;
DROP TABLE property_group CASCADE CONSTRAINTS;
DROP TABLE product_specification CASCADE CONSTRAINTS;
DROP TABLE specification_group CASCADE CONSTRAINTS;
DROP TABLE product_category CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE seq_oa_leave;
DROP SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT;




/* Create Sequences */

CREATE SEQUENCE seq_oa_leave INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE uib_ec.SEQ_OA_TEST_AUDIT INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 61 CACHE 20;



/* Create Tables */

-- 品牌
CREATE TABLE brand
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 排序
	orders number(10,0),
	-- 名称
	name varchar2(32),
	-- 品牌介绍
	introduce varchar2(255),
	-- 品牌url
	url varchar2(255),
	-- 品牌logo
	logo varchar2(255),
	-- 商户编号
	merchant_no varchar2(32),
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


-- 赠品项
CREATE TABLE gift_item
(
	-- 主键
	id varchar2(64) NOT NULL,
	-- 数量
	quantity number(10,0),
	-- 外键，商品ID
	product_id varchar2(64) NOT NULL,
	-- 促销Id
	promotionId varchar2(64),
	-- 创建日期
	create_date timestamp,
	-- 修改日期
	modify_date timestamp,
	PRIMARY KEY (id)
);


-- 商品参数组
CREATE TABLE parameter_group
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 分组编号
	group_no varchar2(32) NOT NULL UNIQUE,
	-- 排序
	orders number(10,0),
	-- 名称
	name varchar2(32),
	-- 绑定分类
	product_category_no varchar2(32),
	-- 商户号
	merchant_no varchar2(64),
	-- 商品分类ID
	product_category_id varchar2(64) NOT NULL,
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


-- 商品表
CREATE TABLE product
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 商品编号
	product_no varchar2(32),
	-- 已分配库存
	allocated_stock number(10,0),
	-- 成本价
	cost number,
	-- 全称
	full_name varchar2(32),
	-- 点击数
	hits number(10,0),
	-- 展示图片
	image varchar2(32),
	-- 介绍
	introduction varchar2(32),
	-- 是否为赠品 1:是 0:否
	is_gift varchar2(1),
	-- 是否列出 1:是 0:否
	is_list varchar2(1),
	-- 是否上架 1:是 0:否
	is_marketable varchar2(1),
	-- 是否置顶 1:是 0:否
	is_top varchar2(1),
	-- 是否会员价 1:是 0:否
	is_member_price varchar2(1),
	-- 热销 1:是 0:否
	hot_sell varchar2(1),
	-- 最新 1:是 0:否
	newest varchar2(1),
	-- 活动促销 1:是 0:否
	promotion varchar2(1),
	-- 搜索关键词
	keyword varchar2(32),
	-- 市场价
	market_price number,
	-- 备注
	memo varchar2(32),
	-- 月点击数
	month_hits number(10,0),
	-- 月点击数更新日期
	month_hits_date timestamp,
	-- 月销售量
	month_sales number(10,0),
	-- 月销售量更新日期
	month_sales_date timestamp,
	-- 名称
	name varchar2(32),
	-- 赠送积分
	point number(10,0),
	-- 销售价格
	price number,
	-- 销量
	sales number(10,0),
	-- 评分
	score number,
	-- 评分数
	score_count varchar2(32),
	-- 页面描述
	seo_description varchar2(32),
	-- 页面关键词
	seo_keywords varchar2(32),
	-- 页面标题
	seo_title varchar2(32),
	-- 库存
	stock number(10,0),
	-- 库存备注
	stock_memo varchar2(32),
	-- 总评分
	total_score number(10,0),
	-- 单位
	unit varchar2(32),
	-- 周点击数
	week_hits number(10,0),
	-- 周点击数更新日期
	week_hits_date timestamp,
	-- 周销售量
	week_sales number(10,0),
	-- 周销售量更新日期
	week_sales_date timestamp,
	-- 重量
	weight number,
	-- 货品
	goods number(10,0),
	-- 商品分类编号
	product_category_no varchar2(32),
	-- 是否自营
	proprietary varchar2(32),
	-- 商户号
	mechant_no varchar2(32),
	-- 商品分类主键ID
	product_category_id varchar2(64) NOT NULL,
	-- 品牌主键ID
	brand_id varchar2(64) NOT NULL,
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


-- 商品分类
CREATE TABLE product_category
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 商品分类编号
	category_no varchar2(32) NOT NULL UNIQUE,
	-- 排序
	orders number(10,0),
	-- 层级
	grade number(10,0),
	-- 名称
	name varchar2(32),
	-- 页面描述
	seo_description varchar2(32),
	-- 页面关健字
	seo_keywords  varchar2(32),
	-- 页面标题
	seo_title varchar2(32),
	-- 商户号
	merchant_no varchar2(64),
	-- 上级分类编号
	parent_id varchar2(64),
	-- 所以父级节点ID
	parent_ids varchar2(2000),
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


-- 商品图片关联表
CREATE TABLE product_image_ref
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 商品编号
	product_no varchar2(32),
	-- 图片文件路径
	file_path varchar2(32),
	-- 标题
	title varchar2(32),
	-- 原图片
	source varchar2(32),
	-- 大图片
	large varchar2(32),
	-- 中图片
	medium varchar2(32),
	-- 缩略图
	thumbnail varchar2(32),
	-- 排序
	orders number(10,0),
	-- 商品主键ID
	product_id varchar2(64) NOT NULL,
	PRIMARY KEY (id)
);


-- 商品参数表
CREATE TABLE product_parameter
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 商品参数编号
	parameter_no varchar2(32) NOT NULL UNIQUE,
	-- 排序
	orders number(10,0),
	-- 商品参数名称
	name varchar2(32),
	-- 所属商品参数组编号
	group_no varchar2(32),
	-- 商户号
	merchant_no varchar2(64),
	-- 商品参数组主键Id
	parameter_group_id varchar2(64) NOT NULL,
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


-- 商品参数关联表
CREATE TABLE product_parameter_ref
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 参数主键ID
	parameter_id varchar2(64) NOT NULL,
	-- 商品主键ID
	product_id varchar2(64) NOT NULL,
	-- 参数值
	parameter_value varchar2(32),
	PRIMARY KEY (id)
);


-- 商品属性表
CREATE TABLE product_property
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 属性编号
	property_no varchar2(32),
	-- 排序
	orders number(10,0),
	-- 属性名称
	name varchar2(32),
	-- 属性组编号，字段来自property_group表
	group_no varchar2(32),
	-- 商户号
	merchant_no varchar2(32),
	-- 商品属性组ID
	group_id varchar2(64) NOT NULL,
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


-- 商品与属性关联表
CREATE TABLE product_property_ref
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 商品属性主键ID
	property_id varchar2(64) NOT NULL,
	-- 商品主键ID
	product_id varchar2(64) NOT NULL,
	PRIMARY KEY (id)
);


-- 商品评论
CREATE TABLE product_review
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 评论内容
	content varchar2(5000),
	-- 评论的ip地址
	ip varchar2(32),
	-- 是否显示 1:是 0:否
	is_show varchar2(1),
	-- 评分
	score number(10,0),
	-- 会员帐号
	use_name varchar2(32),
	-- 商品编号
	product_no varchar2(32),
	-- 商品主键ID
	product_id varchar2(64) NOT NULL,
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


-- 商品规格
CREATE TABLE product_specification
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 商品规格编号
	specification_no varchar2(32),
	-- 排序
	orders number(10,0),
	-- 规格名称
	name varchar2(32),
	-- 规格文件路径
	file_path varchar2(255),
	-- 商品规格组编号
	specification_group_no varchar2(32),
	-- 商户号
	merchant_no varchar2(32),
	-- 规格组主键ID
	specification_group_id varchar2(64) NOT NULL,
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


-- 商品与规格关联表
CREATE TABLE product_specification_ref
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 规格主键ID
	specification_id varchar2(64) NOT NULL,
	-- 商品主键ID
	product_id varchar2(64) NOT NULL,
	PRIMARY KEY (id)
);


-- 商品属性组
CREATE TABLE property_group
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 属性组编号
	group_no varchar2(32),
	-- 排序
	orders number(10,0),
	-- 商品属性组名称
	name varchar2(32),
	-- 绑定分类
	product_category_no varchar2(32),
	-- 商户号
	merchant_no varchar2(64),
	-- 商品分类主键ID
	product_category_id varchar2(64) NOT NULL,
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


-- 商品规格组
CREATE TABLE specification_group
(
	-- 主键ID
	id varchar2(64) NOT NULL,
	-- 规格组编号
	group_no varchar2(32),
	-- 排序
	orders number(10,0),
	-- 规格组名称
	name varchar2(32),
	-- 分类编号
	product_category_no varchar2(32),
	-- 备注
	remark varchar2(255),
	-- 商户号
	merchant_no varchar2(32),
	-- 分类主键ID
	product_category_id varchar2(64) NOT NULL,
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

ALTER TABLE product
	ADD FOREIGN KEY (brand_id)
	REFERENCES brand (id)
;


ALTER TABLE product_parameter
	ADD FOREIGN KEY (parameter_group_id)
	REFERENCES parameter_group (id)
;


ALTER TABLE product_image_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
;


ALTER TABLE product_property_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
;


ALTER TABLE product_specification_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
;


ALTER TABLE gift_item
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
;


ALTER TABLE product_review
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
;


ALTER TABLE product_parameter_ref
	ADD FOREIGN KEY (product_id)
	REFERENCES product (id)
;


ALTER TABLE property_group
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
;


ALTER TABLE parameter_group
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
;


ALTER TABLE specification_group
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
;


ALTER TABLE product
	ADD FOREIGN KEY (product_category_id)
	REFERENCES product_category (id)
;


ALTER TABLE product_parameter_ref
	ADD FOREIGN KEY (parameter_id)
	REFERENCES product_parameter (id)
;


ALTER TABLE product_property_ref
	ADD FOREIGN KEY (property_id)
	REFERENCES product_property (id)
;


ALTER TABLE product_specification_ref
	ADD FOREIGN KEY (specification_id)
	REFERENCES product_specification (id)
;


ALTER TABLE product_property
	ADD FOREIGN KEY (group_id)
	REFERENCES property_group (id)
;


ALTER TABLE product_specification
	ADD FOREIGN KEY (specification_group_id)
	REFERENCES specification_group (id)
;



/* Comments */

COMMENT ON TABLE brand IS '品牌';
COMMENT ON COLUMN brand.id IS '主键ID';
COMMENT ON COLUMN brand.orders IS '排序';
COMMENT ON COLUMN brand.name IS '名称';
COMMENT ON COLUMN brand.introduce IS '品牌介绍';
COMMENT ON COLUMN brand.url IS '品牌url';
COMMENT ON COLUMN brand.logo IS '品牌logo';
COMMENT ON COLUMN brand.merchant_no IS '商户编号';
COMMENT ON COLUMN brand.create_by IS '创建者';
COMMENT ON COLUMN brand.create_date IS '创建时间';
COMMENT ON COLUMN brand.update_by IS '更新者';
COMMENT ON COLUMN brand.update_date IS '更新时间';
COMMENT ON COLUMN brand.remarks IS '备注信息';
COMMENT ON COLUMN brand.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE gift_item IS '赠品项';
COMMENT ON COLUMN gift_item.id IS '主键';
COMMENT ON COLUMN gift_item.quantity IS '数量';
COMMENT ON COLUMN gift_item.product_id IS '外键，商品ID';
COMMENT ON COLUMN gift_item.promotionId IS '促销Id';
COMMENT ON COLUMN gift_item.create_date IS '创建日期';
COMMENT ON COLUMN gift_item.modify_date IS '修改日期';
COMMENT ON TABLE parameter_group IS '商品参数组';
COMMENT ON COLUMN parameter_group.id IS '主键ID';
COMMENT ON COLUMN parameter_group.group_no IS '分组编号';
COMMENT ON COLUMN parameter_group.orders IS '排序';
COMMENT ON COLUMN parameter_group.name IS '名称';
COMMENT ON COLUMN parameter_group.product_category_no IS '绑定分类';
COMMENT ON COLUMN parameter_group.merchant_no IS '商户号';
COMMENT ON COLUMN parameter_group.product_category_id IS '商品分类ID';
COMMENT ON COLUMN parameter_group.create_by IS '创建者';
COMMENT ON COLUMN parameter_group.create_date IS '创建时间';
COMMENT ON COLUMN parameter_group.update_by IS '更新者';
COMMENT ON COLUMN parameter_group.update_date IS '更新时间';
COMMENT ON COLUMN parameter_group.remarks IS '备注信息';
COMMENT ON COLUMN parameter_group.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE product IS '商品表';
COMMENT ON COLUMN product.id IS '主键ID';
COMMENT ON COLUMN product.product_no IS '商品编号';
COMMENT ON COLUMN product.allocated_stock IS '已分配库存';
COMMENT ON COLUMN product.cost IS '成本价';
COMMENT ON COLUMN product.full_name IS '全称';
COMMENT ON COLUMN product.hits IS '点击数';
COMMENT ON COLUMN product.image IS '展示图片';
COMMENT ON COLUMN product.introduction IS '介绍';
COMMENT ON COLUMN product.is_gift IS '是否为赠品 1:是 0:否';
COMMENT ON COLUMN product.is_list IS '是否列出 1:是 0:否';
COMMENT ON COLUMN product.is_marketable IS '是否上架 1:是 0:否';
COMMENT ON COLUMN product.is_top IS '是否置顶 1:是 0:否';
COMMENT ON COLUMN product.is_member_price IS '是否会员价 1:是 0:否';
COMMENT ON COLUMN product.hot_sell IS '热销 1:是 0:否';
COMMENT ON COLUMN product.newest IS '最新 1:是 0:否';
COMMENT ON COLUMN product.promotion IS '活动促销 1:是 0:否';
COMMENT ON COLUMN product.keyword IS '搜索关键词';
COMMENT ON COLUMN product.market_price IS '市场价';
COMMENT ON COLUMN product.memo IS '备注';
COMMENT ON COLUMN product.month_hits IS '月点击数';
COMMENT ON COLUMN product.month_hits_date IS '月点击数更新日期';
COMMENT ON COLUMN product.month_sales IS '月销售量';
COMMENT ON COLUMN product.month_sales_date IS '月销售量更新日期';
COMMENT ON COLUMN product.name IS '名称';
COMMENT ON COLUMN product.point IS '赠送积分';
COMMENT ON COLUMN product.price IS '销售价格';
COMMENT ON COLUMN product.sales IS '销量';
COMMENT ON COLUMN product.score IS '评分';
COMMENT ON COLUMN product.score_count IS '评分数';
COMMENT ON COLUMN product.seo_description IS '页面描述';
COMMENT ON COLUMN product.seo_keywords IS '页面关键词';
COMMENT ON COLUMN product.seo_title IS '页面标题';
COMMENT ON COLUMN product.stock IS '库存';
COMMENT ON COLUMN product.stock_memo IS '库存备注';
COMMENT ON COLUMN product.total_score IS '总评分';
COMMENT ON COLUMN product.unit IS '单位';
COMMENT ON COLUMN product.week_hits IS '周点击数';
COMMENT ON COLUMN product.week_hits_date IS '周点击数更新日期';
COMMENT ON COLUMN product.week_sales IS '周销售量';
COMMENT ON COLUMN product.week_sales_date IS '周销售量更新日期';
COMMENT ON COLUMN product.weight IS '重量';
COMMENT ON COLUMN product.goods IS '货品';
COMMENT ON COLUMN product.product_category_no IS '商品分类编号';
COMMENT ON COLUMN product.proprietary IS '是否自营';
COMMENT ON COLUMN product.mechant_no IS '商户号';
COMMENT ON COLUMN product.product_category_id IS '商品分类主键ID';
COMMENT ON COLUMN product.brand_id IS '品牌主键ID';
COMMENT ON COLUMN product.create_by IS '创建者';
COMMENT ON COLUMN product.create_date IS '创建时间';
COMMENT ON COLUMN product.update_by IS '更新者';
COMMENT ON COLUMN product.update_date IS '更新时间';
COMMENT ON COLUMN product.remarks IS '备注信息';
COMMENT ON COLUMN product.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE product_category IS '商品分类';
COMMENT ON COLUMN product_category.id IS '主键ID';
COMMENT ON COLUMN product_category.category_no IS '商品分类编号';
COMMENT ON COLUMN product_category.orders IS '排序';
COMMENT ON COLUMN product_category.grade IS '层级';
COMMENT ON COLUMN product_category.name IS '名称';
COMMENT ON COLUMN product_category.seo_description IS '页面描述';
COMMENT ON COLUMN product_category.seo_keywords  IS '页面关健字';
COMMENT ON COLUMN product_category.seo_title IS '页面标题';
COMMENT ON COLUMN product_category.merchant_no IS '商户号';
COMMENT ON COLUMN product_category.parent_id IS '上级分类编号';
COMMENT ON COLUMN product_category.parent_ids IS '所以父级节点ID';
COMMENT ON COLUMN product_category.create_by IS '创建者';
COMMENT ON COLUMN product_category.create_date IS '创建时间';
COMMENT ON COLUMN product_category.update_by IS '更新者';
COMMENT ON COLUMN product_category.update_date IS '更新时间';
COMMENT ON COLUMN product_category.remarks IS '备注信息';
COMMENT ON COLUMN product_category.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE product_image_ref IS '商品图片关联表';
COMMENT ON COLUMN product_image_ref.id IS '主键ID';
COMMENT ON COLUMN product_image_ref.product_no IS '商品编号';
COMMENT ON COLUMN product_image_ref.file_path IS '图片文件路径';
COMMENT ON COLUMN product_image_ref.title IS '标题';
COMMENT ON COLUMN product_image_ref.source IS '原图片';
COMMENT ON COLUMN product_image_ref.large IS '大图片';
COMMENT ON COLUMN product_image_ref.medium IS '中图片';
COMMENT ON COLUMN product_image_ref.thumbnail IS '缩略图';
COMMENT ON COLUMN product_image_ref.orders IS '排序';
COMMENT ON COLUMN product_image_ref.product_id IS '商品主键ID';
COMMENT ON TABLE product_parameter IS '商品参数表';
COMMENT ON COLUMN product_parameter.id IS '主键ID';
COMMENT ON COLUMN product_parameter.parameter_no IS '商品参数编号';
COMMENT ON COLUMN product_parameter.orders IS '排序';
COMMENT ON COLUMN product_parameter.name IS '商品参数名称';
COMMENT ON COLUMN product_parameter.group_no IS '所属商品参数组编号';
COMMENT ON COLUMN product_parameter.merchant_no IS '商户号';
COMMENT ON COLUMN product_parameter.parameter_group_id IS '商品参数组主键Id';
COMMENT ON COLUMN product_parameter.create_by IS '创建者';
COMMENT ON COLUMN product_parameter.create_date IS '创建时间';
COMMENT ON COLUMN product_parameter.update_by IS '更新者';
COMMENT ON COLUMN product_parameter.update_date IS '更新时间';
COMMENT ON COLUMN product_parameter.remarks IS '备注信息';
COMMENT ON COLUMN product_parameter.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE product_parameter_ref IS '商品参数关联表';
COMMENT ON COLUMN product_parameter_ref.id IS '主键ID';
COMMENT ON COLUMN product_parameter_ref.parameter_id IS '参数主键ID';
COMMENT ON COLUMN product_parameter_ref.product_id IS '商品主键ID';
COMMENT ON COLUMN product_parameter_ref.parameter_value IS '参数值';
COMMENT ON TABLE product_property IS '商品属性表';
COMMENT ON COLUMN product_property.id IS '主键ID';
COMMENT ON COLUMN product_property.property_no IS '属性编号';
COMMENT ON COLUMN product_property.orders IS '排序';
COMMENT ON COLUMN product_property.name IS '属性名称';
COMMENT ON COLUMN product_property.group_no IS '属性组编号，字段来自property_group表';
COMMENT ON COLUMN product_property.merchant_no IS '商户号';
COMMENT ON COLUMN product_property.group_id IS '商品属性组ID';
COMMENT ON COLUMN product_property.create_by IS '创建者';
COMMENT ON COLUMN product_property.create_date IS '创建时间';
COMMENT ON COLUMN product_property.update_by IS '更新者';
COMMENT ON COLUMN product_property.update_date IS '更新时间';
COMMENT ON COLUMN product_property.remarks IS '备注信息';
COMMENT ON COLUMN product_property.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE product_property_ref IS '商品与属性关联表';
COMMENT ON COLUMN product_property_ref.id IS '主键ID';
COMMENT ON COLUMN product_property_ref.property_id IS '商品属性主键ID';
COMMENT ON COLUMN product_property_ref.product_id IS '商品主键ID';
COMMENT ON TABLE product_review IS '商品评论';
COMMENT ON COLUMN product_review.id IS '主键ID';
COMMENT ON COLUMN product_review.content IS '评论内容';
COMMENT ON COLUMN product_review.ip IS '评论的ip地址';
COMMENT ON COLUMN product_review.is_show IS '是否显示 1:是 0:否';
COMMENT ON COLUMN product_review.score IS '评分';
COMMENT ON COLUMN product_review.use_name IS '会员帐号';
COMMENT ON COLUMN product_review.product_no IS '商品编号';
COMMENT ON COLUMN product_review.product_id IS '商品主键ID';
COMMENT ON COLUMN product_review.create_by IS '创建者';
COMMENT ON COLUMN product_review.create_date IS '创建时间';
COMMENT ON COLUMN product_review.update_by IS '更新者';
COMMENT ON COLUMN product_review.update_date IS '更新时间';
COMMENT ON COLUMN product_review.remarks IS '备注信息';
COMMENT ON COLUMN product_review.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE product_specification IS '商品规格';
COMMENT ON COLUMN product_specification.id IS '主键ID';
COMMENT ON COLUMN product_specification.specification_no IS '商品规格编号';
COMMENT ON COLUMN product_specification.orders IS '排序';
COMMENT ON COLUMN product_specification.name IS '规格名称';
COMMENT ON COLUMN product_specification.file_path IS '规格文件路径';
COMMENT ON COLUMN product_specification.specification_group_no IS '商品规格组编号';
COMMENT ON COLUMN product_specification.merchant_no IS '商户号';
COMMENT ON COLUMN product_specification.specification_group_id IS '规格组主键ID';
COMMENT ON COLUMN product_specification.create_by IS '创建者';
COMMENT ON COLUMN product_specification.create_date IS '创建时间';
COMMENT ON COLUMN product_specification.update_by IS '更新者';
COMMENT ON COLUMN product_specification.update_date IS '更新时间';
COMMENT ON COLUMN product_specification.remarks IS '备注信息';
COMMENT ON COLUMN product_specification.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE product_specification_ref IS '商品与规格关联表';
COMMENT ON COLUMN product_specification_ref.id IS '主键ID';
COMMENT ON COLUMN product_specification_ref.specification_id IS '规格主键ID';
COMMENT ON COLUMN product_specification_ref.product_id IS '商品主键ID';
COMMENT ON TABLE property_group IS '商品属性组';
COMMENT ON COLUMN property_group.id IS '主键ID';
COMMENT ON COLUMN property_group.group_no IS '属性组编号';
COMMENT ON COLUMN property_group.orders IS '排序';
COMMENT ON COLUMN property_group.name IS '商品属性组名称';
COMMENT ON COLUMN property_group.product_category_no IS '绑定分类';
COMMENT ON COLUMN property_group.merchant_no IS '商户号';
COMMENT ON COLUMN property_group.product_category_id IS '商品分类主键ID';
COMMENT ON COLUMN property_group.create_by IS '创建者';
COMMENT ON COLUMN property_group.create_date IS '创建时间';
COMMENT ON COLUMN property_group.update_by IS '更新者';
COMMENT ON COLUMN property_group.update_date IS '更新时间';
COMMENT ON COLUMN property_group.remarks IS '备注信息';
COMMENT ON COLUMN property_group.del_flag IS '删除标记（0：正常；1：删除）';
COMMENT ON TABLE specification_group IS '商品规格组';
COMMENT ON COLUMN specification_group.id IS '主键ID';
COMMENT ON COLUMN specification_group.group_no IS '规格组编号';
COMMENT ON COLUMN specification_group.orders IS '排序';
COMMENT ON COLUMN specification_group.name IS '规格组名称';
COMMENT ON COLUMN specification_group.product_category_no IS '分类编号';
COMMENT ON COLUMN specification_group.remark IS '备注';
COMMENT ON COLUMN specification_group.merchant_no IS '商户号';
COMMENT ON COLUMN specification_group.product_category_id IS '分类主键ID';
COMMENT ON COLUMN specification_group.create_by IS '创建者';
COMMENT ON COLUMN specification_group.create_date IS '创建时间';
COMMENT ON COLUMN specification_group.update_by IS '更新者';
COMMENT ON COLUMN specification_group.update_date IS '更新时间';
COMMENT ON COLUMN specification_group.remarks IS '备注信息';
COMMENT ON COLUMN specification_group.del_flag IS '删除标记（0：正常；1：删除）';



