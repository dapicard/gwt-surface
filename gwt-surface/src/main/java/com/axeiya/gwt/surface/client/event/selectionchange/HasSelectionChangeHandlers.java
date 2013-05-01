package com.axeiya.gwt.surface.client.event.selectionchange;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasSelectionChangeHandlers extends HasHandlers {

	HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler);
}
