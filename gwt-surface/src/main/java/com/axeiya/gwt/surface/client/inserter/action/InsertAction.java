package com.axeiya.gwt.surface.client.inserter.action;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.axeiya.gwt.surface.client.ranges.Range;
import com.axeiya.gwt.surface.client.ranges.SurfaceSelection;
import com.axeiya.gwt.surface.client.util.DOMUtil;

public abstract class InsertAction<E extends Element> {

	public void doAction(final E element, final SurfaceSelection selection) {
		onAction(element, selection);
		DOMUtil.cleanBranch(element);
		Scheduler.get().scheduleFinally(new Command() {
			@Override
			public void execute() {
				Range range = selection.getSelection().getRange();
				range.selectNode(element);
				selection.getAssociatedSurface().setSelection(range);
				selection.getAssociatedSurface().fireDomChange();
			}
		});
	}

	/**
	 * Action à effectuer sur l'élément de DOM passé en paramètre ; toute
	 * opération DOM est possible
	 * 
	 * @param element
	 *          Elément sur lequel effectuer l'action
	 */
	abstract public void onAction(E element, SurfaceSelection selection);

	/**
	 * Action à effectuer lors de la suppression
	 * 
	 * @param element
	 */
	public void onRevert(Element element, SurfaceSelection selection) {
	}

	/**
	 * Fourni un élément de référence (qui sera utilisé pour la récupération du
	 * tag HTML par exemple)
	 * 
	 * @return Elément de référence
	 */
	abstract public E getEmptyElement();
}
