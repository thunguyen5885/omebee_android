package com.omebee.android.layers.services;

import java.util.List;
import java.util.Map;

public interface IWebServiceAccess<T extends IWebServiceModel> {
    static final String WEBSERVICE_HOST = "https://omebee.com/";
	WSResult<T> executeRequest();
	Map<String, List<String>> getHeaders();
	void setHeaders(Map<String, List<String>> headers);
}
