package com.uib.common.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * 验证码图片生成
 * @Title CaptchaEngine
 * @Company: e-life
 * @Copyright: Copyright(C) 2014
 * @Version   1.0
 * @author elife
 * @date 2014-6-10
 * @time 下午2:16:32
 * @Description
 */
public class CaptchaEngine extends ListImageCaptchaEngine {

	/** 图片宽度 */
	private static final int IMAGE_WIDTH = 80;

	/** 图片高度 */
	private static final int IMAGE_HEIGHT = 28;

	/** 最小字体大小 */
	private static final int MIN_FONT_SIZE = 12;

	/** 最大字体大小 */
	private static final int MAX_FONT_SIZE = 16;

	/** 最小字符个数 */
	private static final int MIN_WORD_LENGTH = 4;

	/** 最大字符个数 */
	private static final int MAX_WORD_LENGTH = 4;

	/** 随机字符 */
	private static final String CHAR_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZSZHZOZPZXZXZ";

	/** 随机背景图片路径 */
	//private static final String BACKGROUND_IMAGE_PATH = "me/life/captcha/";

	/**
	 * 随机字体
	 */
	private static final Font[] FONTS = new Font[] { new Font("nyala", Font.BOLD, MAX_FONT_SIZE), new Font("Arial", Font.BOLD, MAX_FONT_SIZE), new Font("nyala", Font.BOLD, MAX_FONT_SIZE), new Font("Bell", Font.BOLD, MAX_FONT_SIZE), new Font("Bell MT", Font.BOLD, MAX_FONT_SIZE), new Font("Credit", Font.BOLD, MAX_FONT_SIZE), new Font("valley", Font.BOLD, MAX_FONT_SIZE),
			new Font("Impact", Font.BOLD, MAX_FONT_SIZE) };

	/**
	 * 随机颜色
	 */
	private static final Color[] COLORS = new Color[] { new Color(255, 255, 255), new Color(255, 220, 220), new Color(220, 255, 255), new Color(220, 220, 255), new Color(255, 255, 220), new Color(220, 255, 220) };

	/**
	 * 验证码图片生成
	 */
	@Override
	protected void buildInitialFactories() {
		FontGenerator fontGenerator = new RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE, FONTS);
	/*	BackgroundGenerator backgroundGenerator = new FileReaderRandomBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT, new ClassPathResource(BACKGROUND_IMAGE_PATH).getPath());
		BackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT, Color.white);  
		TextPaster textPaster = new DecoratedRandomTextPaster(MIN_WORD_LENGTH, MAX_WORD_LENGTH, new RandomListColorGenerator(COLORS), new TextDecorator[] {});
		addFactory(new GimpyFactory(new RandomWordGenerator(CHAR_STRING), new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster)));*/
		
		//word generator  
        //WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));  
  
        //word2image components  
        TextPaster randomPaster = new DecoratedRandomTextPaster(MIN_WORD_LENGTH, MAX_WORD_LENGTH,  
                new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11),  
                        new Color(23, 67, 172) }), new TextDecorator[] {});  
        BackgroundGenerator background = new UniColorBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT, Color.white);  
        
  
        ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});  
        ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});  
        ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});  
  
        WordToImage word2image = new DeformedComposedWordToImage(fontGenerator, background, randomPaster, backDef, textDef,  
                postDef);
       // addFactory(new GimpyFactory(new RandomWordGenerator(CHAR_STRING), new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster)));
        addFactory(new GimpyFactory(new RandomWordGenerator(CHAR_STRING),word2image));  
	}

}