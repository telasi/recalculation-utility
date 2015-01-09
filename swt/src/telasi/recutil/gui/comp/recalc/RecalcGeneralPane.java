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
package telasi.recutil.gui.comp.recalc;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class RecalcGeneralPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblMainTitle;
	private Label lblSeparator2;
	private Label lblId;
	private Text txtId;
	private Text txtCreateDate;
	private Label lblCreateDate;
	private Composite composite6;
	private Label lblSeparator3;
	private Text txtCustomer;
	private Combo cmbAccount;
	private Label lblAccount;
	private Label lblNumber;
	private Label lblAdditionalSubtitle;
	private Composite composite5;
	private Label lblCustomer;
	private Text txtNumber;
	private Composite composite4;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private Recalc recalc;
	private Label lblDescr;
	private Text txtDescr;

	public RecalcGeneralPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages.getMessage("comp.recalc_general_pane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
				}
			}
			{
				GridData lblSeparator1LData = new GridData();
				lblSeparator1LData.horizontalAlignment = GridData.FILL;
				lblSeparator1LData.grabExcessHorizontalSpace = true;
				lblSeparator1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator1.setLayoutData(lblSeparator1LData);
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/recalc.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.recalc_general_pane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				composite3Layout.marginTop = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblMainTitle = new Label(composite3, SWT.NONE);
					GridData lblMainTitleLData = new GridData();
					lblMainTitleLData.horizontalAlignment = GridData.FILL;
					lblMainTitleLData.grabExcessHorizontalSpace = true;
					lblMainTitle.setLayoutData(lblMainTitleLData);
					lblMainTitle.setText(GUIMessages
							.getMessage("comp.genera.main_properties"));
					lblMainTitle.setFont(GUIUtils
							.createSubtitleFont(lblMainTitle.getFont()));
				}
			}
			{
				GridData lblSeparator2LData = new GridData();
				lblSeparator2LData.horizontalAlignment = GridData.FILL;
				lblSeparator2LData.grabExcessHorizontalSpace = true;
				lblSeparator2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator2.setLayoutData(lblSeparator2LData);
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					lblNumber = new Label(composite4, SWT.NONE);
					lblNumber.setText(GUIMessages
							.getMessage("comp.general.number"));
				}
				{
					GridData txtNumberLData = new GridData();
					txtNumberLData.horizontalAlignment = GridData.FILL;
					txtNumberLData.grabExcessHorizontalSpace = true;
					txtNumber = new Text(composite4, SWT.BORDER | SWT.READ_ONLY);
					txtNumber.setLayoutData(txtNumberLData);
				}
				{
					lblCustomer = new Label(composite4, SWT.NONE);
					lblCustomer.setText(GUIMessages
							.getMessage("comp.general.customer"));
				}
				{
					GridData txtCustomerLData = new GridData();
					txtCustomerLData.horizontalAlignment = GridData.FILL;
					txtCustomerLData.grabExcessHorizontalSpace = true;
					txtCustomer = new Text(composite4, SWT.READ_ONLY
							| SWT.BORDER);
					txtCustomer.setLayoutData(txtCustomerLData);
				}
				{
					lblAccount = new Label(composite4, SWT.NONE);
					lblAccount.setText(GUIMessages
							.getMessage("comp.general.account"));
				}
				{
					GridData cmbAccountLData = new GridData();
					cmbAccountLData.horizontalAlignment = GridData.FILL;
					cmbAccountLData.grabExcessHorizontalSpace = true;
					cmbAccount = new Combo(composite4, SWT.READ_ONLY);
					cmbAccount.setLayoutData(cmbAccountLData);
				}
				{
					lblDescr = new Label(composite4, SWT.NONE);
					lblDescr.setText(GUIMessages.getMessage("comp.general.description"));
					GridData d1 = new GridData();
					d1.verticalAlignment = GridData.FILL;
					d1.grabExcessVerticalSpace = true;
					lblDescr.setLayoutData(d1);
					txtDescr = new Text(composite4, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
					GridData d2 = new GridData();
					d2.grabExcessHorizontalSpace = true;
					d2.horizontalAlignment = GridData.FILL;
					d2.heightHint = 50;
					txtDescr.setLayoutData(d2);
				}
			}
			{
				composite5 = new Composite(this, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.makeColumnsEqualWidth = true;
				composite5Layout.marginTop = 10;
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5.setLayoutData(composite5LData);
				composite5.setLayout(composite5Layout);
				{
					lblAdditionalSubtitle = new Label(composite5, SWT.NONE);
					GridData lblAdditionalSubtitleLData = new GridData();
					lblAdditionalSubtitleLData.horizontalAlignment = GridData.FILL;
					lblAdditionalSubtitleLData.grabExcessHorizontalSpace = true;
					lblAdditionalSubtitle
							.setLayoutData(lblAdditionalSubtitleLData);
					lblAdditionalSubtitle.setText(GUIMessages
							.getMessage("comp.genera.additional_properties"));
					lblAdditionalSubtitle
							.setFont(GUIUtils
									.createSubtitleFont(lblAdditionalSubtitle
											.getFont()));
				}
			}
			{
				GridData lblSeparator3LData = new GridData();
				lblSeparator3LData.horizontalAlignment = GridData.FILL;
				lblSeparator3LData.grabExcessHorizontalSpace = true;
				lblSeparator3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator3.setLayoutData(lblSeparator3LData);
			}
			{
				composite6 = new Composite(this, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.numColumns = 2;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					lblCreateDate = new Label(composite6, SWT.NONE);
					lblCreateDate.setText(GUIMessages
							.getMessage("comp.general.create_date"));
				}
				{
					GridData txtCreateDateLData = new GridData();
					txtCreateDateLData.horizontalAlignment = GridData.FILL;
					txtCreateDateLData.grabExcessHorizontalSpace = true;
					txtCreateDate = new Text(composite6, SWT.READ_ONLY
							| SWT.BORDER);
					txtCreateDate.setLayoutData(txtCreateDateLData);
				}
				{
					lblId = new Label(composite6, SWT.NONE);
					lblId.setText(GUIMessages.getMessage("comp.general.db_id"));
				}
				{
					GridData txtIdLData = new GridData();
					txtIdLData.horizontalAlignment = GridData.FILL;
					txtIdLData.grabExcessHorizontalSpace = true;
					txtId = new Text(composite6, SWT.READ_ONLY | SWT.BORDER);
					txtId.setLayoutData(txtIdLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
		reset();
	}

	private Map INDEX_ON_ACCOUNT = new HashMap();

	@SuppressWarnings("unchecked")
	private void reset() {
		cmbAccount.removeAll();
		txtNumber.setText("");
		txtCreateDate.setText("");
		txtCustomer.setText("");
		txtDescr.setText("");
		if (recalc != null) {
			txtNumber.setText(recalc.getNumber() == null ? "" : GUITranslator
					.GEO_ASCII_TO_KA(recalc.getNumber().trim()));
			Customer customer = recalc.getCustomer();
			Account account = recalc.getAccount();
			if (customer != null) {
				txtCustomer.setText(customer.getNumber());
				int selectionIndex = 0;
				for (int i = 0; i < customer.getAccounts().size(); i++) {
					Account acc = (Account) customer.getAccounts().get(i);
					INDEX_ON_ACCOUNT.put(new Integer(i), acc);
					cmbAccount.add(acc.getNumber());
					if (account != null && acc.equals(account)) {
						selectionIndex = i;
					}
				}
				cmbAccount.select(selectionIndex);
			}
			if (recalc.getId() > 0) {
				txtId.setText(String.valueOf(recalc.getId()));
			}
			txtCreateDate.setText(Date.format(recalc.getCreateDate()));
			txtDescr.setText(recalc.getDescription() == null 
					? "" : GUITranslator.GEO_ASCII_TO_KA(recalc.getDescription()));
		}
	}

	public Customer getCustomer() {
		return recalc == null ? null : recalc.getCustomer();
	}

	public Account getAccount() {
		if (recalc == null) {
			return null;
		}
		int index = cmbAccount.getSelectionIndex();
		if (index == -1) {
			return null;
		}
		return (Account) INDEX_ON_ACCOUNT.get(new Integer(index));
	}

	public String getNumber() {
		return txtNumber.getText();
	}

	public String getDescription() {
		return txtDescr.getText();
	}
	
	public void enableAccountChange(boolean enable) {
		cmbAccount.setEnabled(enable);
	}

}
