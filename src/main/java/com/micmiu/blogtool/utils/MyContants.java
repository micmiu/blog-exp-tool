package com.micmiu.blogtool.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.micmiu.blogtool.vo.Category;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class MyContants {

	public static final String MY_BLOG_URL = "http://www.micmiu.com";
	public static final String MY_NAME = "Michael Sun";

	public static final boolean EXP_OBJ = true;
	public static final boolean EXP_PDF = true;
	public static final boolean PDF_ADD_HEADER = true;

	public static final String EXP_ROOT_PATH = "/Users/micmiu/no_sync/ebook/micmiu-blog/export/";
	public static final String EXP_OBJ_PATH = EXP_ROOT_PATH + "obj/";
	public static final String EXP_PDF_PATH = EXP_ROOT_PATH + "pdf/";
	public static final String EXP_SHARE_PATH = EXP_ROOT_PATH + "share/";

	public static final int MAX_RETRY_TIMES = 3;

	public static final long SLEEP_PAGE_URL = 1 * 1000L;

	public static final long SLEEP_BLOG_URL = 2 * 1000L;

	public static final String URL_REGX = "[a-zA-z]+://.[.a-zA-Z\\d-]+/";

	public static BaseFont PDF_CN_BASE_FONT = null;

	public static LineSeparator PDF_LINE = new LineSeparator(1, 100,
			new BaseColor(204, 204, 204), Element.ALIGN_CENTER, -2);

	public static Map<String, String> CATE_NAME_MAP = new HashMap<String, String>();

	public static Map<String, Category> INIT_CATE_MAP = new LinkedHashMap<String, Category>();

	static {
		try {
			PDF_CN_BASE_FONT = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
		} catch (Exception e) {
			try {
				PDF_CN_BASE_FONT = BaseFont.createFont();
			} catch (Exception e1) {
			}
		}
		CATE_NAME_MAP.put("j2ee", "J2EE");
		CATE_NAME_MAP.put("hibernate", "Hibernate");
		CATE_NAME_MAP.put("struts", "Struts");
		CATE_NAME_MAP.put("spring", "Spring");
		CATE_NAME_MAP.put("jdbc-tech", "JDBC");
		CATE_NAME_MAP.put("jta", "JTA");

		CATE_NAME_MAP.put("soa", "SOA");
		CATE_NAME_MAP.put("webservice", "webservice");
		CATE_NAME_MAP.put("rpc", "RPC");

		CATE_NAME_MAP.put("opensource", "OpenSource");
		CATE_NAME_MAP.put("ofc", "openflashchart");
		CATE_NAME_MAP.put("hadoop", "Hadoop");
		CATE_NAME_MAP.put("expdoc", "生成文档");

		CATE_NAME_MAP.put("architecture", "架构设计");
		CATE_NAME_MAP.put("cluster", "集群");
		CATE_NAME_MAP.put("cache", "缓存");

		CATE_NAME_MAP.put("enterprise-app", "企业应用");
		CATE_NAME_MAP.put("snmp", "SNMP");
		CATE_NAME_MAP.put("sso", "SSO");
		CATE_NAME_MAP.put("server", "服务器软件");

		CATE_NAME_MAP.put("lang", "编程语言");
		CATE_NAME_MAP.put("java", "Java");
		CATE_NAME_MAP.put("javascript", "javascript");
		CATE_NAME_MAP.put("groovy", "Groovy");
		CATE_NAME_MAP.put("nodejs", "Nodejs");

		CATE_NAME_MAP.put("nosql", "NoSQL");
		CATE_NAME_MAP.put("berkeley", "Berkeley");

		CATE_NAME_MAP.put("db", "数据库");
		CATE_NAME_MAP.put("oracle-db", "Oracle");
		CATE_NAME_MAP.put("mysql", "mysql");

		CATE_NAME_MAP.put("android", "Android");

		CATE_NAME_MAP.put("web", "web UI");

		CATE_NAME_MAP.put("software", "软件工具");
		CATE_NAME_MAP.put("build", "项目构建");
		CATE_NAME_MAP.put("test", "测试工具");

		CATE_NAME_MAP.put("os", "操作系统");
		CATE_NAME_MAP.put("linux", "linux");
		CATE_NAME_MAP.put("window", "window");
		CATE_NAME_MAP.put("mac", "Mac");

		CATE_NAME_MAP.put("exception", "异常处理");

		CATE_NAME_MAP.put("techother", "杂谈");

		// ---------------------------------------

		INIT_CATE_MAP.put("J2EE", new Category("J2EE"));
		INIT_CATE_MAP.get("J2EE").getSubCateMap()
				.put("Hibernate", new Category("Hibernate"));
		INIT_CATE_MAP.get("J2EE").getSubCateMap()
				.put("Struts", new Category("Struts"));
		INIT_CATE_MAP.get("J2EE").getSubCateMap()
				.put("Spring", new Category("Spring"));
		INIT_CATE_MAP.get("J2EE").getSubCateMap()
				.put("JTA", new Category("JTA"));

		INIT_CATE_MAP.put("SOA", new Category("SOA"));
		INIT_CATE_MAP.get("SOA").getSubCateMap()
				.put("webservice", new Category("webservice"));
		INIT_CATE_MAP.get("SOA").getSubCateMap()
				.put("RPC", new Category("RPC"));

		INIT_CATE_MAP.put("OpenSource", new Category("OpenSource"));
		INIT_CATE_MAP.get("OpenSource").getSubCateMap()
				.put("openflashchart", new Category("openflashchart"));
		INIT_CATE_MAP.get("OpenSource").getSubCateMap()
				.put("Hadoop", new Category("Hadoop"));
		INIT_CATE_MAP.get("OpenSource").getSubCateMap()
				.put("生成文档", new Category("生成文档"));

		INIT_CATE_MAP.put("架构设计", new Category("架构设计"));
		INIT_CATE_MAP.get("架构设计").getSubCateMap().put("集群", new Category("集群"));
		INIT_CATE_MAP.get("架构设计").getSubCateMap().put("缓存", new Category("缓存"));

		INIT_CATE_MAP.put("企业应用", new Category("企业应用"));
		INIT_CATE_MAP.get("企业应用").getSubCateMap()
				.put("SNMP", new Category("SNMP"));
		INIT_CATE_MAP.get("企业应用").getSubCateMap()
				.put("SSO", new Category("SSO"));
		INIT_CATE_MAP.get("企业应用").getSubCateMap()
				.put("服务器软件", new Category("服务器软件"));

		INIT_CATE_MAP.put("编程语言", new Category("编程语言"));
		INIT_CATE_MAP.get("编程语言").getSubCateMap()
				.put("Java", new Category("Java"));
		INIT_CATE_MAP.get("编程语言").getSubCateMap()
				.put("javascript", new Category("javascript"));
		INIT_CATE_MAP.get("编程语言").getSubCateMap()
				.put("Groovy", new Category("Groovy"));
		INIT_CATE_MAP.get("编程语言").getSubCateMap()
				.put("Nodejs", new Category("Nodejs"));

		INIT_CATE_MAP.put("NoSQL", new Category("NoSQL"));
		INIT_CATE_MAP.get("NoSQL").getSubCateMap()
				.put("Berkeley", new Category("Berkeley"));

		INIT_CATE_MAP.put("数据库", new Category("数据库"));
		INIT_CATE_MAP.get("数据库").getSubCateMap()
				.put("Oracle", new Category("Oracle"));
		INIT_CATE_MAP.get("数据库").getSubCateMap()
				.put("mysql", new Category("mysql"));

		INIT_CATE_MAP.put("Android", new Category("Android"));

		INIT_CATE_MAP.put("web UI", new Category("web UI"));

		INIT_CATE_MAP.put("软件工具", new Category("软件工具"));
		INIT_CATE_MAP.get("软件工具").getSubCateMap()
				.put("项目构建", new Category("项目构建"));
		INIT_CATE_MAP.get("软件工具").getSubCateMap()
				.put("测试工具", new Category("测试工具"));

		INIT_CATE_MAP.put("操作系统", new Category("操作系统"));
		INIT_CATE_MAP.get("操作系统").getSubCateMap()
				.put("linux", new Category("linux"));
		INIT_CATE_MAP.get("操作系统").getSubCateMap()
				.put("window", new Category("window"));
		INIT_CATE_MAP.get("操作系统").getSubCateMap()
		.put("Mac", new Category("Mac"));

		INIT_CATE_MAP.put("异常处理", new Category("异常处理"));

		INIT_CATE_MAP.put("杂谈", new Category("杂谈"));

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
