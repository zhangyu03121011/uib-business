package com.uib.ecmanager.modules.product.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.utils.ImageUtil;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.ProductImageRef;
import com.uib.ecmanager.modules.sys.utils.Utils;

public class ImageProcessorService {
	public static Logger logger = LoggerFactory
			.getLogger(ImageProcessorService.class);

	public static ExecutorService executor = Executors.newFixedThreadPool(5);
	public static String SYSTEM_FILE_PATH = "";
	public static final String largePath = "/large_image/";
	public static final String mediumPath = "/medium_image/";
	public static final String thumbnailPath = "/thumbnail_image/";
	public static Integer LARGE_PRODUCT_IMAGE_WIDTH = 800;
	public static Integer LARGE_PRODUCT_IMAGE_HEIGHT = 800;
	public static Integer MEDIUM_PRODUCT_IMAGE_WIDTH = 300;
	public static Integer MEDIUM_PRODUCT_IMAGE_HEIGHT = 300;
	public static Integer THUMBNAIL_PRODUCT_IMAGE_WIDTH = 170;
	public static Integer THUMBNAIL_PRODUCT_IMAGE_HEIGHT = 170;
	static {
		try {
			SYSTEM_FILE_PATH = Global.getConfig("upload.image.path");
			String largeProductImageWidth = Global
					.getConfig("largeProductImageWidth");
			LARGE_PRODUCT_IMAGE_WIDTH = Integer.parseInt(StringUtils
					.isBlank(largeProductImageWidth) ? "800"
					: largeProductImageWidth);
			String largeProductImageHeight = Global
					.getConfig("largeProductImageHeight");
			LARGE_PRODUCT_IMAGE_HEIGHT = Integer.parseInt(StringUtils
					.isBlank(largeProductImageHeight) ? "800"
					: largeProductImageHeight);
			String mediumProductImageWidth = Global
					.getConfig("mediumProductImageWidth");
			MEDIUM_PRODUCT_IMAGE_WIDTH = Integer.parseInt(StringUtils
					.isBlank(mediumProductImageWidth) ? "300"
					: mediumProductImageWidth);
			String mediumProductImageHeight = Global
					.getConfig("mediumProductImageHeight");
			MEDIUM_PRODUCT_IMAGE_HEIGHT = Integer.parseInt(StringUtils
					.isBlank(mediumProductImageHeight) ? "300"
					: mediumProductImageHeight);
			String thumbnailProductImageWidth = Global
					.getConfig("thumbnailProductImageWidth");
			THUMBNAIL_PRODUCT_IMAGE_WIDTH = Integer.parseInt(StringUtils
					.isBlank(thumbnailProductImageWidth) ? "170"
					: thumbnailProductImageWidth);
			String thumbnailProductImageHeight = Global
					.getConfig("thumbnailProductImageHeight");
			THUMBNAIL_PRODUCT_IMAGE_HEIGHT = Integer.parseInt(StringUtils
					.isBlank(thumbnailProductImageHeight) ? "170"
					: thumbnailProductImageHeight);
		} catch (Exception e) {
			logger.warn("图片配置信息加载出错!", e);
		}
	}

	/**
	 * 添加图片处理任务
	 * 
	 * @param sourcePath
	 *            原图片上传路径
	 * @param largePath
	 *            图片文件(大)上传路径
	 * @param mediumPath
	 *            图片文件(小)上传路径
	 * @param thumbnailPath
	 *            图片文件(缩略)上传路径
	 * @param tempFile
	 *            原临时文件
	 * @param contentType
	 *            原文件类型
	 */
	public static void addTask(final String sourcePath, final String largePath,
			final String mediumPath, final String thumbnailPath,
			final File tempFile, final String contentType) {
		try {
			executor.execute(new Runnable() {
				public void run() {
					String tempPath = System.getProperty("java.io.tmpdir");
					// File watermarkFile = new File(servletContext
					// .getRealPath(setting.getWatermarkImage()));
					File largeFile = new File(largePath);
					File mediumFile = new File(mediumPath);
					File thumbnailFile = new File(thumbnailPath);
					try {
						ImageUtil.zoom(new File(sourcePath), largeFile,
								LARGE_PRODUCT_IMAGE_WIDTH,
								LARGE_PRODUCT_IMAGE_HEIGHT);
						// ImageUtil.addWatermark(largeTempFile,
						// largeTempFile, watermarkFile,
						// setting.getWatermarkPosition(),
						// setting.getWatermarkAlpha());
						ImageUtil.zoom(new File(sourcePath), mediumFile,
								MEDIUM_PRODUCT_IMAGE_WIDTH,
								MEDIUM_PRODUCT_IMAGE_HEIGHT);
						// ImageUtil.addWatermark(mediumTempFile,
						// mediumTempFile, watermarkFile,
						// setting.getWatermarkPosition(),
						// setting.getWatermarkAlpha());
						ImageUtil.zoom(new File(sourcePath), thumbnailFile,
								THUMBNAIL_PRODUCT_IMAGE_WIDTH,
								THUMBNAIL_PRODUCT_IMAGE_HEIGHT);
					} catch (Exception e) {
						logger.warn("图片处理出错!", e);
					} finally {
//						FileUtils.deleteQuietly(tempFile);
//						FileUtils.deleteQuietly(largeFile);
//						FileUtils.deleteQuietly(mediumFile);
//						FileUtils.deleteQuietly(thumbnailFile);
					}
				}
			});
		} catch (Exception e) {
			logger.info("\t出错！", e);
		}
	}

	/**
	 * 处理商品图片属性
	 * 
	 * @param product
	 *            商品
	 */
	public static void dealImage(Product product) {
		List<ProductImageRef> imageRefs = product.getProductImageRefList();
		if(Utils.isBlank(imageRefs)){
			return;
		}
		Iterator<ProductImageRef> it = imageRefs.iterator();
		while (it.hasNext()) {
			ProductImageRef productImage=it.next();
			String filePath = productImage.getFilePath();
			if(StringUtils.isBlank(filePath)){
				productImage.setDelFlag("1");
				continue;
			}
			productImage.setSource(filePath);
			// String FileName = getFileName(filePath);
			String contentType = getFileContentType(filePath);
			String largeFilePath = filePath.replace("image/", largePath);
			productImage.setLarge(largeFilePath);
			File largeDirFile = new File(getFileDir(SYSTEM_FILE_PATH
					+ largeFilePath));
			if (!largeDirFile.exists()) {
				largeDirFile.mkdirs();
			}
			String mediumFilePath = filePath.replace("image/", mediumPath);
			File mediumDirFile = new File(getFileDir(SYSTEM_FILE_PATH
					+ mediumFilePath));
			if (!mediumDirFile.exists()) {
				mediumDirFile.mkdirs();
			}
			productImage.setMedium(mediumFilePath);
			String thumbnailFilePath = filePath
					.replace("image/", thumbnailPath);
			File thumbnailDir = new File(getFileDir(SYSTEM_FILE_PATH
					+ thumbnailFilePath));
			if (!thumbnailDir.exists()) {
				thumbnailDir.mkdirs();
			}
			productImage.setThumbnail(thumbnailFilePath);
			addTask(SYSTEM_FILE_PATH + filePath, SYSTEM_FILE_PATH
					+ largeFilePath, SYSTEM_FILE_PATH + mediumFilePath,
					SYSTEM_FILE_PATH + thumbnailFilePath, null, contentType);
		}
	}

	public static String getFileDir(String filePath) {
		return filePath.substring(0, filePath.lastIndexOf("/") + 1);
	}

	public static String getFileContentType(String filePath) {
		String[] strArr = filePath.split("\\.");
		return strArr[strArr.length - 1];
	}

}
