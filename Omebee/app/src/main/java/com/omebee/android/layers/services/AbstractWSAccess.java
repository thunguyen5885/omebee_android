package com.omebee.android.layers.services;

import com.omebee.android.utils.AppConstants;

import java.util.List;
import java.util.Map;



public abstract class AbstractWSAccess<T extends IWebServiceModel> implements IWebServiceAccess {
	protected Map<String, List<String>> headers;
	@Override
	public WSResult<T> executeRequest() {
		// TODO Auto-generated method stub
		WSRequest request = buildRequest();
		if (requiresAuthorization()) {
			//add authorize to request
		}
		WSResponse response = doRequest(request);
		return buildResult(response);
	}
	
	protected WSResult<T> buildResult(WSResponse response) {

		int status = response.status;
		String statusMsg = "";
		String responseBody = null;
		T resource = null;

		try {
			responseBody = new String(response.body, getCharacterEncoding(response.headers));
			resource = parseResponseBody(responseBody);
		} catch (Exception ex) {
			// TODO Should we set some custom status code?
			status = 1001; // spec only defines up to 505
			statusMsg = ex.getMessage();
		}
		return new WSResult<T>(status, statusMsg, resource);
	}
	
	protected abstract WSRequest buildRequest();
	protected abstract T parseResponseBody(String responseBody) throws Exception;
	
	protected boolean requiresAuthorization() {
		return true;
	}
	
	private String getCharacterEncoding(Map<String, List<String>> headers) {
		// TODO get value from headers
		return AppConstants.DEFAULT_ENCODING;
	}
	
	private WSResponse doRequest(WSRequest request) {

		WSClient client = new WSClient();
		return client.execute(request);
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		// TODO Auto-generated method stub
		return this.headers;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setHeaders(Map headers) {
		// TODO Auto-generated method stub
		this.headers = headers;
	}

}
