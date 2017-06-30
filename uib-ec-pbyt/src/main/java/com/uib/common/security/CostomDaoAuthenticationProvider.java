package com.uib.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import com.uib.common.enums.LoginResult;
import com.uib.member.service.MemMemberService;


/**
 * 扩展密码验证方法,保存密码错误日志，用于锁定账户
 * @author shuaiyz
 *
 */
public class CostomDaoAuthenticationProvider extends DaoAuthenticationProvider {
	
	/**
     * The plaintext password used to perform {@link PasswordEncoder#isPasswordValid(String, String, Object)} on when the user is
     * not found to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    //~ Instance fields ================================================================================================

    private PasswordEncoder passwordEncoder;

    /**
     * The password used to perform {@link PasswordEncoder#isPasswordValid(String, String, Object)} on when the user is
     * not found to avoid SEC-2056. This is necessary, because some {@link PasswordEncoder} implementations will short circuit if the
     * password is not in a valid format.
     */
    private String userNotFoundEncodedPassword;

    private SaltSource saltSource;

    private UserDetailsService userDetailsService;
    
    
    @Autowired
    private MemMemberService memMemberService;

	public CostomDaoAuthenticationProvider() {
		setPasswordEncoder(new PlaintextPasswordEncoder());
	}
	
	/**
	 * 重写父类的密码验证方法，记录密码验证错误日志
	 */
	protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;

        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
            logger.debug("Authentication failed: password does not match stored value");
            
          //  loginLogService.addLoginLog(userDetails.getUsername(), LoginResult.PWD_ERROR, "密码输入错误");
            int errorNum = memMemberService.handlerUserLoginPwdExcepton(userDetails.getUsername());
            if (errorNum == 3) {
				throw new BadCredentialsException(messages.getMessage("UserLoginPasswordError.lockMessage"), userDetails);
			}
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }else {
        	//loginLogService.addLoginLog(userDetails.getUsername(), LoginResult.SUCCESS, "");
        }
    }

	 /**
     * Sets the PasswordEncoder instance to be used to encode and validate passwords.
     * If not set, the password will be compared as plain text.
     * <p>
     * For systems which are already using salted password which are encoded with a previous release, the encoder
     * should be of type {@code org.springframework.security.authentication.encoding.PasswordEncoder}. Otherwise,
     * the recommended approach is to use {@code org.springframework.security.crypto.password.PasswordEncoder}.
     *
     * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder} types.
     */
    public void setPasswordEncoder(Object passwordEncoder) {
    	Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

        if (passwordEncoder instanceof PasswordEncoder) {
            setPasswordEncoder((PasswordEncoder) passwordEncoder);
            return;
        }

        if (passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
            final org.springframework.security.crypto.password.PasswordEncoder delegate =
                    (org.springframework.security.crypto.password.PasswordEncoder)passwordEncoder;
            setPasswordEncoder(new PasswordEncoder() {
                public String encodePassword(String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(Object salt) {
                    Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
                }
            });

            return;
        }

        throw new IllegalArgumentException("passwordEncoder must be a PasswordEncoder instance");
    }
    
	private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        
        this.userNotFoundEncodedPassword = passwordEncoder.encodePassword(USER_NOT_FOUND_PASSWORD, null);
        this.passwordEncoder = passwordEncoder;
//        super.setPasswordEncoder(this.passwordEncoder);
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
    
	public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource() {
        return saltSource;
    }

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		super.setUserDetailsService(this.userDetailsService);
	}
    
}
