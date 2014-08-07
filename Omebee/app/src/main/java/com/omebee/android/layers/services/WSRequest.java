package com.omebee.android.layers.services;

import com.omebee.android.utils.AppConstants.WSMethod;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class WSRequest {
	private URI requestUri;
	private Map<String, List<String>> headers;
	private byte[] body;
	private WSMethod method;
	
	public WSRequest(WSMethod method, URI requestUri, Map<String, List<String>> headers,
			byte[] body) {
		super();
		this.method = method;
		this.requestUri = requestUri;
		this.headers = headers;
		this.body = body;
	}
	
	public WSMethod getMethod() {
		return method;
	}

	public URI getRequestUri() {
		return requestUri;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public byte[] getBody() {
		return body;
	}

	public void addHeader(String key, List<String> value) {
		
		if (headers == null) {
			headers = new HashMap<String, List<String>>();
		}
		headers.put(key, value);
	}
}
