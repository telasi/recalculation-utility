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


import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcSubsidyAttachment;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class ViewUtils {

	public static void createCommonTable(TableViewer viewer) {

		TableColumn cols[] = viewer.getTable().getColumns();
		if (cols != null) {
			viewer.remove(viewer.getTable().getColumns());
			for (int i = 0; i < cols.length; i++) {
				cols[i].dispose();
				cols[i] = null;
			}
		}

		{
			TableColumn colImage = new TableColumn(viewer.getTable(), SWT.NONE);
			colImage.setWidth(20);
			colImage.setResizable(false);
		}
		{
			TableColumn colChangeImage = new TableColumn(viewer.getTable(),
					SWT.NONE);
			colChangeImage.setWidth(20);
			colChangeImage.setResizable(false);
		}
		{
			TableColumn colItemDate = new TableColumn(viewer.getTable(),
					SWT.NONE);
			colItemDate
					.setText(GUIMessages.getMessage("comp.general.itemdate"));
			colItemDate.setWidth(75);
		}
		{
			TableColumn colAccount = new TableColumn(viewer.getTable(),
					SWT.NONE);
			colAccount.setText(GUIMessages.getMessage("comp.general.account"));
			colAccount.setWidth(75);
		}
		{
			TableColumn colOperation = new TableColumn(viewer.getTable(),
					SWT.NONE);
			colOperation.setText(GUIMessages
					.getMessage("comp.general.operation"));
			colOperation.setWidth(200);
		}
		{
			TableColumn colCycle = new TableColumn(viewer.getTable(), SWT.NONE);
			colCycle.setWidth(20);
			colCycle.setResizable(false);
		}
		{
			TableColumn colReading = new TableColumn(viewer.getTable(),
					SWT.RIGHT);
			colReading.setText(GUIMessages.getMessage("comp.general.reading"));
			colReading.setWidth(75);
		}
		{
			TableColumn colKwh = new TableColumn(viewer.getTable(), SWT.RIGHT);
			colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
			colKwh.setWidth(75);
		}
		{
			TableColumn colGel = new TableColumn(viewer.getTable(), SWT.RIGHT);
			colGel.setText(GUIMessages.getMessage("comp.general.gel"));
			colGel.setWidth(75);
		}
		{
			TableColumn colBalance = new TableColumn(viewer.getTable(),
					SWT.RIGHT);
			colBalance.setText(GUIMessages.getMessage("comp.general.balance"));
			colBalance.setWidth(75);
		}
		// {
		// TableColumn colBalanceGap = new TableColumn(viewer.getTable(),
		// SWT.RIGHT);
		// colBalanceGap.setText(GUIMessages.getMessage("comp.general.balance_gap"));
		// colBalanceGap.setWidth(75);
		// }
		{
			TableColumn colSubacc = new TableColumn(viewer.getTable(), SWT.NONE);
			colSubacc
					.setText(GUIMessages.getMessage("comp.general.subaccount"));
			colSubacc.setWidth(75);
		}
		{
			TableColumn colEnterDate = new TableColumn(viewer.getTable(),
					SWT.NONE);
			colEnterDate.setText(GUIMessages
					.getMessage("comp.general.enterdate"));
			colEnterDate.setWidth(75);
		}
		{
			TableColumn colItemNumber = new TableColumn(viewer.getTable(),
					SWT.NONE);
			colItemNumber.setText(GUIMessages
					.getMessage("comp.general.document"));
			colItemNumber.setWidth(150);
		}

		viewer.getTable().layout(true);

	}

	public static class ItemLabelProvider extends LabelProvider implements
			ITableLabelProvider, ITableColorProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getItemImage(item);
			case 1:
				return item.isChanged() ? Plugin
						.getImage("icons/16x16/changes.png") : null;
			case 5:
				return item.getCycle() ? Plugin
						.getImage("icons/16x16/true.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 2:
				return Date.format(item.getItemDate());
			case 3:
				return item.getAccount().getNumber();
			case 4:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation()
						.getName());
			case 6:
				return nf.format(item.getReading());
			case 7:
				return nf.format(item.getKwh());
			case 8:
				return nf.format(item.getGel());
			case 9:
				return nf.format(item.getBalance());
				// case 10:
				// return nf.format(item.getBalanceGap());
			case 10:
				return item.getSubAccount() == null ? "" : new String(item
						.getSubAccount().getNumber());
			case 11:
				return Date.format(item.getEnterDate());
			case 12:
				return item.getItemNumber() == null ? "" : item.getItemNumber();
			default:
				return "";

			}
		}

		public Color getForeground(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (item.getStatus()) {
			case RecalcItem.DELETED:
				return Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
			case RecalcItem.OTHER:
				return Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
			case RecalcItem.NEW:
				return Display.getDefault()
						.getSystemColor(SWT.COLOR_DARK_GREEN);
			default:
				return null;
			}
		}

		public Color getBackground(Object element, int columnIndex) {
			return null;
		}

	}

	public static class ElEnergyLabelProvider extends ItemLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		private NumberFormat nf2 = new DecimalFormat("#,###.#####");

		public Image getColumnImage(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getItemImage(item);
			case 3:
				return item.getCycle() ? Plugin
						.getImage("icons/16x16/true.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 1:
				return Date.format(item.getItemDate());
			case 2:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation()
						.getName());
			case 4:
				return nf.format(item.getReading());
			case 5:
				return nf.format(item.getKwh());
			case 6:
				return GUIUtils.getCalculationHintName(item
						.getCalculationHint());
			case 7:
				return item.getMeter().toString();
			case 8:
				return nf.format(item.getMeterCoeff());
			case 9:
				return nf2.format(item.getMeterAcceleration()) + " %";
			default:
				return "";

			}
		}

		public Color getForeground(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			// case 6:
			// return item.isKwhLeavedUnchanged() ?
			// Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN) :
			// Display.getDefault().getSystemColor(SWT.COLOR_RED);
			case 7:
			case 8:
			case 9:
				return item.getMeterStatus() ? Display.getDefault()
						.getSystemColor(SWT.COLOR_DARK_GREEN) : Display
						.getDefault().getSystemColor(SWT.COLOR_RED);
			default:
				return null;
			}

		}

	}

	public static class SubsidyLabelProvider extends ItemLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getItemImage(item);
			case 3:
				return item.getCycle() ? Plugin
						.getImage("icons/16x16/true.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 1:
				return Date.format(item.getItemDate());
			case 2:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation()
						.getName());
			case 4:
				return nf.format(item.getKwh());
			case 5:
				return nf.format(item.getGel());
			case 6:
				RecalcSubsidyAttachment att = item.getSubsidyAttachment();
				String msg;
				if (att == null) {
					msg = GUIMessages
							.getMessage("comp.subsidy_view.lock_subsidy");
				} else {
					String amount = String.valueOf(att.getAmount());
					String count = String.valueOf(att.getCount());
					String unit = GUIUtils.getUnitName(att.getUnit());
					msg = GUIMessages.getMessage(
							"comp.subsidy_view.recalc_subsidy", new Object[] {
									amount, unit, count });
				}
				return msg;
			default:
				return "";

			}
		}

		public Color getForeground(Object element, int columnIndex) {
			switch (columnIndex) {
			case 6:
				RecalcItem item = (RecalcItem) element;
				return item.getSubsidyAttachment() == null ? Display
						.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN)
						: Display.getDefault().getSystemColor(SWT.COLOR_RED);
			default:
				return null;
			}

		}

	}

}
