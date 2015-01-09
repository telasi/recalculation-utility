/*
 *   Copyright (C) 2006 by JSC Telasi
 *   http://www.telasi.ge
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the
 *   Free Software Foundation, Inc.,
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package telasi.recutil.gui.comp.recalc.charge_pane_views;


import java.util.List;

import org.eclipse.swt.widgets.Composite;

import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.gui.comp.recalc.RecalcChargePane;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class ChargePaneView extends Composite {
	private RecalcInterval interval;
	private RecalcChargePane parent;

	public RecalcInterval getInterval() {
		return interval;
	}

	public void setInterval(RecalcInterval interval) {
		this.interval = interval;
		validateView();
	}

	public ChargePaneView(Composite parent, int style) {
		super(parent, style);
	}

	public void displayItems(List items) {
	}

	public void clear() {
	}

	public void validateView() {
	}

	public RecalcChargePane getParentPane() {
		return parent;
	}

	public void setParentPane(RecalcChargePane parent) {
		this.parent = parent;
	}

	public long getSelectedId() {
		return -1;
	}

	public void selectById(long id) {
	}

	public long[] getSelectedIds() {
		return null;
	}

	public void selectById(long[] ids) {
	}

}
