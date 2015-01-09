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
import java.util.Map;

/**
 * Request.
 * @author dimakura
 */
public interface Request extends Serializable {

	//
	// Client strings
	//

	public static final String ECLIPSE_CLIENT_1_0_0 = "ge.telasi.recut.client{version=1.0.0;type=eclipse_rcp}";
	public static final String ECLIPSE_CLIENT_1_0_1 = "telasi.recutil.client{version=1.0.1;type=eclipse_rcp}";
	public static final String ECLIPSE_CLIENT_1_0_2 = "telasi.recutil.client{version=1.0.2;type=eclipse_rcp}";
	public static final String ECLIPSE_CLIENT_1_0_3 = "telasi.recutil.client{version=1.0.3;type=eclipse_rcp}";
	public static final String ECLIPSE_CLIENT_1_0_5 = "telasi.recutil.client{version=1.0.5;type=eclipse_rcp}";
	public static final String ECLIPSE_CLIENT_1_0_7 = "telasi.recutil.client{version=1.0.7;type=eclipse_rcp}";
	public static final String ECLIPSE_CLIENT_1_0_10 = "telasi.recutil.client{version=1.0.10;type=eclipse_rcp}";

	public static final String FINAL = ECLIPSE_CLIENT_1_0_10;

	//
	// Required properties
	//

	/**
	 * Code for this code. This defines what to do with request.
	 */
	public static final String REQUEST_CODE = "request.code";

	/**
	 * String which describes client machine (program version, for example).
	 */
	public static final String CLIENT_STRING = "request.client-string";

	// username/password
	public static final String USERNAME = "request.username";

	public static final String PASSWORD = "request.password";

	//
	// Methods
	//

	/**
	 * Other properties of the database request in the form of mapped
	 * properties.
	 */
	public Map getProperties();

}
