package com.axeiya.gwt.surface.client.event.domchange;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;

/**
 * L'arbre DOM de la surface d'édition a changé<br>
 * Cet évènement permet d'être notifié des changements de contenu sans
 * nécessiter de sérialisation DOM->HTML
 * 
 * @author Damien
 * 
 */
public class DomChangeEvent extends GwtEvent<DomChangeHandler> {

	private static Type<DomChangeHandler> TYPE;

	public static Type<DomChangeHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<DomChangeHandler>();
		}
		return TYPE;
	}

	private Element rootSurfaceElement;

	protected DomChangeEvent(Element rootSurfaceElement) {
		super();
		this.rootSurfaceElement = rootSurfaceElement;
	}

	public Element getRootSurfaceElement() {
		return rootSurfaceElement;
	}

	@Override
	protected void dispatch(DomChangeHandler handler) {
		handler.onDomChange(this);
	}

	@Override
	public GwtEvent.Type<DomChangeHandler> getAssociatedType() {
		return getType();
	}

	public static DomChangeEvent fire(HasDomChangeHandlers source, Element rootSurfaceElement) {
		DomChangeEvent event = new DomChangeEvent(rootSurfaceElement);
		source.fireEvent(event);
		return event;
	}

	public static void fire(HasDomChangeHandlers source, DomChangeEvent event) {
		source.fireEvent(event);
	}
}
