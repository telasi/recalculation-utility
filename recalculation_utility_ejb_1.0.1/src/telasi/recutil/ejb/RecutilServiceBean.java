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

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import telasi.recutil.service.RequestProcessorFactory;

/**
 * @ejb.bean name="RecutilService" display-name="RecutilService"
 *           description="RecutilService" jndi-name="ejb/RecutilService"
 *           type="Stateless" view-type="remote"
 */
public class RecutilServiceBean implements SessionBean {

	private static final long serialVersionUID = -3118355117041767580L;

	public RecutilServiceBean() {
		super();
	}

	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
	}

	public void ejbRemove() throws EJBException, RemoteException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
	}

	//
	// BUSSINESS Methods
	//

	/**
	 * Process signle request.
	 * 
	 * @ejb.interface-method view-type = "remote"
	 */
	public Response processEJBRequest(Request request) throws RequestException {

		// empty request
		if (request == null || request.getProperties() == null || request.getProperties().isEmpty()) {
			throw new RequestException("Empty request");
		}

		// get client string
		Object clnt = request.getProperties().get(Request.CLIENT_STRING);
		if (clnt == null)
			throw new RequestException("Application client string not defined");
		String clntString = clnt.toString();
		if (clntString.trim().length() == 0)
			throw new RequestException("Empty application client string");

		// request processor
		IRequestProcessor processor = RequestProcessorFactory.createRequestProcessor(clntString);

		// process request and return response
		return processor.processRequest(request);

	}

	/**
	 * Process requests.
	 * 
	 * @ejb.interface-method view-type = "remote"
	 */
	public List processDatabaseRequests(List requests) throws RequestException {

		// empty request
		if (requests == null || requests.isEmpty()) {
			throw new RequestException("Empty request list");
		}

		// get first request element
		Request request = (Request) requests.get(0);

		// empty request
		if (request.getProperties() == null || request.getProperties().isEmpty()) {
			throw new RequestException("Empty request");
		}

		// get client string
		Object clnt = request.getProperties().get(Request.CLIENT_STRING);
		if (clnt == null) {
			throw new RequestException("Application client string not defined");
		}
		String clntString = clnt.toString();
		if (clntString.trim().length() == 0) {
			throw new RequestException("Empty application client string");
		}

		// request processor
		IRequestProcessor processor = RequestProcessorFactory.createRequestProcessor(clntString);

		// process request and return response
		return processor.processRequests(requests);
	}

}
