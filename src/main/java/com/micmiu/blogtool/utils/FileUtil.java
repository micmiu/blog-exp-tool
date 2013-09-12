package com.micmiu.blogtool.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class FileUtil {

	private static final String FILE_REGX = "[\\/:*?\"<>|]+";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(FileUtil.parseFileName("asdg/asdf|test123"));
		System.out.println(FileUtil.parseFileName("asdg/asdf|test123测\"试"));

		BlogInfo vo = (BlogInfo) FileUtil.file2Object(MyContants.EXP_OBJ_PATH
				+ "opensource-hadoop-hadoop-start-demo-.obj");
		System.out.println(vo.getBlogURL());
		System.out.println(vo.getPostDate());
	}

	public static String parseFileName(String name) {
		return name.replaceAll(FILE_REGX, "-");
	}

	public static String parseURL2FileName(String url) {
		String tmp = url.replaceAll(".*www.micmiu.com/", "");
		return tmp.replaceAll(FILE_REGX, "-");
	}

	public static void exportBlog2File(BlogInfo vo) {
		String fileName = parseURL2FileName(vo.getBlogURL());
		object2File(vo, MyContants.EXP_OBJ_PATH + fileName + ".obj");
	}

	public static Map<String, Category> convertObj2CategoryMap(String rootPath) {
		File[] list = new File(rootPath).listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				if (pathname.isFile() && pathname.getName().endsWith(".obj")) {
					return true;
				}
				return false;
			}
		});
		Map<String, Category> categoryMap = MyContants.INIT_CATE_MAP;
		for (File file : list) {
			BlogInfo vo = (BlogInfo) FileUtil.file2Object(file);
			BlogUtil.convertCategoryMap(categoryMap, vo);

		}
		return categoryMap;
	}

	/**
	 * 文件转化为Object
	 * 
	 * @param fileName
	 * @return byte[]
	 */
	public static Object file2Object(File file) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			Object object = ois.readObject();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 文件转化为Object
	 * 
	 * @param fileName
	 * @return byte[]
	 */
	public static Object file2Object(String fileName) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			Object object = ois.readObject();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 把Object输出到文件
	 * 
	 * @param obj
	 * @param outputFile
	 */
	public static void object2File(Object obj, String outputFile) {
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(outputFile));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	  * 
     */
	public static String getDateSuffix() {
		String dateStr = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
		dateStr = sdf.format(new Date());
		return dateStr;
	}

}
