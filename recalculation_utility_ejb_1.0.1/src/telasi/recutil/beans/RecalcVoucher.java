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
 * Voucher.
 * @author dimakura
 */
public class RecalcVoucher implements Serializable {
	private static final long serialVersionUID = 4420738396593950666L;
	private List/*String[]*/ properties = new ArrayList();
	private List/*DiffDetail*/ details;

	public List getDetails() {
		return details;
	}

	public void setDetails(List details) {
		this.details = details;
	}

	public List getProperties() {
		return properties;
	}

}
