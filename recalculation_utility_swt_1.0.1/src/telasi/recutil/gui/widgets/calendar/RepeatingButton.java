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
package telasi.recutil.gui.widgets.calendar;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * Push button that repeats selection event based on timer.
 */
@SuppressWarnings("unchecked")
public class RepeatingButton extends Button {
	public static final int DEFAULT_INITIAL_REPEAT_DELAY = 200; // Milliseconds
	public static final int DEFAULT_REPEAT_DELAY = 50; // Milliseconds
	private int initialRepeatDelay = DEFAULT_INITIAL_REPEAT_DELAY;
	private int repeatDelay = DEFAULT_REPEAT_DELAY;
	private ArrayList selectionListeners = new ArrayList(3);
	private Repeater repeater;

	/**
	 * @param parent
	 *            Parent container.
	 * @param style
	 *            Button style.
	 */
	public RepeatingButton(Composite parent, int style) {
		super(parent, style);
		addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				cancelRepeater();

				if (event.button == 1) { // Left click
					buttonPressed(event.stateMask, event.time);

					repeater = new Repeater(event.stateMask);
					getDisplay().timerExec(initialRepeatDelay, repeater);
				}
			}

			public void mouseUp(MouseEvent event) {
				if (event.button == 1) { // Left click
					cancelRepeater();
				}
			}
		});

		addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent e) {
				cancelRepeater();
			}
		});
	}

	public void addSelectionListener(SelectionListener listener) {
		selectionListeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		selectionListeners.remove(listener);
	}

	/**
	 * @return Returns the initial repeat delay in milliseconds.
	 */
	public int getInitialRepeatDelay() {
		return initialRepeatDelay;
	}

	/**
	 * @param initialRepeatDelay
	 *            The new initial repeat delay in milliseconds.
	 */
	public void setInitialRepeatDelay(int initialRepeatDelay) {
		this.initialRepeatDelay = initialRepeatDelay;
	}

	/**
	 * @return Returns the repeat delay in millisecons.
	 */
	public int getRepeatDelay() {
		return repeatDelay;
	}

	/**
	 * @param repeatDelay
	 *            The new repeat delay in milliseconds.
	 */
	public void setRepeatDelay(int repeatDelay) {
		this.repeatDelay = repeatDelay;
	}

	private void buttonPressed(int stateMask, int time) {
		SelectionListener[] listeners = new SelectionListener[selectionListeners
				.size()];
		selectionListeners.toArray(listeners);
		for (int i = 0; i < listeners.length; i++) {
			SelectionListener l = listeners[i];
			Event event = new Event();
			event.type = SWT.Selection;
			event.display = getDisplay();
			event.widget = this;
			event.stateMask = stateMask;
			event.time = time;
			l.widgetSelected(new SelectionEvent(event));
		}
	}

	private void cancelRepeater() {
		if (repeater != null) {
			repeater.cancel();
			repeater = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Widget#checkSubclass()
	 */
	protected void checkSubclass() {
	}

	private class Repeater implements Runnable {
		private boolean canceled;

		private int stateMask;

		public Repeater(int stateMask) {
			super();
			this.stateMask = stateMask;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			if (!canceled) {
				buttonPressed(stateMask, (int) System.currentTimeMillis());

				getDisplay().timerExec(repeatDelay, this);
			}
		}

		public void cancel() {
			canceled = true;
		}
	}
}