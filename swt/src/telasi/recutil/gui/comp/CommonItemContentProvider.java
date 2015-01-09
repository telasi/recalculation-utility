/**
 * 
 */
package telasi.recutil.gui.comp;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This is a common table item provider.
 * 
 * @author dimitri
 */
public class CommonItemContentProvider implements IStructuredContentProvider {

	public List items;

	public CommonItemContentProvider(List items) {
		this.items = items;
	}

	public List getFullItems() {
		return items;
	}

	public Object[] getElements(Object inputElement) {
		return items == null ? new Object[] {} : items.toArray();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}