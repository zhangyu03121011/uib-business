package com.uib.common.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;

/**
 * 扩展用户验证
 * @author shuaiyz
 *
 */
public class CustomUserDetailsService implements UserDetailsService {

	private static Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private MemMemberService memMemberService;
	
	
	
	
	/**
	 * 验证用户
	 */
	public UserDetails loadUserByUsername(String username) { 
		
		UserDetails userDetail = null;
		
		try {
			MemMember tusers =	memMemberService.getMemMemberByUsername(username);
			
			if (tusers != null) {
				List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();  
				authList.add(new GrantedAuthorityImpl("member"));  
				userDetail = new User(tusers.getUsername(), tusers.getPassword().toLowerCase(), tusers.isEnabled(), 
						tusers.isAccountNonExpired(), tusers.isCredentialsNonExpired(), tusers.isAccountNonLocked(), authList); 
			}else {
				//loginLogService.addLoginLog(username, LoginResult.USER_NAME_NOT_FOUND, "用户名不存在");
				throw new UsernameNotFoundException("用户名不存在");
			}
		} catch (Exception e) {
			LOGGER.error("登陆异常", e);
			throw new RuntimeException(e.getMessage());
		}
		return userDetail;
	}
	
	
}
