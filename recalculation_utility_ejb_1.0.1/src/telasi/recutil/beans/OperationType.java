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
package telasi.recutil.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Operation.
 * @author dimakura
 */
public class OperationType implements Serializable {
	private static final long serialVersionUID = -3569227179445653111L;
	//
	// type IDs
	//
	public static final int READING = 1;
	public static final int CHARGE = 2;
	public static final int PAYMENT = 3;
	public static final int VOUCHER = 4;
	public static final int SUBSIDY = 5;
	public static final int ADD_CHARGE = 6;
	public static final int PENALTY = 7;
	public static final int SUBACC_TRANSIT = 8;
	public static final int SERVICE = 9;
	public static final int POWER_OPERATION = 10;
	public static final int SUBACC_CHARGE = 11;
	public static final int DEBT_RESTRUCTURIZATION = 12;
	public static final int TRANSIT = 13;
	public static final int COMPENSATION = 14;
	public static final int AUDIT_READING = 15;
	public static final int USAID6_CORRECTION = 16;
	public static final int NETWORK_OPERATIONS = 17;

	private int id;
	private String name;
	private List/*Operation*/ operations;

	//
	// impelementation of IOperationType
	//
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getOperations() {
		return operations;
	}

	//
	// additional procedures
	//

	public void addOperation(Operation operation) {
		if (operations == null) {
			operations = new ArrayList();
		}
		if (!operations.contains(operation)) {
			operations.add(operation);
			operation.setType(this);
		}
	}

	public boolean removeOperation(Operation operation) {
		if (operations != null && operations.contains(operation)) {
			boolean resp = operations.remove(operation);
			operation.setType(null);
			return resp;
		}
		return false;
	}

	//
	// standard operations
	//

	public boolean equals(Object o) {
		if (!(o instanceof OperationType))
			return false;
		OperationType type = (OperationType) o;
		return type.getId() == id;
	}

}
