package com.micmiu.blogtool.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 */
public class Category implements Serializable {

	/**
	 * serialVersionUIDs
	 */
	private static final long serialVersionUID = -814426285797107389L;

	private String name;

	private Category parentNode;

	private List<BlogInfo> blogList = new ArrayList<BlogInfo>();

	private Map<String, Category> subCateMap = new LinkedHashMap<String, Category>();

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Category getParentNode() {
		return parentNode;
	}

	public List<BlogInfo> getBlogList() {
		return blogList;
	}

	public Map<String, Category> getSubCateMap() {
		return subCateMap;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentNode(Category parentNode) {
		this.parentNode = parentNode;
	}

	public void setBlogList(List<BlogInfo> blogList) {
		this.blogList = blogList;
	}

	public void setSubCategorMap(Map<String, Category> subCategorMap) {
		this.subCateMap = subCategorMap;
	}

}
