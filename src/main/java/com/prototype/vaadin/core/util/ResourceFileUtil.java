/**
 * Copyright (c) 2014 Far Eastone Telecommunications Co., Ltd.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Far Eastone Telecommunications Co., Ltd. ("Confidential Information"). 
 * 
 * You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of license agreement you entered
 * into with Far Eastone Telecommunications Co., Ltd.
 */

package com.prototype.vaadin.core.util;

import java.io.InputStream;

/**
 * 資源管理器
 * 
 * @author VJChou
 */
public enum ResourceFileUtil {

	JSON("json"), // json檔案
	CSV("csv"), // csv檔案
	SQL("sql"); // SQL檔案

	private String target; // 指定資料夾

	private static String rootDir = "template";

	/** separator */
	private static String separator = "/";

	private ResourceFileUtil(String target) {
		this.target = target;
	}

	/**
	 * 根據指定的路徑/檔案名稱回傳[SQL]內容
	 * 
	 * @param params
	 * @return String
	 */
	public final String getResource(String... params) {
		if (params != null && params.length != 0) {
			StringBuilder sb = new StringBuilder(separator + rootDir
					+ separator + target + separator);

			int len = params.length;
			int count = 0;

			for (String index : params) {
				sb.append(index);
				count++;
				if (count < len) {
					sb.append(separator);
				}
			}

			sb.append("." + target);

			// 找到sql檔案
			InputStream inStr = ResourceFileUtil.class.getResourceAsStream(sb
					.toString());
			
			return ReadFileUtil.readTextFile(inStr);
		}

		return "";
	}

}
