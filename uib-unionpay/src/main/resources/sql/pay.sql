-- 商户信息表
CREATE TABLE MERCHANT_INFO (
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,       -- 主键
	MERCHANT_NAME   VARCHAR(300) CHARACTER SET UTF8,           -- 商户名称
	MERCHANT_CODE   VARCHAR(300) CHARACTER SET UTF8,           -- 审请商户号，
	HMD5_PASSWORD   VARCHAR(32) CHARACTER SET UTF8,            -- 商户所对应的接口密码
	RECEIVE_BANK_ID VARCHAR(10)  CHARACTER SET UTF8,           -- 收款银行编号
	BANK_CARD_NO    VARCHAR(30)  CHARACTER SET UTF8,           -- 收款帐号． 
	REMARK           VARCHAR(500) CHARACTER SET UTF8,            -- 备注
	CREATE_TIME     DATETIME,
	IS_ENABLE       VARCHAR(1) ,								-- 是否已启用  0: 未启用, 1: 已启用
	PHONE 			 VARCHAR(13)								--  联系电话
	
)

-- 创建银行信息
CREATE TABLE BANK_INFO_ANYZ(
	 ID      INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,              -- 主键
	 BANK_CODE      VARCHAR(200) CHARACTER SET UTF8,                        -- 银行编号
     BANK_NAME      VARCHAR(300) CHARACTER SET UTF8,                        -- 银行名称
	 REMARK         VARCHAR(2000) CHARACTER SET UTF8                        -- 备注
	 
)


-- 创建支付信息
CREATE TABLE PAYMENT_ORDER(
	 ID      INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,       -- 主键
	 PAYMENT_NO     VARCHAR(200) CHARACTER SET UTF8,                        -- 支付流水号
     MERCHANT_CODE    VARCHAR(300) CHARACTER SET UTF8,                        -- 商户号
	 ORDER_NO       VARCHAR(300) CHARACTER SET UTF8,                        -- 定单号
     ORDER_AMT      DECIMAL(10,2),                                          -- 交易金额
     GOODS_NAME     VARCHAR(2000) CHARACTER SET UTF8,                       -- 商品名称
	 GOODS_DESC     VARCHAR(2000) CHARACTER SET UTF8,                       -- 商品说明
	 MAIL_USER_NAME VARCHAR(300) CHARACTER SET UTF8,                        -- EMAIL邮箱地址
	 CUR_TYPE       VARCHAR(10),                                            -- 币种类型
	 BANK_ID        VARCHAR(10),                                            -- 银行编号
	 RETURN_URL     VARCHAR(300) CHARACTER SET UTF8,                        -- 支付返回商户数据的地址
	 NOTIFY_URL     VARCHAR(300) CHARACTER SET UTF8,                        -- 支付成功返回消息的地址（后台返回）
	 REMARK         VARCHAR(2000) CHARACTER SET UTF8,                        -- 备注
	 TRAN_STAT      VARCHAR(10)   CHARACTER SET UTF8,                       -- 最新状态 订单状态(0:未支付; 1:已支付; 2:失败; 4:已关闭; 5:取消;)
	 TRAN_DATE      DATETIME,                                               -- 返回交易成功日期时间
	 THREE_PAYMENT_NO VARCHAR(2000) CHARACTER SET UTF8,                      -- 支付流水号
	 PAY_METHOD   	VARCHAR(10)   CHARACTER SET UTF8,						-- 渠道类型　0:爱浓支付渠道 , 1: 银联在线支付渠道, 2: 银联全渠道支付
	 QUERY_ID			VARCHAR(30)   CHARACTER SET UTF8,					-- 查询ID
	 CREATE_DATE DATETIME													-- 创建时间
	 
)


-- 退货信息
CREATE TABLE REFUND_ORDER(
	ID      INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,              -- 主键
	REFUND_NO	   VARCHAR(200) CHARACTER SET UTF8,						   -- 退货订单流水
	PAYMENT_NO     VARCHAR(200) CHARACTER SET UTF8,                        -- 支付流水号
	ORDER_NO       VARCHAR(200)  CHARACTER SET UTF8,                       -- 订单号
	REFUND_STATE   VARCHAR(10)  CHARACTER SET UTF8,                        -- 0:退货中, 1: 退货失败, 2:退货成功,3:已退款
	REMARK    VARCHAR(10) CHARACTER SET UTF8,  							   -- 备注
	REFUND_AMT  DECIMAL(10,2),                                             -- 退款金额
	NOTIFY_URL VARCHAR(500) CHARACTER SET UTF8, 							   -- 返回通知URL
	THREE_PAYMENT_NO VARCHAR(2000) CHARACTER SET UTF8,                     -- 支付流水号
	PAY_TYPE   	VARCHAR(10)   CHARACTER SET UTF8,						-- 渠道类型　0:爱浓支付渠道 , 1: 银联支付渠道
	REFUND_DATE  DATETIME,    -- 请求时间
	QUERY_ID    VARCHAR(32)   CHARACTER SET UTF8						-- 银联交易流水号   交易的交易流水号 供查询用
)


-- 交易撤销信息
CREATE TABLE CANCEL_ORDER(
	ID      INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,              -- 主键
	CANCEL_ORDER_NO	   VARCHAR(200) CHARACTER SET UTF8,						   -- 交易撤销订单流水
	PAYMENT_NO     VARCHAR(200) CHARACTER SET UTF8,                        -- 支付流水号
	ORDER_NO       VARCHAR(200)  CHARACTER SET UTF8,                       -- 订单号
	CANCEL_STATE   VARCHAR(10)  CHARACTER SET UTF8,                        -- 0:撤销中, 1: 撤销失败, 2:撤销成功
	REMARK    VARCHAR(10) CHARACTER SET UTF8,  							   -- 备注
	CANCEL_AMT  DECIMAL(10,2),                                             -- 退款金额
	NOTIFY_URL VARCHAR(500) CHARACTER SET UTF8, 							   -- 返回通知URL
	THREE_PAYMENT_NO VARCHAR(2000) CHARACTER SET UTF8,                     -- 支付流水号
	PAY_TYPE   	VARCHAR(10)   CHARACTER SET UTF8,						-- 渠道类型　0:爱浓支付渠道 , 1: 银联支付渠道
	CANCEL_DATE  DATETIME    											   -- 请求时间
)




-- 交易记录表
CREATE TABLE PAYMENT_ORDER_LOG(
  ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,       -- 主键
  PAYMENT_NO VARCHAR(200),                                   --  支付流水
  ORDER_NO       VARCHAR(300) CHARACTER SET UTF8,                        -- 定单号
  CREATE_TIME DATETIME,                                      -- 创建时间
  ORDER_AMT   DECIMAL(10,2),                                  -- 交易金额
  TRAN_STAT   VARCHAR(10),                                    -- 状态  订单状态(0:未支付; 1:已支付; 2:失败; 4:已关闭; 5:取消;)
  MESSAGE VARCHAR(2000),                                      -- 备注字段
  PAY_METHOD   	VARCHAR(10)   CHARACTER SET UTF8,						-- 渠道类型　0:爱浓支付渠道 , 1: 银联支付渠道
  THREE_PAYMENT_NO VARCHAR(2000) CHARACTER SET UTF8                      -- 第三方支付流水号
)












CREATE TABLE TB_USER(
	 ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,       -- 主键
	 USER_NAME VARCHAR(30) UNIQUE  NOT NULL,                    -- 用户名
	 PASSWORD  VARCHAR(32) NOT NULL,                            -- 密码
	 REAL_NAME VARCHAR(30) NOT NULL,                            -- 真实姓名
	 OPEN_USER_NAME  VARCHAR(30),                               -- 开通此帐号的管理员帐号
	 OPEN_TIME DATETIME,                                        -- 开通时间
	 BEGIN_TIME DATETIME,										-- 使用开始时间
	 END_TIME  DATETIME,                                        -- 使用结束时间
	 EMAIL     VARCHAR(30),                                     -- EMAIL地址
	 PHONE     VARCHAR(32),                                     --　手机号
	 IS_LOCK   VARCHAR(1),                                      -- 是否锁定
	 MERCHANT_CODE   VARCHAR(300) CHARACTER SET UTF8,           -- 审请商户号，由商户号
	 USER_TYPE VARCHAR(1),										-- 0:内部帐号,1: 商户帐号
	 EXT_1     VARCHAR(30),										-- 备用字段 1
	 EXT_2     VARCHAR(30),                                     -- 备用字段 2
	 EXT_3     VARCHAR(30)                                      -- 备用字段 3
)

CREATE TABLE USER_LOGIN_LOG(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,       -- 主键
	USER_NAME VARCHAR(30) UNIQUE  NOT NULL,                    -- 用户名
	LOGIN_DATE DATETIME ,									   -- 登陆时间
	 EXT_1     VARCHAR(30),										-- 备用字段 1
	 EXT_2     VARCHAR(30),                                     -- 备用字段 2
	 EXT_3     VARCHAR(30)                                      -- 备用字段 3
)


CREATE TABLE TB_MENU(
	ID INT UNSIGNED NOT NULL  AUTO_INCREMENT PRIMARY KEY,       -- 主键
	MENU_NAME_CN VARCHAR(30)  UNIQUE  NOT NULL,                 -- 菜单中文名
	MENU_NAME_EN VARCHAR(30)  UNIQUE  NOT NULL,                 -- 菜单英文名
	PARENT_ID    INT ,										    -- 上级菜单ID
	MENU_URL     VARCHAR(200),                                   -- 菜单URL
	MENU_ORDER   INT ,											-- 菜单排序号
	EXT_1         VARCHAR(300),									-- 备用字段1
	EXT_2         VARCHAR(300),									-- 备用字段2
	EXT_3         VARCHAR(300)									-- 备用字段3
) 


 
CREATE TABLE TB_ROLE(
	ID INT UNSIGNED NOT NULL  AUTO_INCREMENT PRIMARY KEY,       -- 主键
	ROLE_NAME_CN   VARCHAR(30)  UNIQUE  NOT NULL,               -- 权限中文名
	ROLE_NAME_EN   VARCHAR(30)  UNIQUE  NOT NULL,               -- 权限英文名
	ROLE_REMARK    VARCHAR(200)  UNIQUE  NOT NULL,              -- 备注
	EXT_1           VARCHAR(200)  ,             				-- 备用字段1
	EXT_2           VARCHAR(200)  ,             				-- 备用字段2
	EXT_3           VARCHAR(200)               				    -- 备用字段3
)


CREATE TABLE TB_USER_ROLE_MAP(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    USER_ID INT,
    ROLE_ID INT,
    EXT_1 VARCHAR(30),
    EXT_2 VARCHAR(30),
    EXT_3 VARCHAR(30)
)


CREATE TABLE TB_ROLE_MENU_MAP(
	ID  INT UNSIGNED NOT NULL  AUTO_INCREMENT PRIMARY KEY,      -- 主键
	ROLE_ID  INT ,												-- 角色ID
	MENU_ID  INT ,												-- 菜单ID
	EXT_1    VARCHAR(30),										-- 备用字段1
	EXT_2    VARCHAR(30),										-- 备用字段2
	EXT_3    VARCHAR(30)										-- 备用字段3
)





-- 代扣接口
CREATE TABLE TB_DEDUCTIONS(
	ID INT UNSIGNED NOT NULL  AUTO_INCREMENT PRIMARY KEY,      -- 主键
	DEDUCTIONS_NO VARCHAR(32),								   -- 代扣流水号
	MERCHANT_CODE VARCHAR(32) CHARACTER SET UTF8,
	BANK_NAME     VARCHAR(32) CHARACTER SET UTF8,			   -- 银行名称
	BANK_CODE     VARCHAR(32) CHARACTER SET UTF8,			   -- 银行编号
	ACCOUNT_NAME  VARCHAR(32) CHARACTER SET UTF8,			   -- 户名
	ACCOUNT_NO    VARCHAR(32) CHARACTER SET UTF8,			   -- 代扣卡号
	DEDUCTIONS_AMT   DECIMAL(10,2),                            -- 代扣金额
	REQUEST_TIME  DATETIME,			   						   -- 商户请求时间
	DEDUCTIONS_STATE VARCHAR(1),                               -- 代扣最新状态 0:未成功  1:代扣中, 2:已成功
	DEDUCTIONS_TIEM DATETIME,                                  -- 代扣时间
	SUCCESS_TIME DATETIME,                                     -- 代扣成功时间
    PAY_METHOD   	VARCHAR(10)   CHARACTER SET UTF8,		   -- 渠道类型　0:爱浓支付渠道 , 1: 银联支付渠道  2:银联全渠道支付
	REMARK		  VARCHAR(32) CHARACTER SET UTF8               -- 备注
)

















