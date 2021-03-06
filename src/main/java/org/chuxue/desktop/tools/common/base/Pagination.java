package org.chuxue.desktop.tools.common.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 文件名 ： Pagination.java
 * 包 名 ： tk.ainiyue.danyuan.application.common.base
 * 描 述 ： 通用逻辑分页，参数传递类
 * 作 者 ： wang
 * 时 间 ： 2018年3月1日 下午10:19:40
 * 版 本 ： V1.0
 */
public class Pagination<T> {

	public Integer				pageNumber;
	public Integer				pageSize;
	public String				uuid;
	public String				searchText;
	public String				username;
	public String				sortName;
	public String				filter;
	public String				sortOrder;
	List<T>						list;
	public T					info;
	public Map<String, String>	map	= new HashMap<>();

	/**
	 * 方法名 ： getInfo
	 * 功 能 ： 返回变量 info 的值
	 *
	 * 返 回 ： T
	 */
	public T getInfo() {
		return info;
	}

	/**
	 * 方法名 ： setInfo
	 * 功 能 ： 设置变量 info 的值
	 */
	public void setInfo(T info) {
		this.info = info;
	}

	/**
	 * 方法名 ： getPageNumber
	 * 功 能 ： 返回变量 pageNumber 的值
	 *
	 * 返 回 ： Integer
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * 方法名 ： setPageNumber
	 * 功 能 ： 设置变量 pageNumber 的值
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 方法名 ： getPageSize
	 * 功 能 ： 返回变量 pageSize 的值
	 *
	 * 返 回 ： Integer
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 方法名 ： setPageSize
	 * 功 能 ： 设置变量 pageSize 的值
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 方法名 ： getUuid
	 * 功 能 ： 返回变量 uuid 的值
	 *
	 * 返 回 ： String
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * 方法名 ： setUuid
	 * 功 能 ： 设置变量 uuid 的值
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 方法名 ： getSearchText
	 * 功 能 ： 返回变量 searchText 的值
	 *
	 * 返 回 ： String
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * 方法名 ： setSearchText
	 * 功 能 ： 设置变量 searchText 的值
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * 方法名 ： getList
	 * 功 能 ： 返回变量 list 的值
	 *
	 * 返 回 ： List<T>
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 方法名 ： setList
	 * 功 能 ： 设置变量 list 的值
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * 方法名 ： getUsername
	 * 功 能 ： 返回变量 username 的值
	 *
	 * 返 回 ： String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 方法名 ： setUsername
	 * 功 能 ： 设置变量 username 的值
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getFilter() {
		return filter;
	}


	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * 方法名 ： setSortOrder
	 * 功 能 ： 设置变量 sortOrder 的值
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * 方法名 ： setMap
	 * 功 能 ： 设置变量 map 的值
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * 方法名 ： toString
	 * 功 能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参 数 ： 返 回 :
	 * 参 考 ：  java.lang.Object#toString()
	 * 作 者 ： Administrator
	 */

	@Override
	public String toString() {
		return "Pagination [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", uuid=" + uuid + ", searchText=" + searchText + ", username=" + username + ", sortName=" + sortName + ", filter=" + filter + ", sortOrder=" + sortOrder + ", list=" + list + ", info=" + info + ", map=" + map + "]";
	}

}
