package com.omebee.android.layers.services;



public class WSAccessFactory {
	private static WSAccessFactory instance;
	private static Object lock = new Object();
	
	public static WSAccessFactory getInstance() {
		synchronized (lock) {
			if (instance == null) {
				instance = new WSAccessFactory();
			}
		}

		return instance;
	}
	
	public <T extends IWebServiceAccess<IWebServiceModel>> T getWebServiceAccess(Class<T> clazz)
			throws InstantiationException, IllegalAccessException {
			
		return clazz.newInstance();
	}

}
