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
 * User role.
 * @author dimakura
 */
public class Role implements Serializable {

	private static final long serialVersionUID = -2168622971140673118L;

	public String toString() {
		return name;
	}

	private long id;

	private String name, description;

	private List actions;

	private List users;

	private boolean enabled;

	public Role(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List getActions() {
		return actions;
	}

	public void addAction(int actionId) {
		if (actions == null) {
			actions = new ArrayList();
		}
		Integer action = new Integer(actionId);
		if (!actions.contains(action)) {
			actions.add(action);
		}
	}

	public List getUsers() {
		return users;
	}

	public void addUser(User user) {
		if (users == null) {
			users = new ArrayList();
		}
		if (!users.contains(user)) {
			users.add(user);
		}
		user.setRole(this);
	}

	public void removeUser(User user) {
		if (users != null && users.contains(user)) {
			users.remove(user);
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof Role)) {
			return false;
		}
		Role role = (Role) o;
		return id == role.getId();
	}

}
