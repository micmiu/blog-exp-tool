package com.micmiu.blogtool.vo;

import java.io.Serializable;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class BlogInfo implements Serializable, Comparable<BlogInfo> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5443430043286032752L;

	private String title;

	private String blogURL;

	private String shortURL;

	private String categoryTag;

	private String author;

	private String postDate;

	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public String getShortURL() {
		return shortURL;
	}

	public String getAuthor() {
		return author;
	}

	public String getPostDate() {
		return postDate;
	}

	public String getContent() {
		return content;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategoryTag() {
		return categoryTag;
	}

	public void setCategoryTag(String categoryTag) {
		this.categoryTag = categoryTag;
	}

	public int compareTo(BlogInfo o) {
		try {
			return this.postDate.compareTo(o.postDate);
		} catch (Exception e) {
			return 0;
		}
	}

}
