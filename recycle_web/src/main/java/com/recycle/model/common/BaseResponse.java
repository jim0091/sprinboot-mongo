package com.recycle.model.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class BaseResponse  implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -940795926263067880L;

	//是否成功
	private boolean success;
	
	//结果
	private Object result;
	
	//描述
	private String desc;
	
	//描述编号
	private int descCode = -99999;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getDescCode() {
		return descCode;
	}

	public void setDescCode(int descCode) {
		this.descCode = descCode;
	}
	
	public static String BaseResponse(int resultState, String resultMsg) {
		BaseResponse resp = new BaseResponse();
		resp.setSuccess(false);
		resp.setDesc(resultMsg);
		resp.setDescCode(resultState);
		resp.setResult(resultMsg);
		return JSONObject.toJSONString(resp);
	}
	/**
	 * 操作成功时的返回
	 * @param result
	 * @param desc
	 * @return
	 */
	public BaseResponse setValue(Object result, String desc) {
		this.result = result;
		this.desc = desc;
		this.success = true;
		this.descCode = 200;
		return this;
	}
	
	/**
	 * 操作失败时的返回
	 * @param desc
	 * @param code
	 * @return
	 */
	public BaseResponse setError(String desc, int code) {
		this.descCode = code;
		this.desc = desc;
		this.result = null;
		this.success = false;
		return this;
	}
	
}
