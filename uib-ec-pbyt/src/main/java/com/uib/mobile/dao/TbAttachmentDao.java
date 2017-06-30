package com.uib.mobile.dao;

import com.uib.mobile.dto.TbAttachment;

public interface TbAttachmentDao {
	/**
	 * 添加图片
	 * @param fileName
	 * @param filePath
	 * @throws Exception
	 */
	public void save(TbAttachment tbAttachment)throws Exception;

}