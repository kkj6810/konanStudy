package com.konantech.knsf.common.vo;


import java.util.HashMap;
import java.util.List;

public class ResultListVO {
	private String status;
	private long total;
	private int rows;
	
	private List<HashMap<String, String>> result;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public List<HashMap<String, String>> getResult() {
		return result;
	}

	public void setResult(List<HashMap<String, String>> result) {
		this.result = result;
	}
}
