/*
 * Copyright 2007, Dimitri Kurashvili (dimakura@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package telasi.recutil.ejb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Default impmelentation of EJB request.
 * @author dimakura
 */
public abstract class DefaultEJBRequest implements Request {

	private static final long serialVersionUID = -1797947865291178843L;

	private Map properties;

	public DefaultEJBRequest(int requestId, String userName, String password) {
		if (userName == null || password == null) {
			throw new NullPointerException();
		}
		putProperty(Request.REQUEST_CODE, new Integer(requestId));
		putProperty(Request.USERNAME, userName);
		putProperty(Request.PASSWORD, password);
		putProperty(Request.CLIENT_STRING, Request.FINAL);
	}

	public Map getProperties() {
		return properties;
	}

	public void putProperty(String key, Serializable value) {
		if (properties == null) {
			properties = new HashMap();
		}
		properties.put(key, value);
	}

	public String getUserName() {
		return getProperties().get(Request.USERNAME).toString();
	}

	public String getPassword() {
		return getProperties().get(Request.PASSWORD).toString();
	}

	public Object getValue(String key) {
		return properties == null ? null : properties.get(key);
	}

	public static boolean versionLessThan5(String number) {
		boolean v0 = ECLIPSE_CLIENT_1_0_0.equals(number);
		boolean v1 = ECLIPSE_CLIENT_1_0_1.equals(number);
		boolean v2 = ECLIPSE_CLIENT_1_0_2.equals(number);
		boolean v3 = ECLIPSE_CLIENT_1_0_3.equals(number);
		return !v0 && !v1 && !v2 && !v3;
	}

	public static boolean versionLessThan7(String number) {
		boolean v0 = ECLIPSE_CLIENT_1_0_0.equals(number);
		boolean v1 = ECLIPSE_CLIENT_1_0_1.equals(number);
		boolean v2 = ECLIPSE_CLIENT_1_0_2.equals(number);
		boolean v3 = ECLIPSE_CLIENT_1_0_3.equals(number);
		boolean v5 = ECLIPSE_CLIENT_1_0_5.equals(number);
		return v0 || v1 || v2 || v3 || v5;
	}
	
}
