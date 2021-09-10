package com.xumu.programmer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

public class ExcelUtil {
	
	/**
	 * 创建Excel文件
	 * @param workbook 工作簿
	 * @param fileName 目标文件名
	 * @return
	 */
	public static File createExcelFile(Workbook workbook, String fileName) {
		OutputStream stream = null;
		File file = null;
		try {
			file = File.createTempFile(fileName, ".xlsx");
			stream = new FileOutputStream(file.getAbsoluteFile());
			workbook.write(stream);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(workbook);
			IOUtils.closeQuietly(stream);
		}
		return file;
	}
	
	/**
	 * 下载文件
	 * @param response
	 * @param file
	 * @param fileName
	 */
	public static void downloadFile(HttpServletResponse response, File file, String fileName) {
		try {
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("ISO-8859-1"),"UTF-8")+".xlsx");
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			InputStream is = new FileInputStream(file.getAbsolutePath());
			BufferedInputStream bis = new BufferedInputStream(is);
			int length = 0;
			byte[] temp = new byte[1*1024*10];
			while((length = bis.read(temp)) != -1) {
				bos.write(temp, 0, length);
			}
			bos.flush();
			bis.close();
			bos.close();
			is.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
