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
package telasi.recutil.gui.widgets.spinner;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class SwingNumericSpinner extends Composite
{

static {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
		e.printStackTrace();
	}
}

private Composite composite1;

private JSpinner jSpinner1;

private Panel panel1;

private Frame frame1;

private int digits;

public SwingNumericSpinner(Composite parent, int style)
{
	super(parent, style);
	initGUI();
	setDigits(2);
}

private void initGUI()
{
	try {
		GridLayout thisLayout = new GridLayout();
		thisLayout.horizontalSpacing = 0;
		thisLayout.marginHeight = 0;
		thisLayout.marginWidth = 0;
		thisLayout.verticalSpacing = 0;
		this.setLayout(thisLayout);
		{
			GridData composite1LData = new GridData();
			composite1LData.horizontalAlignment = GridData.FILL;
			composite1LData.grabExcessHorizontalSpace = true;
			composite1LData.heightHint = 20;
			composite1 = new Composite(this, SWT.EMBEDDED);
			composite1.setLayoutData(composite1LData);
			{
				frame1 = SWT_AWT.new_Frame(composite1);
				{
					panel1 = new Panel();
					BorderLayout panel1Layout = new BorderLayout();
					panel1.setLayout(panel1Layout);
					frame1.add(panel1);
					{
						jSpinner1 = new JSpinner();
						panel1.add(jSpinner1, BorderLayout.CENTER);
					}
				}
			}
		}
		this.layout();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public int getDigits()
{
	return digits;
}

public void setDigits(int digits)
{
	this.digits = digits;
	JSpinner.NumberEditor editor1 = (JSpinner.NumberEditor) jSpinner1.getEditor();
	DecimalFormatterFactory factory = new DecimalFormatterFactory();
	factory.setDigits(digits);
	editor1.getTextField().setFormatterFactory(factory);
}

public void setMask(String mask)
{
	JSpinner.NumberEditor editor1 = (JSpinner.NumberEditor) jSpinner1.getEditor();
	DecimalFormatterFactory factory = new DecimalFormatterFactory();
	factory.setMask(mask);
	editor1.getTextField().setFormatterFactory(factory);
}

public boolean apply()
{
	try {
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) jSpinner1.getEditor();
		String text = editor.getTextField().getText();
		AbstractFormatter f = editor.getTextField().getFormatter();
		jSpinner1.setValue(f.stringToValue(text));
		return true;
	} catch (Exception e) {
		return false;
	}
}

public void setValue(Object value)
{
	this.jSpinner1.setValue(value);
}

public Object getValue()
{
	return jSpinner1.getValue();
}

public void setEnabled(boolean enabled)
{
	((JTextField) jSpinner1.getEditor().getComponent(0)).setEditable(enabled);
	jSpinner1.getComponent(0).setEnabled(enabled);
	jSpinner1.getComponent(1).setEnabled(enabled);
}

}
