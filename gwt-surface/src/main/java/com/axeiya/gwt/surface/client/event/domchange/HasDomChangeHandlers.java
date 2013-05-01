package com.axeiya.gwt.surface.client.event.domchange;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasDomChangeHandlers extends HasHandlers {

	HandlerRegistration addDomChangeHandler(DomChangeHandler handler);
}
