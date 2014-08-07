package com.omebee.android.layers.services;


public class WSResult <T extends IWebServiceModel>{
	private int statusCode = 0;
	private String statusMsg;
	private T wsResultModel;
	
	public WSResult(int statusCode, String statusMsg, T wsResultModel) {
		super();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.wsResultModel = wsResultModel;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public T getWsResultModel() {
		return wsResultModel;
	}
	public void setWsResultModel(T wsResultModel) {
		this.wsResultModel = wsResultModel;
	}
	
}
