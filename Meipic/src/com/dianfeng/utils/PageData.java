package com.dianfeng.utils;
/**
 * 分页
 */
import java.util.List;

public class PageData<T>{
	Integer total;
	List<T> rows;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
