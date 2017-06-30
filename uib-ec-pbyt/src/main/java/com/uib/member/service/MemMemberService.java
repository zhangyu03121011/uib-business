package com.uib.member.service;

import java.util.List;
import java.util.Map;

import com.easypay.core.exception.GenericException;
import com.uib.member.dto.MemberDto;
import com.uib.member.entity.Area;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.Deposit;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemReceiver;
import com.uib.member.entity.MemberLoginStatus;
import com.uib.order.entity.OrderTable;

public interface MemMemberService {

	/**
	 * 根据用户名查询会员信息
	 * 
	 * @param username
	 * @return
	 * @throws GenericException
	 */
	MemMember getMemMemberByUsername(String username) throws GenericException;

	MemMember getMemMemberByUsernameNew(String username) throws GenericException;

	/**
	 * 判断会员是否登录
	 * 
	 * @return 会员是否登录
	 */
	boolean isAuthenticated() throws GenericException;;

	/**
	 * 获取当前登录会员
	 * 
	 * @return 当前登录会员，若不存在则返回null
	 */
	MemMember getCurrent() throws GenericException;;

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名，若不存在则返回null
	 */
	String getCurrentUsername() throws GenericException;;

	/**
	 * 获取当前用户
	 * 
	 * @param userName
	 * @return
	 */
	MemMember getCurrentUser(String userName);

	int verifyPassword(String userName, String password);

	/**
	 * 处理用户登录密码异常
	 * 
	 * @param userName
	 */
	int handlerUserLoginPwdExcepton(String userName);

	/**
	 * 保存会员信息
	 * 
	 * @param member
	 * @throws Exception
	 */
	void saveMember(MemberDto memberDto) throws Exception;
	
	/**
	 * app保存会员信息
	 * 
	 * @param member
	 * @throws Exception
	 */
	void saveAppMember(MemberDto memberDto) throws Exception;

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @return
	 */
	boolean sendValidateCode(String phone, String email, String content)
			throws Exception;

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @param email
	 * @param sendType
	 * @param validate
	 * @return
	 * @throws Exception
	 */
	boolean sendValidateCode(String phone, String email, String sendType,
			String validate) throws Exception;

	/**
	 * 根据用户名查询订单
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<OrderTable> getOrderTableByUserName(MemMember member)
			throws Exception;

	/**
	 * 查询所有的预存款记录
	 * 
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public List<Deposit> getAllDepositByUserName(MemMember member)
			throws Exception;

	/**
	 * 通过关键字查询订单
	 * 
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public List<OrderTable> findOrderByKeyword(MemMember member)
			throws Exception;

	/**
	 * 根据会员统计地址数
	 * 
	 * @param memberId
	 * @return
	 * @throws GenericException
	 */
	Integer getMemReceiverByAddrCount(String memberId) throws GenericException;

	/**
	 * 根据会员查询地址信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<MemReceiver> getMemReceiverByAddress(String memberId)
			throws Exception;

	/**
	 * 根据会员查询默认地址信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public MemReceiver getDefaultMemReceiverByMemberId(String memberId)
			throws Exception;

	/**
	 * 根据id查询会员信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	MemMember getMemMember(String id) throws Exception;

	/**
	 * 根据id查询地址信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	MemReceiver getMemReceiverById(String id) throws Exception;

	/**
	 * 根据会员查询优惠码信息
	 * 
	 * @param memberId
	 * @return
	 * @throws GenericException
	 */
	public List<CouponCode> getCouponByMemberId(String memberId)
			throws GenericException;

	/**
	 * 根据code查询优惠码信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public CouponCode getCouponCodeByCouponCode(String code);

	/**
	 * 根据couponId查询优惠券信息
	 * 
	 * @param couponId
	 * @return
	 * @throws Exception
	 */
	public Coupon getCouponByCouponId(String couponId) throws Exception;

	/**
	 * 根据parentId查询区域
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Area> findAreasByParentId(String parentId) throws Exception;

	/**
	 * 根据会员查询地址信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	void update(MemReceiver memReceiver) throws Exception;

	/**
	 * 根据id删除地址信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	void delete(String id) throws Exception;

	/**
	 * 保存地址信息
	 * 
	 * @param memReceiver
	 * @return
	 * @throws Exception
	 */
	void save(MemReceiver memReceiver) throws Exception;

	/**
	 * 修改默认地址
	 * 
	 * @param memReceiver
	 * @return
	 * @throws GenericException
	 */
	void updateIsDefault(MemReceiver memReceiver) throws Exception;

	/**
	 * 保存手机登陆记录
	 * 
	 * @param memberLoginStatus
	 * @throws Exception
	 */
	void saveMemberLoginStatus(MemberLoginStatus memberLoginStatus)
			throws Exception;

	/**
	 * 根据用户ID和sessionID查询用户信息
	 * 
	 * @param memberId
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	MemberLoginStatus findByMemberIdAndSessionId(String memberId,
			String sessionId) throws Exception;

	/**
	 * 根据用户ID查询手机APP用户登陆信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	MemberLoginStatus findByMemberId(String memberId) throws Exception;

	public void updateById(Map<String, String> param);

	public Map<String, Object> findApproveInfo(String id);
	
	public Map<String, Object> getMemberbyCardId(String idCard);

	/**
	 * 
	 * @Title: produceCoupons
	 * @author sl
	 * @Description: 根据优惠券批量生成优惠码
	 * @param @param memberId 参数
	 * @return void 返回类型
	 * @throws
	 */
	public void produceCouponCodes(String memberId, List<Coupon> list);

	/**
	 * 根据用户名或手机号查询会员信息
	 * 
	 * @param username
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public MemMember memberLogin(String username, String phone)
			throws Exception;

	/**
	 * 根据手机号获取会员信息
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public MemMember getMemberByPhone(String phone) throws Exception;

	/**
	 * 根据用户名修改密码
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	void updatePassword(String password, String username) throws Exception;

	/**
	 * 根据优惠码修改优惠码所属状态
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	void updateIsUsedByCode(String code, Integer isUsed) throws Exception;

	/**
	 * 查询身份认证信息
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	MemMember getApproveByUserName(String userName) throws Exception;
	
	/**
	 * 查询当前用户是否预授权
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	MemMember queryIsAuthByUserName(String userId,String authFlag) throws Exception;
	
	/**
	 * 根据sessionId查询会员信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	MemMember getMemMemberBySessionId(String sessionId) throws Exception;
	
	/**
	 * 更新sessionId
	 * @param id
	 * @throws Exception
	 */
	void updateSessionId(String userName,String sessionId)throws Exception;
	/**
	 * 更新佣金（推荐人佣金）
	 * @param memMemberId
	 * @throws Exception
	 */
	void updateCommission(String memMemberId,String commission) throws Exception;
	/**
	 * 设置/忘记支付密码
	 * @param id
	 * @param payPassword
	 * @throws Exception
	 */
	void updatePayPassword(String id,String payPassword) throws Exception;
	
	void updateMemberInfoById(Map<String,String> param) throws Exception;
	
	/**
	 * 查询会员ID是否唯一
	 * @param idCard
	 * @return
	 * @throws Exception
	 */
	public Object queryMemberByIdCard(String idCard)throws Exception;
	
	/**
	 * 根据用户名和登陆密码查询用户信息
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int queryMemberInfo(String userName ,String password)throws Exception;
	
	/***
	 * 获取会员上次下单保存的地址
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	MemReceiver getLastOrderReceiverByMemberId(String userId) throws Exception;
	
	/**
	 * 查询领取和未领取的优惠劵
	 * @param memberId
	 * @param page
	 * @throws Exception
	 */
	List<Coupon> queryGetCoupon(String memberId,String page) throws Exception;
	
	/**
	 * 插入单条数据
	 * @param couponCode
	 * @throws Exception
	 */
	void insert(CouponCode couponCode) throws Exception;
}
