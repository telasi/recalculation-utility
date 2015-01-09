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
 * Summary of difference details.
 * @author dimakura
 */
public class DiffSummary implements Serializable {
	private static final long serialVersionUID = -1379127600132350650L;
	private Date date;
	private List/* DiffDetail */details = new ArrayList();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List getDetails() {
		return details;
	}

	public void add(DiffDetail detail) {
		details.add(detail);
		detail.setSummary(this);
	}
}
