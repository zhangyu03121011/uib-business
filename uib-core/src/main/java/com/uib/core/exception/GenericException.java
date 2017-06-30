package com.uib.core.exception;

/**
 * 所有异常类的基类
 * @Title:    GenericException
 * Company:   uib
 * Copyright: Copyright(C) 2012
 * @Version   1.0
 * @author    wanghuan
 * @date:     2012-12-6
 * @time:     下午03:59:22
 * Description:
 */
public class GenericException extends RuntimeException {

	private String message;
	private Throwable cause;

	private static final long serialVersionUID = -2587048678569478015L;

	public GenericException(Throwable cause) {
		super(cause);
	}
	
	public GenericException(String message) {
		super(message);
		this.message = message;
	}
	
	public GenericException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	public String getMessage() {
		return super.getMessage();
	}

	public String toString() {
		return this.getClass().getName() + ": [message=" + message + ", cause=" + cause + "]";
	}

}
