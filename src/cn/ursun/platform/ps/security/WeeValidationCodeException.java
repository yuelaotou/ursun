package cn.ursun.platform.ps.security;

import org.acegisecurity.AuthenticationException;

public class WeeValidationCodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public WeeValidationCodeException(String s) {
		super(s);
	}

}
