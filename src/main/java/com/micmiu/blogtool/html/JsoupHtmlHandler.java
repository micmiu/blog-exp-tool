package com.micmiu.blogtool.html;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.micmiu.blogtool.utils.BlogUtil;
import com.micmiu.blogtool.utils.FileUtil;
import com.micmiu.blogtool.utils.MyContants;
import com.micmiu.blogtool.utils.StringUtil;
import com.micmiu.blogtool.vo.BlogInfo;
import com.micmiu.blogtool.vo.Category;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class JsoupHtmlHandler implements IHtmlHandler {

	private static final Logger logger = Logger
			.getLogger(JsoupHtmlHandler.class);

	/**
	 * 根据首页规则提取所有的page URL
	 */
	public List<String> fetchPageList(String homeURL) throws Exception {
		Document doc = BlogUtil.getHTMLContent(homeURL);
		Element pagenavi = doc.select("div.wp-pagenavi").first();
		Elements blogLinks = pagenavi.select("a[href]");
		List<String> pageList = new ArrayList<String>();
		pageList.add(homeURL);
		String pageReg = null;
		int max = 0;
		for (Element link : blogLinks) {
			if (link.text().startsWith("最旧")) {
				pageReg = StringUtil.parsePageUrl(link.attr("href"));
				max = StringUtil.parsePageMax(link.attr("href"));
				break;
			}
		}
		if (null == pageReg || max < 2) {
			return pageList;
		}
		for (int i = 2; i <= max; i++) {
			pageList.add(MessageFormat.format(pageReg, i));
		}
		return pageList;
	}

	/**
	 * 根据规则生成page URL
	 * 
	 * @param pageURLReg
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private List<String> fmtPageList(String pageURLReg, int start, int end)
			throws Exception {
		List<String> pageList = new ArrayList<String>();
		for (int i = start; i <= end; i++) {
			pageList.add(MessageFormat.format(pageURLReg, i));
		}
		return pageList;
	}

	/**
	 * 提取page 页面中blog的 标题和 URL
	 */
	public Map<String, String> fetchBlogInfoMap(String pageURL)
			throws Exception {
		Map<String, String> infoMap = new LinkedHashMap<String, String>();
		Document doc = BlogUtil.getHTMLContent(pageURL);
		if (null == doc) {
			return infoMap;
		}

		Elements divPosts = doc.select("div.post");
		for (Element post : divPosts) {

			Element blogAuthor = post.select("a[rel=author]").first();
			String author = blogAuthor.text();
			if (!"Michael".equals(author)) {
				continue;
			}
			Element blogTitle = post.select("h2").first();
			Element rm = post.select("p.read-more").first();
			if (null != rm) {
				Element blogUrl = rm.select("a[href]").first();
				infoMap.put(blogTitle.text(), blogUrl.attr("href"));
				continue;
			} else {
				Element blogMark = post.select("a[rel=bookmark]").first();
				if (null != blogMark) {
					infoMap.put(blogTitle.text(), blogMark.attr("href"));
				}
			}

		}
		return infoMap;
	}

	/**
	 * 提取page 页面中blog的 信息转化为category结构
	 */
	public void fetchBlogInfo(Map<String, Category> categoryMap, String pageURL)
			throws Exception {
		Document doc = BlogUtil.getHTMLContent(pageURL);
		if (null == doc) {
			return;
		}
		Elements divPosts = doc.select("div.post");
		for (Element post : divPosts) {

			Element blogAuthor = post.select("a[rel=author]").first();
			String author = blogAuthor.text();
			if (!"Michael".equals(author)) {
				continue;
			}

			Element blogTitle = post.select("h2").first();

			Element blogMark = post.select("a[rel=bookmark]").first();
			String blogURL = "", postDate = "";
			if (null != blogMark) {
				blogURL = blogMark.attr("href");
				postDate = blogMark.text();
			} else {
				Element rm = post.select("p.read-more").first();
				if (null != rm) {
					Element blogUrl = rm.select("a[href]").first();
					blogURL = blogUrl.attr("href");
				}
			}
			BlogInfo vo = new BlogInfo();
			vo.setTitle(blogTitle.text());
			vo.setAuthor("Michael");
			vo.setBlogURL(blogURL);
			vo.setPostDate(postDate);
			vo.setCategoryTag(StringUtil.parseCategory(blogURL));
			extractContent(vo);
			// vo.setContent(this.extractContent(blogURL));

			if (MyContants.EXP_OBJ) {
				FileUtil.exportBlog2File(vo);
			}
			BlogUtil.convertCategoryMap(categoryMap, vo);
		}

	}

	/**
	 * 提取page页面中blog的信息
	 */
	public List<BlogInfo> fetchBlogVoList(String pageURL) throws Exception {
		Document doc = BlogUtil.getHTMLContent(pageURL);

		if (null == doc) {
			return Collections.emptyList();
		}
		List<BlogInfo> list = new ArrayList<BlogInfo>();
		Elements divPosts = doc.select("div.post");
		for (Element post : divPosts) {
			Element blogAuthor = post.select("a[rel=author]").first();
			String author = blogAuthor.text();
			if (!"Michael".equals(author)) {
				continue;
			}

			Element blogTitle = post.select("h2").first();

			Element blogMark = post.select("a[rel=bookmark]").first();
			String blogURL = "", postDate = "";
			if (null != blogMark) {
				blogURL = blogMark.attr("href");
				postDate = blogMark.text();
			} else {
				Element rm = post.select("p.read-more").first();
				if (null != rm) {
					Element blogUrl = rm.select("a[href]").first();
					blogURL = blogUrl.attr("href");
				}
			}
			BlogInfo vo = new BlogInfo();
			vo.setTitle(blogTitle.text());
			vo.setAuthor("Michael");
			vo.setBlogURL(blogURL);
			vo.setPostDate(postDate);
			vo.setCategoryTag(StringUtil.parseCategory(blogURL));
			extractContent(vo);
			// vo.setContent(this.extractContent(blogURL));

			if (MyContants.EXP_OBJ) {
				FileUtil.exportBlog2File(vo);
			}
			list.add(vo);
		}
		return list;
	}

	/**
	 * 提取文章内容
	 */
	private void extractContent(BlogInfo vo) throws Exception {
		Document doc = BlogUtil.getHTMLContent(vo.getBlogURL());
		if (null == doc) {
			return;
		}
		if (null == vo.getPostDate()) {
			Element e_date = doc.select("span.post-info-date").first();
			vo.setPostDate(e_date.text().split("日期")[1].trim());
		}
		Element entry = doc.select("div.entry").first();
		vo.setContent(formatContentTag(entry));
	}

	/**
	 * img标签处理
	 * 
	 * @param entry
	 * @return
	 */
	private String formatContentTag(Element entry) {
		try {
			entry.select("div").remove();
			// 把 <a href="*.jpg" ><img src="*.jpg"/></a> 替换为 <img
			// src="*.jpg"/>
			for (Element imgEle : entry.select("a[href~=(?i)\\.(png|jpe?g)]")) {
				imgEle.replaceWith(imgEle.select("img").first());
			}
			return entry.html();
		} catch (Exception e) {
			logger.error("format tag error:", e);
			return "";
		}
	}

	/**
	 * 提取page页面中blog的信息
	 */
	public BlogInfo fetchBlogVo(String blogURL) throws Exception {
		BlogInfo vo = new BlogInfo();
		String[] infos = this.extractBlogInfo(blogURL);
		vo.setAuthor("Michael");
		vo.setBlogURL(blogURL);
		vo.setTitle(infos[0]);
		vo.setCategoryTag(infos[1]);
		vo.setPostDate(infos[2]);
		vo.setContent(infos[3]);
		if (MyContants.EXP_OBJ) {
			FileUtil.exportBlog2File(vo);
		}
		return vo;

	}

	/**
	 * return [title,category,post date,content]
	 * 
	 * @param blogURL
	 * @return
	 * @throws Exception
	 */
	public String[] extractBlogInfo(String blogURL, boolean contentFlag)
			throws Exception {
		String[] info = new String[4];
		Document doc = Jsoup.connect(blogURL).get();
		Element e_title = doc.select("h2.title").first();
		info[0] = e_title.text();

		Element e_category = doc.select("a[rel=category tag]").first();
		info[1] = StringUtil.parseCategory(e_category.attr("href"));

		Element e_date = doc.select("span.post-info-date").first();

		String dateStr = e_date.text().split("日期")[1].trim();
		info[2] = dateStr;
		if (contentFlag) {
			Element entry = doc.select("div.entry").first();
			info[3] = formatContentTag(entry);
		}

		return info;
	}

	/**
	 * return [title,category,post date,content]
	 * 
	 * @param blogURL
	 * @return
	 * @throws Exception
	 */
	public String[] extractBlogInfo(String blogURL) throws Exception {
		return extractBlogInfo(blogURL, true);
	}

}
