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
package telasi.recutil.service;

import telasi.recutil.ejb.IRequestProcessor;
import telasi.recutil.ejb.Request;
import telasi.recutil.service.eclipse.RequestProcessor;

/**
 * Request processor facotory.
 * 
 * @author dimakura
 */
public class RequestProcessorFactory {

	/**
	 * Creates request processor depending on client string. This means that we
	 * may supporm many versions of the program by simply adding new processors
	 * here.
	 */
	public static IRequestProcessor createRequestProcessor(String clientString) {

		// Eclipse RCP client version 1.0.0
		if (Request.ECLIPSE_CLIENT_1_0_0.equals(clientString))
			throw new IllegalArgumentException("Not supporte version 1.0.0");

		// old versions
		if (		Request.ECLIPSE_CLIENT_1_0_1.equals(clientString) // 1.0.1
				||  Request.ECLIPSE_CLIENT_1_0_2.equals(clientString) // 1.0.2
				||  Request.ECLIPSE_CLIENT_1_0_3.equals(clientString) ||
					Request.ECLIPSE_CLIENT_1_0_5.equals(clientString) ||
					Request.ECLIPSE_CLIENT_1_0_7.equals(clientString))
			throw new IllegalArgumentException("თქვენი ვერსია ძალიან მოძველდა. გთხოვთ გადმოქაჩოთ უახლესი ვერსია.");

		// 1.10
		if (Request.ECLIPSE_CLIENT_1_0_10.equals(clientString))
			return new RequestProcessor();

		// nothing
		return null;
	}

}

