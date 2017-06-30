package com.uib.common.service.impl;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.uib.common.service.CaptchaService;
import com.uib.common.utils.SpringContextHolder;


@Component("captchaService")
public class CaptchaServiceImpl implements CaptchaService{

	private com.octo.captcha.service.CaptchaService imageCaptchaService;
	
	

	public com.octo.captcha.service.CaptchaService getImageCaptchaService() {
		if (null == imageCaptchaService){
			imageCaptchaService = (com.octo.captcha.service.CaptchaService) SpringContextHolder.getApplicationContext().getBean("imageCaptchaService");
		}
		return imageCaptchaService;
	}

	public BufferedImage buildImage(String captchaId) {
		return (BufferedImage) getImageCaptchaService().getChallengeForID(captchaId);
	}

	public boolean isValid(String captchaId, String captcha) {
		if (StringUtils.isNotEmpty(captchaId)
				&& StringUtils.isNotEmpty(captcha)) {
			try {
				return getImageCaptchaService().validateResponseForID(captchaId,
						captcha.toUpperCase());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
